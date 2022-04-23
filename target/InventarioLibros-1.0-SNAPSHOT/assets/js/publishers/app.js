$(function () {

    fnGetAllPublishers();

    $("#addPublisherForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnAddPublisher").click(function () {
        $("#addPublisherForm").validate({
            rules: {
                nombre: { required: true, minlength: 5 },
                email: { required: true, email: true  },
                telefono: { required: true, digits: true }
            },
            submitHandler: function (form) {
                let url = "/biblioteca/editoriales?accion=registrar";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.ok) {
                            $(form).trigger("reset");
                            $("#addPublisherModal").modal("hide");
                            Swal.fire({
                                icon: "success",
                                title: response.message
                            }).then(function () {
                                fnGetAllPublishers();
                            });
                        }
                    },
                    processData: false,
                    cache: false,
                });
            }
        });

    });

    $("#btnResetSave").click(function () {
        fnResetInvalidForm(this, "#addPublisherForm");
    });

});

function fnResetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function fnViewDetailsPublisher(button) {
    let publisherId = $(button).data("publisherId");
    let url = "/biblioteca/editoriales?accion=buscar";
    $.ajax({
        url: url,
        method: "GET",
        data: { id: publisherId },
        dataType: "JSON",
        success: function (response) {
            if (response.ok) {
                let objPublisher = response.result;
                let classNameBadge = objPublisher.activo ? "success" : "danger";
                let classNameIcon = objPublisher.activo ? "check" : "times";
                let statusText = objPublisher.activo ? "ACTIVO" : "INACTIVO";
                let modalBody = $("#viewPublisherModal .modal-body");
                modalBody.empty();
                let elementHTML = "<dl>";
                    elementHTML += "<dt>Editorial</dt>";
                    elementHTML += "<dd>" + objPublisher.nombre + "</dd>";
                    elementHTML += "<dt>E-mail</dt>";
                    elementHTML += "<dd>" + objPublisher.email + "</dd>";
                    elementHTML += "<dt>Teléfono</dt>";
                    elementHTML += "<dd>" + objPublisher.telefono + "</dd>";
                    elementHTML += "<dt>Activo?</dt>";
                    elementHTML += "<dd><span class='badge badge-"+ classNameBadge + "'><i class='fas fa-"+ classNameIcon + "'></i> " + statusText+ "</span></dd>";
                    elementHTML += "</dl>";
                modalBody.append(elementHTML);
                $("#viewPublisherModal").modal("show");
            }
        }
    });
}

function fnDeactivatePublisher(button) {
    let publisher = $(button).data("publisherName");
    Swal.fire({
        title: "¿Estás seguro que quieres desactivar la editorial: " + publisher + " ?",
        text: "No podrás revertir esta operación!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = "/biblioteca/editoriales?accion=desactivar";
            let publisherId = $(button).data("publisherId");
            $.ajax({
                url: url,
                method: "GET",
                data: { id: publisherId },
                dataType: "JSON",
                success: function (response) {
                    if (response.ok) {
                        $("#publishersDataTable").DataTable().ajax.reload(null, false);
                        Swal.fire(
                            "Desactivado!",
                            response.message,
                            "success"
                        );
                    }
                }
            });
        }
    });
}

function fnGetAllPublishers() {
    let url = "/biblioteca/editoriales?accion=listar";
    let table = $("#publishersDataTable").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            { data: "nombre" },
            { data: "email" },
            {
                data: null,
                render: function (data, type, row) {
                    return row.telefono;
                }
            },
            {
                data: null,
                className: "text-center",
                render: function(data, type, row) {
                    let classNameBadge = row.activo ? "success" : "danger";
                    let classNameIcon = row.activo ? "check" : "times";
                    let statusText = row.activo ? "ACTIVO" : "INACTIVO";
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
                    elementHTML += "<button type='button' onclick='fnViewDetailsPublisher(this)' class='btn btn-info' data-toggle='modal' data-target='#viewPublisherModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-publisher-id='" + row.id + "'><i class='fas fa-eye'></i></button>";
                    if (row.activo) {
                        elementHTML += "<button type='button' onclick='fnEditPublisher(this)' class='btn btn-warning' data-toggle='modal' data-target='#editPublisherModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-publisher-id='" + row.id + "'><i class='fas fa-pen'></i></button>"
                        elementHTML += "<button type='button' onclick='fnDeactivatePublisher(this)' class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-publisher-id='" + row.id + "' data-publisher-name='" + row.nombre + "'><i class='fas fa-flag'></i></button>"
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