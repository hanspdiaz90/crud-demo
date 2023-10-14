<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="addEditModal" tabindex="-1" aria-labelledby="addEditModalLabel" aria-hidden="true">
    <!-- Modal Dialog -->
    <div class="modal-dialog">
        <!-- Modal Content -->
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title text-uppercase" id="addEditModalLabel">
                    <i class="fas fa-file-alt mr-1"></i> Añadir nuevo autor
                </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form id="addEditForm" role="form">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txtAuthorId">Código</label>
                        <input type="text" class="form-control" id="txtAuthorId" name="authorId">
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
                    <div class="form-group">
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