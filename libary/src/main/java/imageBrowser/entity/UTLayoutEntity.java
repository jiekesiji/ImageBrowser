package imageBrowser.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cj on 2016/11/29.
 * Function:
 * Desc:一些关于UTImagePreactivity的一些配置信息
 */

public class UTLayoutEntity implements Parcelable {

    private float indciatorTextSize ;//指示器文字大小
    private int indciatorTextColor;//指示器字体颜色

    private int left = -1;//右上角确定按钮的离父布局的margin值
    private int top = -1;
    private int right = -1;
    private int bottom = -1;

    private float saveTextSize;//右上角文字属性
    private int saveTextColor;

    private int pressColor ;//状态选择器的相关属性
    private int color;
    private int backgrounConner ;

    private int marginButtom;//距离屏幕底部的距离


    private int pointMargin ;//小圆点的属性
    private int pointRadious ;
    private int selectColor;
    private int normalColor;

    private int indicatorStyle;

    private String savePath;//图片保存路径


    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public int getAnimDuration() {
        return animDuration;
    }

    public void setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
    }

    private int animDuration = -1;


    public int getIndicatorStyle() {
        return indicatorStyle;
    }

    public void setIndicatorStyle(int indicatorStyle) {
        this.indicatorStyle = indicatorStyle;
    }

    public int getBackgrounConner() {
        return backgrounConner;
    }

    public void setBackgrounConner(int backgrounConner) {
        this.backgrounConner = backgrounConner;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getIndciatorTextColor() {
        return indciatorTextColor;
    }

    public void setIndciatorTextColor(int indciatorTextColor) {
        this.indciatorTextColor = indciatorTextColor;
    }

    public float getIndciatorTextSize() {
        return indciatorTextSize;
    }

    public void setIndciatorTextSize(float indciatorTextSize) {
        this.indciatorTextSize = indciatorTextSize;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getMarginButtom() {
        return marginButtom;
    }

    public void setMarginButtom(int marginButtom) {
        this.marginButtom = marginButtom;
    }

    public int getNormalColor() {
        return normalColor;
    }

    public void setNormalColor(int normalColor) {
        this.normalColor = normalColor;
    }

    public int getPointMargin() {
        return pointMargin;
    }

    public void setPointMargin(int pointMargin) {
        this.pointMargin = pointMargin;
    }

    public int getPointRadious() {
        return pointRadious;
    }

    public void setPointRadious(int pointRadious) {
        this.pointRadious = pointRadious;
    }

    public int getPressColor() {
        return pressColor;
    }

    public void setPressColor(int pressColor) {
        this.pressColor = pressColor;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getSaveTextColor() {
        return saveTextColor;
    }

    public void setSaveTextColor(int saveTextColor) {
        this.saveTextColor = saveTextColor;
    }

    public float getSaveTextSize() {
        return saveTextSize;
    }

    public void setSaveTextSize(float saveTextSize) {
        this.saveTextSize = saveTextSize;
    }

    public int getSelectColor() {
        return selectColor;
    }

    public void setSelectColor(int selectColor) {
        this.selectColor = selectColor;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.indciatorTextSize);
        dest.writeInt(this.indciatorTextColor);
        dest.writeInt(this.left);
        dest.writeInt(this.top);
        dest.writeInt(this.right);
        dest.writeInt(this.bottom);
        dest.writeFloat(this.saveTextSize);
        dest.writeInt(this.saveTextColor);
        dest.writeInt(this.pressColor);
        dest.writeInt(this.color);
        dest.writeInt(this.backgrounConner);
        dest.writeInt(this.marginButtom);
        dest.writeInt(this.pointMargin);
        dest.writeInt(this.pointRadious);
        dest.writeInt(this.selectColor);
        dest.writeInt(this.normalColor);
        dest.writeInt(this.indicatorStyle);
        dest.writeString(this.savePath);
        dest.writeInt(this.animDuration);
    }

    public UTLayoutEntity() {
    }

    protected UTLayoutEntity(Parcel in) {
        this.indciatorTextSize = in.readFloat();
        this.indciatorTextColor = in.readInt();
        this.left = in.readInt();
        this.top = in.readInt();
        this.right = in.readInt();
        this.bottom = in.readInt();
        this.saveTextSize = in.readFloat();
        this.saveTextColor = in.readInt();
        this.pressColor = in.readInt();
        this.color = in.readInt();
        this.backgrounConner = in.readInt();
        this.marginButtom = in.readInt();
        this.pointMargin = in.readInt();
        this.pointRadious = in.readInt();
        this.selectColor = in.readInt();
        this.normalColor = in.readInt();
        this.indicatorStyle = in.readInt();
        this.savePath = in.readString();
        this.animDuration = in.readInt();
    }

    public static final Parcelable.Creator<UTLayoutEntity> CREATOR = new Parcelable.Creator<UTLayoutEntity>() {
        @Override
        public UTLayoutEntity createFromParcel(Parcel source) {
            return new UTLayoutEntity(source);
        }

        @Override
        public UTLayoutEntity[] newArray(int size) {
            return new UTLayoutEntity[size];
        }
    };
}
