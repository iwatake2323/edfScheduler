package iwatake.com.edf.service

import iwatake.com.edf.model.Job
import iwatake.com.edf.model.Member
import iwatake.com.edf.model.Task
import org.springframework.stereotype.Service

@Service
class SchedulerService {
    fun schedule(job: Job): Job {
        val start = job.start
        var tasks = job.tasks.toMutableList()
        val members = job.members

        while (tasks.isNotEmpty()) {
            val mostCritical = findMostCriticalTask(tasks)
            val member = findOptimalMember(members, mostCritical)
            tasks.remove(mostCritical)
            assignTaskToMember(member, mostCritical)
            resolveDependency(mostCritical, tasks)
        }

        return job
    }

    private fun findMostCriticalTask(tasks: List<Task>): Task {
        println(tasks)
        return tasks
                .filter { it.dependencyCount == 0 }
                .minBy { it.deadline.minusDays(it.duration.toLong()) }
                ?: throw IllegalArgumentException("tasklist empty!")
    }

    private fun findOptimalMember(members: List<Member>, task: Task): Member {
        return members.minBy { it.end.plusDays(task.duration.toLong()) }
                ?: throw IllegalArgumentException("memberlist empty!")
    }

    private fun assignTaskToMember(member: Member, task: Task) {
        member.end = member.end.plusDays(task.duration.toLong())
        member.tasks.add(task)
    }

    private fun resolveDependency(assignedTask: Task, tasks: List<Task>) {
        tasks.filter { it.dependencies.contains(assignedTask.id) }
                .forEach { it.dependencyCount-- }
    }
}