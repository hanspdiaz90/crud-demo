<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="addEditModal">
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
            <form id="addEditForm" role="form">
                <div class="modal-body">
                    <div class="form-group d-none">
                        <label for="txtPublisherId">ID Editorial</label>
                        <input type="text" class="form-control" id="txtPublisherId" name="publisherId" readonly>
                    </div>
                    <div class="form-group">
                        <label for="txtName">Editorial (*)</label>
                        <input type="text" class="form-control" id="txtName" placeholder="Editorial" name="name">
                    </div>
                    <div class="form-group">
                        <label for="txtEmail">E-mail (*)</label>
                        <input type="email" class="form-control" id="txtEmail" placeholder="e-mail" name="email">
                    </div>
                    <div class="form-group">
                        <label for="txtAddress">Dirección (*)</label>
                        <input type="text" class="form-control" id="txtAddress" placeholder="Dirección" name="address">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="txtPhone">Teléfono</label>
                            <input type="text" class="form-control" id="txtPhone" placeholder="Télefono" name="phone">
                        </div>
                        <div class="form-group col-md-6">
                            <label for="txtCellphone">Celular</label>
                            <input type="text" class="form-control" id="txtCellphone" placeholder="Celular" name="cellphone">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="txtWebSite">Página Web</label>
                        <input type="text" class="form-control" id="txtWebSite" placeholder="Página Web" name="webSite">
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
