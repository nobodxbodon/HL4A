导入包 "放课后乐园部.视图.列表";

function 更新(_函数,...)
  local _参数 = {...};
  处理.主线程(function(args);
    _函数(table.unpack(_参数));
  end,{});
end

创建界面 = 弹窗_基本弹窗(当前界面)
.置布局(创建布局 {
  线性布局;
  主题 = "底层";
  方向 = "水平";
  背景 = 颜色.白色;
  重力 = "中间垂直";
  填充 = "16dp";
  {
    进度条;
    右填充 = "16dp";
  };
  {
    文本控件;
    文本 = "创建界面 ~";
  };
}).显示();

if #当前界面.传入数据 == 3 then


  当前界面.不结束 = true;
  _设置文件 = 当前界面.传入数据[0]
  _文件名 = 当前界面.传入数据[1]
  _工程目录 = 当前界面.传入数据[2]

  当前目录 = _工程目录.."/".._文件名.."/源码";
  附加目录 = "";
  当前文件 = "";

  function 读取设置()
    _设置 = 设置.打开XML(_设置文件);
    _设置.上次打开 = _设置.上次打开 or "index.lua";
    _设置.上次目录 = _设置.上次目录 or "";
    _设置.入口文件 = _设置.入口文件 or "index.lua";
  end

  function 保存设置()
    设置.保存XML(_设置文件,_设置);
  end

  读取设置();

  local _布局 = 创建布局 {
    侧滑布局;
    赋值 = "_侧滑";
    {
      线性布局;
      主题 = "底层";
      {
        标题栏;
        标题 = "乐园之土";
        赋值 = "_标题";
      };
      {
        线性布局;
        主题 = "底层";
        方向 = "垂直";
        赋值 = "_底层";
        {
          线性布局;
          宽度 = "最大";
          高度 = "自动";
          背景 = 主题.文字();
          方向 = "水平";
          赋值 = "_选择";
          状态 = "隐藏";
        };
        {
          线性布局;
          宽度 = "最大";
          背景 = "透明";
          {
            横向滚动;
            宽度 = "最大";
            高度 = "自动";
            背景 = 主题.文字();
            {
              线性布局;
              宽度 = "最大";
              填充 = "2dp";
              高度 = "自动";
              赋值 = "_工具";
              方向 = "水平";
            };
          };
        };
      };
    };
    {
      线性布局;
      主题 = "底层";
      背景 = "白色";
      侧滑左布局 = true;
      {
        标题栏;
        赋值 = "_侧滑标题";
        标题 = _设置.工程名;
      };
      {
        下拉刷新布局;
        赋值 = "_刷新";
        填充 = "16dp";
        {
          线性布局;
          主题 = "底层";
          {
            文本控件;
            赋值 = "_提示";
            下填充 = "16dp";
          };
          {
            基本列表;
            赋值 = "_列表";
          };
        };
      };
    };
  };

  _布局.打开(当前界面);

  (创建布局 {
    代码框;
    赋值 = "_代码框";
    代码选中事件 = function(args)
      if args[0] == true then
        _标题.隐藏();
        _工具.隐藏();
        _选择.显示();
      else
        _标题.显示();
        _工具.显示();
        _选择.隐藏();
      end
    end;
  }).加入到(_底层);

  function 打开文件()
    读取设置();
    _设置.上次打开 = 当前文件;
    _设置.上次目录 = 附加目录;
    _代码框.文本 = 字符.读取(当前目录.._设置.上次目录.."/".._设置.上次打开);
    保存设置();
  end

  function 保存文件()
    字符.保存(当前目录..附加目录.."/"..当前文件,_代码框.文本);
  end

  if 文件.是文件(当前目录.._设置.上次目录.."/".._设置.上次打开) then
    附加目录 = _设置.上次目录;
    当前文件 = _设置.上次打开;
  else
    _设置.上次目录 = "";
    _设置.上次打开 = _设置.入口文件
  end
  if 文件.是文件(当前目录.."/".._设置.入口文件) then
    附加目录 = "";
    当前文件 = _设置.入口文件;
  else
    弹窗.提示 "入口文件不存在 正重新创建 ~";
    _设置.入口文件 = "index.lua";
    字符.保存(当前目录.."/".._设置.入口文件,"");
    if 文件.是文件(当前目录.."/".._设置.入口文件) then
      附加目录 = "";
      当前文件 = _设置.入口文件;
    else
      弹窗.提示 "写出文件失败 ~";
    end
  end

  保存设置();
  打开文件();
  
  function 添加按钮(_内容,_方法,_加入)
    local _布局 = 创建布局 {
      线性布局;
      宽度 = "自动";
      高度 = "自动";
      填充 = "10dp";
      左填充 = "16dp";
      右填充 = "16dp";
      背景 = "透明";
      重力 = "中间";
      单击事件 = _方法;
      {
        文本控件;
        文本颜色 = 颜色.白色;
        文本 = _内容;
      };
    };
    return _布局.加入到(_加入);
  end


  _侧滑标题
  .左按钮("安卓",function()
    入口文件();
  end)

  添加按钮("全选",function()
    _代码框.全选();
  end,_选择).置布局权重(1);

  添加按钮("复制",function()
    _代码框.复制();
  end,_选择).置布局权重(1);

  添加按钮("剪切",function()
    _代码框.剪切();
  end,_选择).置布局权重(1);

  添加按钮("粘贴",function()
    _代码框.粘贴();
  end,_选择).置布局权重(1);

  -- 文件处理部分 

  function 添加项目(_内容,_方法,_加入)
    local _布局 = 创建布局 {
      线性布局;
      填充 = "6dp";
      左填充 = "16dp";
      右填充 = "16dp";
      宽度 = "最大";
      背景 = "透明";
      重力 = "中间垂直";
      单击事件 = _方法;
      {
        文本控件;
        文本 = _内容;
      };
    };
    _加入.添加视图(_布局);
    return _布局;
  end


  创建文件 = 创建文件 or 弹窗.普通(当前界面,
  "创建文件/文件夹",
  创建布局 {
    线性布局;
    主题 = 底层;
    {
      编辑框;
      赋值 = "_创建名";
      默认文本 = "文件/文件夹名";
    }
  },
  "文件",function()
    _地址 = 当前目录..附加目录.."/".._创建名.文本;
    if 文件.是否存在(_地址) then
      弹窗.提示 "已存在 ~";
    else
      文件.删除(_地址);
      字符.保存(_地址,"");
      if 文件.是否存在(_地址) then
        弹窗.提示 "创建成功 ~";
        创建文件.隐藏();
      else 
        弹窗.提示 "创建失败 未知原因 ~";
      end
    end
  end,
  "文件夹",function()
    local _地址 = 当前目录..附加目录.."/".._创建名.文本;
    if 文件.是否存在(_地址) then
      弹窗.提示 "已存在 ~";
    else
      文件.删除(_地址);
      File(_地址).mkdirs();
      if 文件.是否存在(_地址) then
        弹窗.提示 "创建成功 ~";
        创建文件.隐藏();
      else 
        弹窗.提示 "创建失败 未知原因 ~";
      end
    end
  end
  )

  function 删除文件(_名称)
    local _地址 = 当前目录..附加目录.."/".._名称;
    _删除文件 = 弹窗.普通(当前界面,
    "删除文件/文件夹",
    "真的要删除 ".._地址.." 吗？",
    "删除",function()
      if _地址 == 当前目录.."/".._设置.入口文件 then
        弹窗.提示 "入口文件不能删除 ~";
        入口文件();
      else
        文件.删除(_地址);
        弹窗.提示 "删除完成 ~";
        _删除文件.隐藏();
      end
    end,
    "取消",function()
      _删除文件.隐藏();
    end
    ).显示();
  end

  function 更新文件()

    _刷新.刷新状态 = true;

    _列表.清空();

    local _文件列表 = 文件.取文件列表(当前目录..附加目录);

    if _文件列表 == nil then

      File(当前目录).mkdirs();

      _文件列表 = 文件.取文件列表(当前目录..附加目录);

    end

    _提示.文本 = 当前目录..附加目录;

    添加项目("创建文件/文件夹",function()
      创建文件.显示();
    end,_列表);

    if 附加目录 ~= "" then
      添加项目("回到顶层目录",function()
        附加目录 = "";
        更新文件();
        弹窗.提示 "已返回 ~";
      end,_列表);
    end

    if #_文件列表 > 0 then
      当前文件 = _文件列表[0].Name;
      打开文件();
    end

    for n = 0, #_文件列表-1 do
      local _名称 = _文件列表[n].Name;
      if 文件.是文件(_文件列表[n].Path) then
        添加项目(_名称.."   - 文件",function()
          保存文件();
          当前文件 = _名称;
          打开文件();
          弹窗.提示 "已打开 ~";
        end,_列表).置长按事件(function()
          删除文件(_名称);
        end);
      else
        添加项目(_名称.."   - 文件夹",function()
          保存文件();
          附加目录 = 附加目录.."/".._名称;
          更新文件();
          弹窗.提示 "已打开目录 ~";
        end,_列表).置长按事件(function()
          删除文件(_名称);
        end);

      end
    end

    _刷新.刷新状态 = false;

  end

  _刷新.刷新事件 = function()
    更新文件();
  end

  当前界面.取得焦点事件 = function()
    pcall(function()
      更新文件();
    end);
  end


  线程(function()

  添加按钮("运行",function()
    保存文件();
    local _入口 = 当前目录.."/".._设置.入口文件;
    if 文件.是文件(_入口) then
      当前界面.跳转界面(_入口)
    else
      弹窗.提示 "入口文件不存在 ~";
      入口文件();
    end
  end,_工具);
  添加按钮("格式化",function()
    _代码框.format();
  end,_工具);
  添加按钮("撤销",function()
    _代码框.撤销();
  end,_工具);
  添加按钮("重做",function()
    _代码框.重做();
  end,_工具);
  添加按钮("保存",function()
    保存文件();
    弹窗.提示 "已保存 ~";
  end,_工具);

  
    local _符号={";","(",")","[","]","{","}","\"","=",":",".",",","_","+","-","*","/","\\","%","#","^","$","?","<",">","~","'"};
    for k,v in ipairs(_符号) do
      更新(添加按钮,v,function()
        _代码框.插入(v)
      end,_工具);
    end
    创建界面.隐藏();
  end,{}).启动();

  _标题
  .左按钮(function()
    _侧滑.打开侧滑();
  end)

  function 入口文件()
    _入口文件 = _入口文件 or 弹窗.普通(当前界面,
    "更改入口文件",
    创建布局 {
      线性布局;
      主题 = 底层;
      {
        编辑框;
        赋值 = "_入口名";
        文本 = _设置.入口文件;
        默认文本 = "index.lua";
      }
    },
    "更改",function()
      if _入口名.文本 == "" then
        弹窗.提示 "入口文件不要留空 ~";
      else
        local _文件 = 当前目录.."/".._入口名.文本;
        if 文件.是文件(_文件) then
          读取设置();
          _设置.入口文件 = _入口名.文本;
          保存设置();
          弹窗.提示 "更改成功 ~";
          _入口文件.隐藏();
        else
          弹窗.提示 "文件不存在 ~";
        end
      end
    end,
    "取消",function()
      _入口文件.隐藏();
    end)
    _入口文件.显示();
  end

else

  弹窗.提示 "配置文件不存在 ~";
  当前界面.结束();

end
