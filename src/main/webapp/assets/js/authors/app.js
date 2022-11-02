$(function () {

    getAuthors();

    $("#authorAddForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnAdd").click(function () {
        $("#authorAddForm").validate({
            rules: {
                firstname: { required: true, minlength: 3 },
                lastname: { required: true, minlength: 3 },
                city: { required: true, minlength: 3 },
                birthDate: {required: true}
            },
            submitHandler: function (form) {
                let url = "/biblioteca/autores?accion=crear";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.status == "success") {
                            $(form).trigger("reset");
                            $("#authorAddModal").modal("hide");
                            $("#authorsDataTable").DataTable().ajax.reload(null, false);
                            Swal.fire("Registrado!", response.message, "success");
                        }
                    },
                    processData: false,
                    cache: false,
                });
            }
        });
    });

    $("#btnResetAdd").click(function () {
        resetInvalidForm(this, "#authorAddForm");
    });

});

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function viewDetailsAuthor(button) {
    let url = "/biblioteca/autores?accion=verDetalles";
    let authorId = $(button).data("authorId");
    $.ajax({
        url: url,
        method: "GET",
        data: { authorId: authorId },
        dataType: "JSON",
        success: function (response) {
            if (response.status == "success") {
                let authorObj = response.result;
                let classNameBadge = authorObj.active ? "success" : "danger";
                let classNameIcon = authorObj.active ? "check" : "times";
                let statusText = authorObj.active ? "ACTIVO" : "INACTIVO";
                let modalBody = $("#authorViewModal .modal-body");
                modalBody.empty();
                let elementHTML = "<dl>";
                elementHTML += "<dt>Autor</dt>";
                elementHTML += "<dd>" + authorObj.firstname + " " + authorObj.lastname + "</dd>";
                elementHTML += "<dt>Ciudad</dt>";
                elementHTML += "<dd>" + authorObj.city + "</dd>";
                elementHTML += "<dt>Activo?</dt>";
                elementHTML += "<dd><span class='badge badge-" + classNameBadge + "'><i class='fas fa-" + classNameIcon + "'></i> " + statusText + "</span></dd>";
                elementHTML += "</dl>";
                modalBody.append(elementHTML);
                $("#authorViewModal").modal("show");
            }
        }
    });
}

function disableAuthor(button) {
    let authorFullname = $(button).data("authorFullname");
    Swal.fire({
        title: "¿Estás seguro que quieres deshabilitar el autor: " + authorFullname + " ?",
        text: "¡No podrás revertir esta operación!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = "/biblioteca/autores?accion=deshabilitar";
            let authorId = $(button).data("authorId");
            $.ajax({
                url: url,
                method: "GET",
                data: { authorId: authorId },
                dataType: "JSON",
                success: function (response) {
                    if (response.status == "success") {
                        $("#authorsDataTable").DataTable().ajax.reload(null, false);
                        Swal.fire("Deshabilitado!", response.message, "success");
                    }
                }
            });
        }
    });
}

function getAuthors() {
    let url = "/biblioteca/autores?accion=listar";
    let table = $("#authorsDataTable").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            {
                data: null,
                render: function (data, type, row) {
                    return row.firstname + " " + row.lastname;
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    return row.city + ", " + row.dateBirth.year;
                }
            },
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    let classNameBadge = row.active ? "success" : "danger";
                    let classNameIcon = row.active ? "check" : "times";
                    let statusText = row.active ? "ACTIVO" : "INACTIVO";
                    let elementHTML = "<span class='badge badge-" + classNameBadge + "'>";
                    elementHTML += "<i class='fas fa-" + classNameIcon + "'></i> <span>" + statusText + "</span>";
                    elementHTML += "</span>";
                    return elementHTML;
                }
            },
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    let elementHTML = "<div class='btn-group btn-group-sm'>";
                    elementHTML += "<button type='button' onclick='viewDetailsAuthor(this)' class='btn btn-info' data-toggle='modal' data-target='#authorViewModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-author-id='" + row.authorId + "'><i class='fas fa-eye'></i></button>";
                    if (row.active) {
                        elementHTML += "<button type='button' onclick='editAuthor(this)' class='btn btn-warning' data-toggle='modal' data-target='#authorEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-author-id='" + row.authorId + "'><i class='fas fa-pen'></i></button>"
                        elementHTML += "<button type='button' onclick='disableAuthor(this)' class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-author-id='" + row.authorId + "' data-author-fullname='" + row.firstname + " " + row.lastname + "'><i class='fas fa-flag'></i></button>"
                    }
                    elementHTML += "</div>"
                    return elementHTML;
                }
            }
        ]
    });
    table.on("draw", function () {
        $("[data-tooltip='tooltip']").tooltip();
    });

    // function changeStatusAuthor(button) {
//     let active = $(button).data("authorStatus");
//     Swal.fire({
//         title: (active ? "¿Estás seguro que quieres dar de baja al autor?" : "¿Estás seguro que quieres dar de alta al autor?"),
//         icon: "warning",
//         showCancelButton: true,
//         confirmButtonColor: "#3085d6",
//         cancelButtonColor: "#d33",
//         confirmButtonText: "Si, realizar operación"
//     }).then((result) => {
//         if (result.isConfirmed) {
//             let url = "/biblioteca/autores?accion=cambiarEstado";
//             let authorId = $(button).data("authorId");
//             $.ajax({
//                 url: url,
//                 method: "GET",
//                 data: { id: authorId },
//                 dataType: "JSON",
//                 success: function (response) {
//                     if (response.ok) {
//                         $("#authorsDataTable").DataTable().ajax.reload(null, false);
//                         Swal.fire(
//                             (active ? "Baja" : "Alta"),
//                             (active ? "El autor fue dado de baja" : "El autor fue dado de alta"),
//                             "success"
//                         );
//                     }
//                 }
//             });
//         }
//     });
// }

}