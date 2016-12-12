package imageBrowser.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import imageBrowser.interfaces.ILoadingCallBack;

/**
 * Created by cj on 2016/11/25.
 * Function:ImageLoader的抽象父类
 * Desc:
 */

public abstract class UTImageLoader {
    protected String getPath(String path) {
        if (path == null) {
            path = "";
        }

        if (!path.startsWith("http") && !path.startsWith("file")) {
            path = "file://" + path;
        }
        return path;
    }

    //怎样加载图片和现实图片
    public abstract void disPlayImage(Context context, ImageView imageView, String url, ILoadingCallBack loadingCallBack);


    //当前框架的本地缓存图片
    public abstract Bitmap getCacheImage(String url);

}
