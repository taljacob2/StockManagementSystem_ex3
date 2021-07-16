
let htmlElement = document.getElementById("thisHTML");

function ajaxGetHello() {

    // DO GET
    $.ajax({
        type: "GET",
        url: "/" + webModuleName() + "/hello/time",
        dataType: "text",

        success: function (result) {
            console.log(result)
            htmlElement.innerHTML = result;
        },
        error: function (e) {
            htmlElement.value = "ERROR: FAIL";
        }
    });
}

ajaxGetHello();

// setInterval(ajaxGetHello, 1000);
