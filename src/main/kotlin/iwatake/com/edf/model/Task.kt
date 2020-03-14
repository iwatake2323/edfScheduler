package iwatake.com.edf.model

import java.time.LocalDate

data class Task(
        val id: Int?,
        val name: String = "task",
        val duration: Int = 1,
        val deadline: LocalDate = LocalDate.now(),
        val dependencies: List<Int> = listOf(),
        var dependencyCount: Int = dependencies.count(),
        var startable: LocalDate = LocalDate.of(0, 1,1),
        var start: LocalDate? = null,
        var end: LocalDate? = null
)
