var currentTermUrl = '/admin/term';
var nextTermUrl = '/admin/term/next';
var registerInstructorUrl = '/admin/instructor/register';
var registerStudentUrl = '/admin/student/register';
var registerCourseUrl = '/admin/course/register';

$(document).ready(function () {
    init();
});

var init = function() {
    // Clear Local Storage
    localStorage.clear();
    $('input').val('');

    $('.admin-content-link').click(function(e) {
        e.preventDefault();
        $('.admin-content-link').removeClass('active');
        $(this).addClass('active');
        var contentElement = '#' + $(this).data('content-box');
        displayAdminContent(contentElement);
    });

    initCurrentTerm();
    initNextTerm();
    initInstructor();
    initStudent();
    initCourse();
};

var displayAdminContent = function(element) {
    $('.admin-content-box').hide();
    $(element).show();
};

/*************************************/
/*           Current Term            */
/*************************************/
var initCurrentTerm = function() {
    // Get current term information on load
    $.ajax({
        url: currentTermUrl,
        method: 'GET',
        cache: false
    }).done(function(data) {
        console.log(data);
        localStorage.setItem('currentTerm', data.currentTerm);
        localStorage.setItem('currentYear', data.currentYear);
        updateTermInfo();
    });
};

var displayAcademicTerm = function() {
    $('#current-term-link').click();
};

var updateTermInfo = function() {
    $('#current-term').text(localStorage.getItem('currentTerm'));
    $('#current-year').text(localStorage.getItem('currentYear'));
};

/*************************************/
/*             Next Term             */
/*************************************/
var initNextTerm = function() {
    $('#next-term-link').click(function(e) {
        e.preventDefault();
        displayNextTerm();
    });

    $('#start-next-term-button').click(function() {
        beginNextTerm();
    });

    $('#stay-term-button').click(function() {
        displayAcademicTerm();
    });
};

var displayNextTerm = function() {
  $('#next-term-prompt').show();
  $('#next-term-status').hide();
};

var beginNextTerm = function() {
    $('#next-term-prompt').hide();
    $('#next-term-status')
        .show()
        .html('<i class="fa fa-circle-o-notch fa-spin"> </i> Starting next term');

    $.ajax({
        url: nextTermUrl,
        method: 'POST'
    }).success(function(data) {
        localStorage.setItem('currentTerm', data.currentTerm);
        localStorage.setItem('currentYear', data.currentYear);
        console.log(localStorage);
        updateTermInfo();
        var message = localStorage.getItem('currentTerm') + ' ' + localStorage.getItem('currentYear')+ ' has begun.';
        successModal(message);
        displayAcademicTerm();
    }).error(function (jqXHR, textStatus) {
        console.log(jqXHR);
        console.log(textStatus);
    });
};


/*************************************/
/*          Add Instructor           */
/*************************************/
var initInstructor = function() {
    $('#instructor-register-btn').click(function(e) {
        e.preventDefault();
        registerInstructor();
    });
};

var registerInstructor = function() {
    var name = $('#instructor-name').val();
    var address = $('#instructor-address').val();
    var number = $('#instructor-number').val();

    var data = {
        "name": name,
        "address": address,
        "phoneNumber": number
    };

    $.ajax({
        url: registerInstructorUrl,
        method: 'POST',
        data: JSON.stringify(data),
        contentType: 'application/json'
    }).success(function (data) {
        console.log(data);
        var footer = '<a href="' + data.location + '" class="btn btn-primary">View</a>';
        successModal('Instructor registered.', footer);
    }).error(function (jqXHR, textStatus) {
        console.log(jqXHR);
        console.log(textStatus);
        dangerModal("Unable to register instructor. Please contact an administrator for more information.");
    });
};
/*************************************/
/*          Add Student           */
/*************************************/
var initStudent = function() {
    $('#student-register-btn').click(function(e) {
        e.preventDefault();
        registerStudent();
    });
};

var registerStudent = function() {
    var name = $('#student-name').val();
    var address = $('#student-address').val();
    var number = $('#student-number').val();

    var data = {
        "name": name,
        "address": address,
        "phoneNumber": number
    };

    $.ajax({
        url: registerStudentUrl,
        method: 'POST',
        data: JSON.stringify(data),
        contentType: 'application/json'
    }).success(function (data) {
        console.log(data);
        var footer = '<a href="' + data.location + '" class="btn btn-primary">View</a>';
        successModal('Student registered.', footer);
    }).error(function (jqXHR, textStatus) {
        console.log(jqXHR);
        console.log(textStatus);
        dangerModal("Unable to register student. Please contact an administrator for more information.");
    });
};
/*************************************/
/*          Add Course           */
/*************************************/
var initCourse = function() {
    $('#course-register-btn').click(function(e) {
        e.preventDefault();
        registerCourse();
    });
};

var registerCourse = function() {
    var name = $('#course-name').val();
    var code = $('#course-code').val();
     var data = {
         "courseId": code,
         "courseName": name
     };

    $.ajax({
        url: registerCourseUrl,
        method: 'POST',
        data: JSON.stringify(data),
        contentType: 'application/json'
    }).success(function (data) {
        console.log(data);
        var footer = '<a href="' + data.location + '" class="btn btn-primary">View</a>';
        successModal('Course registered.', footer);
    }).error(function (jqXHR, textStatus) {
        console.log(jqXHR);
        console.log(textStatus);
        dangerModal("Unable to create course. Please contact an administrator for more information.");
    });
};
