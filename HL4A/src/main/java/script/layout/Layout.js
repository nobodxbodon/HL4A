自由变量 Layout = Layout || 函数(_界面) {

此.底层 = 新 线性布局(_界面).置背景("白色");

此.标题 = 新 标题栏(_界面,应用.取应用名()).加入到(此.底层)

此.布局 = 新 线性布局(_界面).加入到(此.底层);

此.打开 = () => {
_界面.打开布局(此.底层);
返 此;
}

此.创建菜单 = (_菜单) => {

如 (_菜单 == 空) _菜单 = Menu;

变量 _按钮 = 此.标题.右按钮(函数() {
_菜单.显示();
});

_菜单 = 新 _菜单(_按钮);

返 _菜单;

}

返 此;

}