$(function () {

    getAllModules();

    $("#moduleAddForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnAdd").click(function () {
        $("#moduleAddForm").validate({
            rules: {
                title: {required: true, minlength: 3}
            },
            submitHandler: function (form) {
                let url = contextPath + "/admincrud/modulos?action=create";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.success) {
                            $(form).trigger("reset");
                            $("#moduleAddModal").modal("hide");
                            $("#modulesDataTable").DataTable().ajax.reload(null, false);
                            Swal.fire("Registrado!", response.message, response.status);
                        }
                    },
                    processData: false,
                    cache: false,
                });
            }
        });
    });

    $("#btnResetAdd").click(function () {
        resetInvalidForm(this, "#moduleAddForm");
    });

});

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function viewDetailsModule(button) {
    let url = contextPath + "/admincrud/modulos?action=findById";
    let moduleId = $(button).data("moduleId");
    $.ajax({
        url: url,
        method: "GET",
        data: {moduleId: moduleId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let moduleObj = response.result;
                let classNameBadge = moduleObj.active ? "success" : "danger";
                let classNameIcon = moduleObj.active ? "check" : "times";
                let statusText = moduleObj.active ? "ACTIVO" : "INACTIVO";
                let modalBody = $("#moduleViewModal .modal-body");
                modalBody.empty();
                let elementHTML = "<dl>";
                elementHTML += "<dt>Título</dt>";
                elementHTML += "<dd>" + moduleObj.title + "</dd>";
                elementHTML += "<dt>Descripción</dt>";
                elementHTML += "<dd>" + (moduleObj.description ?? "N/A") + "</dd>";
                elementHTML += "<dt>Activo?</dt>";
                elementHTML += "<dd><span class='badge badge-" + classNameBadge + "'><i class='fas fa-" + classNameIcon + "'></i> " + statusText + "</span></dd>";
                elementHTML += "</dl>";
                modalBody.append(elementHTML);
                $("#moduleViewModal").modal("show");
            }
        }
    });
}

function disableModule(button) {
    let title = $(button).data("title");
    Swal.fire({
        title: "¿Estás seguro que quieres deshabilitar el autor: " + title + " ?",
        text: "¡No podrás revertir esta operación!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = contextPath + "/admincrud/modulos?action=disableById";
            let moduleId = $(button).data("moduleId");
            $.ajax({
                url: url,
                method: "POST",
                data: {moduleId: moduleId},
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        $("#modulesDataTable").DataTable().ajax.reload(null, false);
                        Swal.fire("Deshabilitado!", response.message, response.status);
                    }
                }
            });
        }
    });
}

function getAllModules() {
    let url = contextPath + "/admincrud/modulos?action=findAll";
    let table = $("#modulesDataTable").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            { data: "title" },
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
                    elementHTML += "<button type='button' onclick='viewDetailsModule(this)' class='btn btn-info' data-toggle='modal' data-target='#moduleViewModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-module-id='" + row.moduleId + "'><i class='fas fa-eye'></i></button>";
                    if (row.active) {
                        elementHTML += "<button type='button' onclick='editModule(this)' class='btn btn-warning' data-toggle='modal' data-target='#modalEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-module-id='" + row.moduleId + "'><i class='fas fa-pen'></i></button>"
                        elementHTML += "<button type='button' onclick='disableModule(this)' class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-module-id='" + row.moduleId + "' data-title='" + row.title + "'><i class='fas fa-flag'></i></button>"
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