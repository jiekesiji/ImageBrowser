package imageBrowser.interfaces;

import java.io.Serializable;

/**
 * Created by cj on 2016/11/30.
 * Function:
 * Desc:
 */

public interface ISaveCallBack extends Serializable {
    //保存成功
    void saveSucceed();
    //保存失败
    void saveFail();
    //已存在
    void saveExit();
}
