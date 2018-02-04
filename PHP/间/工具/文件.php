<?php

namespace 间\工具;

use 间\数据\哈希表;

class 文件 {

private static $替换表 = new 哈希表();

public static 替换地址($地址,$目标) {
$this -> 替换表 -> 设置($地址,$目标);
}

public static 检查地址($地址) {
if (this -> 替换表 - > 检查($地址)) {
return
}
}

}