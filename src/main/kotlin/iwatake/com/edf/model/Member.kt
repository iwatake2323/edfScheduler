package iwatake.com.edf.model

import java.time.LocalDate

data class Member(
        val name: String = "member",
        val tasks: MutableList<Task> = mutableListOf(),
        var end: LocalDate = LocalDate.now()
) {
}