<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="modal fade" id="authorAddModal">
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
            <form id="authorAddForm" role="form">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="txtFirstname">Nombres (*)</label>
                        <input type="text" class="form-control" id="txtFirstname" placeholder="Nombres" name="firstname">
                    </div>
                    <div class="form-group">
                        <label for="txtLastname">Apellidos (*)</label>
                        <input type="text" class="form-control" id="txtLastname" placeholder="Apellidos" name="lastname">
                    </div>
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label for="txtCity">Ciudad (*)</label>
                            <input type="text" class="form-control" id="txtCity" placeholder="Ciudad" name="city">
                        </div>
                        <div class="form-group col-md-6">
                            <label>Fecha de Nacimiento (*)</label>
                            <div class="input-group date" id="dtBirthDate" data-target-input="nearest">
                                <input type="text" class="form-control datetimepicker-input" name="birthDate" data-target="#dtBirthDate"/>
                                <div class="input-group-append" data-target="#dtBirthDate" data-toggle="datetimepicker">
                                    <div class="input-group-text"><i class="fa fa-calendar"></i></div>
                                </div>
                            </div>
                        </div>
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
