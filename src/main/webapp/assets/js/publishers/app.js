$(function () {

    getAllPublishers();

    $("#publisherAddForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnAdd").click(function () {
        $("#publisherAddForm").validate({
            rules: {
                name: { required: true, minlength: 5 },
                address: { required: true },
                email: { required: true, email: true  },
                phone: { required: true, digits: true },
                cellphone: { required: true, digits: true },
                webSite: { required: true }
            },
            submitHandler: function (form) {
                let url = contextPath + "/biblioteca/editoriales?accion=crear";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.status == "success") {
                            $(form).trigger("reset");
                            $("#publisherAddModal").modal("hide");
                            $("#publishersDataTable").DataTable().ajax.reload(null, false);
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
        resetInvalidForm(this, "#publisherAddForm");
    });

});

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function viewDetailsPublisher(button) {
    let publisherId = $(button).data("publisherId");
    let url = contextPath + "/biblioteca/editoriales?accion=verDetalles";
    $.ajax({
        url: url,
        method: "GET",
        data: { publisherId: publisherId },
        dataType: "JSON",
        success: function (response) {
            if (response.status == "success") {
                let publisherObj = response.result;
                let classNameBadge = publisherObj.active ? "success" : "danger";
                let classNameIcon = publisherObj.active ? "check" : "times";
                let statusText = publisherObj.active ? "ACTIVO" : "INACTIVO";
                let modalBody = $("#publisherViewModal .modal-body");
                modalBody.empty();
                let elementHTML = "<dl>";
                    elementHTML += "<dt>Editorial</dt>";
                    elementHTML += "<dd>" + publisherObj.name + "</dd>";
                    elementHTML += "<dt>Dirección</dt>";
                    elementHTML += "<dd>" + publisherObj.address + "</dd>";
                    elementHTML += "<dt>Página Web</dt>";
                    elementHTML += "<dd><a href='https://" + publisherObj.webSite + "' target='_blank'>" + publisherObj.webSite + "</a></dd>";
                    elementHTML += "<dt>E-mail</dt>";
                    elementHTML += "<dd>" + publisherObj.email + "</dd>";
                    elementHTML += "<dt>Teléfono</dt>";
                    elementHTML += "<dd>" + publisherObj.phone + "</dd>";
                    elementHTML += "<dt>Celular</dt>";
                    elementHTML += "<dd>" + publisherObj.cellphone + "</dd>";
                    elementHTML += "<dt>Activo?</dt>";
                    elementHTML += "<dd><span class='badge badge-"+ classNameBadge + "'><i class='fas fa-"+ classNameIcon + "'></i> " + statusText+ "</span></dd>";
                    elementHTML += "</dl>";
                modalBody.append(elementHTML);
                $("#publisherViewModal").modal("show");
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
            let url = contextPath + "/biblioteca/editoriales?accion=deshabilitar";
            let publisherId = $(button).data("publisherId");
            $.ajax({
                url: url,
                method: "GET",
                data: { publisherId: publisherId },
                dataType: "JSON",
                success: function (response) {
                    if (response.status == "success") {
                        $("#publishersDataTable").DataTable().ajax.reload(null, false);
                        Swal.fire("Deshabilitado!", response.message, "success");
                    }
                }
            });
        }
    });
}

function getAllPublishers() {
    let url = contextPath + "/biblioteca/editoriales?accion=listar";
    let table = $("#publishersDataTable").DataTable({
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
                    elementHTML += "<button type='button' onclick='viewDetailsPublisher(this)' class='btn btn-info' data-toggle='modal' data-target='#publisherViewModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-publisher-id='" + row.publisherId + "'><i class='fas fa-eye'></i></button>";
                    if (row.active) {
                        elementHTML += "<button type='button' onclick='editPublisher(this)' class='btn btn-warning' data-toggle='modal' data-target='#publisherEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-publisher-id='" + row.publisherId + "'><i class='fas fa-pen'></i></button>"
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