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
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543389812000&di=1768a7c6024f5abfba29258e3eafe91e&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3Dd2824fcdb63eb13550cabff8ce77c2a6%2F32fa828ba61ea8d3af9717219d0a304e251f5859.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543389811999&di=c57a0c625065d04b791ce97128b1d618&imgtype=0&src=http%3A%2F%2Fpic7.nipic.com%2F20100429%2F3017209_133726827311_2.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543389812000&di=1768a7c6024f5abfba29258e3eafe91e&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3Dd2824fcdb63eb13550cabff8ce77c2a6%2F32fa828ba61ea8d3af9717219d0a304e251f5859.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1543389812000&di=1768a7c6024f5abfba29258e3eafe91e&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimage%2Fc0%253Dshijue1%252C0%252C0%252C294%252C40%2Fsign%3Dd2824fcdb63eb13550cabff8ce77c2a6%2F32fa828ba61ea8d3af9717219d0a304e251f5859.jpg");
        ImgBrowserAdapter adapter = new ImgBrowserAdapter(list, this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
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
            if (i % 2 == 0) {
                imageView = (ImageView) gridView.getChildAt(i).findViewById(R.id.img_gridView);
            }else {
                imageView = null;
            }
            helper.addImageView(imageView, list.get(i));
        }
        helper.setIndicatorStyle(currentStyle);
        helper.startPreActivity(position);
    }
}
