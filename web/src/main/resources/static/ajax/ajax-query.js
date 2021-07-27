/*
 * MUST import:
 *
 * "web-module-name.js"
 *
 * <script
 * th:src="@{/sign/Login_v4/vendor/jquery/jquery-3.2.1.min.js}"
 * type="text/javascript"></script>
 *
 */


// function ajaxQuery() {
//
//     // DO GET
//     $.ajax({
//         type: "GET",
//         // url: "/" + webModuleName() + "/hello/time",
//         // url: /* [[@{/ajax/fragments/stocksList/user}]] */ null,
//         url: "/" + webModuleName() + "/ajax/fragments/stocksList/user",
//         dataType: "text",
//
//         success: function (result) {
//             console.log("result = " + result);
//             // htmlElement.innerHTML = result;
//         },
//         error: function (e) {
//             console.log("ajax-GET-error");
//         }
//     });
// }


function ajaxGetQuery(url, successCallback) {

    // DO GET
    $.ajax({
        type: "GET",
        url,
        dataType: "json",
        success: successCallback,
        error: function (e) {
            console.log("ajax-GET-error: " + url);
        }
    });
}


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
    let postLogoutURL = "/" + webModuleName() + "/ajax/user/logout";

    jQuery.ajax ({
        url: postLogoutURL,
        type: "POST",
        // data: JSON.stringify({data:"test"}),
        data: userName,
        dataType: "text",
        contentType: "text/plain; charset=utf-8",
        success: function(){
            //
        }
    });

}

// function updateStock() {
//
//     // Posting the userName to server, in order to remove it from 'signedInUsers' list:
//     let urlQuery = "/" + webModuleName() + "/stock";
//
//     jQuery.ajax ({
//         url: urlQuery,
//         type: "POST",
//         // data: JSON.stringify({data:"test"}),
//         data: [[${stockSymbol}]],
//         dataType: "text",
//         contentType: "text/plain; charset=utf-8",
//         success: function(){
//             //
//         }
//     });

// }



function setIntervalImmediatelyAjaxGet(url, intervalTime, successCallbackFunction) {

    ajaxGetQuery(
        url,
        successCallbackFunction);

    setInterval(ajaxGetQuery, intervalTime, url,
        successCallbackFunction);

}
