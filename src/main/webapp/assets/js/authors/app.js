let isNew = false;
$(function () {

    findAllAuthors();

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnSave").click(function () {
        $("#addEditForm").validate({
            rules: {
                firstName: {required: true, minlength: 3},
                lastName: {required: true, minlength: 3},
                city: {required: true, minlength: 3},
                dob: {required: true}
            },
            submitHandler: function (form) {
                let url = contextPath + "/admincrud/autores?action=create";
                let labelModal = "Registrado!";
                if (!isNew) {
                    url = contextPath + "/admincrud/autores?action=update";
                    labelModal = "Actualizado!";
                }
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.success) {
                            $(form).trigger("reset");
                            $("#addEditModal").modal("hide");
                            $("#tblAuthors").DataTable().ajax.reload(null, false);
                            Swal.fire(labelModal, response.message, response.status);
                            if (isNew) isNew = false;
                        }
                    },
                    processData: false,
                    cache: false,
                });
            }
        });
    });

    $("#btnReset").click(function () {
        resetInvalidForm(this, "#addEditForm");
        addClassFormGroup(false);
    });

    $("#btnNew").click(function () {
        addClassFormGroup(true);
    });

});

function addClassFormGroup(flag) {
    isNew = flag;
    let modalBody = $("#addEditModal .modal-body")
    modalBody.find(".form-group #txtAuthorId").parent().addClass("d-none");
    modalBody.find(".form-group .custom-switch").parent().addClass("d-none");
}

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function formatDateLocale(param, format) {
    let date = new Date(param);
    date.setMinutes(date.getMinutes() + date.getTimezoneOffset());
    return moment(date, "YYYY-MM-DD").format(format);
}

function updateAndViewDetailsAuthor(button, isEditable) {
    let url = contextPath + "/admincrud/autores?action=findById";
    let authorId = $(button).data("authorId");
    $.ajax({
        url: url,
        method: "GET",
        data: {authorId: authorId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundAuthor = response.result;
                if (isEditable) {
                    isNew = false;
                    $("#addEditModal .modal-body > .form-group.d-none").removeClass("d-none");
                    let modalBody = $("#addEditModal .modal-body");
                    modalBody.find(".form-group #txtAuthorId").val(foundAuthor.authorId);
                    modalBody.find(".form-group #txtFirstname").val(foundAuthor.firstName);
                    modalBody.find(".form-group #txtLastname").val(foundAuthor.lastName);
                    modalBody.find(".form-group #txtCity").val(foundAuthor.city);
                    modalBody.find(".form-group #dtDob").val(formatDateLocale(foundAuthor.dob, "DD/MM/YYYY"));
                    modalBody.find(".form-group #chkActive").attr("checked", foundAuthor.active);
                    $("#addEditModal").modal("show");
                } else {
                    let classNameBadge = foundAuthor.active ? "success" : "danger";
                    let classNameIcon = foundAuthor.active ? "check" : "times";
                    let statusText = foundAuthor.active ? "ACTIVO" : "INACTIVO";
                    let modalBody = $("#viewDetailModal .modal-body");
                    modalBody.empty();
                    let elementHTML = "<dl>";
                    elementHTML += "<dt>Autor</dt>";
                    elementHTML += "<dd>" + foundAuthor.firstName + " " + foundAuthor.lastName + "</dd>";
                    elementHTML += "<dt>Ciudad, Año de Nacimiento</dt>";
                    elementHTML += "<dd>" + foundAuthor.city + ", " + new Date(foundAuthor.dob).getFullYear() + "</dd>";
                    elementHTML += "<dt>Activo?</dt>";
                    elementHTML += "<dd><span class='badge badge-" + classNameBadge + "'><i class='fas fa-" + classNameIcon + "'></i> " + statusText + "</span></dd>";
                    elementHTML += "</dl>";
                    modalBody.append(elementHTML);
                    $("#viewDetailModal").modal("show");
                    isNew = false;
                }
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
            let url = contextPath + "/admincrud/autores?action=disableById";
            let authorId = $(button).data("authorId");
            $.ajax({
                url: url,
                method: "POST",
                data: {authorId: authorId},
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        $("#tblAuthors").DataTable().ajax.reload(null, false);
                        Swal.fire("Deshabilitado!", response.message, response.status);
                    }
                }
            });
        }
    });
}

function findAllAuthors() {
    let url = contextPath + "/admincrud/autores?action=findAll";
    let table = $("#tblAuthors").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            {
                data: null,
                render: function (data, type, row) {
                    return row.firstName + " " + row.lastName;
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    return row.city + ", " + new Date(row.dob).getFullYear();
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
                    elementHTML += "<button type='button' onclick='updateAndViewDetailsAuthor(this, false)' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-author-id='" + row.authorId + "'><i class='fas fa-eye'></i></button>";
                    elementHTML += "<button type='button' onclick='updateAndViewDetailsAuthor(this, true)' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-author-id='" + row.authorId + "'><i class='fas fa-pen'></i></button>"
                    elementHTML += "<button type='button' onclick='disableAuthor(this)' class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar' data-author-id='" + row.authorId + "' data-author-fullname='" + row.firstName + " " + row.lastName + "'><i class='fas fa-flag'></i></button>"
                    elementHTML += "</div>"
                    return elementHTML;
                }
            }
        ]
    });
    table.on("draw", function () {
        $("[data-tooltip='tooltip']").tooltip();
    });
}

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