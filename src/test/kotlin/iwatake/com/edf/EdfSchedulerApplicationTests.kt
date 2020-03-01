package iwatake.com.edf

import iwatake.com.edf.controller.SchedulerController
import iwatake.com.edf.model.Job
import iwatake.com.edf.model.Member
import iwatake.com.edf.model.Task
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate

@SpringBootTest
class EdfSchedulerApplicationTests(
        @Autowired private val schedulerController: SchedulerController
) {
    val start: LocalDate = LocalDate.of(2020, 3, 1)
    val deadline: LocalDate = LocalDate.of(2020, 3, 10)

    @Test
    fun testSchedulerEmptyJob() {
        val job = Job()
        val after = schedulerController.schedule(job)
        println(after.members)
    }

    @Test
    fun testScheduler2Member2Task() {
        val job = Job(
                members = listOf(Member(name = "john"), Member(name = "smith")),
                tasks = listOf(
                        Task(id = 0, name = "Develop module A", duration = 1, deadline = deadline),
                        Task(id = 1, name = "Develop module B", duration = 2, deadline = deadline)
                ),
                start = start
        )
        val after = schedulerController.schedule(job)
        println(after.members)
    }

    @Test
    fun testSchedulerWithDependency() {
        val job = Job(
                members = listOf(Member(name = "john"), Member(name = "smith")),
                tasks = listOf(
                        Task(id = 0, name = "Develop module A", duration = 2, deadline = deadline),
                        Task(id = 1, name = "Develop module B", duration = 1, deadline = deadline),
                        Task(id = 2, name = "Test module A", duration = 3, deadline = deadline, dependencies = listOf(0)),
                        Task(id = 3, name = "Test module B", duration = 5, deadline = deadline, dependencies = listOf(1)),
                        Task(id = 4, name = "Integration test", duration = 10, deadline = deadline, dependencies = listOf(2, 3))
                ),
                start = start
        )
        val after = schedulerController.schedule(job)
        println(after.members)

    }

}
