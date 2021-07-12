// MUST set the id tag of the 'html' to be: <html id="thisHTML"></html>

let htmlElement = document.getElementById("thisHTML");

function ajaxGenericGet() {

    // DO GET
    $.ajax({
        type: "GET",
        url: "",

        success: function (result) {
            htmlElement.innerHTML = result;
        },
        error: function (e) {
            htmlElement.value = "ERROR: FAIL";
        }
    });
}

setInterval(ajaxGenericGet, 1000);