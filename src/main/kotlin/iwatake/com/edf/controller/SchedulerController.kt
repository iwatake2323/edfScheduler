package iwatake.com.edf.controller

import iwatake.com.edf.model.Job
import iwatake.com.edf.service.SchedulerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SchedulerController(
        @Autowired private val service: SchedulerService
) {

    @PostMapping("/schedule")
    fun schedule(@RequestBody job: Job): Job {
        return service.schedule(job)
    }
}