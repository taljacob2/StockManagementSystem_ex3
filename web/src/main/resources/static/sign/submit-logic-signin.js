let isFound = false;
let isEmpty = false;
let divValidate = document.getElementById('div-validate');
let usernameInputValueGlobal = null;

window.addEventListener("pageshow", () => {
    // update hidden input field
    document.getElementById('usernameInput').value = '';
});





function validateUsername(usersNameList, usersNameListIsPresent) {
    isFound = false;

    let usernameInputValue = document.getElementById('usernameInput').value;
    if (usersNameListIsPresent) {
        for (let i = 0; i < usersNameList.length; i++) {
            if (usersNameList[i] === usernameInputValue) {
                isFound = true;
                break;
            }
        }
    }
    divValidate.setAttribute('data-validate', validateMessage(isFound, usernameInputValue));

    var input = $('.validate-input .input100');

    for (var i = 0; i < input.length; i++) {
        showValidate(input[i]);
    }

}

function validateMessage(isFound, usernameInputValue) {
    usernameInputValueGlobal = usernameInputValue;
    if (isFound) {
        $(divValidate).removeClass("isInValid");
        $(divValidate).addClass("isValid");
        return "Username found";
    } else if ((usernameInputValue.trim() === '') ? isEmpty = true : isEmpty = false) {
        $(divValidate).removeClass("isValid");
        $(divValidate).addClass("isInValid");
        return "Username is required";
    } else {
        $(divValidate).removeClass("isValid");
        $(divValidate).addClass("isInValid");
        return "Username not found";
    }
}


/*==================================================================
[ Validate On Submit ]*/
var input = $('.validate-input .input100');


$('.validate-form').on('submit', function () {
    var enableSubmit = true;
    for (var i = 0; i < input.length; i++) {
        let usernameInputValue = document.getElementById('usernameInput').value;
        divValidate.setAttribute('data-validate', validateMessage(isFound, usernameInputValue, divValidate));
        if ($(divValidate).hasClass("isInValid")) {
            showValidate(input[i]);
            enableSubmit = false;
        }
    }
    if(enableSubmit){

        // check if there is already a signed in user.
        if (localStorage.getItem("user") !== null) {
            console.log("there is already a signed user");
            location.replace("home");
        }

        // Converts to JSON to string
        localStorage.setItem("user", usernameInputValueGlobal);
    }
    return enableSubmit;
});


$('.validate-form .input100').each(function () {
    $(this).focus(function () {
        hideValidate(this);
    });
});

// function validate(input, isFound) {
//     if ($(input).attr('type') == 'email' || $(input).attr('name') == 'email') {
//         if ($(input).val().trim().match(/^([a-zA-Z0-9_\-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([a-zA-Z0-9\-]+\.)+))([a-zA-Z]{1,5}|[0-9]{1,3})(\]?)$/) == null) {
//             return false;
//         }
//     } else {
//         if (isEmpty || isFound) {
//             return false;
//         }
//     }
// }

function showValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).addClass('alert-validate');
}

function hideValidate(input) {
    var thisAlert = $(input).parent();

    $(thisAlert).removeClass('alert-validate');
}