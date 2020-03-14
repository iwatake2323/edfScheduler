package iwatake.com.edf.service

import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import iwatake.com.edf.csv.Holiday
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service
import java.nio.charset.Charset
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class HolidayService {
    fun get(): List<Holiday> {
        val holidayCsv = ClassPathResource("holiday\\syukujitsu.csv")
                .inputStream
                .bufferedReader(Charset.forName("SJIS"))

        val javaTimeModule = JavaTimeModule().addDeserializer(
                LocalDate::class.java,
                LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy/M/d"))
        )
        val reader = CsvMapper()
                .registerModule(javaTimeModule)
                .readerFor(Holiday::class.java)
                .with(CsvSchema.emptySchema().withHeader())

        return reader
                .readValues<Holiday>(holidayCsv)
                .readAll()
                .toList()
    }

    fun getSet(): Set<LocalDate> {
        return get().map(Holiday::date).toSet()
    }
}
