let isNew = true;
$(function () {

    findAllModules();

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnSave").click(function () {
        $("#addEditForm").validate({
            rules: {
                title: {required: true, minlength: 3}
            },
            submitHandler: function (form) {
                let url = contextPath + "/admincrud/modulos?action=create";
                let title = "Registrado!";
                if (!isNew) {
                    url = contextPath + "/admincrud/modulos?action=update";
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
                            let formBody = $("#addEditForm");
                            setDisplayNoneToInputs(formBody, ".form-group #txtModuleId");
                            setDisplayNoneToInputs(formBody, ".form-group .custom-switch");
                            setDisabledAndReadOnlyToInputId(formBody, ".form-group #txtModuleId", true);
                            let collapseCard = $("#collapseCard");
                            setCollapseOnCard(collapseCard, false);
                            $("#tblModules").DataTable().ajax.reload(null, false);
                            Swal.fire(title, response.message, response.status);
                            if (!isNew) isNew = true;
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
        isNew = true;
        let formBody = $("#addEditForm");
        setDisplayNoneToInputs(formBody, ".form-group #txtModuleId");
        setDisplayNoneToInputs(formBody, ".form-group .custom-switch");
        setDisabledAndReadOnlyToInputId(formBody, ".form-group #txtModuleId", true);
        let collapseCard = $("#collapseCard");
        setCollapseOnCard(collapseCard, false);
    });

});

function setDisabledAndReadOnlyToInputId(formBody, inputId, flag) {
    formBody.find(inputId).prop("disabled", flag);
    formBody.find(inputId).prop("readonly", !flag);
}

function setDisplayNoneToInputs(formBody, input) {
    formBody.find(input).parent().addClass("d-none");
}

function setCollapseOnCard(formBody, flag) {
    if (flag) {
        formBody.find("button .fas").toggleClass("fa-plus fa-minus");
    } else {
        formBody.find("button .fas").toggleClass("fa-minus fa-plus");
    }
    formBody.find(".card-body").slideToggle("slow");
    formBody.toggleClass("collapsed-card");
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
                    let formBody = $("#addEditForm");
                    formBody.find(".form-group.d-none").removeClass("d-none");
                    setDisabledAndReadOnlyToInputId(formBody, ".form-group #txtModuleId", false);
                    formBody.find(".form-group #txtModuleId").val(foundModule.moduleId);
                    formBody.find(".form-group #txtTitle").val(foundModule.title);
                    formBody.find(".form-group #txtDescription").val(foundModule.description);
                    formBody.find(".form-group #chkActive").attr("checked", foundModule.active);
                    let collapseCard = $("#collapseCard");
                    let exists = collapseCard.hasClass("collapsed-card");
                    if (exists) {
                        setCollapseOnCard(collapseCard, true);
                    }
                    formBody.find(".form-group #txtTitle").focus();
                    formBody.find(".form-group #txtTitle").select();
                    isNew = false;
                } else {
                    let modalBody = $("#viewDetailModal .modal-body");
                    modalBody.empty();
                    let elementHTML = "<dl>";
                    elementHTML += "<dt>Título</dt>";
                    elementHTML += "<dd>" + foundModule.title + "</dd>";
                    elementHTML += "<dt>Descripción</dt>";
                    elementHTML += "<dd>" + (foundModule.description ?? "-") + "</dd>";
                    elementHTML += "<dt>" + displayStatus(foundModule.active) + "</dt>";
                    elementHTML += "</dl>";
                    modalBody.append(elementHTML);
                    $("#viewDetailModal").modal("show");
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
                        $("#tblModules").DataTable().ajax.reload(null, false);
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
            { data: "title" },
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
                    let elementHTML = "<div class='btn-group btn-group-sm'>";
                    elementHTML += "<button type='button' onclick='showFormEditAndViewDetailModule(this, false)' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-module-id='" + data + "'><i class='fas fa-eye'></i></button>";
                    elementHTML += "<button type='button' onclick='showFormEditAndViewDetailModule(this, true)' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-module-id='" + data + "'><i class='fas fa-pen'></i></button>"
                    elementHTML += "<button type='button' onclick='disableModule(this)' " +  (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-module-id='" + data + "' data-title='" + row.title + "'><i class='fas fa-trash'></i></button>"
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