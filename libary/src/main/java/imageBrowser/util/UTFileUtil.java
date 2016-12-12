package imageBrowser.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Looper;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import imageBrowser.interfaces.ISaveCallBack;

/**
 * Created by cj on 2016/11/28.
 * Function:
 * Desc:根据文件名对文件进行存储
 */

public class UTFileUtil {

    //将bitmap对象保存到指定的文件夹中
    public static void saveBitmap(Context context,Bitmap bitmap, String filePath){
        try {
            File dirFile = new File(filePath);
            if(!dirFile.exists()){
                dirFile.mkdir();
            }
            File file = new File(filePath , "1.png");
            BufferedOutputStream bos  = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
            Toast.makeText(context, "保存成功！", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "保存失败！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 从网络下在图片进行保存
     * @param context
     * @param path
     * @param filePath
     * @param callBack
     */
    public static void saveImageView(final Context context, final String path, final String filePath, final ISaveCallBack callBack){

        File parent = new File(filePath);
        if (!parent.exists()) parent.mkdir();

        final File file = new File(parent , UTMd5Util.stringToMD5(path)+".png");

        if (path.startsWith("file://") || file.exists()){
            if (callBack!= null){
                callBack.saveExit();
            }else{
                Toast.makeText(context, "该文件本地已保存！", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        new Thread(){
            @Override
            public void run() {
                try {

                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5 * 1000);
                    conn.setRequestMethod("GET");

                    if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                        InputStream inStream = conn.getInputStream();

                        BufferedOutputStream bos  = new BufferedOutputStream(new FileOutputStream(file));
                        byte[] buffer = new byte[1024];
                        int len ;
                        while( (len=inStream.read(buffer)) != -1){
                            bos.write(buffer, 0, len);
                        }
                        bos.close();
                        inStream.close();
                        if (callBack!= null){
                            callBack.saveSucceed();
                        }else{
                            Looper.prepare();
                            Toast.makeText(context, "保存成功！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack!= null){
                        callBack.saveFail();
                    }else{
                        Looper.prepare();
                        Toast.makeText(context, "保存失败！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            }
        }.start();
    }
}
