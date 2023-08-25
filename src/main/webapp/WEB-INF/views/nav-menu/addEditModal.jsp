<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<form id="addEditForm" role="form">
    <div class="form-group d-none">
        <label for="txtMenuId">ID Menú</label>
        <input type="text" class="form-control" id="txtMenuId" name="menuId">
    </div>
    <div class="form-row">
        <div class="form-group col-md-6">
            <label for="txtTitle">Texto (*)</label>
            <input type="text" class="form-control" id="txtTitle" placeholder="Texto" name="title">
        </div>
        <div class="form-group col-md-6">
            <label for="txtIcon">Icono (*)</label>
            <input type="text" class="form-control" id="txtIcon" placeholder="Icono" name="icon">
        </div>
    </div>
    <div class="form-row">
        <div class="form-group col-md-4">
            <label for="txtRoute">Ruta</label>
            <input type="text" class="form-control" id="txtRoute" placeholder="Ruta" name="route">
        </div>
        <div class="form-group col-md-4">
            <label for="cbxModules">Módulo (*)</label>
            <select class="form-control" id="cbxModules" name="module">
                <option value="0">[ Seleccionar módulo ]</option>
            </select>
        </div>
        <div class="form-group col-md-4">
            <label for="cbxParents">Pariente</label>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <div class="input-group-text">
                        <input type="checkbox" aria-label="Checkbox for following text input" id="isParent">
                    </div>
                </div>
                <select class="form-control" id="cbxParents" name="parent" aria-label="Select input with checkbox">
                    <option value="0">[ Seleccionar menú ]</option>
                </select>
            </div>
        </div>
    </div>
    <div class="form-group d-none">
        <div class="custom-control custom-switch custom-switch-off-danger custom-switch-on-success">
            <input type="checkbox" class="custom-control-input" id="chkActive" name="isActive">
            <label for="chkActive" class="custom-control-label"></label>
        </div>
    </div>
    <div class="form-group">
        <button type="reset" id="btnReset" class="btn btn-secondary">Cancelar</button>
        <button type="submit" id="btnSave" class="btn btn-primary">Guardar</button>
    </div>
</form>