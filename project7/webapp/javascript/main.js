$(document).ready(function() {

    $('#hire-instructor-form').submit(function(e) {
        e.preventDefault();
        var id = $('#hire-instructor-input').val();
        hireInstructor(id);
    });
});

var hireInstructor = function(id) {

    $.ajax({
        url: '/admin/hire',
        data: {
            id: id
        },
        method: 'POST',
        contentType: 'application/json'
    })
    .done(function(data) {

    })
    .fail(function(response) {
        console.log(response);
        var json = response.responseJSON;
        $('#user-alert-modal-body').text(json.errorMessage);

    })

    $('#user-alert-modal').modal({
        show: true
    });
}