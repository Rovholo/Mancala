const getGameState = () => {
    // document.getElementById("demo").innerHTML = "Paragraph changed. haha";
};

(function(){

    const queryString = window.location.search;
    console.log(queryString);
    const urlParams = new URLSearchParams(queryString);
    const product = urlParams.get('product')
    console.log(product);

    setInterval(getGameState, 500);
})();