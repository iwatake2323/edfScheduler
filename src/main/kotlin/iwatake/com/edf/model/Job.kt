package iwatake.com.edf.model

import iwatake.com.edf.service.HolidayService
import java.time.DayOfWeek
import java.time.LocalDate

data class Job(
        val tasks: List<Task> = listOf(Task(id = 0)),
        val members: List<Member> = listOf(Member()),
        val start: LocalDate = LocalDate.now(),
        val holidays: Set<LocalDate> = HolidayService().getSet(),
        val businessDays: Set<DayOfWeek> = setOf(
                DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY,
                DayOfWeek.WEDNESDAY,
                DayOfWeek.THURSDAY,
                DayOfWeek.FRIDAY
        )
)
