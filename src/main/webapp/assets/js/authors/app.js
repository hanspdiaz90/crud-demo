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
                let title = "Registrado!";
                if (!isNew) {
                    url = contextPath + "/admincrud/autores?action=update";
                    title = "Actualizado!";
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
                            Swal.fire(title, response.message, response.status);
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
        isNew = false;
        let modalBody = $("#addEditModal .modal-body");
        setDisplayNoneToInputs(modalBody, ".form-group #txtAuthorId");
        setDisplayNoneToInputs(modalBody, ".form-group .custom-switch");
        setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtAuthorId", true);
    });

    $("#btnNew").click(function () {
        isNew = true;
        let modalBody = $("#addEditModal .modal-body");
        setDisplayNoneToInputs(modalBody, ".form-group #txtAuthorId");
        setDisplayNoneToInputs(modalBody, ".form-group .custom-switch");
        setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtAuthorId", true);
    });

});

function setDisabledAndReadOnlyToInputId(modalBody, inputId, flag) {
    modalBody.find(inputId).prop("disabled", flag);
    modalBody.find(inputId).prop("readonly", !flag);
}

function setDisplayNoneToInputs(modalBody, input) {
    modalBody.find(input).parent().addClass("d-none");
}

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

// function displayLocalDate(date, format) {
//     return moment(date).format(format);
// }

function displayLocalDate (date) {
    let options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    };
    return new Date(date + "T00:00:00").toLocaleString("es-ES", options);
}

function displayFullName(firstname, lastname) {
    return firstname.concat(" ", lastname);
}

function displayOnlyYear(date) {
    return new Date(date).getFullYear();
}

function displayStatus(status) {
    let classNameBadge = status ? "success" : "danger";
    let classNameIcon = status ? "check" : "times";
    let statusText = status ? "ACTIVO" : "INACTIVO";
    let elementHTML = "<span class='badge badge-" + classNameBadge + "'>";
    elementHTML += "<i class='fas fa-" + classNameIcon + "'></i> <span>" + statusText + "</span>";
    elementHTML += "</span>";
    return elementHTML;
}

function showModalEditAndViewAuthor(button, isEditable) {
    let url = contextPath + "/admincrud/autores?action=findById";
    let authorId = $(button).data("authorId");
    $.ajax({
        url: url,
        type: "GET",
        data: {authorId: authorId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundAuthor = response.result;
                if (isEditable) {
                    let modalBody = $("#addEditModal .modal-body");
                    modalBody.find(".form-group.d-none").removeClass("d-none");
                    setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtAuthorId", false);
                    modalBody.find(".form-group #txtAuthorId").val(foundAuthor.authorId);
                    modalBody.find(".form-group #txtFirstname").val(foundAuthor.firstName);
                    modalBody.find(".form-group #txtLastname").val(foundAuthor.lastName);
                    modalBody.find(".form-group #txtCity").val(foundAuthor.city);
                    modalBody.find(".form-group #dtDob").val(displayLocalDate(foundAuthor.dob));
                    // modalBody.find(".form-group #dtDob").val(displayLocalDate(foundAuthor.dob, "DD/MM/YYYY"));
                    modalBody.find(".form-group #chkActive").attr("checked", foundAuthor.active);
                    $("#addEditModal").modal("show");
                    isNew = false;
                } else {
                    let modalBody = $("#viewDetailModal .modal-body");
                    modalBody.empty();
                    let elementHTML = "<dl>";
                    elementHTML += "<dt>Autor</dt>";
                    elementHTML += "<dd>" + displayFullName(foundAuthor.firstName, foundAuthor.lastName) + "</dd>";
                    elementHTML += "<dt>Ciudad, Año de Nacimiento</dt>";
                    elementHTML += "<dd>" + foundAuthor.city + ", " + displayOnlyYear(foundAuthor.dob) + "</dd>";
                    elementHTML += "<dt>" + displayStatus(foundAuthor.active) + "</dt>";
                    elementHTML += "</dl>";
                    modalBody.append(elementHTML);
                    $("#viewDetailModal").modal("show");
                }
            }
        }
    });
}

function disableAuthor(button) {
    let authorFullname = $(button).data("authorFullname");
    Swal.fire({
        title: "¿Estás seguro que quieres desactivar el autor: " + authorFullname + " ?",
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
                type: "POST",
                data: {authorId: authorId},
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        $("#tblAuthors").DataTable().ajax.reload(null, false);
                        Swal.fire("Desactivado!", response.message, response.status);
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
                    return displayFullName(row.firstName, row.lastName);
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    return row.city + ", " + displayOnlyYear(row.dob);
                }
            },
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    return displayStatus(row.active);
                }
            },
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    let elementHTML = "<div class='btn-group btn-group-sm'>";
                    elementHTML += "<button type='button' onclick='showModalEditAndViewAuthor(this, false)' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-author-id='" + row.authorId + "'><i class='fas fa-eye'></i></button>";
                    elementHTML += "<button type='button' onclick='showModalEditAndViewAuthor(this, true)' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-author-id='" + row.authorId + "'><i class='fas fa-pen'></i></button>";
                    elementHTML += "<button type='button' onclick='disableAuthor(this)' " +  (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar' data-author-id='" + row.authorId + "' data-author-fullname='" + displayFullName(row.firstName, row.lastName) + "'><i class='fas fa-trash'></i></button>";
                    elementHTML += "</div>";
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