package imageBrowser.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.utsoft.libary.R;

import imageBrowser.interfaces.ILoadingCallBack;


/**
 * Created by cj on 2016/11/25.
 * Function:
 * Desc:
 */

public class UTUILImageLoader extends UTImageLoader {
    public DisplayImageOptions options = new DisplayImageOptions.Builder()//
            .showImageOnFail(R.drawable.pre_error)
            .showImageForEmptyUri(R.drawable.pre_error)
            .cacheInMemory(true)                                //设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)                                  //设置下载的图片是否缓存在SD卡中
            .build();


    @Override
    public void disPlayImage(Context context, ImageView imageView, String url,final ILoadingCallBack loadingCallBack) {
        url = getPath(url);

        ImageLoader.getInstance()
                .displayImage(url, imageView, options, new SimpleImageLoadingListener(){
                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        loadingCallBack.loadingComplete();
                    }

                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        loadingCallBack.startLoading();
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        loadingCallBack.loadingError();
                    }
                });
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }

}
