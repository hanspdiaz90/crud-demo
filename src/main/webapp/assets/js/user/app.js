$(function () {

    $("#loginForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnLogin").click(function () {
        $("#loginForm").validate({
            // rules: {
            //     email: {required: true, email: true},
            //     password: { required: true }
            // },
            submitHandler: function (form) {
                let url = "/biblioteca/login?accion=iniciarSesion";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        console.log(response);
                        if (response == "success") {
                            console.log(response);
                            window.location = "/views/dashboard/index.jsp";
                        }
                    }
                    // processData: false,
                    // cache: false,
                });
            }
        });
    });

});
