自由变量 layout_index = layout_index || 函数(_界面) {

此.__proto__ = 新 Layout(_界面);
//super(Context);

此.刷新 = 新 下拉刷新布局(_界面)
.置填充("16dp")
.加入到(此.布局);

此.列表 = 新 基本列表(_界面)
.加入到(此.刷新);

此.打开();

返 此;

}

自由变量 layout_index_edit = 函数(_界面) {

此.布局 = 新 线性布局(_界面);

此.编辑 = 新 编辑框(_界面)
.加入到(此.布局);

返 此;

}