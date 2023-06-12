$(function () {
    getNavigationMenu(roleId);
});

function getNavigationMenu(sessionRoleId) {
    let url = contextPath + "/admincrud/menu-permisos?accion=listarMenuNavegacion";
    $.ajax({
        url: url,
        method: "GET",
        data: {roleId: sessionRoleId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let menuData = response.result;
                let menuView = $(".main-sidebar .sidebar ul");
                parseMenu(menuView, menuData)
            }
        }
    });
}

//let li = $(ul).append("<li class='nav-header'>" + "MÓDULO ".concat(menu[i].menu.module.title) + "</li>");
function parseMenu(ul, menu) {
    for (let i = 0; i < menu.length; i++) {
        let menuId = (i + 1);
        if (menu[i].children.length > 0) {
            let li = $(ul).append("<li class='nav-item'>" +
                "<a href='#' class='nav-link'>" +
                "<i class='nav-icon " + menu[i].menu.icon + "'></i>" +
                "<p>" + menu[i].menu.title +
                "<i class='fas fa-angle-left right'></i>" +
                "<span class='badge badge-info right'>" + menu[i].children.length + "</span>" +
                "</p>" +
                "</a>" +
                "<ul class='nav nav-treeview' id='menu-" + menuId + "'>" +
                "</ul>" +
                "</li>");
            let submenu = $("#menu-" + menuId);
            parseMenu(submenu, menu[i].children);
        } else {
            let li = $(ul).append("<li class='nav-item'>" +
                "<a href='" + (menu[i].menu.route != null ? contextPath.concat(menu[i].menu.route) : "#") + "' class='nav-link'>" +
                "<i class='" + menu[i].menu.icon + " nav-icon'></i>" +
                "<p>" + menu[i].menu.title + "</p>" +
                "</a>" +
                "</li>");
        }
    }
}