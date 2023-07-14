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
                let labelModal = "Registrado!";
                if (!isNew) {
                    url = contextPath + "/admincrud/editoriales?action=update";
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
                            $("#tblPublishers").DataTable().ajax.reload(null, false);
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
    modalBody.find(".form-group #txtPublisherId").parent().addClass("d-none");
    modalBody.find(".form-group .custom-switch").parent().addClass("d-none");
}

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function updateAndViewDetailsAuthor(button, isEditable) {
    let publisherId = $(button).data("publisherId");
    let url = contextPath + "/admincrud/editoriales?action=findById";
    $.ajax({
        url: url,
        method: "GET",
        data: { publisherId: publisherId },
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundPublisher = response.result;
                if (isEditable) {
                    isNew = false;
                    $("#addEditModal .modal-body > .form-group.d-none").removeClass("d-none");
                    let modalBody = $("#addEditModal .modal-body");
                    modalBody.find(".form-group #txtPublisherId").val(foundPublisher.publisherId);
                    modalBody.find(".form-group #txtName").val(foundPublisher.name);
                    modalBody.find(".form-group #txtEmail").val(foundPublisher.email);
                    modalBody.find(".form-group #txtAddress").val(foundPublisher.address);
                    modalBody.find(".form-group #txtPhone").val(foundPublisher.phone);
                    modalBody.find(".form-group #txtCellphone").val(foundPublisher.cellphone);
                    modalBody.find(".form-group #txtWebSite").val(foundPublisher.webSite);
                    modalBody.find(".form-group #chkActive").attr("checked", foundPublisher.active);
                    $("#addEditModal").modal("show");
                } else {
                    let classNameBadge = foundPublisher.active ? "success" : "danger";
                    let classNameIcon = foundPublisher.active ? "check" : "times";
                    let statusText = foundPublisher.active ? "ACTIVO" : "INACTIVO";
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
                    elementHTML += "<dd>" + (foundPublisher.phone ?? "-") + "</dd>";
                    elementHTML += "<dt>Celular</dt>";
                    elementHTML += "<dd>" + (foundPublisher.cellphone ?? "-") + "</dd>";
                    elementHTML += "<dt>Página Web</dt>";
                    elementHTML += "<dd><a href='https://" + foundPublisher.webSite + "' target='_blank'>" + foundPublisher.webSite + "</a></dd>";
                    elementHTML += "<dt><span class='badge badge-"+ classNameBadge + "'><i class='fas fa-"+ classNameIcon + "'></i> " + statusText+ "</span></dt>";
                    elementHTML += "</dl>";
                    modalBody.append(elementHTML);
                    $("#viewDetailModal").modal("show");
                    isNew = false;
                }
            }
        }
    });
}

function disablePublisher(button) {
    let publisherName = $(button).data("publisherName");
    Swal.fire({
        title: "¿Estás seguro que quieres deshabilitar la editorial: " + publisherName + " ?",
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
                method: "POST",
                data: { publisherId: publisherId },
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        $("#tblPublishers").DataTable().ajax.reload(null, false);
                        Swal.fire("Deshabilitado!", response.message, response.status);
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
                    if (row.phone === null ) return "-";
                    let regex = /(\d{3})?(\d{4})/;
                    return row.phone.replace(regex, "$1-$2");
                }
            },
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    if (row.cellphone === null ) return "-";
                    let regex = /(\d{3})?(\d{3})?(\d{3})/;
                    return row.cellphone.replace(regex, "$1-$2-$3");
                }
            },
            {
                data: null,
                className: "text-center",
                render: function(data, type, row) {
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
                    elementHTML += "<button type='button' onclick='updateAndViewDetailsAuthor(this, false)' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-publisher-id='" + row.publisherId + "'><i class='fas fa-eye'></i></button>";
                    if (row.active) {
                        elementHTML += "<button type='button' onclick='updateAndViewDetailsAuthor(this, true)' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-publisher-id='" + row.publisherId + "'><i class='fas fa-pen'></i></button>"
                        elementHTML += "<button type='button' onclick='disablePublisher(this)' class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-publisher-id='" + row.publisherId + "' data-publisher-name='" + row.name + "'><i class='fas fa-flag'></i></button>"
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

}