package iwatake.com.edf.form

import java.time.LocalDate

data class TaskForm(
        val name: String = "task",
        val duration: Int = 1,
        val deadline: LocalDate = LocalDate.now(),
        val dependencies: List<String>
)