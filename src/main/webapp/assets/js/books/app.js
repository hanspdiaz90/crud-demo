let isNew = false;
$(function () {

    findAllBooks();

    $("#cbxAuthors").select2({
        ajax: {
            url: contextPath + "/admincrud/libros?action=findActiveAuthors",
            dataType: "JSON",
            delay: 250,
            data: function (params) {
                return { filter: params.term };
            },
            processResults: function (response) {
                let data = $.map(response.result, function (item) {
                    return {
                        id: item.authorId,
                        text: showFullNames(item.firstName, item.lastName)
                    };
                });
                return {
                    results: data
                };
            },
            cache: true
        },
        placeholder: {
            id: "-1",
            text: "[ Seleccionar autor ]"
        },
        destroy: true,
        theme: "bootstrap4",
        allowClear: true
    });

    $("#cbxPublishers").select2({
        ajax: {
            url: contextPath + "/admincrud/libros?action=findActivePublishers",
            dataType: "JSON",
            delay: 250,
            data: function (params) {
                return { filter: params.term };
            },
            processResults: function (response) {
                let data = $.map(response.result, function (item) {
                    return {
                        id: item.publisherId,
                        text: item.name
                    };
                });
                return {
                    results: data
                };
            },
            cache: true
        },
        placeholder: {
            id: "-1",
            text: "[ Seleccionar editorial ]"
        },
        destroy: true,
        theme: "bootstrap4",
        allowClear: true
    });

    $("#cbxGenres").select2({
        ajax: {
            url: contextPath + "/admincrud/libros?action=findActiveGenres",
            dataType: "JSON",
            delay: 250,
            data: function (params) {
                return { filter: params.term };
            },
            processResults: function (response) {
                let data = $.map(response.result, function (item) {
                    return {
                        id: item.genreId,
                        text: item.name
                    };
                });
                return {
                    results: data
                };
            },
            cache: true
        },
        placeholder: {
            id: "-1",
            text: "[ Seleccionar género ]"
        },
        destroy: true,
        theme: "bootstrap4",
        allowClear: true
    });

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#addEditForm").validate({
        rules: {
            title: {required: true},
            isbn: {required: true, minlength: 13, maxlength: 13, digits: true},
            yearEdition: {required: true},
            coverImage: {required: true},
            author: {required: true},
            publisher: {required: true},
            genre: {required: true},
            numberPages: {required: true},
            price: {required: true},
            review: {required: true}
        },
        submitHandler: function (form) {
            let url = contextPath + "/admincrud/libros?action=create";
            let alertTitle = "Registrado!";
            if (!isNew) {
                url = contextPath + "/admincrud/libros?action=update";
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
                        $("#addEditModal").modal("hide");
                        let table = $("#tblBooks").DataTable();
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
        let modalBody = $("#addEditModal .modal-body");
        hideInputElement(modalBody, ".form-group #txtBookId");
        hideInputElement(modalBody, ".form-group .custom-switch");
        setPropertiesToInputElementId(modalBody, ".form-group #txtBookId", true);
    });

    $("#btnNew").click(function () {
        isNew = true;
        let modalBody = $("#addEditModal .modal-body");
        hideInputElement(modalBody, ".form-group #txtBookId");
        hideInputElement(modalBody, ".form-group .custom-switch");
        setPropertiesToInputElementId(modalBody, ".form-group #txtBookId", true);
    });

});

function setPropertiesToInputElementId(modalBody, inputId, flag) {
    modalBody.find(inputId).prop("disabled", flag);
    modalBody.find(inputId).prop("readonly", !flag);
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

function showFullNames(firstname, lastname) {
    return firstname.concat(" ", lastname);
}

function toFormatISBN(isbn) {
    let regex = /(\d{3})?(\d{3})?(\d{5})?(\d)?(\d)/;
    return isbn.replace(regex, "$1-$2-$3-$4-$5");
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

function showModalEditAndViewDetailBook(button, isEditable) {
    let url = contextPath + "/admincrud/libros?action=findById";
    let bookId = $(button).data("bookId");
    $.ajax({
        url: url,
        type: "GET",
        data: {bookId: bookId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundBook = response.result;
                if (isEditable) {
                    let modalBody = $("#addEditModal .modal-body");
                    modalBody.find(".form-group.d-none").removeClass("d-none");
                    setPropertiesToInputElementId(modalBody, ".form-group #txtBookId", false);
                    modalBody.find(".form-group #txtBookId").val(foundBook.bookId);
                    modalBody.find(".form-group #txtTitle").val(foundBook.title);
                    modalBody.find(".form-group #txtISBN").val(foundBook.isbn);
                    modalBody.find(".form-group #txtYearEdition").val(foundBook.yearEdition);
                    modalBody.find(".form-group #txtNumberPages").val(foundBook.numberPages);
                    modalBody.find(".form-group #cbxAuthors").append($("<option>", {
                        value: foundBook.author.authorId,
                        text: showFullNames(foundBook.author.firstName, foundBook.author.lastName)
                    }));
                    modalBody.find(".form-group #cbxPublishers").append($("<option>", {
                        value: foundBook.publisher.publisherId,
                        text: foundBook.publisher.name
                    }));
                    modalBody.find(".form-group #cbxGenres").append($("<option>", {
                        value: foundBook.genre.genreId,
                        text: foundBook.genre.name
                    }));
                    modalBody.find(".form-group #txtPrice").val(foundBook.price);
                    modalBody.find(".form-group #txtCoverImage").val(foundBook.coverImage);
                    modalBody.find(".form-group #txtReview").val(foundBook.review);
                    modalBody.find(".form-group #chkActive").attr("checked", foundBook.active);
                    $("#addEditModal").modal("show");
                    isNew = false;
                } else {
                    let modalBody = $("#viewDetailModal .modal-body");
                    modalBody.empty();
                    let container = "<dl>";
                    container += "<dt>ISBN</dt>";
                    container += "<dd>" + toFormatISBN(foundBook.isbn) + "</dd>";
                    container += "<dt>Título</dt>";
                    container += "<dd>" + foundBook.title + "</dd>";
                    container += "<dt>Reseña</dt>";
                    container += "<dd>" + foundBook.review + "</dd>";
                    container += "<dt>Autor</dt>";
                    container += "<dd>" + showFullNames(foundBook.author.firstName, foundBook.author.lastName) + "</dd>";
                    container += "<dt>Editorial</dt>";
                    container += "<dd>" + foundBook.publisher.name + "</dd>";
                    container += "<dt>Género Literario</dt>";
                    container += "<dd>" + foundBook.genre.name + "</dd>";
                    container += "<dt>" + showStatus(foundBook.active) + "</dt>";
                    container += "</dl>";
                    modalBody.append(container);
                    $("#viewDetailModal").modal("show");
                }
            }
        }
    });
}

function disableBook(button) {
    let bookTitle = $(button).data("bookTitle");
    Swal.fire({
        title: "¿Estás seguro que quieres desactivar el libro: " + bookTitle + " ?",
        text: "No podrás revertir esta operación!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "Si, realizar operación"
    }).then((result) => {
        if (result.isConfirmed) {
            let url = contextPath + "/admincrud/libros?action=disableById";
            let bookId = $(button).data("bookId");
            $.ajax({
                url: url,
                type: "POST",
                data: {bookId: bookId},
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        let table = $("#tblBooks").DataTable();
                        table.ajax.reload(null, false);
                        Swal.fire("Desactivado!", response.message, response.status);
                    }
                }
            });
        }
    });
}

// function loadActiveAuthors() {
//     let url = "/admincrud/libros";
//     $.ajax({
//         url: url,
//         method: "GET",
//         data: { accion: "cargarAutores" },
//         dataType: "JSON",
//         success: function (response) {
//         }
//     });
// }

function findAllBooks() {
    let url = contextPath + "/admincrud/libros?action=findAll";
    let table = $("#tblBooks").DataTable({
        destroy: true,
        ajax: {
            url: url,
            dataSrc: "result"
        },
        columns: [
            {
                data: "isbn",
                render: function (data, type, row) {
                    return toFormatISBN(data);
                }
            },
            {data: "title"},
            {
                data: null,
                render: function (data, type, row) {
                    return showFullNames(row.author.firstName, row.author.lastName);
                }
            },
            {
                data: "publisher.name",
                render: function (data, type, row) {
                    return data;
                }
            },
            {
                data: "genre.name",
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
                data: "data",
                className: "text-center",
                render: function (data, type, row) {
                    let isEditable = true;
                    let buttons = "<div class='btn-group btn-group-sm'>";
                    buttons += "<button type='button' onclick='showModalEditAndViewDetailBook(this, " + !isEditable + ")' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-book-id='" + data + "'><i class='fas fa-eye'></i></button>";
                    buttons += "<button type='button' onclick='showModalEditAndViewDetailBook(this, " + isEditable + ")' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-book-id='" + data + "'><i class='fas fa-pen'></i></button>"
                    buttons += "<button type='button' onclick='disableBook(this)' " + (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-book-id='" + data + "' data-book-title='" + row.title + "'><i class='fas fa-trash'></i></button>"
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