package com.ibm.rqm;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;


public class MyInfo extends Activity {

    private String[] items = new String[] { "选择本地图片", "拍照" };
    private SharedPreferences mPrefs;
    private ImageView mImageView;
    private Button mLogoutBtn;

    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        mPrefs = ((IBMApplication)getApplication()).getPrefs();
        mImageView = (ImageView)findViewById(R.id.info_header);
        mImageView.setOnClickListener(new OnImageViewClickListener());
        mLogoutBtn = (Button)findViewById(R.id.logout_Button);
        mLogoutBtn.setOnClickListener(new OnLogoutClickListener());
    }

    public class OnImageViewClickListener implements  View.OnClickListener{
        public void onClick(View v){
            showDialog();
        }
    }

    private void showDialog(){
        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(items,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case 1:
                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                intentFromCapture.putExtra(
                                        MediaStore.EXTRA_OUTPUT,
                                        Uri.fromFile(new File(Environment
                                                .getExternalStorageDirectory(),
                                                IMAGE_FILE_NAME)));

                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (resultCode != RESULT_CANCELED) {

            switch (requestCode) {
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                case CAMERA_REQUEST_CODE:
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory()
                                    + IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     *
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = (extras.getParcelable("data"));
//            Drawable drawable = new BitmapDrawable(photo);
            mImageView.setImageBitmap(photo);
//            mImageView.setImageDrawable(drawable);
        }
    }

    private class OnLogoutClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {

            mPrefs.edit().putBoolean("isLogined", false).commit();
            Toast.makeText(MyInfo.this, "Logout Success!", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

}
