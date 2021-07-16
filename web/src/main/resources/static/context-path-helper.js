// extract the context path using the window.location data items
function calculateContextPath() {

    // Get the first letter in the "web" application module name.
    var pathWithoutLeadingSlash = window.location.pathname.substring(1);

    // Extract the last index of the "web" application module.
    var contextPathEndIndex = pathWithoutLeadingSlash.indexOf('/');

    // Substring the path to be the "web" application module only:
    return pathWithoutLeadingSlash.substring(0, contextPathEndIndex)
}



// console.log("calculateContextPath=" + calculateContextPath());

// console.log("calculateContextPath=" + calculateContextPath() + "/order/[[${stockSymbolLink}]]");
