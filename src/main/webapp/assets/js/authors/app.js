$(function () {

    fnGetAllAuthors();

    $("#addAuthorForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnAddAuthor").click(function () {
        $("#addAuthorForm").validate({
            rules: {
                nombres: { required: true, minlength: 3 },
                apellidos: { required: true, minlength: 3 },
                ciudad: { required: true, minlength: 3 }
            },
            submitHandler: function (form) {
                let url = "/biblioteca/autores?accion=registrar";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.ok) {
                            $(form).trigger("reset");
                            $("#addAuthorModal").modal("hide");
                            Swal.fire({
                                icon: "success",
                                title: response.message
                            }).then(function () {
                                fnGetAllAuthors();
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
        fnResetInvalidForm(this, "#addAuthorForm");
    });

});

function fnResetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function fnViewDetailsAuthor(button) {
    let url = "/biblioteca/autores?accion=buscar";
    let authorId = $(button).data("authorId");
    $.ajax({
        url: url,
        method: "GET",
        data: { id: authorId },
        dataType: "JSON",
        success: function (response) {
            if (response.ok) {
                let objAuthor = response.data;
                let classNameBadge = objAuthor.activo ? "success" : "danger";
                let classNameIcon = objAuthor.activo ? "check" : "times";
                let statusText = objAuthor.activo ? "ACTIVO" : "INACTIVO";
                let modalBody = $("#viewAuthorModal .modal-body");
                modalBody.empty();
                let elementHTML = "<dl>";
                elementHTML += "<dt>Autor</dt>";
                elementHTML += "<dd>" + objAuthor.nombres + " " + objAuthor.apellidos + "</dd>";
                elementHTML += "<dt>Ciudad</dt>";
                elementHTML += "<dd>" + objAuthor.ciudad + "</dd>";
                elementHTML += "<dt>Activo?</dt>";
                elementHTML += "<dd><span class='badge badge-" + classNameBadge + "'><i class='fas fa-" + classNameIcon + "'></i> " + statusText + "</span></dd>";
                elementHTML += "</dl>";
                modalBody.append(elementHTML);
                $("#viewAuthorModal").modal("show");
            }
        }
    });
}

function fnChangeStatusAuthor(button) {
    let active = $(button).data("authorStatus");
    Swal.fire({
        title: (active ? "¿Estás seguro que quieres dar de baja al autor?" : "¿Estás seguro que quieres dar de alta al autor?"),
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = "/biblioteca/autores?accion=cambiarEstado";
            let authorId = $(button).data("authorId");
            $.ajax({
                url: url,
                method: "GET",
                data: { id: authorId },
                dataType: "JSON",
                success: function (response) {
                    if (response.ok) {
                        $("#authorsDataTable").DataTable().ajax.reload(null, false);
                        Swal.fire(
                            (active ? "Baja" : "Alta"),
                            (active ? "El autor fue dado de baja" : "El autor fue dado de alta"),
                            "success"
                        );
                    }
                }
            });
        }
    });
}

function fnGetAllAuthors() {
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
                    return row.nombres + " " + row.apellidos;
                }
            },
            {data: "ciudad"},
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
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
                    let classNameButton = row.activo ? "danger" : "success";
                    let tooltipTitle = row.activo ? "Dar de baja" : "Dar de alta";
                    let elementHTML = "<div class='btn-group btn-group-sm'>";
                    elementHTML += "<button type='button' onclick='fnViewDetailsAuthor(this)' class='btn btn-info' data-toggle='modal' data-target='#viewDetailsAuthorModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-author-id='" + row.id + "'><i class='fas fa-eye'></i></button>";
                    elementHTML += "<button type='button' onclick='fnEditAuthor(this)' class='btn btn-warning' data-toggle='modal' data-target='#editAuthorModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-author-id='" + row.id + "'><i class='fas fa-pen'></i></button>";
                    elementHTML += "<button type='button' onclick='fnChangeStatusAuthor(this)' class='btn btn-" + classNameButton + "' data-tooltip='tooltip' data-placement='top' title='" + tooltipTitle + "'  data-author-id='" + row.id + "' data-author-status='" + row.activo + "'><i class='fas fa-flag'></i></button>";
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