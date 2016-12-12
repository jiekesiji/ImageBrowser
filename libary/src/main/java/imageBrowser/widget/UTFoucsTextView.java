package imageBrowser.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * Created by cj on 2016/11/29.
 * Function:
 * Desc:
 */

public class UTFoucsTextView extends TextView {
    public UTFoucsTextView(Context context) {
        super(context);
    }

    public UTFoucsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UTFoucsTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //请求父控件不要拦截，不然偶尔点击textview会相应photoview的点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }
}
