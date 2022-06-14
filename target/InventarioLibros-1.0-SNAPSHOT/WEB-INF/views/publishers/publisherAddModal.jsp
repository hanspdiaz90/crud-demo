<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="publisherAddModal">
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
            <form id="publisherAddForm" role="form">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txtEditorial">Editorial (*)</label>
                        <input type="text" class="form-control" id="txtEditorial" placeholder="Editorial" name="nombre">
                    </div>
                    <div class="form-group">
                        <label for="txtDireccion">Dirección (*)</label>
                        <input type="text" class="form-control" id="txtDireccion" placeholder="Dirección" name="direccion">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="txtEmail">E-mail (*)</label>
                            <input type="email" class="form-control" id="txtEmail" placeholder="E-mail" name="email">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="txtNroTelefono">Telf./Celular (*)</label>
                            <input type="text" class="form-control" id="txtNroTelefono" placeholder="Nro Teléfono" name="nroTelefono">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="txtPaginaWeb">Página Web (*)</label>
                        <input type="text" class="form-control" id="txtPaginaWeb" placeholder="Página Web" name="paginaWeb">
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
