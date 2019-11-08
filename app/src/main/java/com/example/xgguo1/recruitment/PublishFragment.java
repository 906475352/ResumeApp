package com.example.xgguo1.recruitment;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.xgguo1.recruitment.JavaBean.Recruitment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Locale;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublishFragment extends Fragment {

    static final int PICTURE_DIALOG_ID = 0;

    EditText pf_editPosition,pf_editType,pf_editExperience,pf_editEducate,
            pf_editSalary,pf_editCity,pf_editPositionDesc,pf_editPositionRequest,pf_editAddress;

    //图片
    ImageView pf_CompanyImg;
    public String picpath;
    byte[] b;
    String fileurl;
    //按钮
    Button pf_BtnPublish,pf_BtnClear;

    public PublishFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish, container, false);

//        初始化组件
        initView(view);

        //点击照片事件
        pf_CompanyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(PICTURE_DIALOG_ID);
            }
        });

        //点击发布事件处理
        pf_BtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String txtPosition = pf_editPosition.getText().toString();
                final String txtType = pf_editType.getText().toString();
                final String txtExperience = pf_editExperience.getText().toString();
                final String txtEducate = pf_editEducate.getText().toString();
                final String txtSalary = pf_editSalary.getText().toString();
                final String txtCity = pf_editCity.getText().toString();
                final String txtPositionDesc = pf_editPositionDesc.getText().toString();
                final String txtPositionRequest = pf_editPositionRequest.getText().toString();
                final String txtAddress = pf_editAddress.getText().toString();
                final BmobFile uploadfile = new BmobFile(new File(fileurl));//设置照片绝对路径
                uploadfile.upload(new UploadFileListener() {//开始上传
                    @Override
                    public void done(BmobException e) {//上传成功
                        Recruitment recruitment = new Recruitment();
                        recruitment.setJbPosition(txtPosition);
                        recruitment.setJbType(txtType);
                        recruitment.setJbExperience(txtExperience);
                        recruitment.setJbEducation(txtEducate);
                        recruitment.setJbSalary(txtSalary);
                        recruitment.setJbCity(txtCity);
                        recruitment.setJbPositionDesc(txtPositionDesc);
                        recruitment.setJbPositionRequest(txtPositionRequest);
                        recruitment.setJbAdress(txtAddress);
                        recruitment.setJbCompanyImg(uploadfile);//设置照片文件

                        //开始插入
                        recruitment.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e==null){
                                    Toast.makeText(getActivity(),"发布成功！",Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getActivity(),"发布失败！",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });

        return view;
    }

//    初始化组件
    private void initView(View view) {

        //输入框
        pf_editPosition = (EditText) view.findViewById(R.id.editPosition);

        pf_editType = (EditText) view.findViewById(R.id.editType);

        pf_editExperience = (EditText) view.findViewById(R.id.editExperience);

        pf_editEducate = (EditText) view.findViewById(R.id.editEducate);

        pf_editSalary = (EditText) view.findViewById(R.id.editSalary);

        pf_editCity = (EditText) view.findViewById(R.id.editCity);

        pf_editPositionDesc = (EditText) view.findViewById(R.id.editPositionDesc);

        pf_editPositionRequest = (EditText) view.findViewById(R.id.editPositionRequest);

        pf_editAddress = (EditText) view.findViewById(R.id.editAddress);

        //照片
        pf_CompanyImg = (ImageView) view.findViewById(R.id.CompanyImg);

        //按钮
        pf_BtnPublish = (Button) view.findViewById(R.id.BtnPublish);
        pf_BtnClear = (Button) view.findViewById(R.id.BtnClear);

    }


    // 对话框创建
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PICTURE_DIALOG_ID:
                new AlertDialog.Builder(getActivity())
                        .setTitle("请选择图片来源")
                        .setNegativeButton("相册",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(
                                                Intent.ACTION_PICK, null);
                                        intent.setDataAndType(
                                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                                "image/*");
                                        startActivityForResult(intent, 1);

                                    }
                                })
                        .setPositiveButton("拍照",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        dialog.dismiss();
                                        Intent intent = new Intent(
                                                MediaStore.ACTION_IMAGE_CAPTURE);
                                        intent.putExtra(
                                                MediaStore.EXTRA_OUTPUT,
                                                Uri.fromFile(new File(
                                                        Environment
                                                                .getExternalStorageDirectory(),
                                                        "temp.jpg")));
                                        startActivityForResult(intent, 2);
                                    }
                                }).show();
        }
        return null;
    }

//    照片处理事件开始
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case 1:
                if (data == null) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case 2:

                File temp = new File(Environment.getExternalStorageDirectory()
                        + "/temp.jpg");
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case 3:
                if (data != null) {
                    setPicToView(data);
                }
                break;

            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        FileOutputStream b = null;
        String name = new DateFormat().format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";
        File temp = new File(Environment.getExternalStorageDirectory() + "/"
                + name);
        //fileurl 拿到照片路径 并赋值给全局变量
        fileurl=temp.getAbsolutePath();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            try {
                b = new FileOutputStream(temp);
                photo.compress(Bitmap.CompressFormat.JPEG, 75, b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            pf_CompanyImg.setImageBitmap(photo);
            picpath = temp.getAbsolutePath();
        }
    }
    //    照片处理事件结束
}
