<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="addEditModal">
    <!-- Modal Dialog -->
    <div class="modal-dialog">
        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Añadir nuevo autor</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="addEditForm" role="form">
                <div class="modal-body">
                    <div class="form-group d-none">
                        <label for="txtAuthorId">ID Autor</label>
                        <input type="text" class="form-control" id="txtAuthorId" name="authorId" readonly>
                    </div>
                    <div class="form-group">
                        <label for="txtFirstname">Nombres (*)</label>
                        <input type="text" class="form-control" id="txtFirstname" placeholder="Nombres" name="firstName">
                    </div>
                    <div class="form-group">
                        <label for="txtLastname">Apellidos (*)</label>
                        <input type="text" class="form-control" id="txtLastname" placeholder="Apellidos" name="lastName">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="txtCity">Ciudad (*)</label>
                            <input type="text" class="form-control" id="txtCity" placeholder="Ciudad" name="city">
                        </div>
                        <div class="form-group col-md-6">
                            <label>Fecha de Nacimiento (*)</label>
                            <div class="input-group date" id="dateTimeDob" data-target-input="nearest">
                                <input type="text" class="form-control datetimepicker-input" id="dtDob" data-target="#dateTimeDob" placeholder="DD/MM/YYYY" name="dob"/>
                                <div class="input-group-append" data-target="#dateTimeDob" data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
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