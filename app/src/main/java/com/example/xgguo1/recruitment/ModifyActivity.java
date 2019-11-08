package com.example.xgguo1.recruitment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.example.xgguo1.recruitment.JavaBean.Recruitment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class ModifyActivity extends AppCompatActivity {

    private static ImageLoader imageLoader;// 图片缓存器
    String deletefileurl;
    static final int PICTURE_DIALOG_ID = 0;
    String objectid;
    EditText pf_editPosition,pf_editType,pf_editExperience,pf_editEducate,
            pf_editSalary,pf_editCity,pf_editPositionDesc,pf_editPositionRequest,pf_editAddress;

    //图片
    NetworkImageView pf_CompanyImg;
    public String picpath;
    byte[] b;
    String fileurl="";
    //按钮
    Button pf_BtnPublish,pf_BtnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        //        初始化组件
        initView();
        showdata();

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

                if (fileurl != "")
                {
                    final BmobFile uploadfile = new BmobFile(new File(fileurl));
                    uploadfile.upload(new UploadFileListener() {
                        @Override
                                public void done(BmobException e) {
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
                                    recruitment.setObjectId(objectid);
                                    recruitment.setJbCompanyImg(uploadfile);
                                    recruitment.update(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            Toast.makeText(getBaseContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                                            BmobFile file = new BmobFile();
                                            file.setUrl(deletefileurl);
                                            file.delete(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    Intent intentBack = new Intent(getBaseContext(),MainActivity.class);
                                                    startActivity(intentBack);
                                                    finish();
                                                }
                                            });
                                        }
                                    });
                                }
                            });

                        }else {
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
                            recruitment.setObjectId(objectid);
                            recruitment.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    Toast.makeText(getBaseContext(), "修改成功！", Toast.LENGTH_SHORT).show();
                                    Intent intentBack = new Intent(getBaseContext(),MainActivity.class);
                                    startActivity(intentBack);
                                    finish();
                                }
                            });

                        }
            }
        });
    }

    private void showdata() {
        Intent intent = getIntent();
        objectid = intent.getStringExtra("objectid");
//        Toast.makeText(getBaseContext(),objectid,Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this); // 请求队列
        imageLoader = new ImageLoader(queue, new VolleyBitmapLruCache(this));

        BmobQuery<Recruitment> query = new BmobQuery<Recruitment>();
        query.getObject(objectid, new QueryListener<Recruitment>() {
            @Override
            public void done(Recruitment recruitment, BmobException e) {
                pf_editPosition.setText(recruitment.getJbPosition());
                pf_editType.setText(recruitment.getJbType());
                pf_editExperience.setText(recruitment.getJbExperience());
                pf_editEducate.setText(recruitment.getJbEducation());
                pf_editSalary.setText(recruitment.getJbSalary());
                pf_editCity.setText(recruitment.getJbCity());
                pf_editPositionDesc.setText(recruitment.getJbPositionDesc());
                pf_editPositionRequest.setText(recruitment.getJbPositionRequest());
                pf_editAddress.setText(recruitment.getJbAdress());
                pf_CompanyImg.setImageUrl(recruitment.getJbCompanyImg().getFileUrl(),imageLoader);
                deletefileurl=recruitment.getJbCompanyImg().getFileUrl();
//                Toast.makeText(getBaseContext(),deletefileurl,Toast.LENGTH_SHORT).show();
            }
        });
    }


    //    初始化组件
    private void initView() {

        //输入框
        pf_editPosition = (EditText) findViewById(R.id.editPosition);

        pf_editType = (EditText) findViewById(R.id.editType);

        pf_editExperience = (EditText) findViewById(R.id.editExperience);

        pf_editEducate = (EditText) findViewById(R.id.editEducate);

        pf_editSalary = (EditText) findViewById(R.id.editSalary);

        pf_editCity = (EditText) findViewById(R.id.editCity);

        pf_editPositionDesc = (EditText) findViewById(R.id.editPositionDesc);

        pf_editPositionRequest = (EditText) findViewById(R.id.editPositionRequest);

        pf_editAddress = (EditText) findViewById(R.id.editAddress);

        //照片
        pf_CompanyImg = (NetworkImageView) findViewById(R.id.CompanyImg);

        //按钮
        pf_BtnPublish = (Button) findViewById(R.id.BtnPublish);
        pf_BtnClear = (Button) findViewById(R.id.BtnClear);

    }


    // 对话框创建
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case PICTURE_DIALOG_ID:
                new AlertDialog.Builder(this)
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
