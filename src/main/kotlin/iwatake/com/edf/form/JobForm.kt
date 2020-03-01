package iwatake.com.edf.form

import java.time.LocalDate

data class JobForm(
        val tasks: List<TaskForm> = listOf(),
        val members: List<MemberForm> = listOf(MemberForm()),
        val startDate: LocalDate = LocalDate.now()
)

