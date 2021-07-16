/* MUST import "web-module-name.js" */

let htmlElement = document.getElementById("thisTime");

function ajaxGetHello() {

    // DO GET
    $.ajax({
        type: "GET",
        url: "/" + webModuleName() + "/hello/time",
        dataType: "text",

        success: function (result) {
            htmlElement.innerHTML = result;
        },
        error: function (e) {
            htmlElement.value = "ERROR: FAIL";
        }
    });
}

// ajaxGetHello();

setInterval(ajaxGetHello, 1000);
