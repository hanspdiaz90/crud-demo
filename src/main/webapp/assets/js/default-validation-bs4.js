$(function () {
    $.validator.setDefaults({
        errorElement: "div",
        errorPlacement: function (error, element) {
            // Add the .invalid-feedback class to the error element
            error.addClass("invalid-feedback");
            element.closest(".form-group").append(error);
        },
        highlight: function (element, errorClass, validClass) {
            $(element).addClass("is-invalid");
            // $(element).addClass("is-invalid").removeClass("is-valid");
        },
        unhighlight: function (element, errorClass, validClass) {
            $(element).removeClass("is-invalid");
            // $(element).addClass("is-valid").removeClass("is-invalid");
        }
    });
});