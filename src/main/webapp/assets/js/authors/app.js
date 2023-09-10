let isNew = false;
$(function () {

    findAllAuthors();

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#addEditForm").validate({
        rules: {
            firstName: {required: true, minlength: 3},
            lastName: {required: true, minlength: 3},
            city: {required: true, minlength: 3},
            dob: {required: true}
        },
        submitHandler: function (form) {
            let url = contextPath + "/admincrud/autores?action=create";
            let alertTitle = "Registrado!";
            if (!isNew) {
                url = contextPath + "/admincrud/autores?action=update";
                alertTitle = "Actualizado!";
            }
            let postData = $(form).serialize();
            $.ajax({
                url: url,
                type: "POST",
                data: postData,
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        $(form).trigger("reset");
                        $("#addEditModal").modal("hide");
                        let table = $("#tblAuthors").DataTable();
                        table.ajax.reload(null, false);
                        Swal.fire(alertTitle, response.message, response.status);
                        if (isNew) isNew = false;
                    }
                },
                processData: false,
                cache: false,
            });
        }
    });

    $("#btnReset").click(function () {
        resetInvalidForm(this, "#addEditForm");
        isNew = false;
        let modalBody = $("#addEditModal .modal-body");
        hideInputElement(modalBody, ".form-group #txtAuthorId");
        hideInputElement(modalBody, ".form-group .custom-switch");
        setPropertiesToInputElementId(modalBody, ".form-group #txtAuthorId", true);
    });

    $("#btnNew").click(function () {
        isNew = true;
        let modalBody = $("#addEditModal .modal-body");
        hideInputElement(modalBody, ".form-group #txtAuthorId");
        hideInputElement(modalBody, ".form-group .custom-switch");
        setPropertiesToInputElementId(modalBody, ".form-group #txtAuthorId", true);
    });

});

function setPropertiesToInputElementId(modalBody, inputId, flag) {
    modalBody.find(inputId).prop("disabled", flag);
    modalBody.find(inputId).prop("readonly", !flag);
}

function hideInputElement(modalBody, input) {
    if (!modalBody.find(input).parent().hasClass("d-none")) {
        modalBody.find(input).parent().addClass("d-none");
    }
}

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

// function toFormatLocalDate(date) {
//     return new moment(date + "T00:00:00").format("DD/MM/YYYY");
// }

function toFormatLocalDate (date) {
    let options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric'
    };
    let mseconds = Date.parse(date + "T00:00:00");
    return new Date(mseconds).toLocaleString("es-ES", options);
}

function showFullNames(firstname, lastname) {
    return firstname.concat(" ", lastname);
}

function showOnlyYear(date) {
    return new Date(date).getFullYear();
}

function showStatus(status) {
    let badge = {
        false: {
            class: "danger",
            icon: "times",
            text: "INACTIVO"
        },
        true: {
            class: "success",
            icon: "check",
            text: "ACTIVO"
        }
    };
    let span = "<span class='badge badge-" + badge[status].class + "'>";
    span += "<i class='fas fa-" + badge[status].icon + "'></i> <span>" + badge[status].text + "</span>";
    span += "</span>";
    return span;
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
                    setPropertiesToInputElementId(modalBody, ".form-group #txtAuthorId", false);
                    modalBody.find(".form-group #txtAuthorId").val(foundAuthor.authorId);
                    modalBody.find(".form-group #txtFirstname").val(foundAuthor.firstName);
                    modalBody.find(".form-group #txtLastname").val(foundAuthor.lastName);
                    modalBody.find(".form-group #txtCity").val(foundAuthor.city);
                    modalBody.find(".form-group #dtDob").val(toFormatLocalDate(foundAuthor.dob));
                    modalBody.find(".form-group #chkActive").attr("checked", foundAuthor.active);
                    $("#addEditModal").modal("show");
                    isNew = false;
                } else {
                    let modalBody = $("#viewDetailModal .modal-body");
                    modalBody.empty();
                    let container = "<dl>";
                    container += "<dt>Autor</dt>";
                    container += "<dd>" + showFullNames(foundAuthor.firstName, foundAuthor.lastName) + "</dd>";
                    container += "<dt>Ciudad, Año de Nacimiento</dt>";
                    container += "<dd>" + foundAuthor.city + ", " + showOnlyYear(foundAuthor.dob) + "</dd>";
                    container += "<dt>" + showStatus(foundAuthor.active) + "</dt>";
                    container += "</dl>";
                    modalBody.append(container);
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
                        let table = $("#tblAuthors").DataTable();
                        table.ajax.reload(null, false);
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
                    return showFullNames(row.firstName, row.lastName);
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    return row.city + ", " + showOnlyYear(row.dob);
                }
            },
            {
                data: "active",
                className: "text-center",
                render: function (data, type, row) {
                    return showStatus(data);
                }
            },
            {
                data: "authorId",
                className: "text-center",
                render: function (data, type, row) {
                    let isEditable = true;
                    let buttons = "<div class='btn-group btn-group-sm'>";
                    buttons += "<button type='button' onclick='showModalEditAndViewAuthor(this, " + !isEditable + ")' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-author-id='" + data + "'><i class='fas fa-eye'></i></button>";
                    buttons += "<button type='button' onclick='showModalEditAndViewAuthor(this, " + isEditable + ")' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-author-id='" + data + "'><i class='fas fa-pen'></i></button>";
                    buttons += "<button type='button' onclick='disableAuthor(this)' " +  (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar' data-author-id='" + data + "' data-author-fullname='" + showFullNames(row.firstName, row.lastName) + "'><i class='fas fa-trash'></i></button>";
                    buttons += "</div>";
                    return buttons;
                }
            }
        ]
    });

    table.on("draw", function () {
        $("[data-tooltip='tooltip']").tooltip();
    });

}