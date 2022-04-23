<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade" id="addAuthorModal">
    <!-- Modal Dialog -->
    <div class="modal-dialog">
        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Añadir nuevo author</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="addAuthorForm" role="form">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txtNombres">Nombres (*)</label>
                        <input type="text" class="form-control" id="txtNombres" placeholder="Nombres" name="nombres">
                    </div>
                    <div class="form-group">
                        <label for="txtApellidos">Apellidos (*)</label>
                        <input type="text" class="form-control" id="txtApellidos" placeholder="Apellidos" name="apellidos">
                    </div>
                    <div class="form-group">
                        <label for="txtCiudad">Ciudad (*)</label>
                        <input type="text" class="form-control" id="txtCiudad" placeholder="Ciudad" name="ciudad">
                    </div>
                </div>
                <div class="modal-footer d-flex justify-content-end">
                    <button type="reset" id="btnResetSave" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="submit" id="btnAddAuthor" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
