<?php

namespace 间\收集;

class 哈希表 {

private $表 = array();

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

}