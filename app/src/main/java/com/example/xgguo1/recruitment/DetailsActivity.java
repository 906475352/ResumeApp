package com.example.xgguo1.recruitment;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.xgguo1.recruitment.JavaBean.Recruitment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class DetailsActivity extends AppCompatActivity {

    List<Recruitment> mData;
    private static ImageLoader imageLoader;// 图片缓存器
    String objectid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        RequestQueue queue = Volley.newRequestQueue(this); // 请求队列
        imageLoader = new ImageLoader(queue, new VolleyBitmapLruCache(this));
        Intent i=getIntent();
        objectid=i.getStringExtra("objectid");
//        Toast.makeText(getBaseContext(),objectid,Toast.LENGTH_SHORT).show();
        BmobQuery<Recruitment> query = new BmobQuery<Recruitment>();
        String sql="select * from Recruitment where objectId=?";
        query.setSQL(sql);
        query.setPreparedParams(new Object[]{objectid});
        query.doSQLQuery(new SQLQueryListener<Recruitment>() {
            @Override
            public void done(BmobQueryResult<Recruitment> bmobQueryResult, BmobException e) {
                mData=(List<Recruitment>)bmobQueryResult.getResults();
//                Toast.makeText(getBaseContext(),bmobQueryResult.toString(),Toast.LENGTH_SHORT).show();
                CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
                collapsingToolbar.setTitle(mData.get(0).getJbPosition());

                NetworkImageView ivImage = (NetworkImageView)findViewById(R.id.CompanyImg);
                ivImage.setImageUrl(mData.get(0).getJbCompanyImg().getFileUrl(),imageLoader);

                TextView requestment=(TextView)findViewById(R.id.RequestmentText);
                requestment.setText(mData.get(0).getJbPositionRequest());

                TextView responsibility = (TextView) findViewById(R.id.ResponsibilityText);
                responsibility.setText(mData.get(0).getJbPositionDesc());
            }
        });


    }

    public void BtnModify(View view) {
        Intent i=new Intent(getBaseContext(),ModifyActivity.class);
        i.putExtra("objectid",objectid);
        startActivity(i);
    }

    public void BtnDelete(View view) {
        Recruitment recruitment = new Recruitment();
        recruitment.setObjectId(objectid);
        recruitment.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                Intent intentBack = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intentBack);
                finish();
            }
        });
    }
}
