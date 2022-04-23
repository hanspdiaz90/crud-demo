<div class="modal fade" id="modal-register">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Nuevo autor</h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="${pageContext.request.contextPath}/autores?accion=registrar" method="POST">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="inputNombres">Nombres</label>
                        <input type="text" class="form-control" id="inputNombres" placeholder="Nombres"
                               name="nombres" value="${nombres}">
                    </div>
                    <div class="form-group">
                        <label for="inputApellidos">Apellidos</label>
                        <input type="text" class="form-control" id="inputApellidos" placeholder="Apellidos"
                               name="apellidos" value="${apellidos}">
                    </div>
                    <div class="form-group">
                        <label for="inputCiudad">Ciudad</label>
                        <input type="text" class="form-control" id="inputCiudad" placeholder="Ciudad"
                               name="ciudad" value="${ciudad}">
                    </div>
<%--                    <div class="form-check">--%>
<%--                        <input type="checkbox" class="form-check-input" id="inputActivo">--%>
<%--                        <label class="form-check-label" for="inputActivo">Check me out</label>--%>
<%--                    </div>--%>
                </div>
                <div class="modal-footer justify-content-between">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Cerrar</button>
                    <button type="submit" class="btn btn-primary">Guardar cambios</button>
                </div>
            </form>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- /.modal -->
