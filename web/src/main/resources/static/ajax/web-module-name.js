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