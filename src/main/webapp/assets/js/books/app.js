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
                let results = $.map(response.result, function (item) {
                    return {
                        id: item.authorId,
                        text: displayFullName(item.firstName, item.lastName)
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
        destroy: true,
        theme: "bootstrap4",
        allowClear: true
    });

    $("#addEditForm").submit(function (event) {
        event.preventDefault();
    });

    $("#btnSave").click(function () {
        $("#addEditForm").validate({
            rules: {
                title: { required: true },
                isbn: { required: true, minlength: 13, maxlength:13, digits: true },
                yearEdition: { required: true },
                coverImage: { required: true },
                author: {required: true },
                publisher: {required: true },
                genre: {required: true },
                numberPages: { required: true },
                price: { required: true },
                review: { required: true }
            },
            submitHandler: function (form) {
                let url = contextPath + "/admincrud/libros?action=create";
                let title = "Registrado!";
                if (!isNew) {
                    url = contextPath + "/admincrud/libros?action=update";
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
                            $("#addEditModal").modal("hide");
                            $("#tblBooks").DataTable().ajax.reload(null, false);
                            Swal.fire(title, response.message, response.status);
                            if (isNew) isNew = false;
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
        isNew = false;
        let modalBody = $("#addEditModal .modal-body");
        setDisplayNoneToInputs(modalBody, ".form-group #txtBookId");
        setDisplayNoneToInputs(modalBody, ".form-group .custom-switch");
        setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtBookId", true);
    });

    $("#btnNew").click(function () {
        isNew = true;
        let modalBody = $("#addEditModal .modal-body");
        setDisplayNoneToInputs(modalBody, ".form-group #txtBookId");
        setDisplayNoneToInputs(modalBody, ".form-group .custom-switch");
        setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtBookId", true);
    });

});

function setDisabledAndReadOnlyToInputId(modalBody, inputId, flag) {
    modalBody.find(inputId).prop("disabled", flag);
    modalBody.find(inputId).prop("readonly", !flag);
}

function setDisplayNoneToInputs(modalBody, input) {
    modalBody.find(input).parent().addClass("d-none");
}

function resetInvalidForm(button, validatedForm) {
    let form = $(button).closest(validatedForm);
    let validator = form.validate();
    validator.resetForm();
    form.find(".form-control").removeClass("is-invalid");
    $(validatedForm).trigger("reset");
}

function displayFullName(firstname, lastname) {
    return firstname.concat(" ", lastname);
}

function displayISBN(isbn) {
    let regex = /(\d{3})?(\d{3})?(\d{5})?(\d)?(\d)/;
    return isbn.replace(regex, "$1-$2-$3-$4-$5");
}

function displayStatus(status) {
    let classNameBadge = status ? "success" : "danger";
    let classNameIcon = status ? "check" : "times";
    let statusText = status ? "ACTIVO" : "INACTIVO";
    let elementHTML = "<span class='badge badge-" + classNameBadge + "'>";
    elementHTML += "<i class='fas fa-" + classNameIcon + "'></i> <span>" + statusText + "</span>";
    elementHTML += "</span>";
    return elementHTML;
}

function showModalEditAndViewDetailBook(button, isEditable) {
    let url = contextPath + "/admincrud/libros?action=findById";
    let bookId = $(button).data("bookId");
    $.ajax({
        url: url,
        type: "GET",
        data: { bookId: bookId },
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let foundBook = response.result;
                if (isEditable) {
                    let modalBody = $("#addEditModal .modal-body");
                    modalBody.find(".form-group.d-none").removeClass("d-none");
                    setDisabledAndReadOnlyToInputId(modalBody, ".form-group #txtBookId", false);
                    modalBody.find(".form-group #txtBookId").val(foundBook.bookId);
                    modalBody.find(".form-group #txtTitle").val(foundBook.title);
                    modalBody.find(".form-group #txtISBN").val(foundBook.isbn);
                    modalBody.find(".form-group #txtYearEdition").val(foundBook.yearEdition);
                    modalBody.find(".form-group #txtNumberPages").val(foundBook.numberPages);
                    modalBody.find(".form-group #cbxAuthors").append($("<option>", {
                        value: foundBook.author.authorId,
                        text: displayFullName(foundBook.author.firstName, foundBook.author.lastName)
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
                    let elementHTML = "<dl>";
                    elementHTML += "<dt>ISBN</dt>";
                    elementHTML += "<dd>" + displayISBN(foundBook.isbn) + "</dd>";
                    elementHTML += "<dt>Título</dt>";
                    elementHTML += "<dd>" + foundBook.title + "</dd>";
                    elementHTML += "<dt>Reseña</dt>";
                    elementHTML += "<dd>" + foundBook.review + "</dd>";
                    elementHTML += "<dt>Autor</dt>";
                    elementHTML += "<dd>" + displayFullName(foundBook.author.firstName, foundBook.author.lastName) + "</dd>";
                    elementHTML += "<dt>Editorial</dt>";
                    elementHTML += "<dd>" + foundBook.publisher.name + "</dd>";
                    elementHTML += "<dt>Género Literario</dt>";
                    elementHTML += "<dd>" + foundBook.genre.name + "</dd>";
                    elementHTML += "<dt>" + displayStatus(foundBook.active) + "</dt>";
                    elementHTML += "</dl>";
                    modalBody.append(elementHTML);
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
                data: { bookId: bookId },
                dataType: "JSON",
                success: function (response) {
                    if (response.success) {
                        $("#tblBooks").DataTable().ajax.reload(null, false);
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
                data: null,
                render: function (data, type, row) {
                    return displayISBN(row.isbn);
                }
            },
            { data: "title" },
            {
                data: null,
                render: function (data, type, row) {
                    return displayFullName(row.author.firstName, row.author.lastName);
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
                    return displayStatus(row.active);
                }
            },
            {
                data: null,
                className: "text-center",
                render: function (data, type, row) {
                    let elementHTML = "<div class='btn-group btn-group-sm'>";
                    elementHTML += "<button type='button' onclick='showModalEditAndViewDetailBook(this, false)' class='btn btn-info' data-toggle='modal' data-target='#viewDetailModal' data-tooltip='tooltip' data-placement='left' title='Más información' data-book-id='" + row.bookId + "'><i class='fas fa-eye'></i></button>";
                    elementHTML += "<button type='button' onclick='showModalEditAndViewDetailBook(this, true)' class='btn btn-warning' data-toggle='modal' data-target='#addEditModal' data-tooltip='tooltip' data-placement='bottom' title='Editar' data-book-id='" + row.bookId + "'><i class='fas fa-pen'></i></button>"
                    elementHTML += "<button type='button' onclick='disableBook(this)' " +  (!row.active ? 'disabled' : '') + " class='btn btn-danger' data-tooltip='tooltip' data-placement='top' title='Desactivar'  data-book-id='" + row.bookId + "' data-book-title='" + row.title + "'><i class='fas fa-trash'></i></button>"
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