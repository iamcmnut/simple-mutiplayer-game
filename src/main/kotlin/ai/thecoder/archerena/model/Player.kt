package ai.thecoder.archerena.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias

@TypeAlias("player")
data class Player(@Id val id: String, val posX: Int = 100, val posY: Int = 250) {

    fun moveLeft(pixel: Int) =
            Player(id, posX - pixel, posY)

    fun moveRight(pixel: Int) =
            Player(id, posX + pixel, posY)
}