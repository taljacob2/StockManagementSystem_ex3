/**
 * Extracts the pure name of the "web" application module.
 * @returns {string}
 */
function webModuleName() {

    // Get the first letter in the "web" application module name.
    var pathWithoutLeadingSlash = window.location.pathname.substring(1);

    // Extract the last index of the "web" application module.
    var contextPathEndIndex = pathWithoutLeadingSlash.indexOf('/');

    // Substring the path to be the "web" application module only:
    return pathWithoutLeadingSlash.substring(0, contextPathEndIndex)
}


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

function ajaxGetHello() {

    // DO GET
    $.ajax({
        type: "GET",
        url: "/" + webModuleName() + "/hello/time",
        // url: "/web/hello/123123", /* TODO: WORKS! */
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

// setInterval(ajaxGenericGet, 1000);