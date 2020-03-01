package iwatake.com.edf.model

import java.time.LocalDate

data class Job(
        val tasks: List<Task> = listOf(Task(id = 0)),
        val members: List<Member> = listOf(Member()),
        val start: LocalDate = LocalDate.now()
)