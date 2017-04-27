package com.shanfu.tianxia.ui;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.ninegrid.NineGridView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.shanfu.tianxia.MainActivity;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.bean.UploadUserImg;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.utils.AppUtils;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.CircleImageView;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

public class SettingActivity extends BaseFragmentActivity implements View.OnClickListener {

    private RelativeLayout setting_top;
    private RelativeLayout content_head_back;
    private TextView content_head_title;

    @Bind(R.id.setting_login_pwd_layout)
    RelativeLayout setting_login_pwd_layout;
    @Bind(R.id.setting_pay_pwd_layout)
    RelativeLayout setting_pay_pwd_layout;
    @Bind(R.id.real_name_authentication_layout)
    RelativeLayout real_name_authentication_layout;
    @Bind(R.id.safe_exit)
    RelativeLayout safe_exit;
    @Bind(R.id.qqLayout)
    RelativeLayout qqLayout;
    @Bind(R.id.head_image1)
    CircleImageView head_image1;
    @Bind(R.id.user_num)
    TextView user_num;
    @Bind(R.id.ni_cheng)
    TextView ni_cheng;
    @Bind(R.id.ni_cheng_layout)
    RelativeLayout ni_cheng_layout;



    //调用照相机返回图片临时文件
    private File tempFile;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    // 1: qq, 2: weixin
    private int type;

    private Intent intent;
    private String avater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        avater = SPUtils.getInstance().getString("avater","");
        initView();
        //创建拍照存储的临时文件
        createCameraTempFile(savedInstanceState);
    }
    private void initView() {
        setting_top = (RelativeLayout) findViewById(R.id.setting_top);
        content_head_back = (RelativeLayout) setting_top.findViewById(R.id.content_head_back);
        content_head_title = (TextView) setting_top.findViewById(R.id.content_head_title);
        content_head_title.setText("安全设置");
        content_head_back.setOnClickListener(this);
        if(!TextUtils.isEmpty(avater)){
            NineGridView.getImageLoader().onDisplayImage(SettingActivity.this, head_image1, avater);
        }


        user_num.setText(SPUtils.getInstance().getString("phoneNum", ""));
        setting_login_pwd_layout.setOnClickListener(this);
        setting_pay_pwd_layout.setOnClickListener(this);
        real_name_authentication_layout.setOnClickListener(this);
        safe_exit.setOnClickListener(this);
        qqLayout.setOnClickListener(this);
        ni_cheng_layout.setOnClickListener(this);



    }
    /**
     * 创建调用系统照相机待存储的临时文件
     *
     * @param savedInstanceState
     */
    private void createCameraTempFile(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("tempFile")) {
            tempFile = (File) savedInstanceState.getSerializable("tempFile");
        } else {
            tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"),
                    System.currentTimeMillis() + ".jpg");
        }
    }
    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("tempFile", tempFile);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.content_head_back:
                finish();
                break;
            //修改登录密码
            case R.id.setting_login_pwd_layout:
                intent = new Intent(this,SettingLoginPwdActivity.class);
                startActivity(intent);
                break;
            //修改交易密码
            case R.id.setting_pay_pwd_layout:
                if("1".equals(p_status)){
                    intent = new Intent(this,ChangePwdActivity.class);
                    startActivity(intent);
                }else{
                    intent = new Intent(this,SetUpPwdActivity.class);
                    startActivity(intent);
                }

                break;
            //实名认证
            case R.id.real_name_authentication_layout:

                if(!"1".equals(t_status)){
                    intent = new Intent(this,AuthenticationActivity.class);
                    startActivity(intent);
                }


                break;
            //安全退出
            case R.id.safe_exit:
                SPUtils.getInstance().putString("uid","");
                SPUtils.getInstance().putString("b_status","");
                SPUtils.getInstance().putString("p_status","");
                SPUtils.getInstance().putString("t_status","");
                SPUtils.getInstance().putString("bankcard","");
                //SPUtils.getInstance().putString("phoneNum","");
                SPUtils.getInstance().putBoolean("request",false);
//                Log.e("LOG","11111111");
                intent = new Intent(SettingActivity.this, MainActivity.class);
                intent.putExtra("comefrom","home");
                startActivity(intent);
                finish();
                break;
            //设置用户头像
            case R.id.qqLayout:
                type = 2;
                uploadHeadImage();
                break;

            //设置昵称
            case R.id.ni_cheng_layout:
                intent = new Intent(SettingActivity.this,NiChengActivity.class);
                startActivity(intent);
                break;
        }
    }

    private File file;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    if (type == 2) {
                        head_image1.setImageBitmap(bitMap);
                    } else {
                        //headImage2.setImageBitmap(bitMap);
                    }
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                    file = new File(cropImagePath);
                    uploadImage(file);

                }
                break;
        }
    }


    private void uploadImage(File file){
        try {
            String time = DateUtils.getLinuxTime();
            String token = MD5Utils.MD5(Constants.appKey + time);
            String version = AppUtils.getVerName(SettingActivity.this);
            String ptoken = SPUtils.getInstance().getString("ptoken", "");
            String uid = SPUtils.getInstance().getString("uid", "");
            HttpParams params = new HttpParams();
            params.put("time", time);
            params.put("token", token);
            params.put("version", version);
            params.put("ptoken", ptoken);
            params.put("uid", uid);

            //params.put("uploadPic",files);//评论图片url
            OkGo.post(Urls.setAvatar)
                    .tag(this)
                    .params(params)
                    .params("uploadPic",file)
                    .execute(new DialogCallback<UploadUserImg>(this) {
                        @Override
                        public void onSuccess(UploadUserImg uploadUserImg, Call call, Response response) {
                            decodeImageData(uploadUserImg);
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            TUtils.showShort(SettingActivity.this, "数据获取失败，请检查网络后重试");
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void decodeImageData(UploadUserImg uploadUserImg){
        String code = uploadUserImg.getErr_code();
        String msg = uploadUserImg.getErr_msg();
        if("200".equals(code)){
            String avater = uploadUserImg.getData().getUrl();
            SPUtils.getInstance().putString("avater", avater);
            SPUtils.getInstance().putString("ptoken", uploadUserImg.getData().getPtoken());
        }else if("103".equals(code)){
            Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else{
            TUtils.showShort(SettingActivity.this,msg);
        }

    }



    /**
     * 打开截图界面
     *
     * @param uri
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }

    /**
     * 根据Uri返回文件绝对路径
     * 兼容了file:///开头的 和 content://开头的情况
     *
     * @param context
     * @param uri
     * @return the file path or null
     */
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCarema();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统图库
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    /**
     * 跳转到照相机
     */
    private void gotoCarema() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 外部存储权限申请返回
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCarema();
            } else {
                // Permission Denied
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            } else {
                // Permission Denied
            }
        }
    }

    private String t_status,p_status,b_status;
    @Override
    protected void onResume() {
        super.onResume();
        String nickname = SPUtils.getInstance().getString("nickname","");
        t_status = SPUtils.getInstance().getString("t_status","");
        p_status = SPUtils.getInstance().getString("p_status","");
        b_status = SPUtils.getInstance().getString("b_status","");
        if(!TextUtils.isEmpty(nickname)){
            ni_cheng.setText(nickname);
        }


    }

}
