package imageBrowser.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.utsoft.libary.R;

import java.util.List;

import imageBrowser.adapter.UTPreViewAdapter;
import imageBrowser.entity.UTImageEntity;
import imageBrowser.entity.UTLayoutEntity;
import imageBrowser.interfaces.ISaveCallBack;
import imageBrowser.util.UTDimenUtil;
import imageBrowser.util.UTDrawableUtil;
import imageBrowser.util.UTFileUtil;
import imageBrowser.widget.UTFoucsTextView;

/**
 * Created by cj on 2016/11/25.
 * Function:展示大图的activity
 * Desc:
 */

public class UTImageBrowserActivity extends Activity implements ViewTreeObserver.OnPreDrawListener, View.OnClickListener {


    public static final String IMAGE_INFOS = "IMAGE_INFOS";
    public static final String CURRENT_ITEM = "CURRENT_ITEM";
    public static final String LAYOUT_INFO = "LAYOUT_INFO";
    public static final String SAVE_CALLBACK = "SAVE_CALLBACK";

    public int duration;

    public static final int TYPE_POINT = 1;//数字风格
    public static final int TYPE_TEXT = 2;//文字风格
    private int type_indicator;//提示器风格

    private int currentItem;
    private int screenWidth;
    private int screenHeight;

    private int imageHeight;
    private int imageWidth;


    private UTPreViewAdapter adapter;

    private RelativeLayout rootView;//整个布局的根文件
    private TextView tv_pager;//指示器文字

    private float indciatorTextSize;//指示器文字大小
    private int indciatorTextColor;//指示器字体颜色

    private UTFoucsTextView tv_save;
    private int left;
    private int top;
    private int right;
    private int bottom;

    private float saveTextSize;
    private int saveTextColor;
    //背景状态选择器的颜色
    private int pressColor;
    private int color;
    private int backgrounConner;

    private String filePath;


    private ViewPager viewPager;

    private List<UTImageEntity> imageEntities;

    private RelativeLayout container;
    private int marginButtom;//距离屏幕底部的距离


    //解决多次点击重复打开出现的bug
    public static int endFlag = 0;

    private int pointMargin;//小圆点之间的间距
    private int pointRadious;
    private int selectColor;
    private int normalColor;

    private ISaveCallBack callBack;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        endFlag = 1;
        setContentView(R.layout.activity_preimage);

        initView();

        getScreenInfo();

        resolveIntent();

        adapter = new UTPreViewAdapter(imageEntities, this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.getViewTreeObserver().addOnPreDrawListener(this);


    }

    private void resolveIntent() {
        Intent intent = getIntent();
        imageEntities = (List<UTImageEntity>) (intent.getSerializableExtra(IMAGE_INFOS));
        currentItem = intent.getIntExtra(CURRENT_ITEM, 0);
        callBack = (ISaveCallBack) intent.getSerializableExtra(SAVE_CALLBACK);
        UTLayoutEntity entity = (UTLayoutEntity) (intent.getParcelableExtra(LAYOUT_INFO));
        initLayoutParams(entity);
    }

    /**
     * 获取该activity的一些配置参数
     *
     * @param entity
     */
    private void initLayoutParams(UTLayoutEntity entity) {
        type_indicator = entity.getIndicatorStyle() == 0 ? TYPE_POINT : entity.getIndicatorStyle();
        indciatorTextSize = entity.getIndciatorTextSize() == 0 ? 14 : entity.getIndciatorTextSize();
        indciatorTextColor = entity.getIndciatorTextColor() == 0 ? Color.WHITE : entity.getIndciatorTextColor();
        left = entity.getLeft() == -1 ? UTDimenUtil.dip2px(this, 10) : entity.getLeft();
        top = entity.getTop() == -1 ? UTDimenUtil.dip2px(this, 10) : entity.getTop();
        right = entity.getRight() == -1 ? UTDimenUtil.dip2px(this, 16) : entity.getRight();
        bottom = entity.getBottom() == -1 ? UTDimenUtil.dip2px(this, 18) : entity.getBottom();
        saveTextSize = entity.getSaveTextSize() == 0 ? 14 : entity.getSaveTextSize();
        saveTextColor = entity.getSaveTextColor() == 0 ? Color.WHITE : entity.getSaveTextColor();
        pressColor = entity.getPressColor() == 0 ? Color.TRANSPARENT: entity.getPressColor();
        color = entity.getColor() == 0 ? Color.TRANSPARENT : entity.getColor();
        backgrounConner = entity.getBackgrounConner() == 0 ? UTDimenUtil.dip2px(this, 5) : entity.getBackgrounConner();
        marginButtom = entity.getMarginButtom() == 0 ? 0 : entity.getMarginButtom();
        pointMargin = entity.getPointMargin() == 0 ? UTDimenUtil.dip2px(this, 15) : entity.getPointMargin();
        pointRadious = entity.getPointRadious() == 0 ? UTDimenUtil.dip2px(this, 7) : entity.getPointRadious();
        selectColor = entity.getSelectColor() == 0 ? Color.parseColor("#ffffff") : entity.getSelectColor();
        normalColor = entity.getNormalColor() == 0 ? Color.parseColor("#33E1DFDF") : entity.getNormalColor();
        duration = entity.getAnimDuration() == -1 ? 200 : entity.getAnimDuration();
        filePath = entity.getSavePath() == null ? getApplication().getFilesDir().getAbsolutePath() + "/save/" : entity.getSavePath();
    }

    @Override
    protected void onStart() {
        super.onStart();

        renderLayOut();
        setIndicator();
        renderIndicator();
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                currentItem = position;
                renderIndicator();
            }
        });
    }

    /**
     * 设置下方容器和右上方的文字的布局
     */
    private void renderLayOut() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) container.getLayoutParams();
        layoutParams.bottomMargin = marginButtom;
        container.setLayoutParams(layoutParams);

        tv_save.setTextSize(saveTextSize);
        tv_save.setTextColor(saveTextColor);
        tv_save.setOnClickListener(this);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_save.getLayoutParams();
        params.leftMargin = left;
        params.topMargin = top;
        params.rightMargin = right;
        params.bottomMargin = bottom;
        tv_save.setLayoutParams(params);

        Drawable normalDrawable = UTDrawableUtil.generateDrawable(color, backgrounConner);
        Drawable pressDrawable = UTDrawableUtil.generateDrawable(pressColor, backgrounConner);
        Drawable drawable = UTDrawableUtil.generateSelector(pressDrawable, normalDrawable);
        tv_save.setBackgroundDrawable(drawable);

    }

    /**
     * 根据用户需求设置指示器风格
     */
    private void setIndicator() {
        if (type_indicator == TYPE_POINT) {//小点风格
            int count = viewPager.getAdapter().getCount();
            container.setGravity(Gravity.CENTER);
            for (int i = 0; i < count; i++) {
                ImageView imageView = new ImageView(this);
                setSelectorColor(imageView, normalColor);
                if (i != 0) {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    layoutParams.leftMargin = i * pointMargin;
                    container.addView(imageView, layoutParams);
                } else {
                    container.addView(imageView);
                }
            }
        } else if (type_indicator == TYPE_TEXT) {//文本风格
            tv_pager = new UTFoucsTextView(this);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.leftMargin = UTDimenUtil.dip2px(this, 16);

            tv_pager.setTextSize(indciatorTextSize);
            tv_pager.setTextColor(indciatorTextColor);

            container.addView(tv_pager,layoutParams);
        }
    }

    private void setSelectorColor(ImageView imageView, int color) {
        //动态改变颜色，和半径
        GradientDrawable myGrad = new GradientDrawable();
        myGrad.setColor(color);
        myGrad.setCornerRadius(pointRadious);
        myGrad.setSize(pointRadious, pointRadious);
        imageView.setBackgroundDrawable(myGrad);
    }

    /**
     * 渲染指示器
     */
    private void renderIndicator() {
        if (type_indicator == TYPE_POINT) {//小点风格
            int count = viewPager.getAdapter().getCount();
            for (int i = 0; i < count; i++) {
                ImageView imageView = (ImageView) container.getChildAt(i);
                if (i == currentItem) {
                    setSelectorColor(imageView, selectColor);
                } else {
                    setSelectorColor(imageView, normalColor);
                }
            }
        } else if (type_indicator == TYPE_TEXT) {//文本风格
            tv_pager.setText(String.format(getString(R.string.select), currentItem + 1 + "", imageEntities.size() + ""));
        }
    }

    //开始会之前的动画
    @Override
    public boolean onPreDraw() {

        rootView.getViewTreeObserver().removeOnPreDrawListener(this);
        //获取当前要展示的item
        final View view = adapter.getPrimaryItem();
        final ImageView imageView = adapter.getPrimaryImageView();
        //修正宽和高
        fixHeightAndWidth(imageView);

        final UTImageEntity info = imageEntities.get(currentItem);
        final float vx = info.imageViewWidth * 1.0f / imageWidth;
        final float vy = info.imageViewHeight * 1.0f / imageHeight;
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long duration = animation.getDuration();
//                long playTime = animation.getCurrentPlayTime();
                float fraction = animation.getAnimatedFraction();

//                float fraction = duration > 0 ? (float) playTime / duration : 1f;
//                System.out.println("fraction>>>>>>" + fraction + ">>>>>>>>>" + fraction + "duration>>>" + duration + "playTime>>>>" + playTime + "fraction" + playTime/duration);
                if (fraction > 1) fraction = 1;
                view.setTranslationX(evaluateInt(fraction, info.imageViewX + info.imageViewWidth / 2 - imageView.getWidth() / 2, 0));
                view.setTranslationY(evaluateInt(fraction, info.imageViewY + info.imageViewHeight / 2 - imageView.getHeight() / 2, 0));

                view.setScaleX(evaluateFloat(fraction, vx, 1));
                view.setScaleY(evaluateFloat(fraction, vy, 1));
                view.setAlpha(fraction);
                rootView.setBackgroundColor(evaluateArgb(fraction, Color.TRANSPARENT, Color.BLACK));
            }
        });
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                rootView.setBackgroundColor(0x0);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                container.setVisibility(View.VISIBLE);
                tv_save.setVisibility(View.VISIBLE);

//                progressBar.setVisibility(View.VISIBLE);
            }
        });
        animator.setDuration(duration);
        animator.start();
        return true;
    }

    private void fixHeightAndWidth(ImageView imageView) {
        //获取图片真实大小
        Drawable drawable = imageView.getDrawable();
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();

        float h = screenHeight * 1.0f / intrinsicHeight;
        float w = screenWidth * 1.0f / screenWidth;

        if (h > w) {
            h = w;
        } else {
            w = h;
        }
        imageHeight = (int) (intrinsicHeight * h);
        imageWidth = (int) (intrinsicWidth * w);
    }

    //关闭activity时执行的动画
    public void finishActivityAnim() {
        container.setVisibility(View.INVISIBLE);
        tv_save.setVisibility(View.INVISIBLE);

        final View view = adapter.getPrimaryItem();
        final ImageView imageView = adapter.getPrimaryImageView();
//        if (imageView != null){
        //imageView.getDrawable()为空则说明正在加载
        if (imageView.getDrawable() != null){
            fixHeightAndWidth(imageView);
        }

        final UTImageEntity info = imageEntities.get(currentItem);
        final float vx = info.imageViewWidth * 1.0f / imageWidth;
        final float vy = info.imageViewHeight * 1.0f / imageHeight;
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1.0f);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                long duration = animation.getDuration();
                long playTime = animation.getCurrentPlayTime();
//                float fraction = duration > 0 ? (float) playTime / duration : 1f;
                float fraction = animation.getAnimatedFraction();
                if (fraction > 1) fraction = 1;

                if (imageView != null) {
                    view.setTranslationX(evaluateInt(fraction, 0, info.imageViewX + info.imageViewWidth / 2 - imageView.getWidth() / 2));
                    view.setTranslationY(evaluateInt(fraction, 0, info.imageViewY + info.imageViewHeight / 2 - imageView.getHeight() / 2));
                }
                view.setScaleX(evaluateFloat(fraction, 1, vx));
                view.setScaleY(evaluateFloat(fraction, 1, vy));
                view.setAlpha(1 - fraction);
                rootView.setBackgroundColor(evaluateArgb(fraction, Color.BLACK, Color.TRANSPARENT));
            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                rootView.setBackgroundColor(0x0);
            }
        });
        animator.setDuration(duration);
        animator.start();
    }

    //获取屏幕参数
    public void getScreenInfo() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screenWidth = metric.widthPixels;
        screenHeight = metric.heightPixels;
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        rootView = (RelativeLayout) findViewById(R.id.rootView);
        container = (RelativeLayout) findViewById(R.id.container);
        tv_save = (UTFoucsTextView) findViewById(R.id.tv_save);
//        progressBar = (ProgressBar) findViewById(R.id.progress);
    }

    @Override
    public void onBackPressed() {
        finishActivityAnim();
    }

    /**
     * Integer 估值器
     */
    public Integer evaluateInt(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (startInt + fraction * (endValue - startInt));
    }

    /**
     * Float 估值器
     */
    public Float evaluateFloat(float fraction, Number startValue, Number endValue) {
        float startFloat = startValue.floatValue();
        return startFloat + fraction * (endValue.floatValue() - startFloat);
    }

    /**
     * Argb 估值器
     */
    public int evaluateArgb(float fraction, int startValue, int endValue) {
        int startA = (startValue >> 24) & 0xff;
        int startR = (startValue >> 16) & 0xff;
        int startG = (startValue >> 8) & 0xff;
        int startB = startValue & 0xff;

        int endA = (endValue >> 24) & 0xff;
        int endR = (endValue >> 16) & 0xff;
        int endG = (endValue >> 8) & 0xff;
        int endB = endValue & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24//
                | (startR + (int) (fraction * (endR - startR))) << 16//
                | (startG + (int) (fraction * (endG - startG))) << 8//
                | (startB + (int) (fraction * (endB - startB)));
    }

    @Override
    protected void onDestroy() {
        endFlag = 0;
        callBack = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_save) {
            //该方法不可行，因为获取的cacheBitmap相当是对当前的imageview进行了截图
//            ImageView imageView = adapter.getPrimaryImageView();
//            imageView.setDrawingCacheEnabled(true);
//            Bitmap bitmap = imageView.getDrawingCache();
//            UTFileUtil.saveBitmap(this,bitmap,filePath);
//            imageView.setDrawingCacheEnabled(false);
            // TODO: 2016/12/1  暂时解决办法从网络下载，后期研究各个框架获取内存中的图片的方法
            UTFileUtil.saveImageView(this, imageEntities.get(currentItem).bigImageUrl, filePath, callBack);
        }
    }
}
