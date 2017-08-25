package imageBrowser.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

import imageBrowser.activity.UTImageBrowserActivity;
import imageBrowser.entity.UTImageEntity;
import imageBrowser.entity.UTLayoutEntity;
import imageBrowser.interfaces.ISaveCallBack;

/**
 * Created by cj on 2016/11/29.
 * Function:UTImagePreActivity的帮助配置类
 * Desc:
 */

public class UTPreImageViewHelper {
    private ArrayList<UTImageEntity> entrys;//保存的一些图片位置信息
    private Activity activity;
    private int statusHeight;
    private UTLayoutEntity info;
    private ISaveCallBack callBack;




    public UTPreImageViewHelper(Activity activity) {
        this.activity = activity;
        entrys = new ArrayList<>();
        statusHeight = getStatusHeight(activity);
        info = new UTLayoutEntity();
    }

    /**
     * 添加需要在大图查看页面的imageview
     * @param imageView 小图的imageview
     * @param url 在大图查看页查看的图片的url
     */
    public void addImageView(ImageView imageView, String url){
        UTImageEntity info = new UTImageEntity();
        info.bigImageUrl = url;
        int[] points = new int[2];

        if (imageView != null){
            info.imageViewHeight = imageView.getHeight();
            info.imageViewWidth = imageView.getWidth();
            imageView.getLocationInWindow(points);
        }else{
            points[0] = getDeviceInfo()[0]/2;
            points[1] = getDeviceInfo()[1]/2;
        }

        info.imageViewX = points[0];
        info.imageViewY = points[1] - statusHeight;
        entrys.add(info);
    }

    public int[] getDeviceInfo(){
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        return new int[]{width,height};
    }

    /**
     * 开启大图查看页面
     */
    public void startPreActivity(int currentItem){
        //重复点击出现的bug
        if (UTImageBrowserActivity.endFlag != 0) return;

        Intent intent = new Intent(activity, UTImageBrowserActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(UTImageBrowserActivity.IMAGE_INFOS, (Serializable) entrys);
        bundle.putInt(UTImageBrowserActivity.CURRENT_ITEM, currentItem);
        bundle.putParcelable(UTImageBrowserActivity.LAYOUT_INFO,info);
        bundle.putSerializable(UTImageBrowserActivity.SAVE_CALLBACK, callBack);
        intent.putExtras(bundle);
        activity.startActivity(intent);
        activity.overridePendingTransition(0, 0);
    }

    /**
     * 设置保存成功的监听
     * @param callBack
     */
    public void registerCallBack(ISaveCallBack callBack) {
        this.callBack = callBack;
    }

    /**
     * 移除大图保存成功的监听
     */
    public void removeCallBack(){
        this.callBack = null;
    }

    /**
     * 获得状态栏的高度
     */
    private int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 设置图片保存路径
     * @param path
     */
    public void setFilePath(String path){info.setSavePath(path);}

    /**
     * 设置指示器显示的风格
     * @param style
     */
    public void setIndicatorStyle(int style){
        info.setIndicatorStyle(style);
    }

    public void setAnimDuration(int animDuration){
        info.setAnimDuration(animDuration);
    }

    /**
     * 设置指示器文字的大小
     * @param textSize
     */
    public void setIndciatorTextSize(float textSize){
        info.setIndciatorTextSize(textSize);
    }

    /**
     * 设置指示器文字的颜色
     * @param textColor
     */
    public void setIndciatorTextColor(int textColor){
        info.setIndciatorTextColor(textColor);
    }

    /**
     * 设置右上方文字的位置
     * @param left
     * @param top
     * @param right
     * @param buttom
     */
    public void setSaveTextMargin(int left ,int top,int right,int buttom){
        info.setTop(top);
        info.setBottom(buttom);
        info.setLeft(left);
        info.setRight(right);
    }

    /**
     * 设置右上方文字的大小
     * @param textSize
     */
    public void setSaveTextSize(float textSize){
        info.setSaveTextSize(textSize);
    }

    /**
     * 设置右上方文字的颜色
     * @param textColor
     */
    public void setSaveTextColor(int textColor){
        info.setSaveTextColor(textColor);
    }

    /**
     * 设置状态选择器按压下的颜色
     * @param color
     */
    public void setPressColor(int color){
        info.setPressColor(color);
    }

    /**
     * 设置未按下显示的颜色
     * @param textColor
     */
    public void setNormalColor(int textColor){
        info.setPressColor(textColor);
    }

    /**
     * 设置状态选择器的圆角
     * @param conner
     */
    public void setBackGroundConner(int conner){
        info.setBackgrounConner(conner);
    }

    /**
     * 设置指示器离屏幕底部的高度
     * @param marginButtom
     */
    public void setIndicatorMarginButtom(int marginButtom){
        info.setMarginButtom(marginButtom);
    }

    /**
     * 设置小圆点的间距,设置该方法的时候一定要加上小圆点的半径，不然会造成误差
     * @param margin
     */
    public void setPointMargin(int margin){
        info.setPointMargin(margin);
    }

    /**
     * 设置小圆点半径
     * @param radious
     */
    public void setPointRadious(int radious){
        info.setPointRadious(radious);
    }

    /**
     * 设置当前页小圆点的颜色
     * @param selectorColor
     */
    public void setPointSelectorColor(int selectorColor){
        info.setSelectColor(selectorColor);
    }

    /**
     * 设置正常情况下小圆点的颜色
     * @param noramlColor
     */
    public void setPointNoramlColor(int noramlColor){
        info.setNormalColor(noramlColor);
    }
}
