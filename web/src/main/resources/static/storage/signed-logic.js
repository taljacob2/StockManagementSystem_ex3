
// window.addEventListener("pageshow", () => {
//     checkUserExistence();
// });
//
//
// function checkUserExistence() {
//
//     // check if this user is signed in.
//     if (localStorage.getItem("user") === null) {
//         console.log("user doesnt exist");
//         location.replace("home");
//     }
//
// }

function logout() {
    localStorage.removeItem("user");
}

function setSpanUserName() {
    document.getElementById("span-username").innerText = localStorage.getItem("user");
}