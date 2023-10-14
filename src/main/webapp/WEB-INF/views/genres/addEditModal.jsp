<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="addEditModal" tabindex="-1" aria-labelledby="addEditModalLabel" aria-hidden="true">
    <!-- Modal Dialog -->
    <div class="modal-dialog">
        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-uppercase" id="addEditModalLabel">
                    <i class="fas fa-file-alt mr-1"></i> Añadir nuevo género literario
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="addEditForm" role="form">
                <div class="modal-body">
                    <div class="form-group d-none">
                        <label for="txtGenreId">Código</label>
                        <input type="text" class="form-control" id="txtGenreId" name="genreId">
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
                <div class="modal-footer">
                    <button type="reset" class="btn btn-secondary" id="btnReset" data-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>