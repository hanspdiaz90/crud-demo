<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="genreAddModal">
    <!-- Modal Dialog -->
    <div class="modal-dialog">
        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Añadir Nuevo Género Literario</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="genreAddForm" role="form">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txtGenero">Género Literario (*)</label>
                        <input type="text" class="form-control" id="txtGenero" placeholder="Género Literario" name="nombre">
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
