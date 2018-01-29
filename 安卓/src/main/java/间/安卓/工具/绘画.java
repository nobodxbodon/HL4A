package 间.安卓.工具;

import android.content.res.ColorStateList;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import 间.安卓.绘画.按下变色绘画;
import 间.安卓.绘画.点九图绘画;

public class 绘画 {

    public static Drawable 透明() {
        return 生成背景(颜色.透明, 颜色.半透明);
    }

    public static Drawable 白色() {
        return 生成背景(颜色.白色, 颜色.白透明);
    }

    public static Drawable 黑色() {
        return 生成背景(颜色.黑色, 颜色.黑透明);
    }

    public static Drawable 主题() {
        return 生成背景(主题.取主题颜色().取控件色(), 主题.取主题颜色().取基本色());
    }

    public static Drawable 生成背景(Object $普通,Object $按下) {
        if (设备.取SDK() > 21) {
            ShapeDrawable $波纹 = new ShapeDrawable();
            $波纹.setShape(new RectShape());
            InsetDrawable $绘画 = new InsetDrawable($波纹, -1);
            RippleDrawable $涟漪 = new RippleDrawable(ColorStateList.valueOf(主题.取主题颜色().取淡色()), 绘画.颜色转绘画($普通), $绘画);
            return $涟漪;
        } else {
            return new 按下变色绘画($普通, $按下);
        }
        //return new 涟漪绘画($普通,$按下,颜色.白色);
    }

    public static Drawable 颜色转绘画(Object $颜色) {
        return new ColorDrawable(视图.检查颜色($颜色));
    }

    public static Drawable 图片转绘画(String $文件) {
        return new BitmapDrawable(图片.读取($文件));
    }

    public static Drawable 点九图转绘画(String $文件) {
        return new 点九图绘画(图片.读取($文件));
    }

}
