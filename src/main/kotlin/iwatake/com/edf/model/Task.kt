package iwatake.com.edf.model

import java.time.LocalDate

data class Task(
        val id: Int?,
        val name: String = "task",
        val duration: Int = 1,
        val deadline: LocalDate = LocalDate.now(),
        val start: LocalDate = LocalDate.now(),
        val dependencies: List<Int> = listOf(),
        var dependencyCount: Int = dependencies.count()
)