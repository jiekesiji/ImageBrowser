package imageBrowser.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.utsoft.libary.R;

import java.util.List;

import imageBrowser.activity.UTImageBrowserActivity;
import imageBrowser.entity.UTImageEntity;
import imageBrowser.imageloader.UTLoader;
import imageBrowser.interfaces.ILoadingCallBack;
import photoview.PhotoView;
import photoview.PhotoViewAttacher;

import static com.utsoft.libary.R.id.photoView;


/**
 * Created by cj on 2016/11/25.
 * Function:
 * Desc:
 */

public class UTPreViewAdapter extends PagerAdapter implements PhotoViewAttacher.OnPhotoTapListener {
    private List<UTImageEntity> entities;
    private Context mContext;
    private View currentView;


    public UTPreViewAdapter(List<UTImageEntity> entities, Context mContext) {
        super();
        this.entities = entities;
        this.mContext = mContext;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    public ImageView getPrimaryImageView() {
        ImageView imageView = (ImageView) currentView.findViewById(photoView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_photoview, container, false);
        final PhotoView photoView = (PhotoView) view.findViewById(R.id.photoView);
        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress);

        UTImageEntity info = entities.get(position);
        photoView.setOnPhotoTapListener(this);
        showMidState(info, photoView);

        UTLoader.displayImage(mContext, photoView, info.bigImageUrl, new ILoadingCallBack() {
            @Override
            public void startLoading() {//该回调在有些加载框架不会被调用

            }

            @Override
            public void onLoading(long toal, int currentProgress) {//该回调在有些加载框架不会被调用

            }

            @Override
            public void loadingComplete() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void loadingError() {
                progressBar.setVisibility(View.GONE);
            }
        });
        container.addView(view);
        return view;
    }

    //找缓存
    private void showMidState(UTImageEntity info, PhotoView photoView) {

        Bitmap cacheImage = UTLoader.getCacheImage(info.bigImageUrl);
        if (cacheImage == null) cacheImage = UTLoader.getCacheImage(info.thumbnailUrl);
        if (cacheImage == null) {
            photoView.setImageResource(R.drawable.ic_default_color);
        } else {
            photoView.setImageBitmap(cacheImage);
        }
    }


    @Override
    public int getCount() {
        if (entities != null) return entities.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //单击时使用的回调
    @Override
    public void onPhotoTap(View view, float x, float y) {
        //当前imageview为空的时候就不往下走，不然会造成空指针异常
//        if (progressBar.getVisibility() != View.GONE) return;
        ((UTImageBrowserActivity) mContext).finishActivityAnim();
    }

    @Override
    public void onOutsidePhotoTap() {

    }
}
