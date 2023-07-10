<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="moduleAddModal">
    <!-- Modal Dialog -->
    <div class="modal-dialog">
        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Añadir nuevo módulo</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="moduleAddForm" role="form">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txtTitle">Título (*)</label>
                        <input type="text" class="form-control" id="txtTitle" placeholder="Título" name="title">
                    </div>
                    <div class="form-group">
                        <label for="txtDescription">Descripción</label>
                        <textarea class="form-control" id="txtDescription" rows="5" placeholder="Descripción" name="description"></textarea>
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
