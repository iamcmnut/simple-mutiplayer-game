(() => {
    class Player {
        constructor(id, posX, posY) {
            this.id = id;
            this.posX = posX;
            this.posY = posY;
            this.step = 0;
        }
    }

    var players = {};
    var ws = new SockJS('/game');

    function keyDownHandler(key) {
        let k = "";
        // arrow key LEFT
        if (key.keyCode == 37) {
            k = "left";
        }

        // arrow key UP
        else if (key.keyCode == 38) {
            //k = "up";
        }

        // arrow key RIGHT
        else if (key.keyCode == 39) {
        k = "right";
        }

        // arrow key DOWN
        else if (key.keyCode == 40) {
        //k = "down";
        }
        ws.send(JSON.stringify({type: "move:" + k, data: "okay"}));
    }

    function renderBg(bgArr) {

    }

    function renderPlayers() {
        let keys = Object.keys(players);
        for (let k of keys) {
            ctx.beginPath();
            ctx.strokeStyle = "#FFFFFF";
            ctx.rect(players[k].posX, players[k].posY, 10, 10);
            ctx.stroke();
        }
    }

    function animator() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        renderBg(bg);
        renderPlayers(players);

        window.requestAnimationFrame(animator);
    }

    function wsOnMessageHandler(msg) {
        let m = JSON.parse(msg.data)
        if (m.msgType == "player:all") {
            for (let p of m.data) {
                players[p.id] = new Player(p.id, p.posX, p.posY);
            }
        } else if (m.msgType == "player:out") {
            let id = m.data;
            delete players[id];
        }
    }


    function wsOnCloseHandler() {
        //TODO: implement this function
    }

    function wsOnOpenHandler() {

    }

    ws.onmessage = wsOnMessageHandler;
    ws.onclose = wsOnCloseHandler;
    ws.onopen = wsOnOpenHandler;

    let canvas = document.getElementById("mycanvas");
    canvas.width = 730;
    canvas.height = 364;
    let ctx = canvas.getContext("2d");
    let bg = [[0, 1, 2], [0, 0, 0]];

    window.addEventListener("keydown", keyDownHandler);
    animator();
})();