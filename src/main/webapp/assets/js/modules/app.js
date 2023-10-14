let isNew = true;
$(function () {

    findAllModules();

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#addEditForm").validate({
        rules: {
            title: {required: true, minlength: 3}
        },
        submitHandler: function (form) {
            let url = contextPath + "/admincrud/modulos?action=create";
            let alertTitle = "Registrado!";
            if (!isNew) {
                url = contextPath + "/admincrud/modulos?action=update";
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
                        let card = $("#collapseCard");
                        let cardHeader = card.find(".card-header");
                        cardHeader.find(".card-title").html("<i class='fas fa-file-alt mr-1'></i> Añadir nuevo módulo");
                        let formBody = $("#addEditForm");
                        hideInputElement(formBody, ".form-group #txtModuleId");
                        hideInputElement(formBody, ".form-group .custom-switch");
                        blockInputElement(formBody, ".form-group #txtModuleId", true);
                        collapseCard(card, false);
                        let table = $("#tblModules").DataTable();
                        table.ajax.reload(null, false);
                        Swal.fire(alertTitle, response.message, response.status);
                        if (!isNew) isNew = true;
                    }
                },
                processData: false,
                cache: false,
            });
        }
    });

    $("#btnReset").click(function () {
        resetInvalidForm(this, "#addEditForm");
        isNew = true;
        let card = $("#collapseCard");
        let cardHeader = card.find(".card-header");
        cardHeader.find(".card-title").html("<i class='fas fa-file-alt mr-1'></i> Añadir nuevo módulo");
        let formBody = $("#addEditForm");
        hideInputElement(formBody, ".form-group #txtModuleId");
        hideInputElement(formBody, ".form-group .custom-switch");
        blockInputElement(formBody, ".form-group #txtModuleId", true);
        collapseCard(card, false);
    });

});

function blockInputElement(formBody, inputId, isBlocked) {
    formBody.find(inputId).prop("disabled", isBlocked);
    formBody.find(inputId).prop("readonly", !isBlocked);
}

function hideInputElement(formBody, input) {
    if (!formBody.find(input).parent().hasClass("d-none")) {
        formBody.find(input).parent().addClass("d-none");
    }
}

function collapseCard(card, isCollapsed) {
    if (isCollapsed) {
        card.find("button .fas").toggleClass("fa-angle-down fa-angle-up");
    } else {
        card.find("button .fas").toggleClass("fa-angle-up fa-angle-down");
    }
    card.find(".card-body").slideToggle("slow");
    card.toggleClass("collapsed-card");
}

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function displayStatus(status) {
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
    let elementHTML = "<span class='badge badge-" + badge[status].class + "'>";
    elementHTML += "<i class='fas fa-" + badge[status].icon + "'></i> <span>" + badge[status].text + "</span>";
    elementHTML += "</span>";
    return elementHTML;
}

function showFormEditAndViewDetailModule(button, isEditable) {
    let url = contextPath + "/admincrud/modulos?action=findById";
    let moduleId = $(button).data("moduleId");
    $.ajax({
        url: url,
        type: "GET",
        data: {moduleId: moduleId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundModule = response.result;
                if (isEditable) {
                    let card = $("#collapseCard");
                    let cardHeader = card.find(".card-header");
                    cardHeader.find(".card-title").html("<i class='fas fa-edit mr-1'></i> Editar datos del módulo");
                    let formBody = $("#addEditForm");
                    formBody.find(".form-group.d-none").removeClass("d-none");
                    blockInputElement(formBody, ".form-group #txtModuleId", false);
                    formBody.find(".form-group #txtModuleId").val(foundModule.moduleId);
                    formBody.find(".form-group #txtTitle").val(foundModule.title);
                    formBody.find(".form-group #txtDescription").val(foundModule.description);
                    formBody.find(".form-group #chkActive").attr("checked", foundModule.active);
                    let hasClass = card.hasClass("collapsed-card");
                    if (hasClass) {
                        collapseCard(card, true);
                    }
                    formBody.find(".form-group #txtTitle").focus();
                    formBody.find(".form-group #txtTitle").select();
                    isNew = false;
                } else {
                    let modal = $("#viewDetailModal");
                    let modalBody = modal.find(".modal-body");
                    modalBody.empty();
                    let container = "<dl>";
                    container += "<dt>Título</dt>";
                    container += "<dd>" + foundModule.title + "</dd>";
                    container += "<dt>Descripción</dt>";
                    container += "<dd>" + (foundModule.description ?? "-") + "</dd>";
                    container += "<dt>" + displayStatus(foundModule.active) + "</dt>";
                    container += "</dl>";
                    modalBody.append(container);
                    modal.modal("show");
                }
            }
        }
    });
}

function disableModule(button) {
    let title = $(button).data("title");
    Swal.fire({
        title: "¿Estás seguro que quieres desactivar el módulo: " + title + " ?",
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
                type: "POST",
                data: {moduleId: moduleId},
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        let table = $("#tblModules").DataTable();
                        table.ajax.reload(null, false);
                        Swal.fire("Desactivado!", response.message, response.status);
                    }
                }
            });
        }
    });
}

function findAllModules() {
    let url = contextPath + "/admincrud/modulos?action=findAll";
    let table = $("#tblModules").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            {data: "title"},
            {
                data: "active",
                className: "text-center",
                render: function (data, type, row) {
                    return displayStatus(data);
                }
            },
            {
                data: "moduleId",
                className: "text-center",
                render: function (data, type, row) {
                    let isEditable = true;
                    let buttons = "<div class='btn-group btn-group-sm'>";
                    buttons += "<button type='button' onclick='showFormEditAndViewDetailModule(this, " + !isEditable + ")' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-module-id='" + data + "'><i class='fas fa-eye'></i></button>";
                    buttons += "<button type='button' onclick='showFormEditAndViewDetailModule(this, " + isEditable + ")' class='btn btn-warning' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-module-id='" + data + "'><i class='fas fa-pen'></i></button>"
                    buttons += "<button type='button' onclick='disableModule(this)' " + (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-module-id='" + data + "' data-title='" + row.title + "'><i class='fas fa-trash'></i></button>"
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