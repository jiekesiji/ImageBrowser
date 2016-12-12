# ImgBrowser
一个可以自己定制的大图查看库

## 简介 ##

　　近期公司的一个需求，点击后进入大图浏览界面，可以点击放大缩小，对该模块进行一个简单的封装。方便以后使用，欢迎star&fork，有什么意见和建议欢迎指教。该库参考了jeasonlzy　　 [http://https://github.com/jeasonlzy/NineGridView](http://https://github.com/jeasonlzy/NineGridView "九宫格")使用了大名鼎鼎的PhotoView库，再次感谢各位大神。

　　目前该库图片加载支持UILImageLoader，Glide，Picasso这三个库，三库选择一个就可以了。在大图查看界面中指示器和按钮可以自己定义，能够实现指示器风格和一些属性的设置（目前只有小圆点和文字两种，市面比较常见的，如果有需求后期可以加上），这些属性都是在UTPreImageViewHelper中进行设置。

![](http://i.imgur.com/fA0CYer.gif)

实际效果还可以但是录制gif图有点卡，将就将就。

----------
## 怎样使用 ##
**集成步骤：**

	1.在项目gradle中添加

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	2.在app gradle中添加

	dependencies {
	        compile 'com.github.jiekesiji:ImageBrowser:v1.0.0'
	}

**使用步骤：**

step0：在应用中配置

        <activity android:name="imageBrowser.activity.UTImageBrowserActivity"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"/>

step1：
	
	UTPreImageViewHelper helper = new UTPreImageViewHelper(context);

step2：
	
	helper.addImageView(imageView,imgeViewUrl);

step3：

	helper.startPreActivity(position);
>说明：

　　helper内部维护一个集合，在添加时候的顺序要个你打开时调用的startPreActivity中传入的position要对应，不然就会出现动画错乱现象。

## API说明： ##


<div>
    <table border="0">
      <tr>
        <th>方法名或类类名</th>
        <th>功能</th>
      </tr>
      <tr>
        <td>registerCallBack（ISaveCallBack callBack）</td>
        <td>点击保存成功时的回调，一定要记得unRegisterCallBack</td>
      </tr>
      <tr>
        <td>setFilePath(String path)</td>
        <td>设置保存文件的路径</td>
      </tr>
      <tr>
        <td>setIndicatorStyle（int style）</td>
        <td>设置指示器的风格，style取值为UTImageBrowserActivity.TYPE_POINT或UTImageBrowserActivity.TYPE_TEXT两种</td>
      </tr>
       <tr>
        <td>setAnimDuration(int animDuration)</td>
        <td>设置转场动画持续时长</td>
      </tr>
      <tr>
        <td>setIndciatorTextSize(float textSize)</td>
        <td>文字指示器的大小</td>
      </tr>
      <tr>
        <td>setIndciatorTextColor(float textColor)</td>
        <td>文字指示器的颜色</td>
      </tr>
      <tr>
        <td>setPointRadious(int radious)</td>
        <td>设置小圆点指示器的半径</td>
      </tr>
      <tr>
        <td>------</td>
        <td>------</td>
      </tr>
    </table>
</div>

　　更多设置请参看UTPreImageViewHelper，该类是一个对库的管理类，该类能达到对大图界面的ui的一些更改，指示器，按钮背景，状态选择器的设置等等。
