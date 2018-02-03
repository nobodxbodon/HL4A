package 间.接口;

import 间.工具.*;

public class 调用 {

    public static 方法 代理(final Object $对象,final String $方法名) {
        return new 方法() {
            @Override
            public Object 调用(Object[] $参数) {
                反射.调用方法($对象, $方法名, $参数);
                return null;
            }
        };
    }
    
    public static 方法 异步代理(final Object $对象,final String $方法名) {
        return new 线程<Object>(new 方法<Object>() {
            @Override
            public Object 调用(Object[] $参数) {
                反射.调用方法($对象, $方法名, $参数);
                return null;
            }
        }).启动;
    }

    public static 方法 空方法 = new 方法() {
        @Override
        public Object 调用(Object[] $参数) {
            return null;
        }
    };
    
    public static Object 普通(方法 $方法,Object... $参数) {
        return $方法.调用($参数);
    }

    public static Object 安全(方法 $方法,Object... $参数) {
        try {
            return 普通($方法, $参数);
        } catch (Exception $错误) {
        }
        return null;

    }

    public static Object 事件(方法 $方法,Object... $参数) {
        if ($方法 != null)
            return 普通($方法, $参数);
        return null;
    }

}
