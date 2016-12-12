package imageBrowser.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.utsoft.libary.R;

import imageBrowser.interfaces.ILoadingCallBack;


/**
 * Created by cj on 2016/11/25.
 * Function:
 * Desc:
 */

public class UTGlideImageLoader extends UTImageLoader {


    @Override
    public void disPlayImage(Context context, ImageView imageView, String url, final ILoadingCallBack loadingCallBack) {
        url = getPath(url);
        Glide.with(context)
                .load(url)//
                .error(R.drawable.pre_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        loadingCallBack.loadingError();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
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
