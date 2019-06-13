/*************************************/
/*             Utilities             */
/*************************************/
var dangerModal = function(message) {
    $('.modal-dialog').removeClass('modal-success').addClass('modal-danger');
    $('#message-modal-status').text(message);
    $('#message-modal').modal('show');
};

var successModal = function(message, footer) {
    $('.modal-dialog').addClass('modal-success').removeClass('modal-danger');
    $('#message-modal-status').text(message);
    $('#message-modal').modal('show');

    if (footer === null || footer === undefined) {
        $('.modal-footer').html('');
    } else {
        $('.modal-footer').html(footer);
    }
};

toggleSpinner = function(element) {
    $(element).toggleClass('fa-circle-o-notch fa-spin fa');
};
