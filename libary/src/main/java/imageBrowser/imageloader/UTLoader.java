package imageBrowser.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import imageBrowser.interfaces.ILoadingCallBack;

/**
 * Created by cj on 2016/11/25.
 * Function:
 * Desc:
 */

public class UTLoader {
    private static UTImageLoader loader;

    private UTLoader(){
    }

    private static final UTImageLoader getLoader(){
        if (loader == null){
            synchronized (UTLoader.class){
                if(loader == null){
                    if (isClassExists("com.bumptech.glide.Glide")){
                        loader = new UTGlideImageLoader();
                    }else if(isClassExists("com.squareup.picasso.Picasso")){
                        loader = new UTPicassoImageLoader();
                    }else if(isClassExists("com.nostra13.universalimageloader.core.ImageLoader")){
                        loader = new UTPicassoImageLoader();
                    }else{
                        throw new RuntimeException("you must use one of 「Glide、Picasso、universal-image-loader」in your gradle");
                    }
                }
            }
        }
        return loader;
    }

    /**
     * 判断一个应用的是那个库
     * @param classFullName
     * @return
     */
    private static final boolean isClassExists(String classFullName){
        try{
            Class.forName(classFullName);
            return true;
        }catch (ClassNotFoundException e){
            return false;
        }
    }

    public static void displayImage(Context context, ImageView imageView, String url, ILoadingCallBack loadingCallBack) {
        getLoader().disPlayImage(context,imageView,url,loadingCallBack);
    }
    public static Bitmap getCacheImage(String path) {
        return getLoader().getCacheImage(path);
    }
}
