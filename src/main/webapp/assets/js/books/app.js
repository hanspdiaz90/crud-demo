$(function () {

    getAllBooks();

    $("#cbxAuthors").select2({
        ajax: {
            url: contextPath + "/biblioteca/libros?accion=listarAutoresActivos",
            dataType: "JSON",
            delay: 250,
            data: function (params) {
                return { filter: params.term };
            },
            processResults: function (response) {
                let results = $.map(response.result, function (item) {
                    return {
                        id: item.authorId,
                        text: item.firstName + " " + item.lastName
                    };
                });
                return {
                    results: results
                };
            },
            cache: true
        },
        placeholder: {
            id: "-1",
            text: "[ Seleccionar autor ]"
        },
        theme: "bootstrap4",
        allowClear: true
    });
    $("#cbxPublishers").select2({
        ajax: {
            url: contextPath + "/biblioteca/libros?accion=listarEditorialesActivos",
            dataType: "JSON",
            delay: 250,
            data: function (params) {
                return { filter: params.term };
            },
            processResults: function (response) {
                console.log(response.result);
                let results = $.map(response.result, function (item) {
                    return {
                        id: item.publisherId,
                        text: item.name
                    };
                });
                return {
                    results: results
                };
            },
            cache: true
        },
        placeholder: {
            id: "-1",
            text: "[ Seleccionar editorial ]"
        },
        theme: "bootstrap4",
        allowClear: true
    });
    $("#cbxGenres").select2({
        ajax: {
            url: contextPath + "/biblioteca/libros?accion=listarGenerosActivos",
            dataType: "JSON",
            delay: 250,
            data: function (params) {
                return { filter: params.term };
            },
            processResults: function (response) {
                let results = $.map(response.result, function (item) {
                    return {
                        id: item.genreId,
                        text: item.name
                    };
                });
                return {
                    results: results
                };
            },
            cache: true
        },
        placeholder: {
            id: "-1",
            text: "[ Seleccionar género ]"
        },
        theme: "bootstrap4",
        allowClear: true
    });

    $("#bookAddForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnAdd").click(function () {
        $("#bookAddForm").validate({
            rules: {
                title: { required: true },
                isbn: { required: true, minlength: 13, maxlength:13, digits: true },
                yearEdition: { required: true },
                coverImage: { required: true },
                author: {required: true },
                publisher: {required: true },
                genre: {required: true },
                numberPages: { required: true },
                copies: { required: true },
                price: { required: true },
                review: { required: true }
            },
            submitHandler: function (form) {
                let url = contextPath + "/biblioteca/libros?accion=crear";
                let formData = $(form).serialize();
                $.ajax({
                    url: url,
                    type: "POST",
                    data: formData,
                    dataType: "JSON",
                    success: function (response) {
                        if (response.status == "success") {
                            $(form).trigger("reset");
                            $("#authorAddModal").modal("hide");
                            $("#authorsDataTable").DataTable().ajax.reload(null, false);
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
        resetInvalidForm(this, "#bookAddForm");
    });

});

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function viewDetailsBook(button) {
    let url = contextPath + "/biblioteca/libros?accion=verDetalles";
    let bookId = $(button).data("bookId");
    $.ajax({
        url: url,
        method: "GET",
        data: { bookId: bookId },
        dataType: "JSON",
        success: function (response) {
            if (response.status == "success") {
                let bookObj = response.result;
                let classNameBadge = bookObj.active ? "success" : "danger";
                let classNameIcon = bookObj.active ? "check" : "times";
                let statusText = bookObj.active ? "ACTIVO" : "INACTIVO";
                let modalBody = $("#bookViewModal .modal-body");
                modalBody.empty();
                let regex = /(\d{3})?(\d{3})?(\d{5})?(\d)?(\d)/;
                let elementHTML = "<dl>";
                    elementHTML += "<dt>ISBN</dt>";
                    elementHTML += "<dd>" + bookObj.isbn.replace(regex, "$1-$2-$3-$4-$5") + "</dd>";
                    elementHTML += "<dt>Título</dt>";
                    elementHTML += "<dd>" + bookObj.title + "</dd>";
                    elementHTML += "<dt>Reseña</dt>";
                    elementHTML += "<dd>" + bookObj.review + "</dd>";
                    elementHTML += "<dt>Autor</dt>";
                    elementHTML += "<dd>" + bookObj.author.firstName + " " + bookObj.author.lastName + "</dd>";
                    elementHTML += "<dt>Editorial</dt>";
                    elementHTML += "<dd>" + bookObj.publisher.name + "</dd>";
                    elementHTML += "<dt>Género Literario</dt>";
                    elementHTML += "<dd>" + bookObj.genre.name + "</dd>";
                    elementHTML += "<dt>Precio</dt>";
                    elementHTML += "<dd>" + bookObj.price + "</dd>";
                    elementHTML += "<dt>Activo?</dt>";
                    elementHTML += "<dd><span class='badge badge-" + classNameBadge + "'><i class='fas fa-" + classNameIcon + "'></i> " + statusText+ "</span></dd>";
                    elementHTML += "</dl>";
                modalBody.append(elementHTML);
                $("#bookViewModal").modal("show");
            }
        }
    });
}

function disableBook(button) {
    let bookTitle = $(button).data("bookTitle");
    Swal.fire({
        title: "¿Estás seguro que quieres deshabilitar el libro: " + bookTitle + " ?",
        text: "No podrás revertir esta operación!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = contextPath + "/biblioteca/libros?accion=deshabilitar";
            let bookId = $(button).data("bookId");
            $.ajax({
                url: url,
                method: "GET",
                data: { bookId: bookId },
                dataType: "JSON",
                success: function (response) {
                    if (response.status == "success") {
                        $("#gendersDataTable").DataTable().ajax.reload(null, false);
                        Swal.fire("Deshabilitado!", response.message, "success");
                    }
                }
            });
        }
    });
}

// function loadActiveAuthors() {
//     let url = "/biblioteca/libros";
//     $.ajax({
//         url: url,
//         method: "GET",
//         data: { accion: "cargarAutores" },
//         dataType: "JSON",
//         success: function (response) {
//             if (response.ok) {
//                 let objAuthors = response.result;
//                 let select = $("#cbxAutores");
//                 // select.find("option").remove();
//                 $.each(objAuthors, function (key, value) {
//                     select.append("<option value='" + value.id + "'>" + value.nombres + " " + value.apellidos + " </option>");
//                     // $("<option>").val(value.id).text(value.nombres + " " + value.apellidos).appendTo(select);
//                 })
//             }
//         }
//     });
// }

function getAllBooks() {
    let url = contextPath + "/biblioteca/libros?accion=listar";
    let table = $("#booksDataTable").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            {
                data: null,
                render: function (data, type, row) {
                    let regex = /(\d{3})?(\d{3})?(\d{5})?(\d)?(\d)/;
                    return row.isbn.replace(regex, "$1-$2-$3-$4-$5");
                }
            },
            { data: "title" },
            {
                data: null,
                render: function (data, type, row) {
                    return row.author.firstName + " " + row.author.lastName;
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    return row.publisher.name;
                }
            },
            {
                data: null,
                render: function (data, type, row) {
                    return row.genre.name;
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
                    elementHTML += "<button type='button' onclick='viewDetailsBook(this)' class='btn btn-info' data-toggle='modal' data-target='#bookViewModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-book-id='" + row.bookId + "'><i class='fas fa-eye'></i></button>";
                    if (row.active) {
                        elementHTML += "<button type='button' onclick='editBook(this)' class='btn btn-warning' data-toggle='modal' data-target='#bookEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-book-id='" + row.bookId + "'><i class='fas fa-pen'></i></button>"
                        elementHTML += "<button type='button' onclick='disableBook(this)' class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-book-id='" + row.bookId + "' data-book-title='" + row.title + "'><i class='fas fa-flag'></i></button>"
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