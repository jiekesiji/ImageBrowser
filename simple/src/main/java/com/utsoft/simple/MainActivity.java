package com.utsoft.simple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.utsoft.simple.adapter.ImgBrowserAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import imageBrowser.activity.UTImageBrowserActivity;
import imageBrowser.helper.UTPreImageViewHelper;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    @BindView(R.id.gridView_mainActivity)
    GridView gridView;
    @BindView(R.id.rb_text)
    RadioButton rbText;
    @BindView(R.id.rb_point)
    RadioButton rbPoint;
    @BindView(R.id.rg_setting)
    RadioGroup rgSetting;


    private UTPreImageViewHelper helper;
    private int currentStyle = UTImageBrowserActivity.TYPE_POINT;
    private List<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        rgSetting.setOnCheckedChangeListener(this);
        list = new ArrayList<>();
        list.add("http://img5.imgtn.bdimg.com/it/u=4026157554,782106015&fm=21&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=2349436394,432792758&fm=21&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=1928187712,1085842886&fm=21&gp=0.jpg");
        list.add("http://img0.imgtn.bdimg.com/it/u=847160309,407021011&fm=21&gp=0.jpg");
        list.add("http://img4.imgtn.bdimg.com/it/u=1702430413,3824866561&fm=21&gp=0.jpg");
        list.add("http://img1.imgtn.bdimg.com/it/u=1465127792,2108785823&fm=21&gp=0.jpg");
        ImgBrowserAdapter adapter = new ImgBrowserAdapter(list,this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.rb_text:
                currentStyle = UTImageBrowserActivity.TYPE_TEXT;
            break;
             case R.id.rb_point:
                 currentStyle = UTImageBrowserActivity.TYPE_POINT;
            break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        helper = new UTPreImageViewHelper(this);
        ImgBrowserAdapter adapter = (ImgBrowserAdapter) parent.getAdapter();
        ImageView imageView;
        for (int i = 0; i < adapter.getCount(); i++) {
            imageView = (ImageView) gridView.getChildAt(i).findViewById(R.id.img_gridView);
            helper.addImageView(imageView,list.get(i));
        }
        helper.setIndicatorStyle(currentStyle);
        helper.startPreActivity(position);
    }
}
