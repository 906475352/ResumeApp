package com.example.xgguo1.recruitment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.xgguo1.recruitment.JavaBean.Recruitment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChildRunningFragment extends Fragment {

    List<Recruitment> myData = new ArrayList<Recruitment>();
    private static ImageLoader imageLoader;// 图片缓存器
    RecyclerView recycleView;
    View view;

    public ChildRunningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_child_running, container, false);

        RequestQueue queue = Volley.newRequestQueue(getActivity()); // 请求队列
        imageLoader = new ImageLoader(queue, new VolleyBitmapLruCache(getActivity()));

        recycleView = (RecyclerView) view.findViewById(R.id.recycle_view);//找到当前recycleview组件
        //拿到数据
        getData();


        return view;
    }

    //执行查询操作拿到数据
    private void getData() {
        String str = "运营";
        BmobQuery<Recruitment> query = new BmobQuery<Recruitment>();
        query.addWhereEqualTo("jbType",str);
        query.order("-createdAt");//按时间降序
        query.findObjects(new FindListener<Recruitment>() {
            @Override
            public void done(List<Recruitment> list, BmobException e) {

                RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
                recycleView.setItemAnimator(new DefaultItemAnimator());
                recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));//垂直线性布局

                MyRecycleViewAdapter adapter=new MyRecycleViewAdapter(getActivity(),list);
                recyclerView.setAdapter(adapter);
                myData.addAll(list);
            }
        });
    }


    class MyRecycleViewAdapter extends RecyclerView.Adapter<MyRecycleViewAdapter.ViewHolder> {
        private LayoutInflater mLayoutInflater;
        List<Recruitment> mData=new ArrayList<Recruitment>();

        public MyRecycleViewAdapter(Context context,List<Recruitment> Data){
            this.mLayoutInflater = LayoutInflater.from(context);
            this.mData = Data;
        }
        public  class ViewHolder extends RecyclerView.ViewHolder {
            public NetworkImageView CompanyImg;
            public TextView txtPosition;
            public TextView txtSalary;
            public TextView txtExperience;
            public TextView txtTime;
            public TextView txtCity;
            public TextView txtDate;

            public ViewHolder(View convertView) {
                super(convertView);
                CompanyImg = (NetworkImageView) convertView.findViewById(R.id.CompanyImg);
                txtPosition = (TextView)convertView.findViewById(R.id.txtPosition);
                txtSalary = (TextView)convertView.findViewById(R.id.txtSalary);
                txtExperience = (TextView)convertView.findViewById(R.id.txtExperience);
                txtTime = (TextView)convertView.findViewById(R.id.txtTime);
                txtCity = (TextView)convertView.findViewById(R.id.txtCity);
                txtDate = (TextView)convertView.findViewById(R.id.txtDate);
            }
        }
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View v = mLayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recycle_item, viewGroup, false);
            ViewHolder holder = new ViewHolder(v);
            return  holder;
        }
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.CompanyImg.setImageUrl(mData.get(position).getJbCompanyImg().getFileUrl(),imageLoader);
            holder.txtPosition.setText((String)mData.get(position).getJbPosition());
            holder.txtSalary.setText((String)mData.get(position).getJbSalary());
            holder.txtExperience.setText("经验："+mData.get(position).getJbExperience());
//            holder.txtTime.setText((String)mData.get(position).());
            holder.txtCity.setText("城市："+mData.get(position).getJbCity());
//            holder.txtDate.setText((String)mData.get(position).getJbSalary());

            // 直接设置图片地址到NetworkImage,并将图片放入缓存
            holder.CompanyImg.setDefaultImageResId(R.mipmap.ic_launcher); // 当图片还没加载出来时显示的图片

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity(),DetailsActivity.class);
                    i.putExtra("objectid",myData.get(position).getObjectId());
                    startActivity(i);
                }
            });
        }
        public int getItemCount() {
            return mData.size();
        }

    }

}
