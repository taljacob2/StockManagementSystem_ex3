// MUST set the id tag of the 'html' to be: <html id="thisHTML"></html>

let timeElement = document.getElementById("thisHTML");

function ajaxGenericGet() {

    // DO GET
    $.ajax({
        type: "GET",
        url: "",

        success: function (result) {
            timeElement.innerHTML = result;
        },
        error: function (e) {
            timeElement.value = "ERROR: FAIL";
        }
    });
}

setInterval(ajaxGenericGet, 100);