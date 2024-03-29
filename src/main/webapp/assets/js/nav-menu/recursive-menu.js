$(function () {
    getNavigationMenu(roleId);
});

function getNavigationMenu(roleId) {
    let url = contextPath + "/admincrud/menu-permisos?action=getNavigationMenuByRole";
    $.ajax({
        url: url,
        type: "GET",
        data: {roleId: roleId},
        dataType: "JSON",
        success: function (response) {
            if (response.success) {
                let menu = response.result;
                let container = $(".main-sidebar .sidebar ul");
                createRecursiveMenu(container, menu);
                // Alternar la barra lateral de navegación
                $("ul.nav-sidebar a").filter(toggleNavSidebar);
                // Alternar vista de árbol de navegación
                $("ul.nav-treeview a").filter(toggleNavTreeview);
            }
        }
    });
}

function createRecursiveMenu(container, menu) {
    for (let i = 0; i < menu.length; i++) {
        if (menu[i].children.length > 0) {
            let ul = $(container);
            let li = "<li class='nav-item'>" +
                "<a href='#' class='nav-link'>" +
                "<i class='nav-icon " + menu[i].menu.icon + "'></i>" +
                "<p>" + menu[i].menu.title +
                "<i class='fas fa-angle-left right'></i>" +
                "<span class='badge badge-info right'>" + menu[i].children.length + "</span>" +
                "</p>" +
                "</a>" +
                "<ul class='nav nav-treeview' id='submenu-" + menu[i].menu.title.toLowerCase() + "'>" +
                "</ul>" +
                "</li>";
            ul.append(li);
            let subContainer = $("#submenu-" + menu[i].menu.title.toLowerCase());
            createRecursiveMenu(subContainer, menu[i].children);
        } else {
            let ul = $(container);
            let li = "<li class='nav-item'>" +
                "<a href='" + (menu[i].menu.route != null ? contextPath.concat(menu[i].menu.route) : '#') + "' class='nav-link'>" +
                "<i class='" + menu[i].menu.icon + " nav-icon'></i>" +
                "<p>" + menu[i].menu.title + "</p>" +
                "</a>" +
                "</li>";
            ul.append(li);
        }
    }
}

function toggleNavSidebar() {
    if (this.href == url.href) return $(this).addClass("active");
}

function toggleNavTreeview() {
    if (this.href == url.href) return $(this).parentsUntil(".nav-sidebar > .nav-treeview").addClass("menu-open").prev("a").addClass("active");
}