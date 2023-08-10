let isNew = false;
$(function () {

    findAllPublishers();

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnSave").click(function () {
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
                let title = "Registrado!";
                if (!isNew) {
                    url = contextPath + "/admincrud/editoriales?action=update";
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
                            $("#tblPublishers").DataTable().ajax.reload(null, false);
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
        setDisplayNoneToInputs(modalBody, ".form-group #txtPublisherId");
        setDisplayNoneToInputs(modalBody, ".form-group .custom-switch");
        setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtPublisherId", true);
    });

    $("#btnNew").click(function () {
        isNew = true;
        let modalBody = $("#addEditModal .modal-body");
        setDisplayNoneToInputs(modalBody, ".form-group #txtPublisherId");
        setDisplayNoneToInputs(modalBody, ".form-group .custom-switch");
        setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtPublisherId", true);
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

function displayPhone(phone) {
    let regex = /(\d{3})?(\d{4})/;
    return phone !== null ? phone.replace(regex, "$1-$2") : "-";
}

function displayCellphone(cellphone) {
    let regex = /(\d{3})?(\d{3})?(\d{3})/;
    return cellphone !== null ? cellphone.replace(regex, "$1-$2-$3") : "-";
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
                    setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtPublisherId", false);
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
                    let elementHTML = "<dl>";
                    elementHTML += "<dt>Editorial</dt>";
                    elementHTML += "<dd>" + foundPublisher.name + "</dd>";
                    elementHTML += "<dt>Dirección</dt>";
                    elementHTML += "<dd>" + foundPublisher.address + "</dd>";
                    elementHTML += "<dt>E-mail</dt>";
                    elementHTML += "<dd>" + foundPublisher.email + "</dd>";
                    elementHTML += "<dt>Teléfono</dt>";
                    elementHTML += "<dd>" + displayPhone(foundPublisher.phone) + "</dd>";
                    elementHTML += "<dt>Celular</dt>";
                    elementHTML += "<dd>" + displayCellphone(foundPublisher.cellphone) + "</dd>";
                    elementHTML += "<dt>Página Web</dt>";
                    elementHTML += "<dd>" + (foundPublisher.webSite != null ? "<a href='https://" + foundPublisher.webSite + "' target='_blank'>" + foundPublisher.webSite + "</a>" : "-") + "</dd>";
                    elementHTML += "<dt>" + displayStatus(foundPublisher.active) + "</dt>";
                    elementHTML += "</dl>";
                    modalBody.append(elementHTML);
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
                        $("#tblPublishers").DataTable().ajax.reload(null, false);
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
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    return displayPhone(row.phone);
                }
            },
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    return displayCellphone(row.cellphone);
                }
            },
            {
                data: null,
                className: "text-center",
                render: function(data, type, row) {
                    return displayStatus(row.active);
                }
            },
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    let elementHTML = "<div class='btn-group btn-group-sm'>";
                    elementHTML += "<button type='button' onclick='showModalEditAndViewDetailPublisher(this, false)' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-publisher-id='" + row.publisherId + "'><i class='fas fa-eye'></i></button>";
                    elementHTML += "<button type='button' onclick='showModalEditAndViewDetailPublisher(this, true)' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-publisher-id='" + row.publisherId + "'><i class='fas fa-pen'></i></button>"
                    elementHTML += "<button type='button' onclick='disablePublisher(this)' " +  (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-publisher-id='" + row.publisherId + "' data-publisher-name='" + row.name + "'><i class='fas fa-trash'></i></button>"
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