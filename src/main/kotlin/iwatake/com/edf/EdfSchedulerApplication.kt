package iwatake.com.edf

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class EdfSchedulerApplication

fun main(args: Array<String>) {
    runApplication<EdfSchedulerApplication>(*args)
}
