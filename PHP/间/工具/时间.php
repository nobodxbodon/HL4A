<?php

namespace 间\工具;

class 时间 {

public static function 时间戳() {
return date_timestamp_get(date_create());
}

public static function 年份() {
return date("Y");
}

public static function 月份() {
return date("m");
}

public static function 星期() {
return date("N");
}

public static function 天数() {
return date("d");
}

public static function 小时() {
return date("H");
}

public static function 分钟() {
return date("i");
}

public static function 秒数() {
return date("s");
}

public static function 微秒() {
return date("u");
}

}