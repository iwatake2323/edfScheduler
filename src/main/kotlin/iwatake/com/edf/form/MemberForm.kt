package iwatake.com.edf.form

import java.time.LocalDate

data class MemberForm(
        val name: String = "member",
        val from: LocalDate = LocalDate.MIN,
        val to: LocalDate = LocalDate.MAX
) {
}