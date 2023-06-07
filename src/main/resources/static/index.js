function myFunction() {
    window.location.href="/game/game.html"
}

function joinGame() {
    const val =  document.getElementById('nameField').value;
    window.location.href = "/game/game.html?id=" + val;
}