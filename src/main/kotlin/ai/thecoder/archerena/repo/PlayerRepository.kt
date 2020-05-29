package ai.thecoder.archerena.repo

import ai.thecoder.archerena.model.Player
import org.springframework.data.repository.CrudRepository

interface PlayerRepository : CrudRepository<Player, String> {

}