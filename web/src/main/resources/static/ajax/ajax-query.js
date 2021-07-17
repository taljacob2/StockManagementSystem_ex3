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

function ajaxQueryInnerHTML(type, url, dataType, idOfElementToUpdate) {

    $.ajax({
        type,
        url,
        dataType,

        success: function (result) {
            let htmlElementToUpdate = document.getElementById(idOfElementToUpdate);
            htmlElementToUpdate.innerHTML = result;
        },
        error: function (e) {
            console.log(`ajax-ajaxQueryInnerHTML-${type}-error`);
        }
    });
}


function ajaxGetQueryLoadToResultsBlock(url, resultsBlockID) {

    $("#" + resultsBlockID).load(url);

}