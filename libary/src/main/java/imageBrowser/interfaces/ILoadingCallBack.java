package imageBrowser.interfaces;

/**
 * Created by cj on 2016/12/1.
 * Function:
 * Desc:
 */

public interface ILoadingCallBack {
    //开始加载
    void startLoading();
    //正在加载
    void onLoading(long toal,int currentProgress);
    //加载完成
    void loadingComplete();
    //加载失败
    void loadingError();
}
