// MUST set the id tag of the 'html' to be: <html id="thisHTML"></html>

let htmlElement = document.getElementById("thisHTML");

function ajaxGet() {

    // DO GET
    $.ajax({
        type: "GET",
        url: "",

        success: function (result) {
            htmlElement.innerHTML = result;

            /* Set the span-username */
            setSpanUserName();
        },
        error: function (e) {
            htmlElement.value = "ERROR: FAIL";
        }
    });
}

setInterval(ajaxGet, 1000);