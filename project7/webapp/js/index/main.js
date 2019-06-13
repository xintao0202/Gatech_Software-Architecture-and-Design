var initDataUrl = '/admin/initializeData';
var initSpinner = '#init-spinner';

$(document).ready(function() {

    $('#init-data-button').click(function(e) {
       e.preventDefault();
       if ($('#init-data-button').hasClass('active')) {
           return;
       }
       loadData();
    });
});

loadData = function() {

    $('#init-data-button').addClass('active');
    toggleSpinner(initSpinner);
    $.ajax({
        url: initDataUrl,
        method: 'POST',
        contentType: 'application/json'
    }).always(function() {
        toggleSpinner(initSpinner);
        $('#init-data-button').removeClass('active');
    })
};