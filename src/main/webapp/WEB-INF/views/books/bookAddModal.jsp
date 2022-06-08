<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
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
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txtTitulo">Título (*)</label>
                        <input type="text" class="form-control" id="txtTitulo" placeholder="Título" name="titulo">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="txtISBN">ISBN (*)</label>
                            <input type="text" class="form-control" id="txtISBN" placeholder="ISBN" name="isbn">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="txtAnioEdicion">Año de Edición (*)</label>
                            <input type="text" class="form-control" id="txtAnioEdicion" placeholder="Año de edición" name="anioEdicion">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="txtPortada">Foto de portada (*)</label>
                            <input type="text" class="form-control" id="txtPortada" placeholder="Foto de portada" name="portada">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="cbxAutores">Autor (*)</label>
                            <select class="form-control" id="cbxAutores" name="autor">
                            </select>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="cbxEditoriales">Editorial (*)</label>
                            <select class="form-control" id="cbxEditoriales" name="editorial">
                            </select>
                        </div>
                        <div class="form-group col-md-4">
                            <label for="cbxGeneros">Género Literario (*)</label>
                            <select class="form-control" id="cbxGeneros" name="genero">
                            </select>
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-4">
                            <label for="txtNroPaginas">N° Páginas (*)</label>
                            <input type="number" class="form-control" id="txtNroPaginas" name="nroPaginas">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="txtEjemplares">Ejemplares (*)</label>
                            <input type="number" class="form-control" id="txtEjemplares" name="ejemplares">
                        </div>
                        <div class="form-group col-md-4">
                            <label for="txtPrecio">Precio (*)</label>
                            <input type="number" class="form-control" id="txtPrecio" name="precio">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="txtResenha">Reseña</label>
                        <textarea class="form-control" id="txtResenha" rows="4" placeholder="Descripción" name="resenha"></textarea>
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
