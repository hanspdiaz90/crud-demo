$(function () {

    $("#loginForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnLogin").click(function () {
        $("#loginForm").validate({
            rules: {
                email: {required: true, email: true},
                password: { required: true }
            },
            submitHandler: function (form) {
                let url = contextPathLogin + "/admincrud/login";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.success) {
                            $(location).attr("href", contextPathLogin + response.url);
                        } else {
                            Swal.fire("Upsss...!", response.message, response.status);
                        }
                    },
                    processData: false,
                    cache: false
                });
            }
        });
    });

});
