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
                let url = contextPath + "/biblioteca/login";
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
                        } else if (response.message === "success" ) {
                            console.log("entró al login ok");
                            $(location).attr('href', contextPath + '/biblioteca/dashboard');
                        }
                    },
                    processData: false,
                    cache: false
                });
            }
        });
    });

});
