package ai.thecoder.archerena.model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias

@TypeAlias("player")
data class Player(@Id val id: String, val posX: Int = 100, val posY: Int = 250, val character: Int = 1) {

    fun moveLeft(pixel: Int) =
            Player(id, posX - pixel, posY, character)

    fun moveRight(pixel: Int) =
            Player(id, posX + pixel, posY, character)

    fun moveUp(pixel: Int) =
            Player(id, posX, posY - pixel, character)

    fun moveDown(pixel: Int) =
            Player(id, posX, posY + pixel, character)
}