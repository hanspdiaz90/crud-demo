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
                // let url = "/biblioteca/login?accion=iniciarSesion";
                let url = "/cruddemo/biblioteca/login";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        console.log(response);
                        if (response.message == "El email y/o contraseña no pueden estar vacíos") {
                            Swal.fire("Upsss...!", response.message, "error");

                        } else if (response.message == "El e-mail y/o contraseña son incorrectos") {
                            Swal.fire("Upsss...!", response.message, "error");
                        }
                        // else {
                        //     //Swal.fire("Genial...!", response.message, "success");
                        //     //window.location.href = "/biblioteca/dashboard";
                        //     window.location = "/biblioteca/dashboard";
                        // }
                    },
                    // processData: false,
                    // cache: false,
                });
            }
        });
    });

});
