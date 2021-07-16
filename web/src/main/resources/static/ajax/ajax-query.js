/* MUST import "web-module-name.js" */

// function ajaxQuery() {
//
//     // DO GET
//     $.ajax({
//         type: "GET",
//         url: "/" + webModuleName() + "/hello/time",
//         dataType: "text",
//
//         success: function (result) {
//             htmlElement.innerHTML = result;
//         },
//         error: function (e) {
//             console.log("ajax-GET-error");
//         }
//     });
// }

function ajaxQueryParams(type, url, dataType, idOfElementToUpdate) {

    $.ajax({
        type,
        url,
        dataType,

        success: function (result) {
            let htmlElementToUpdate = document.getElementById(idOfElementToUpdate);
            htmlElementToUpdate.innerHTML = result;
        },
        error: function (e) {
            console.log(`ajax-${type}-error`);
        }
    });
}

