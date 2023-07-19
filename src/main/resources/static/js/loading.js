$(document).ready(function() {
    // Remove the loading screen when the long-running task is completed
    $(document).ajaxStop(function() {
        var loadingContainer = document.getElementById('loadingContainer');
        loadingContainer.innerHTML = '';
    });
});