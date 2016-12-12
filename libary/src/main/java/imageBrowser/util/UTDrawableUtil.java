package imageBrowser.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;

/**
 * Created by cj on 2016/11/29.
 * Function:
 * Desc:
 */

public class UTDrawableUtil {
    public static Drawable generateSelector(Drawable pressed, Drawable normal){
        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);//设置按下的图片
        drawable.addState(new int[]{}, normal);//设置默认的图片
        //设置状态选择器过度动画
        if(Build.VERSION.SDK_INT>10){
            drawable.setEnterFadeDuration(200);
            drawable.setExitFadeDuration(200);
        }
        return drawable;
    }

    public static Drawable generateDrawable(int rgb,float radius){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);//设置为矩形，默认就是矩形
        drawable.setColor(rgb);
        drawable.setCornerRadius(radius);
        return drawable;
    }
}
