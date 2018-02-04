<?php

namespace 间\收集;

use 间\收集\哈希表;

class 集合 extends 哈希表 {

private $表 = array();
private $数量 = 0;

public function 设置($键值, $内容) {
$this->表[$键值] = $内容;
}

public function 读取($键值) {
if (检查($键值)) {
return $this->表[$键值];
} else {
return null;
}
}

public function 检查($键值) {
return array_key_exists($键值, $this->表)
}

public function 添加($内容) {
$this - > 数量 ++;
}

}