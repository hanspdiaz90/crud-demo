<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form id="addEditForm" role="form">
    <div class="form-group d-none">
        <label for="txtModuleId">ID Módulo</label>
        <input type="text" class="form-control" id="txtModuleId" name="moduleId">
    </div>
    <div class="form-group">
        <label for="txtTitle">Título (*)</label>
        <input type="text" class="form-control" id="txtTitle" placeholder="Título" name="title">
    </div>
    <div class="form-group">
        <label for="txtDescription">Descripción</label>
        <textarea class="form-control" id="txtDescription" rows="3" placeholder="Descripción" name="description"></textarea>
    </div>
    <div class="form-group d-none">
        <div class="custom-control custom-switch custom-switch-off-danger custom-switch-on-success">
            <input type="checkbox" class="custom-control-input" id="chkActive" name="isActive">
            <label class="custom-control-label" for="chkActive"></label>
        </div>
    </div>
    <div class="form-group">
        <button type="reset" id="btnReset" class="btn btn-secondary">Cancelar</button>
        <button type="submit" id="btnSave" class="btn btn-primary">Guardar</button>
    </div>
</form>