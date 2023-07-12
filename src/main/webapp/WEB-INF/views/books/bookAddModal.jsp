<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="bookAddModal">
    <!-- Modal Dialog -->
    <div class="modal-dialog modal-lg">
        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Añadir Nuevo Libro</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="bookAddForm" role="form">

                <div class="modal-body" style="max-height: calc(100vh - 200px); overflow-y: auto;">

                    <div class="form-group">
                        <label for="txtTitle">Título (*)</label>
                        <input type="text" class="form-control" id="txtTitle" placeholder="Título" name="title">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="txtISBN">ISBN (*)</label>
                            <input type="text" class="form-control" id="txtISBN" placeholder="ISBN" name="isbn">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="txtYearEdition">Año de Edición (*)</label>
                            <input type="text" class="form-control" id="txtYearEdition" placeholder="Año de edición" name="yearEdition">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="txtNumberPages">N° Páginas (*)</label>
                            <input type="number" class="form-control" id="txtNumberPages" name="numberPages">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="cbxAuthors">Autor (*)</label>
                            <select class="form-control" id="cbxAuthors" name="author">
                            </select>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="cbxPublishers">Editorial (*)</label>
                            <select class="form-control" id="cbxPublishers" name="publisher">
                            </select>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="cbxGenres">Género Literario (*)</label>
                            <select class="form-control" id="cbxGenres" name="genre">
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="txtPrice">Precio (*)</label>
                            <input type="number" class="form-control" id="txtPrice" name="price">
                        </div>
                        <div class="form-group col-md-8">
                            <label for="txtCoverImage">Foto de portada (*)</label>
                            <input type="text" class="form-control" id="txtCoverImage" placeholder="Foto de portada" name="coverImage">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="txtReview">Reseña</label>
                        <textarea class="form-control" id="txtReview" rows="5" placeholder="Descripción" name="review"></textarea>
                    </div>
                </div>

                <div class="modal-footer d-flex justify-content-end">
                    <button type="reset" id="btnResetAdd" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="submit" id="btnAdd" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>