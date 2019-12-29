function showNav() {
    document.getElementById("toggle-nav").style.transform = "translateX(100%)";
    document.getElementById("side-nav").style.transform = "translateX(0)";
    // document.getElementById("main-container").style.marginRight="250px";
}

function closeNav() {
    document.getElementById("side-nav").style.transform = "translateX(100%)";
    document.getElementById("toggle-nav").style.transform = "translateX(0)";
    document.getElementById("main-container").style.marginRight="0";
}