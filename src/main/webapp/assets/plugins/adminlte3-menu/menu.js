/**
 *Author 283124296@qq.com 20210930
 */

;(function($){
    $.fn.AdminLTE3Menu = function(options){

        var defaults= {
            leafKey: "leaf",
            childKey: "children",
            menuTitleKey: "title",
            menuRouteKey: "route",
            menuIdKey: "id",
            menuIconKey: "",
            menuRightTextKey: "",
            defaultTrunkIcon: "nav-icon fas fa-circle",
            defaultLeafIcon: "far fa-circle nav-icon",
            menuData:[],
        }

        var options = $.extend(defaults, options);
        //this.each(function(){	});
        creatMenu(options.menuData, options, $(this));

        return this;
    }

    function creatMenu(menuData, options, container){
        console.log(menuData)
        for(let i = 0 ;i < menuData.length; i++){
            //当前节点的信息
            let hasChildtmp = menuData[i][options.childKey] ? true : false;
            let hasChild = (hasChildtmp && menuData[i][options.childKey].length>0) ? true : false;
            let menuTitle = menuData[i][options.menuTitleKey];
            let menuId = menuData[i][options.menuIdKey];
            let menuIcon = options.menuIconKey ? menuData[i][options.menuIconKey] : false;
            let rightText = options.menuRightTextKey ? menuData[i][options.menuRightTextKey]?menuData[i][options.menuRightTextKey]:"" : "";

            //存在子节点，才为枝干节点
            if(hasChild){
                //是中间根节点
                let icon = menuIcon ? menuIcon : options.defaultTrunkIcon;
                let node  = '<li class="nav-item">'+
                    '<a href="#" class="nav-link">'+
                    '<i class="'+icon+'"></i>'+
                    '<p>'+menuTitle+
                    '<i class="right fas fa-angle-left"></i>'+
                    '<span class="badge badge-info right">'+rightText+'</span>'+
                    '</p>'+
                    '</a>'+
                    '<ul class="nav nav-treeview" id="menu'+menuId+'">'+
                    '</ul>'+
                    '</li>';
                $(container).append(node);
                creatMenu(menuData[i][options.childKey],options,$('#menu'+menuId));//递归构建子菜单
            }else{
                //是叶子节点
                let icon = menuIcon ? menuIcon : options.defaultLeafIcon;
                let path = menuData[i][options.menuRouteKey];
                let leaf  = '<li class="nav-item">'+
                    '<a href="'+path+'" class="nav-link">'+
                    '<i class="'+icon+'"></i>'+
                    '<p>'+menuTitle+
                    '<span class="badge badge-info right">'+rightText+'</span>'+
                    '</p>'+
                    '</a>'+
                    '</li>';
                $(container).append(leaf);
            }
        }
    }

})(jQuery);

