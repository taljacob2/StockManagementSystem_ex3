function setStorage(requestUserDTO) {
    console.log("test storage");
    console.log(requestUserDTO);
    //let user = {'name': requestUserDTO};
    //localStorage.setItem("user", user);
    //let testObj = {age: "20", name: "ido"};
    //localStorage.setItem("test", JSON.stringify(testObj));

    // Converts to JSON to string
    localStorage.setItem("user", JSON.stringify(requestUserDTO));


}

function logout() {
    localStorage.clear();
}