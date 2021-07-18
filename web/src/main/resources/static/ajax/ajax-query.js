/*
 * MUST import:
 *
 * "web-module-name.js"
 *
 * <script
 * src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
 * type="text/javascript"></script>
 *
 */


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


function ajaxLoadToResultsBlock(url, resultsBlockID) {
    $("#" + resultsBlockID).load(url);
}

function logout() {

    // Remove "user" from localStorage:
    localStorage.removeItem("user");

    // Posting the userName to server, in order to remove it from 'signedInUsers' list:
    let postLogoutURL = "/" + webModuleName() + "/user/logout";

    $.post({
        url: postLogoutURL,
        data: userName,
        contentType: 'text/plain; charset=utf-8'
    })
    // .done(function (response) {
    //     //Do something on success response...
    // });

}