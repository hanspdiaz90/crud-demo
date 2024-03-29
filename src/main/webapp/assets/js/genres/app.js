let isNew = false;
$(function () {

    findAllGenres();

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#addEditForm").validate({
        rules: {
            name: {required: true, minlength: 3}
        },
        submitHandler: function (form) {
            let url = contextPath + "/admincrud/generos?action=create";
            let alertTitle = "Registrado!";
            if (!isNew) {
                url = contextPath + "/admincrud/generos?action=update";
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
                        let modal = $("#addEditModal");
                        let modalHeader = modal.find(".modal-header");
                        modalHeader.find(".modal-title").html("<i class='fas fa-file-alt mr-1'></i> Añadir nuevo género literario");
                        modal.modal("hide");
                        let table = $("#tblGenres").DataTable();
                        table.ajax.reload(null, false);
                        Swal.fire(alertTitle, response.message, response.status);
                        if (isNew) isNew = false;
                    }
                },
                processData: false,
                cache: false,
            });
        }
    });

    $("#btnReset").click(function () {
        resetInvalidForm(this, "#addEditForm");
        isNew = false;
        let modal = $("#addEditModal");
        let modalHeader = modal.find(".modal-header");
        modalHeader.find(".modal-title").html("<i class='fas fa-file-alt mr-1'></i> Añadir nuevo género literario");
        let modalBody = modal.find(".modal-body");
        hideInputElement(modalBody, ".form-group #txtGenreId");
        hideInputElement(modalBody, ".form-group .custom-switch");
        blockInputElement(modalBody, ".form-group #txtGenreId", true);
    });

    $("#btnNew").click(function () {
        isNew = true;
        let modal = $("#addEditModal");
        let modalHeader = modal.find(".modal-header");
        modalHeader.find(".modal-title").html("<i class='fas fa-file-alt mr-1'></i> Añadir nuevo género literario");
        let modalBody = modal.find(".modal-body");
        hideInputElement(modalBody, ".form-group #txtGenreId");
        hideInputElement(modalBody, ".form-group .custom-switch");
        blockInputElement(modalBody, ".form-group #txtGenreId", true);
    });

});

function blockInputElement(modalBody, inputId, isBlocked) {
    modalBody.find(inputId).prop("disabled", isBlocked);
    modalBody.find(inputId).prop("readonly", !isBlocked);
}

function hideInputElement(modalBody, input) {
    if (!modalBody.find(input).parent().hasClass("d-none")) {
        modalBody.find(input).parent().addClass("d-none");
    }
}

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
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

function showModalEditAndViewDetailGenre(button, isEditable) {
    let url = contextPath + "/admincrud/generos?action=findById";
    let genreId = $(button).data("genreId");
    $.ajax({
        url: url,
        type: "GET",
        data: {genreId: genreId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundGenre = response.result;
                if (isEditable) {
                    let modal = $("#addEditModal");
                    let modalHeader = modal.find(".modal-header");
                    modalHeader.find(".modal-title").html("<i class='fas fa-edit mr-1'></i> Editar datos del género literario");
                    let modalBody = modal.find(".modal-body");
                    modalBody.find(".form-group.d-none").removeClass("d-none");
                    blockInputElement(modalBody, ".form-group #txtGenreId", false);
                    modalBody.find(".form-group #txtGenreId").val(foundGenre.genreId);
                    modalBody.find(".form-group #txtGenre").val(foundGenre.name);
                    modalBody.find(".form-group #chkActive").attr("checked", foundGenre.active);
                    modal.modal("show");
                    isNew = false;
                } else {
                    let modal = $("#viewDetailModal");
                    let modalBody = modal.find(".modal-body");
                    modalBody.empty();
                    let container = "<dl>";
                    container += "<dt>Género Literario</dt>";
                    container += "<dd>" + foundGenre.name + "</dd>";
                    container += "<dt>" + showStatus(foundGenre.active) + "</dt>";
                    container += "</dl>";
                    modalBody.append(container);
                    modal.modal("show");
                }
            }
        }
    });
}

function disableGenre(button) {
    let genreName = $(button).data("genreName");
    Swal.fire({
        title: "¿Estás seguro que quieres desactivar el género literario: " + genreName + " ?",
        text: "No podrás revertir esta operación!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = contextPath + "/admincrud/generos?action=disableById";
            let genreId = $(button).data("genreId");
            $.ajax({
                url: url,
                type: "POST",
                data: {genreId: genreId},
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        let table = $("#tblGenres").DataTable();
                        table.ajax.reload(null, false);
                        Swal.fire("Desactivado!", response.message, response.status);
                    }
                }
            });
        }
    });
}

function findAllGenres() {
    let url = contextPath + "/admincrud/generos?action=findAll";
    let table = $("#tblGenres").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            {data: "name"},
            {
                data: "active",
                className: "text-center",
                render: function (data, type, row) {
                    return showStatus(data);
                }
            },
            {
                data: "genreId",
                className: "text-center",
                render: function (data, type, row) {
                    let isEditable = true;
                    let buttons = "<div class='btn-group btn-group-sm'>";
                    buttons += "<button type='button' onclick='showModalEditAndViewDetailGenre(this, " + !isEditable + ")' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='MÁS DETALLE' data-genre-id='" + data + "'><i class='fas fa-eye'></i></button>";
                    buttons += "<button type='button' onclick='showModalEditAndViewDetailGenre(this, " + isEditable + ")' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='EDITAR' data-genre-id='" + data + "'><i class='fas fa-pen'></i></button>";
                    buttons += "<button type='button' onclick='disableGenre(this)' " + (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='DESACTIVAR'  data-genre-id='" + data + "' data-genre-name='" + row.name + "'><i class='fas fa-trash'></i></button>";
                    buttons += "</div>";
                    return buttons;
                }
            }
        ]
    });

    table.on("draw", function () {
        $("[data-tooltip='tooltip']").tooltip();
    });

}