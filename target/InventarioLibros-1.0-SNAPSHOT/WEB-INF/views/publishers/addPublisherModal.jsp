<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<div class="modal fade" id="addPublisherModal">
    <!-- Modal Dialog -->
    <div class="modal-dialog">
        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Añadir Nueva Editorial</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="addPublisherForm" role="form">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txtEditorial">Editorial (*)</label>
                        <input type="text" class="form-control" id="txtEditorial" placeholder="Editorial" name="nombre">
                    </div>
                    <div class="form-group">
                        <label for="txtEmail">E-mail (*)</label>
                        <input type="email" class="form-control" id="txtEmail" placeholder="E-mail" name="email">
                    </div>
                    <div class="form-group">
                        <label for="txtTelefono">Telefono (*)</label>
                        <input type="text" class="form-control" id="txtTelefono" placeholder="Ciudad" name="telefono">
                    </div>
                </div>
                <div class="modal-footer d-flex justify-content-end">
                    <button type="reset" id="btnResetSave" class="btn btn-secondary" data-dismiss="modal">Cerrar</button>
                    <button type="submit" id="btnAddPublisher" class="btn btn-primary">Guardar</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
