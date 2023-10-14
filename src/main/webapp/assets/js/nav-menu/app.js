let isNew = true;
$(function () {

    findAllMenus();
    findActiveModules();
    findAllParents();

    let formBody = $("#addEditForm");
    activateSelection(formBody, ".form-group #cbxParents", true);

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

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
            let alertTitle = "Registrado!";
            if (!isNew) {
                url = contextPath + "/admincrud/menu-navegacion?action=update";
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
                        //let formBody = $("#addEditForm");
                        hideInputElement(formBody, ".form-group #txtMenuId");
                        hideInputElement(formBody, ".form-group .custom-switch");
                        blockInputElement(formBody, ".form-group #txtMenuId", true);
                        let card = $("#collapseCard");
                        collapseCard(card, false);
                        let table = $("#tblNavMenu").DataTable();
                        table.ajax.reload(null, false);
                        findAllParents();
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
        //let formBody = $("#addEditForm");
        hideInputElement(formBody, ".form-group #txtMenuId");
        hideInputElement(formBody, ".form-group .custom-switch");
        blockInputElement(formBody, ".form-group #txtMenuId", true);
        activateSelection(formBody, ".form-group #cbxParents", true);
        let collapseCard = $("#collapseCard");
        collapseCard(collapseCard, false);
    });

    $("#isParent").change(function () {
        //let formBody = $("#addEditForm");
        if (this.checked) {
            activateSelection(formBody, ".form-group #cbxParents", false);
        } else {
            activateSelection(formBody, ".form-group #cbxParents", true);
        }
    });

});

function activateSelection(formBody, select, flag) {
    formBody.find(select).prop("disabled", flag);
}

function blockInputElement(formBody, inputId, isBlocked) {
    formBody.find(inputId).prop("disabled", isBlocked);
    formBody.find(inputId).prop("readonly", !isBlocked);
}

function hideInputElement(formBody, input) {
    if (!formBody.find(input).parent().hasClass("d-none")) {
        formBody.find(input).parent().addClass("d-none");
    }
}

function collapseCard(formBody, isCollapsed) {
    if (isCollapsed) {
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

function showIcon(icon) {
    let span = "<span><i class='" + icon + "'></i></span>";
    return span;
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
                    blockInputElement(formBody, ".form-group #txtMenuId", false);
                    formBody.find(".form-group #txtMenuId").val(foundMenu.menuId);
                    formBody.find(".form-group #txtTitle").val(foundMenu.title);
                    formBody.find(".form-group #txtIcon").val(foundMenu.icon);
                    formBody.find(".form-group #txtRoute").val(foundMenu.route);
                    formBody.find(".form-group #chkActive").attr("checked", foundMenu.active);
                    let card = $("#collapseCard");
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
                    container += "<dd>" + foundMenu.title + "</dd>";
                    container += "<dt>Ruta</dt>";
                    container += "<dd>" + (foundMenu.route ?? "-") + "</dd>";
                    container += "<dt>" + showStatus(foundMenu.active) + "</dt>";
                    container += "</dl>";
                    modalBody.append(container);
                    modalBody.modal("show");
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
                data: {menuId: menuId},
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
            {data: "menuId"},
            {data: "title"},
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
                    return showIcon(data);
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
                    return showStatus(data);
                }
            },
            {
                data: "menuId",
                className: "text-center",
                render: function (data, type, row) {
                    let isEditable = true;
                    let buttons = "<div class='btn-group btn-group-sm'>";
                    buttons += "<button type='button' onclick='showFormEditAndViewDetailMenu(this, " + !isEditable + ")' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-menu-id='" + data + "'><i class='fas fa-eye'></i></button>";
                    buttons += "<button type='button' onclick='showFormEditAndViewDetailMenu(this, " + isEditable + ")' class='btn btn-warning' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-menu-id='" + data + "'><i class='fas fa-pen'></i></button>"
                    buttons += "<button type='button' onclick='disableMenu(this)' " + (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar' data-menu-id='" + data + "' data-title='" + row.title + "'><i class='fas fa-trash'></i></button>"
                    buttons += "</div>"
                    return buttons;
                }
            }
        ]
    });

    table.on("draw", function () {
        $("[data-tooltip='tooltip']").tooltip();

        /* MÉTODO NRO 01 */
        let nodes = table.rows().nodes().to$();
        $.each(nodes, function (i, element) {
            let parentText = "";
            let parentId = $(element).find("td:eq(5)").text();
            if (parentId == 0) {
                parentText = "-";
                $(element).find("td:eq(5)").text(parentText);
            } else {
                $.each(nodes, function (j, anotherElement) {
                    let menuId = $(anotherElement).find("td:eq(0)").text();
                    if (parentId == menuId) {
                        parentText = $(anotherElement).find("td:eq(1)").text();
                        $(element).find("td:eq(5)").text(parentText);
                        return;
                    }
                });
            }
        });

        /* MÉTODO NRO 02 */
        // let nodes = [];
        // table.rows().iterator("row", function (context, index) {
        //     let node = this.row(index).node();
        //     nodes.push(node);
        // });
        // $.each(nodes, function (i, element) {
        //     let parentText = "";
        //     let parentId = $(element).find("td:eq(5)").text();
        //     if (parentId == 0) {
        //         parentText = "-";
        //         $(element).find("td:eq(5)").text(parentText);
        //     } else {
        //         $.each(nodes, function (j, anotherElement) {
        //             let menuId = $(anotherElement).find("td:eq(0)").text();
        //             if (parentId == menuId) {
        //                 parentText = $(anotherElement).find("td:eq(1)").text();
        //                 $(element).find("td:eq(5)").text(parentText);
        //                 return;
        //             }
        //         });
        //     }
        // });

        /* MÉTODO NRO 03 */
        // let data = table.data().toArray();
        // let nodes = table.rows().nodes().to$();
        // data.forEach(function (value, index, array) {
        //     let parentText;
        //     let parentId = value.parentId;
        //     if (parentId == 0) {
        //         parentText = "-";
        //         $(nodes[index]).find("td:eq(5)").text(parentText);
        //     } else {
        //         let foundValue = array.find(function (anotherValue) {
        //             let menuId = anotherValue.menuId;
        //             return parentId == menuId;
        //         });
        //         parentText = foundValue.title;
        //         $(nodes[index]).find("td:eq(5)").text(parentText);
        //     }
        // });

        /* MÉTODO NRO 03 */
        // let data = [];
        // let nodes = [];
        // table.rows().iterator("row", function (context, index) {
        //     let row = this.row(index).data();
        //     let node = this.row(index).node();
        //     data.push(row);
        //     nodes.push(node);
        // });
        // data.forEach(function (value, index, array) {
        //     let parentText = "";
        //     let parentId = value.parentId;
        //     if (parentId == 0) {
        //         parentText = "-";
        //         $(nodes[index]).find("td:eq(5)").text(parentText);
        //     } else {
        //         let foundValue = array.find(function (anotherValue) {
        //             let menuId = anotherValue.menuId;
        //             return parentId == menuId;
        //         });
        //         let parentText = foundValue.title;
        //         $(nodes[index]).find("td:eq(5)").text(parentText);
        //     }
        // });

    });

}