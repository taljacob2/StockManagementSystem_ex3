/* MUST import "web-module-name.js" */

let htmlElement = document.getElementById("thisTime");

function ajaxQuery() {

    // DO GET
    $.ajax({
        type: "GET",
        url: "/" + webModuleName() + "/hello/time",
        dataType: "text",

        success: function (result) {
            htmlElement.innerHTML = result;
        },
        error: function (e) {
            console.log("ajax-GET-error");
        }
    });
}

function ajaxQueryParams(type, url, dataType) {

    $.ajax({
        type,
        url,
        dataType,

        success: function (result) {
            htmlElement.innerHTML = result;
        },
        error: function (e) {
            console.log(`ajax-${type}-error`);
        }
    });
}

// ajaxGetHello();


setInterval(ajaxQueryParams, 100, "GET", "/" + webModuleName() + "/hello/time", "text");
