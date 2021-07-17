
function logout() {
    localStorage.removeItem("user");
}

function setSpanUserName() {
    document.getElementById("span-username").innerText = localStorage.getItem("user");
}