<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="addEditModal">
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
            <form id="addEditForm" role="form">
                <div class="modal-body">
                    <div class="form-group d-none">
                        <label for="txtGenreId">ID Género</label>
                        <input type="text" class="form-control" id="txtGenreId" name="genreId" readonly>
                    </div>
                    <div class="form-group">
                        <label for="txtGenre">Género Literario (*)</label>
                        <input type="text" class="form-control" id="txtGenre" placeholder="Género Literario" name="name">
                    </div>
                    <div class="form-group d-none">
                        <div class="custom-control custom-switch custom-switch-off-danger custom-switch-on-success">
                            <input type="checkbox" class="custom-control-input" id="chkActive" name="isActive">
                            <label class="custom-control-label" for="chkActive"></label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer d-flex justify-content-end">
                    <button type="reset" id="btnReset" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="submit" id="btnSave" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>