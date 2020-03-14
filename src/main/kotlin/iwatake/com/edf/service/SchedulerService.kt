package iwatake.com.edf.service

import iwatake.com.edf.model.Job
import iwatake.com.edf.model.Member
import iwatake.com.edf.model.Task
import org.springframework.stereotype.Service
import java.time.DayOfWeek
import java.time.LocalDate

@Service
class SchedulerService {
    fun schedule(job: Job): Job {
        var tasks = job.tasks.toMutableList()
        val members = job.members

        while (tasks.isNotEmpty()) {
            val assignable = members.filter { it.availableFrom == members.map(Member::availableFrom).min() }
            val mostCritical = selectTask(tasks, assignable.first().availableFrom)
            val member = findOptimalMember(assignable, mostCritical)
            tasks.remove(mostCritical)
            assignTaskToMember(member, mostCritical, job.holidays, job.businessDays)
            resolveDependency(mostCritical, tasks)
            println(job.members)
        }

        return job
    }

    private fun selectTask(tasks: List<Task>, targetDate: LocalDate): Task {
        val resolvedTasks = tasks.filter { it.dependencyCount == 0 }
        val startableTasks = resolvedTasks.filter { it.startable <= targetDate }
        return if(startableTasks.isEmpty()) {
            resolvedTasks
        } else {
            startableTasks
        }
                .minBy { it.deadline.minusDays(it.duration.toLong()) }
                ?: throw IllegalArgumentException("tasklist empty!")
    }

    private fun findOptimalMember(members: List<Member>, task: Task): Member {
        return members.minBy { it.availableFrom.plusDays(task.duration.toLong()) }
                ?: throw IllegalArgumentException("memberlist empty!")
    }

    private fun assignTaskToMember(member: Member, task: Task, holidays: Set<LocalDate>, businessDays: Set<DayOfWeek>) {
        task.start = findBusinessDay(
                when {
                    member.availableFrom > task.startable -> member.availableFrom
                    else -> task.startable
                },
                1,
                holidays,
                businessDays
        )
        task.end = findBusinessDay(
                task.start!!,
                task.duration,
                holidays,
                businessDays
        )
        member.availableFrom = task.end!!.plusDays(1L)
        member.tasks.add(task)
    }

    private fun resolveDependency(assignedTask: Task, tasks: List<Task>) {
        tasks.filter { it.dependencies.contains(assignedTask.id) }
                .forEach {
                    it.dependencyCount--
                    if (it.startable == null) {
                        it.startable = assignedTask.end!!.plusDays(1L)
                    } else if (it.startable < assignedTask.end) {
                        it.startable = assignedTask.end!!.plusDays(1L)
                    }
                }
    }

    private fun findBusinessDay(from: LocalDate, duration: Int, holidays: Set<LocalDate>, businessDays: Set<DayOfWeek>): LocalDate {
        var dayCount = 0
        var date = from
        while (dayCount < duration) {
            if (businessDays.contains(date.dayOfWeek) && !holidays.contains(date)) {
                dayCount++
            }
            if (dayCount < duration) date = date.plusDays(1L)
        }
        return date
    }
}
