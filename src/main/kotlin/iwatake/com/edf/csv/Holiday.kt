package iwatake.com.edf.csv

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class Holiday(
        @JsonProperty("国民の祝日・休日月日")
        val date: LocalDate,
        @JsonProperty("国民の祝日・休日名称")
        val name: String
)
