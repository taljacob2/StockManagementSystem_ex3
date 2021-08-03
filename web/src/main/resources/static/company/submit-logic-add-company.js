let isFound = false;
let isEmpty = false;
let divValidate = document.getElementById('div-validate');
let inputValueGlobal = null;

window.addEventListener("pageshow", () => {
    // update hidden input field
    document.getElementById('symbolInput').value = '';
});


function validateStockSymbol(stocksSymbolList) {
    isFound = false;

    // setting input to be upperCase
    document.getElementById('symbolInput').value = document.getElementById('symbolInput').value.toUpperCase();

    let symbolInputValue = document.getElementById('symbolInput').value;

    for (let i = 0; i < stocksSymbolList.length; i++) {
        if (stocksSymbolList[i].toUpperCase() === symbolInputValue.toUpperCase()) {
            isFound = true;
            break;
        }
    }

    divValidate.setAttribute('data-validate', validateMessage(isFound, symbolInputValue));

    var input = $('.validate-input .input100');

    for (var i = 0; i < input.length; i++) {
        showValidate(input[i]);
    }

}

function validateMessage(isFound, inputValue) {
    inputValueGlobal = inputValue;
    if (isFound) {

        // input was found in list
        $(divValidate).removeClass("isValid");
        $(divValidate).addClass("isInValid");
        return "Symbol is taken";
    } else if ((inputValue.trim() === '') ? isEmpty = true : isEmpty = false) {

        // input is empty
        $(divValidate).removeClass("isValid");
        $(divValidate).addClass("isInValid");
        return "Symbol is required";
    } else {

        // not found
        $(divValidate).removeClass("isInValid");
        $(divValidate).addClass("isValid");
        return "Symbol is available";
    }
}


/*==================================================================
[ Validate On Submit ]*/
var input = $('.validate-input .input100');


$('.validate-form').on('submit', function () {
    // setting input to be upperCase
    document.getElementById('symbolInput').value = document.getElementById('symbolInput').value.toUpperCase();

    var enableSubmit = true;
    for (var i = 0; i < input.length; i++) {
        let usernameInputValue = document.getElementById('symbolInput').value;
        divValidate.setAttribute('data-validate', validateMessage(isFound, usernameInputValue, divValidate));
        if ($(divValidate).hasClass("isInValid")) {
            showValidate(input[i]);
            enableSubmit = false;
        }
    }
    if(enableSubmit){

        // check if there is already a signed in user.
        if (localStorage.getItem("user") !== null) {
            location.replace("home");
        }
    }
    return enableSubmit;
});


$('.validate-form .input100').each(function () {
    $(this).focus(function () {
        hideValidate(this);
    });
});

function showValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).addClass('alert-validate');
}

function hideValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).removeClass('alert-validate');
}