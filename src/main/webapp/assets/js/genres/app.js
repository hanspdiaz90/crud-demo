$(function () {

    getAllGenres();

    $("#genreAddForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnAdd").click(function () {
        $("#genreAddForm").validate({
            rules: {
                name: { required: true, minlength: 5 }
            },
            submitHandler: function (form) {
                let url = contextPath + "/admincrud/generos?action=create";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.success) {
                            $(form).trigger("reset");
                            $("#genreAddModal").modal("hide");
                            $("#genresDataTable").DataTable().ajax.reload(null, false);
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
        resetInvalidForm(this, "#genreAddForm");
    });

});

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function viewDetailsGenre(button) {
    let url = contextPath + "/admincrud/generos?action=findById";
    let genreId = $(button).data("genreId");
    $.ajax({
        url: url,
        method: "GET",
        data: { genreId: genreId },
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let genreObj = response.result;
                let classNameBadge = genreObj.active ? "success" : "danger";
                let classNameIcon = genreObj.active ? "check" : "times";
                let statusText = genreObj.active ? "ACTIVO" : "INACTIVO";
                let modalBody = $("#genreViewModal .modal-body");
                modalBody.empty();
                let elementHTML = "<dl>";
                    elementHTML += "<dt>Género Literario</dt>";
                    elementHTML += "<dd>" + genreObj.name + "</dd>";
                    elementHTML += "<dt>Activo?</dt>";
                    elementHTML += "<dd><span class='badge badge-"+ classNameBadge + "'><i class='fas fa-"+ classNameIcon + "'></i> " + statusText+ "</span></dd>";
                    elementHTML += "</dl>";
                modalBody.append(elementHTML);
                $("#genreViewModal").modal("show");
            }
        }
    });
}

function disableGenre(button) {
    let genreName = $(button).data("genreName");
    Swal.fire({
        title: "¿Estás seguro que quieres deshabilitar el género literario: " + genreName + " ?",
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
                method: "POST",
                data: { genreId: genreId },
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        $("#genresDataTable").DataTable().ajax.reload(null, false);
                        Swal.fire("Deshabilitado!", response.message, response.status);
                    }
                }
            });
        }
    });
}

function getAllGenres() {
    let url = contextPath + "/admincrud/generos?action=findAll";
    let table = $("#genresDataTable").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            { data: "name" },
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
                    elementHTML += "<button type='button' onclick='viewDetailsGenre(this)' class='btn btn-info' data-toggle='modal' data-target='#genreViewModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-genre-id='" + row.genreId + "'><i class='fas fa-eye'></i></button>";
                    if (row.active) {
                        elementHTML += "<button type='button' onclick='editGenre(this)' class='btn btn-warning' data-toggle='modal' data-target='#genreEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-genre-id='" + row.genreId + "'><i class='fas fa-pen'></i></button>"
                        elementHTML += "<button type='button' onclick='disableGenre(this)' class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-genre-id='" + row.genreId + "' data-genre-name='" + row.name + "'><i class='fas fa-flag'></i></button>"
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