// Packages
const express = require("express")
const parser = require("body-parser")
const os = require('os');
const networkInterface = os.networkInterfaces()[Object.keys(os.networkInterfaces())[0]];
const address = networkInterface[1][Object.keys(networkInterface[1])[0]];
const fs = require('fs');
const path = require("path")
const {v4: uuidv4} = require('uuid')
const Logic = require(path.join(__dirname, 'ServerFiles', 'logic.js'))

// Server Data
let serverData = JSON.parse(fs.readFileSync("ServerFiles/data.json"));

// Client Data
let clients = {}

// Game Logic
let logic;
let gameState = false;

const port = 8080
const app = express();
app.use("/static", express.static(path.join(__dirname, "public")));
app.use(parser.json());
app.use(express.static(__dirname)); // Allows external css and js files
app.listen(port, function() { console.log("Join Game URL:", address + ":" + String(port)) });


app.get("/", function(req, res) {
    // Send the html file over
    res.sendFile("index.html", {root: __dirname});
});

app.get("/start", function(req, res) {
    if (!gameState) { // Make sure game isn't currently running
        let id = req.query.userId;
        let state = req.query.state;
        if (false) { // Enough people have voted
            logic = new Logic(clients);
            logic.StartGame();
            gameState = true;
            console.log("LET THE GAME BEGIN!");
            /// PUSH DATA TO USERS THROUGH EVENT STREAM (game has started)
        }
    }
    res.end();
});

app.post('/play', function(req, res) { // Play a card
    if (gameState) {
        let userId = req.body.userId;
        if (!(userId in clients)) {
            res.end();
            return;
        }

        /// MANIPULATE LOGIC DATA VIA METHODS IN THE LOGIC
        let cardIndex = req.body.cardIndex;

        ///
        res.end();
    }
    else {
        res.end();
    }
});

app.post("/draw", function(req, res) { // Draw a card
    if (gameState) {
        let userId = req.body.userId;
        if (!(userId in clients)) {
            res.end();
            return;
        }

        /// MANIPULATE LOGIC DATA VIA METHODS IN THE LOGIC

        ///
        res.end();
    }
    else {
        res.end();
    }
});

function sendEvent(type, id) {
    if (type == 2) { // Send personal id to specified client and send usernames to all clients
        let identification = JSON.stringify({playerId: id});
        clients[id].response.write(`data: ${identification}\n\n`);
    }
    else if (type == 1 && !gameState) { // Send menu display info only if game isn't running
        let names = JSON.stringify({names: clientNames});
        for (let client in clients) {
            clients[client].response.write(`data: ${names}\n\n`);
        }
    }
    else if (gameState) { // Otherwise, send to all clients
        // Check game/session state
        gameState = logic.sessionState;
        // Data to be distributed to everyone
        let packet = JSON.stringify(GrabData());
        for(let client in clients){
            clients[client].response.write(`data: ${packet}\n\n`);
        }
    }
}
app.get("/events", function(req, res) { // Store client as a player when they subscribe to event stream
    res.setHeader('Cache-Control', 'no-cache');
    res.setHeader('Content-Type', 'text/event-stream');
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Connection', 'keep-alive');
    res.flushHeaders(); 

    /// ADD req.body.username TO CLIENT DICTIONARY

    let client = {
        id: uuidv4(),
        response: res
    }
    clients[client.id] = client; // Add client to dictionary of clients
    console.log("Somebody joined the game");

    sendEvent(2, client.id); // Send client their personal id
    sendEvent(1); // Update display info

    res.on('close', () => {
        console.log("Somebody disconnected");
        clientNames.splice(Object.keys(clients).indexOf(client.id), 1);
        delete clients[client.id];
        if (gameState) { // Logic exists
            logic.DisconnectPlayer(client.id);
        }
        sendEvent(1); // Update display info
        if (Object.keys(clients).length == 0) { // Everyone disconnected
            gameState = false;
        }
        res.end();
    })
})

function GrabData() {
    let playersData = logic.players; // Send over data of the players
    /// ADD MORE DATA AS NEEDED
    const logicData = {
        players: playersData,
        state: gameState
    }
    return logicData;
}

function LogClients() { // Debugging purposes only
    console.log("LOGGING CLIENTS");
    for (let client in clients) {
        console.log(client);
    }
    console.log(clientNames);
    console.log("END COMMUNICATION");
}