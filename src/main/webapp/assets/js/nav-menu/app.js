let isNew = true;
$(function () {

    findAllMenus();
    findActiveModules();
    findAllParents();

    let formBody = $("#addEditForm");
    setDisabledSelect(formBody, ".form-group #cbxParents", true);

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnSave").click(function () {
        $("#addEditForm").validate({
            rules: {
                title: {required: true, minlength: 3},
                icon: {required: true, minlength: 10},
                module: {
                    selectModule: true
                }
            },
            submitHandler: function (form) {
                let url = contextPath + "/admincrud/menu-navegacion?action=create";
                let title = "Registrado!";
                if (!isNew) {
                    url = contextPath + "/admincrud/menu-navegacion?action=update";
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
                            setDisplayNoneToInputs(formBody, ".form-group #txtMenuId");
                            setDisplayNoneToInputs(formBody, ".form-group .custom-switch");
                            setDisabledAndReadOnlyToInputId(formBody, ".form-group #txtMenuId", true);
                            let collapseCard = $("#collapseCard");
                            setCollapseOnCard(collapseCard, false);
                            let table = $("#tblNavMenu").DataTable();
                            table.ajax.reload(null, false);
                            findAllParents();
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
        setDisplayNoneToInputs(formBody, ".form-group #txtMenuId");
        setDisplayNoneToInputs(formBody, ".form-group .custom-switch");
        setDisabledAndReadOnlyToInputId(formBody, ".form-group #txtMenuId", true);
        setDisabledSelect(formBody, ".form-group #cbxParents", true);
        let collapseCard = $("#collapseCard");
        setCollapseOnCard(collapseCard, false);
    });

    $("#isParent").change(function () {
        let formBody = $("#addEditForm");
        if (this.checked) {
            setDisabledSelect(formBody, ".form-group #cbxParents", false);
        } else {
            setDisabledSelect(formBody, ".form-group #cbxParents", true);
        }
    });

});

function setDisabledSelect(formBody, select, flag) {
    formBody.find(select).prop("disabled", flag);
}

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

function displayIcon(icon) {
    let elementHTML = "<span><i class='" + icon + "'></i></span>";
    return elementHTML;
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

function showFormEditAndViewDetailMenu(button, isEditable) {
    let url = contextPath + "/admincrud/menu-navegacion?action=findById";
    let menuId = $(button).data("menuId");
    $.ajax({
        url: url,
        type: "GET",
        data: {menuId: menuId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundMenu = response.result;
                if (isEditable) {
                    let formBody = $("#addEditForm");
                    formBody.find(".form-group.d-none").removeClass("d-none");
                    setDisabledAndReadOnlyToInputId(formBody, ".form-group #txtMenuId", false);
                    formBody.find(".form-group #txtMenuId").val(foundMenu.moduleId);
                    formBody.find(".form-group #txtTitle").val(foundMenu.title);
                    formBody.find(".form-group #txtDescription").val(foundMenu.description);
                    formBody.find(".form-group #chkActive").attr("checked", foundMenu.active);
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
                    elementHTML += "<dd>" + foundMenu.title + "</dd>";
                    elementHTML += "<dt>Descripción</dt>";
                    elementHTML += "<dd>" + (foundMenu.description ?? "-") + "</dd>";
                    elementHTML += "<dt>" + displayStatus(foundMenu.active) + "</dt>";
                    elementHTML += "</dl>";
                    modalBody.append(elementHTML);
                    $("#viewDetailModal").modal("show");
                }
            }
        }
    });
}

function disableMenu(button) {
    let title = $(button).data("title");
    Swal.fire({
        title: "¿Estás seguro que quieres desactivar el menú: " + title + " ?",
        text: "¡No podrás revertir esta operación!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = contextPath + "/admincrud/menu-navegacion?action=disableById";
            let menuId = $(button).data("menuId");
            $.ajax({
                url: url,
                type: "POST",
                data: {moduleId: menuId},
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        let table = $("#tblNavMenu").DataTable();
                        table.ajax.reload(null, false);
                        Swal.fire("Desactivado!", response.message, response.status);
                    }
                }
            });
        }
    });
}

function findAllParents() {
    let url = contextPath + "/admincrud/menu-navegacion?action=findAll";
    $.ajax({
        url: url,
        method: "GET",
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let result = response.result;
                let select = $("#cbxParents");
                select.find("option").remove();
                select.append("<option value='0'>[ Seleccionar menú ]</option>");
                $.each(result, function (key, value) {
                    let option = "<option value='" + value.menuId + "'>" + value.title + "</option>";
                    select.append(option);
                });
            }
        }
    });
}

function findActiveModules() {
    let url = contextPath + "/admincrud/menu-navegacion?action=findActiveModules";
    $.ajax({
        url: url,
        method: "GET",
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let result = response.result;
                let select = $("#cbxModules");
                select.find("option").remove();
                select.append("<option value='0'>[ Seleccionar módulo ]</option>");
                $.each(result, function (key, value) {
                    let option = "<option value='" + value.moduleId + "'>" + value.title + "</option>";
                    select.append(option);
                });
            }
        }
    });
}

function findAllMenus() {
    let url = contextPath + "/admincrud/menu-navegacion?action=findAll";
    let table = $("#tblNavMenu").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        rowId: "menuId",
        columns: [
            { data: "menuId" },
            { data: "title" },
            {
                data: "module.title",
                render: function (data, type, row) {
                    return data.toString().toUpperCase();
                }
            },
            {
                data: "icon",
                className: "text-center",
                render: function (data, type, row) {
                    return displayIcon(data);
                }
            },
            {
                data: "route",
                defaultContent: "-"
            },
            {
                data: "parentId",
                className: "text-center",
                render: function (data, type, row) {
                    return data;
                }
            },
            {
                data: "active",
                className: "text-center",
                render: function (data, type, row) {
                    return displayStatus(data);
                }
            },
            {
                data: "menuId",
                className: "text-center",
                render: function (data, type, row) {
                    let elementHTML = "<div class='btn-group btn-group-sm'>";
                    elementHTML += "<button type='button' onclick='showFormEditAndViewDetailMenu(this, false)' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-menu-id='" + data + "'><i class='fas fa-eye'></i></button>";
                    elementHTML += "<button type='button' onclick='showFormEditAndViewDetailMenu(this, true)' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-menu-id='" + data + "'><i class='fas fa-pen'></i></button>"
                    elementHTML += "<button type='button' onclick='disableMenu(this)' " +  (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-menu-id='" + data + "' data-title='" + row.title + "'><i class='fas fa-trash'></i></button>"
                    elementHTML += "</div>"
                    return elementHTML;
                }
            }
        ]
    });
    table.on("draw", function () {
        $("[data-tooltip='tooltip']").tooltip();
        let rows = [];
        let nodes = [];
        table.rows().iterator("row", function (context, index) {
            let data = this.row(index).data();
            let row = this.row(index).node();
            // let row = $(this.row(index).node());
            rows.push(data);
            nodes.push(row);
        });

        // let row = $(this.row(index).node())
        // console.log(nodes[9][0].cells[5].innerText);
        // nodes[9][0].cells[5].innerText = "HOLA";

        // let row = this.row(index).node();
        // console.log($(nodes[9]).find("td:eq(5)").text());
        // $(nodes[9]).find("td:eq(5)").text("HOLA");

        // $.each(rows, function (index, value) {
        //     let temp = $("#tblNavMenu").DataTable();
        //     let currentRow = $(temp.rows(index).node());
        //     console.log(currentRow);
        //     //onsole.log(currentRow[0].parentId);
        //     // if (value.parentId === 0) {
        //     //
        //     // }
        // });
    });
}