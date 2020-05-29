package ai.thecoder.archerena.controller

import ai.thecoder.archerena.service.PlayerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class MainController {
    @Autowired
    lateinit var playerService: PlayerService

    @GetMapping("/")
    fun index(): String {
        return "index"
    }
}