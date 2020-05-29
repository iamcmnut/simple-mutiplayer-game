package ai.thecoder.archerena.ws

import kotlin.random.Random
import ai.thecoder.archerena.model.Player
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.handler.TextWebSocketHandler

class Message(val msgType: String, val data: Any)

class GameWsHandler: TextWebSocketHandler() {
    val sessionList = HashMap<WebSocketSession, Player>()
    var moveStep = 10

    @Throws(Exception::class)
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessionList -= session
        broadcast(Message("player:out", session.id))
    }

    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val json = ObjectMapper().readTree(message.payload)
        when (json.get("type").asText()) {
            "move:left" -> {
                var player : Player? = sessionList[session]
                sessionList[session] = player?.moveLeft(moveStep)!!
                sendPlayers()
            }
            "move:right" -> {
                var player = sessionList[session]
                sessionList[session] = player?.moveRight(moveStep)!!
                sendPlayers()
            }
            "move:up" -> {
                var player = sessionList[session]
                sessionList[session] = player?.moveUp(moveStep)!!
                sendPlayers()
            }
            "move:down" -> {
                var player = sessionList[session]
                sessionList[session] = player?.moveDown(moveStep)!!
                sendPlayers()
            }
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        var rand = Random(System.nanoTime()).nextInt(4 - 1 + 1) + 1
        val player = Player(session.id, 240, 154, rand)
        sessionList.put(session, player)
        sendPlayers()
    }

    fun sendPlayers() {
        var players = ArrayList<Player>()
        sessionList.forEach {
            players.add(it.value)
        }
        broadcast(Message("player:all", players))
    }

    fun emit(session: WebSocketSession, msg: Message) = session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsString(msg)))
    fun broadcast(msg: Message) = sessionList.forEach { emit(it.key, msg) }
}

@Configuration @EnableWebSocket
class WSConfig : WebSocketConfigurer {
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(GameWsHandler(), "/game").withSockJS()
    }
}