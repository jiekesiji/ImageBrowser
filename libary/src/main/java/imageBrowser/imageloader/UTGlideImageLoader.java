package imageBrowser.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import imageBrowser.interfaces.ILoadingCallBack;


/**
 * Created by cj on 2016/11/25.
 * Function:
 * Desc:
 */

public class UTGlideImageLoader extends UTImageLoader {


    private static RequestOptions getRequestOptions() {
        RequestOptions requestOptions = new RequestOptions()
                .centerInside() // 填充方式
                .priority(Priority.HIGH) //优先级
                .diskCacheStrategy(DiskCacheStrategy.ALL); //缓存策略
        return requestOptions;
    }

    @Override
    public void disPlayImage(Context context, ImageView imageView, String url, final ILoadingCallBack loadingCallBack) {
        url = getPath(url);
        Glide.with(context)
                .load(url)//
                .apply(getRequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        loadingCallBack.loadingError();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        loadingCallBack.loadingComplete();
                        return false;
                    }
                })
                .into(imageView);
    }


    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
