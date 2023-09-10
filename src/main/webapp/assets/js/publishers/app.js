let isNew = false;
$(function () {

    findAllPublishers();

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#addEditForm").validate({
        rules: {
            name: { required: true, minlength: 5 },
            email: { required: true, email: true  },
            address: { required: true },
            phone: { digits: true },
            cellphone: { digits: true }
        },
        submitHandler: function (form) {
            let url = contextPath + "/admincrud/editoriales?action=create";
            let alertTitle = "Registrado!";
            if (!isNew) {
                url = contextPath + "/admincrud/editoriales?action=update";
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
                        let table = $("#tblPublishers").DataTable();
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
        hideInputElement(modalBody, ".form-group #txtPublisherId");
        hideInputElement(modalBody, ".form-group .custom-switch");
        setPropertiesToInputElementId(modalBody, ".form-group #txtPublisherId", true);
    });

    $("#btnNew").click(function () {
        isNew = true;
        let modalBody = $("#addEditModal .modal-body");
        hideInputElement(modalBody, ".form-group #txtPublisherId");
        hideInputElement(modalBody, ".form-group .custom-switch");
        setPropertiesToInputElementId(modalBody, ".form-group #txtPublisherId", true);
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

function showFormatPhone(phone) {
    let regex = /(\d{3})?(\d{4})/;
    return phone !== null ? phone.replace(regex, "$1-$2") : "-";
}

function showFormatCellphone(cellphone) {
    let regex = /(\d{3})?(\d{3})?(\d{3})/;
    return cellphone !== null ? cellphone.replace(regex, "$1-$2-$3") : "-";
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

function showModalEditAndViewDetailPublisher(button, isEditable) {
    let publisherId = $(button).data("publisherId");
    let url = contextPath + "/admincrud/editoriales?action=findById";
    $.ajax({
        url: url,
        type: "GET",
        data: { publisherId: publisherId },
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundPublisher = response.result;
                if (isEditable) {
                    let modalBody = $("#addEditModal .modal-body");
                    modalBody.find(".form-group.d-none").removeClass("d-none");
                    setPropertiesToInputElementId(modalBody, ".form-group #txtPublisherId", false);
                    modalBody.find(".form-group #txtPublisherId").val(foundPublisher.publisherId);
                    modalBody.find(".form-group #txtName").val(foundPublisher.name);
                    modalBody.find(".form-group #txtEmail").val(foundPublisher.email);
                    modalBody.find(".form-group #txtAddress").val(foundPublisher.address);
                    modalBody.find(".form-group #txtPhone").val(foundPublisher.phone);
                    modalBody.find(".form-group #txtCellphone").val(foundPublisher.cellphone);
                    modalBody.find(".form-group #txtWebSite").val(foundPublisher.webSite);
                    modalBody.find(".form-group #chkActive").attr("checked", foundPublisher.active);
                    $("#addEditModal").modal("show");
                    isNew = false;
                } else {
                    let modalBody = $("#viewDetailModal .modal-body");
                    modalBody.empty();
                    let container = "<dl>";
                    container += "<dt>Editorial</dt>";
                    container += "<dd>" + foundPublisher.name + "</dd>";
                    container += "<dt>Dirección</dt>";
                    container += "<dd>" + foundPublisher.address + "</dd>";
                    container += "<dt>E-mail</dt>";
                    container += "<dd>" + foundPublisher.email + "</dd>";
                    container += "<dt>Teléfono</dt>";
                    container += "<dd>" + showFormatPhone(foundPublisher.phone) + "</dd>";
                    container += "<dt>Celular</dt>";
                    container += "<dd>" + showFormatCellphone(foundPublisher.cellphone) + "</dd>";
                    container += "<dt>Página Web</dt>";
                    container += "<dd>" + (foundPublisher.webSite != null ? "<a href='https://" + foundPublisher.webSite + "' target='_blank'>" + foundPublisher.webSite + "</a>" : "-") + "</dd>";
                    container += "<dt>" + showStatus(foundPublisher.active) + "</dt>";
                    container += "</dl>";
                    modalBody.append(container);
                    $("#viewDetailModal").modal("show");
                }
            }
        }
    });
}

function disablePublisher(button) {
    let publisherName = $(button).data("publisherName");
    Swal.fire({
        title: "¿Estás seguro que quieres desactivar la editorial: " + publisherName + " ?",
        text: "No podrás revertir esta operación!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = contextPath + "/admincrud/editoriales?action=disableById";
            let publisherId = $(button).data("publisherId");
            $.ajax({
                url: url,
                type: "POST",
                data: { publisherId: publisherId },
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        let table = $("#tblPublishers").DataTable();
                        table.ajax.reload(null, false);
                        Swal.fire("Desactivado!", response.message, response.status);
                        isNew = false;
                    }
                }
            });
        }
    });
}

function findAllPublishers() {
    let url = contextPath + "/admincrud/editoriales?action=findAll";
    let table = $("#tblPublishers").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            { data: "name" },
            { data: "email" },
            {
                data: "phone",
                className: "text-center",
                render: function (data, type, row) {
                    return showFormatPhone(data);
                }
            },
            {
                data: "cellphone",
                className: "text-center",
                render: function (data, type, row) {
                    return showFormatCellphone(data);
                }
            },
            {
                data: "active",
                className: "text-center",
                render: function(data, type, row) {
                    return showStatus(data);
                }
            },
            {
                data: "publisherId",
                className: "text-center",
                render: function (data, type, row) {
                    let isEditable = true;
                    let buttons = "<div class='btn-group btn-group-sm'>";
                    buttons += "<button type='button' onclick='showModalEditAndViewDetailPublisher(this, " + !isEditable + ")' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-publisher-id='" + data + "'><i class='fas fa-eye'></i></button>";
                    buttons += "<button type='button' onclick='showModalEditAndViewDetailPublisher(this, " + isEditable + ")' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-publisher-id='" + data + "'><i class='fas fa-pen'></i></button>"
                    buttons += "<button type='button' onclick='disablePublisher(this)' " +  (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-publisher-id='" + data + "' data-publisher-name='" + row.name + "'><i class='fas fa-trash'></i></button>"
                    buttons += "</div>"
                    return buttons;
                }
            }
        ]
    });

    table.on("draw", function () {
        $("[data-tooltip='tooltip']").tooltip();
    });

}