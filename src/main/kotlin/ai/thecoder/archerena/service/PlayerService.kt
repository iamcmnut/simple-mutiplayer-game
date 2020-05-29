package ai.thecoder.archerena.service

import ai.thecoder.archerena.model.Player
import ai.thecoder.archerena.repo.PlayerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface PlayerService {
    fun moveLeft(id: String, pixel: Int): Player
    fun moveRight(id: String, pixel: Int): Player
}

@Service("playerService")
class PlayerServiceImpl : PlayerService {
    @Autowired
    lateinit var playerRepository: PlayerRepository

    override fun moveLeft(id: String, pixel: Int): Player {
        val player = playerRepository.findById(id).orElse(Player(id))
        player.moveLeft(pixel)
        playerRepository.save(player)
        return player
    }

    override fun moveRight(id: String, pixel: Int): Player {
        val player = playerRepository.findById(id).orElse(Player(id))
        player.moveRight(pixel)
        playerRepository.save(player)
        return player
    }
}