$(function () {
    let url = window.location;
    // Alternar la barra lateral de navegación
    $("ul.nav-sidebar a").filter(toggleNavSidebar);
    // Alternar vista de árbol de navegación
    $("ul.nav-treeview a").filter(toggleNavTreeview);
    function toggleNavSidebar() {
        if (this.href == url.href) return $(this).addClass("active");
    }
    function toggleNavTreeview() {
        if (this.href == url.href) return $(this).parentsUntil(".nav-sidebar > .nav-treeview").addClass("menu-open").prev("a").addClass("active");
    }
});