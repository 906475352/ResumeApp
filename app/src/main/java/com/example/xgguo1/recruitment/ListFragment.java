package com.example.xgguo1.recruitment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.xgguo1.recruitment.JavaBean.Recruitment;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    List<Recruitment> myData = new ArrayList<Recruitment>();
    //依赖VolleyBitmapLruCache.java
    private static ImageLoader imageLoader;
    ListView listView;
    View view;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_list, container, false);
        RequestQueue queue = Volley.newRequestQueue(getActivity());//创建请求队列
        imageLoader = new ImageLoader(queue,new VolleyBitmapLruCache(getActivity()));


        listView = (ListView) view.findViewById(R.id.datalist);//找到当前listview组件
        getData();

        return view;
    }


    //执行查询操作拿到数据
    private void getData() {

        BmobQuery<Recruitment> query = new BmobQuery<Recruitment>();
        query.order("-createdAt");//按时间降序
        query.findObjects(new FindListener<Recruitment>() {
            @Override
            public void done(List<Recruitment> list, BmobException e) {

                //把拿到的数据传递到adapter
                listView.setAdapter(new MyAdapter(getActivity(),list));
                myData.addAll(list);
                //点击单个项事件处理
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i=new Intent(getActivity(),DetailsActivity.class);
                        i.putExtra("objectid",myData.get(position).getObjectId());
                        startActivity(i);
                    }
                });

            }

        });



    }

    //定义缓存类
    public final class ViewHolder{
        public NetworkImageView CompanyImg;
        public TextView txtPosition;
        public TextView txtSalary;
        public TextView txtExperience;
        public TextView txtTime;
        public TextView txtCity;
        public TextView txtDate;
    }

    //实现适配器方法内部类
    public class MyAdapter extends BaseAdapter {


        private LayoutInflater mInflater;
        List<Recruitment> mData = new ArrayList<Recruitment>();

        //接收传递过来的数据
        public MyAdapter(Context context,List<Recruitment> Data){
            this.mInflater = LayoutInflater.from(context);
            this.mData=Data;
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
           // Toast.makeText(getActivity(),String.valueOf(mData.size()),Toast.LENGTH_SHORT).show();
            return mData.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

//            Toast.makeText(getActivity(),"test4",Toast.LENGTH_SHORT).show();
            ViewHolder holder = null;
            if (convertView == null) {

                holder=new ViewHolder();

                convertView = mInflater.inflate(R.layout.layout_list_item, null);
                holder.CompanyImg = (NetworkImageView) convertView.findViewById(R.id.CompanyImg);
                holder.txtPosition = (TextView)convertView.findViewById(R.id.txtPosition);
                holder.txtSalary = (TextView)convertView.findViewById(R.id.txtSalary);
                holder.txtExperience = (TextView)convertView.findViewById(R.id.txtExperience);
                holder.txtTime = (TextView)convertView.findViewById(R.id.txtTime);
                holder.txtCity = (TextView)convertView.findViewById(R.id.txtCity);
                holder.txtDate = (TextView)convertView.findViewById(R.id.txtDate);
                convertView.setTag(holder);

            }else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.CompanyImg.setImageUrl(mData.get(position).getJbCompanyImg().getFileUrl(),imageLoader);
            holder.txtPosition.setText((String)mData.get(position).getJbPosition());
            holder.txtSalary.setText((String)mData.get(position).getJbSalary());
            holder.txtExperience.setText("经验："+mData.get(position).getJbExperience());
//            holder.txtTime.setText((String)mData.get(position).());
            holder.txtCity.setText("城市："+mData.get(position).getJbCity());
//            holder.txtDate.setText((String)mData.get(position).getJbSalary());

            // 直接设置图片地址到NetworkImage,并将图片放入缓存
            holder.CompanyImg.setDefaultImageResId(R.mipmap.ic_launcher); // 当图片还没加载出来时显示的图片

            return convertView;
        }

    }


}
