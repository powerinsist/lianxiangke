package com.shanfu.tianxia.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.view.CropImageView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.shanfu.tianxia.R;
import com.shanfu.tianxia.appconfig.Constants;
import com.shanfu.tianxia.base.BaseFragmentActivity;
import com.shanfu.tianxia.base.UploadGoodsBean;
import com.shanfu.tianxia.bean.RsultBean;
import com.shanfu.tianxia.bean.UploadImageBean;
import com.shanfu.tianxia.listener.DialogCallback;
import com.shanfu.tianxia.listener.PicassoImageLoaderByLzy;
import com.shanfu.tianxia.listener.StringDialogCallback;
import com.shanfu.tianxia.network.NetworkManager;
import com.shanfu.tianxia.utils.Config;
import com.shanfu.tianxia.utils.DateUtils;
import com.shanfu.tianxia.utils.DbTOPxUtil;
import com.shanfu.tianxia.utils.GlideImageLoader;
import com.shanfu.tianxia.utils.ListResultToString;
import com.shanfu.tianxia.utils.LogType;
import com.shanfu.tianxia.utils.LogUtil;
import com.shanfu.tianxia.utils.MD5Utils;
import com.shanfu.tianxia.utils.SPUtils;
import com.shanfu.tianxia.utils.TUtils;
import com.shanfu.tianxia.utils.Urls;
import com.shanfu.tianxia.view.MyImagePickerGridView;
import com.shanfu.tianxia.view.StarBar;
import com.zzti.fengyongge.imagepicker.PhotoPreviewActivity;
import com.zzti.fengyongge.imagepicker.PhotoSelectorActivity;
import com.zzti.fengyongge.imagepicker.model.PhotoModel;
import com.zzti.fengyongge.imagepicker.util.CommonUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 评价商家
 */
public class EvaluationMerchantActivity extends BaseFragmentActivity implements View.OnClickListener
        {

    @Bind(R.id.business_img)
    NetworkImageView business_img;
    @Bind(R.id.business_name)
    TextView business_name;

    @Bind(R.id.business_adress)
    TextView business_adress;
    @Bind(R.id.evaluate_starBar)
    StarBar evaluate_starBar;
    @Bind(R.id.price_starBar)
    StarBar price_starBar;
    @Bind(R.id.environmental_starBar)
    StarBar environmental_starBar;
    @Bind(R.id.service_starBar)
    StarBar service_starBar;
    @Bind(R.id.price_money)
    EditText price_money;
            @Bind(R.id.comment)
            EditText comment;
            private ImageView add_IB;
            GridImgAdapter gridImgsAdapter;

    @Bind(R.id.gridView)
    GridView gridView;
            @Bind(R.id.my_goods_GV)
            MyImagePickerGridView my_goods_GV;
            @Bind(R.id.evaluation_merchant_back)
            RelativeLayout evaluation_merchant_back;
            @Bind(R.id.release)
            TextView release;


    public static final int REQUEST_CODE_SELECT = 100;
    private final int REQ_IMAGE = 1433;
    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_TACK_PHOTO = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

   private List<String> imgUrls = new ArrayList<String> ();
   private int screen_widthOffset;

    private Uri photoUri;
            private ImagePicker imagePicker;
            private ArrayList<ImageItem> images ;
            private ArrayList<ImageItem> imageLists = new ArrayList<ImageItem>() ;
            private MyAdapter adapter;

            private String shopid;
    private String evaluate_value,price_value,environmental_value,service_value;
            private String uid,img,address,shopname;
            private LayoutInflater inflater;
            private ArrayList<UploadGoodsBean> img_uri = new ArrayList<UploadGoodsBean>();
            private List<PhotoModel> single_photos = new ArrayList<PhotoModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation_merchant);
        ButterKnife.bind(this);
        initImagePicker();
        shopid =  getIntent().getStringExtra("shopid");
        uid = SPUtils.getInstance().getString("uid", "");
        img = getIntent().getStringExtra("img");
        address = getIntent().getStringExtra("address");
        shopname = getIntent().getStringExtra("shopname");
        init();
        initView();
        //requestData();
    }


            private void init(){
                DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                        .cacheInMemory(true).cacheOnDisc(true).build();
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                        getApplicationContext()).defaultDisplayImageOptions(
                        defaultOptions).build();
                ImageLoader.getInstance().init(config);
                Config.ScreenMap = Config.getScreenSize(this, this);
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                screen_widthOffset = (display.getWidth() - (3* DbTOPxUtil.dip2px(this, 2)))/4;
                inflater = LayoutInflater.from(this);
                gridImgsAdapter = new GridImgAdapter();
                my_goods_GV.setAdapter(gridImgsAdapter);
                img_uri.add(null);
                gridImgsAdapter.notifyDataSetChanged();
            }
    private void initView(){
        imagePicker = ImagePicker.getInstance();
        imagePicker.setSelectLimit(3);
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);

        if(adapter == null){
            adapter = new MyAdapter(imageLists);
            gridView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

        NetworkManager.getInstance().setImageUrl(business_img, img);
        business_name.setText(shopname);
        business_adress.setText(address);

        evaluate_starBar.setIntegerMark(true);
        price_starBar.setIntegerMark(true);
        environmental_starBar.setIntegerMark(true);
        service_starBar.setIntegerMark(true);
        release.setOnClickListener(this);
        evaluate_starBar.setOnClickListener(this);
        price_starBar.setOnClickListener(this);
        environmental_starBar.setOnClickListener(this);
        service_starBar.setOnClickListener(this);
        evaluation_merchant_back.setOnClickListener(this);




        price_money.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        price_money.setText(s);
                        price_money.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    price_money.setText(s);
                    price_money.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        price_money.setText(s.subSequence(0, 1));
                        price_money.setSelection(1);
                        return;
                    }
                }

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

            }
        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.evaluation_merchant_back:
                finish();
                break;
            case R.id.evaluate_starBar:
                //evaluate_value = evaluate_starBar.getStarMark()+"";
                break;
            case R.id.price_starBar:
                break;
            case R.id.environmental_starBar:
                break;
            case R.id.service_starBar:
                break;
            //发布
            case R.id.release:
                evaluate_value =  evaluate_starBar.getStarMark()+"";
                price_value = price_starBar.getStarMark()+"";
                environmental_value = environmental_starBar.getStarMark()+"";
                service_value = service_starBar.getStarMark()+"";


                if("0.0".equals(evaluate_value)){
                    TUtils.showShort(EvaluationMerchantActivity.this,"请对商家进行评价");
                    return;
                }
                if("0.0".equals(price_value)){
                    TUtils.showShort(EvaluationMerchantActivity.this,"请对商家价格进行评价");
                    return;
                }
                if("0.0".equals(environmental_value)){
                    TUtils.showShort(EvaluationMerchantActivity.this,"请对商家环境进行评价");
                    return;
                }
                if("0.0".equals(service_value)){
                    TUtils.showShort(EvaluationMerchantActivity.this,"请对商家服务进行评价");
                    return;
                }

               String money =  price_money.getText().toString().trim();
                if(TextUtils.isEmpty(money)){
                    TUtils.showShort(EvaluationMerchantActivity.this,"请输入消费金额");
                    return;
                }
                String input_comment = comment.getText().toString().trim();
                if("0".equals(money)||"0.0".equals(money)||"0.00".equals(money)){
                    TUtils.showShort(EvaluationMerchantActivity.this,"消费金额必须大于0");
                    return;
                }
                //requestData();
                //requestData(uid,"嘿嘿",input_comment,evaluate_value,price_value,environmental_value,service_value,"85","");
               // requestData(uid,"高庆","很好吃",evaluate_value,price_value,environmental_value,service_value,"35","");

                //upload();
               // upload();
                if(img_uri.size()-1>0){
                    upload();
                }else{
                    requestData(uid,input_comment,evaluate_value,price_value,environmental_value,service_value,"0","");
                }


                break;
        }
    }


           /* @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                    if (data != null && requestCode == REQUEST_CODE_SELECT) {
                        imageLists.clear();
                        images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                        imageLists.addAll(images);
                        if(adapter == null){
                            adapter = new MyAdapter(imageLists);
                            gridView.setAdapter(adapter);
                        }else{
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "没有数据", Toast.LENGTH_SHORT).show();
                    }
                }
            }*/
           @Override
           public void onActivityResult(int requestCode, int resultCode, Intent data) {
               switch (requestCode) {
                   case 0:
                       if (data != null) {
                           List<String> paths = (List<String>) data.getExtras().getSerializable("photos");
                           if (img_uri.size() > 0) {
                               img_uri.remove(img_uri.size() - 1);
                           }

                           for (int i = 0; i < paths.size(); i++) {
                               img_uri.add(new UploadGoodsBean(paths.get(i), false));
                               //上传参数
                           }
                           for (int i = 0; i < paths.size(); i++) {
                               PhotoModel photoModel = new PhotoModel();
                               photoModel.setOriginalPath(paths.get(i));
                               photoModel.setChecked(true);
                               single_photos.add(photoModel);
                           }
                           if (img_uri.size() < 9) {
                               img_uri.add(null);
                           }
                           gridImgsAdapter.notifyDataSetChanged();
                       }
                       break;
                   default:
                       break;
               }
               super.onActivityResult(requestCode, resultCode, data);
           }



            private class MyAdapter extends BaseAdapter {

                private List<ImageItem> items;
                private static final int ITEM_TYPE_CAMERA = 0;// the first Item may be
                // Camera
                private static final int ITEM_TYPE_NORMAL = 1;

                public MyAdapter(List<ImageItem> items) {
                    this.items = items;
                }

                public void setData(List<ImageItem> items) {
                    this.items = items;
                    notifyDataSetChanged();
                }@Override
                 public int getViewTypeCount() {
                    return 2;
                }

                @Override
                public int getItemViewType(int position) {

                    if (items.size() < 3) {
                        return position == items.size() ? ITEM_TYPE_CAMERA
                                : ITEM_TYPE_NORMAL;
                    } else {
                        return ITEM_TYPE_NORMAL;
                    }

                }

                @Override
                public int getCount() {

                    return items.size() < 3 ? items.size() + 1 : items.size();
                }

                @Override
                public ImageItem getItem(int position) {
                    return items.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    int itemViewType = getItemViewType(position);
                    if(itemViewType == ITEM_TYPE_CAMERA){
                        convertView = LayoutInflater.from(getApplicationContext()).inflate(
                                R.layout.layout_grid_item_camera, parent, false);
                        convertView.setTag(null);
                        convertView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                               /* Intent intent = new Intent(EvaluationMerchantActivity.this, ImageGridActivity.class);
                                startActivityForResult(intent, REQUEST_CODE_SELECT);*/
                                Intent intent = new Intent(EvaluationMerchantActivity.this, PhotoSelectorActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.putExtra("limit", 3 );//number是选择图片的数量
                                startActivityForResult(intent, 0);
                            }
                        });
                    }else{
                        int size = gridView.getWidth() / 3;
                        ViewHolder holder;
                        if (convertView == null) {
                            convertView = View.inflate(getApplicationContext(), R.layout.item_upload_manager, null);
                            holder = new ViewHolder(convertView);
                            convertView.setTag(holder);
                        } else {
                            holder = (ViewHolder) convertView.getTag();
                        }
                        imagePicker.getImageLoader().displayImage(EvaluationMerchantActivity.this, getItem(position).path, holder.imageView, size, size);
                    }

                    return convertView;
                }
            }
            private class ViewHolder {

                private ImageView imageView;
                public ViewHolder(View convertView) {
                    imageView = (ImageView) convertView.findViewById(R.id.imageView);
                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gridView.getWidth() / 3);
                    imageView.setLayoutParams(params);

                }




            }


           /* public void upload(View view) {
               *//* if (images != null) {
                    for (int i = 0; i < images.size(); i++) {
                        MyUploadListener listener = new MyUploadListener();
                        listener.setUserTag(gridView.getChildAt(i));
                        PostRequest postRequest = OkGo.post(Urls.URL_FORM_UPLOAD)//
                                .headers("headerKey1", "headerValue1")//
                                .headers("headerKey2", "headerValue2")//
                                .params("paramKey1", "paramValue1")//
                                .params("paramKey2", "paramValue2")//
                                .params("fileKey" + i, new File(images.get(i).path));
                        uploadManager.addTask(images.get(i).path, postRequest, listener);
                    }
                }*//*
            }*/


            /**
             *
             * @param uid
             *
             * @param comment 评论内容
             * @param score 评价
             * @param jg 价格评分
             * @param hj 环境评分
             * @param fw 服务评分
             * @param price 平均价格
             * @param pics 评论图片url
             */
            private void requestData(String uid,String comment,String score,String jg,
                                     String hj,String  fw,String price,String pics){

                try {
                    String nickname = SPUtils.getInstance().getString("nickname","");
                    String time = DateUtils.getLinuxTime();
                    String token = MD5Utils.MD5(Constants.appKey + time);
                    HttpParams params = new HttpParams();
                    params.put("time", time);
                    params.put("token", token);
                    params.put("uid", uid);
                    params.put("nickname", nickname);
                    params.put("shop_id", shopid);
                    params.put("comment", comment);
                    params.put("score", score);//评价
                    params.put("jg", jg);//价格评分
                    params.put("hj", hj);//环境评分
                    params.put("fw", fw);//服务评分
                    params.put("price", price);//平均价格
                    params.put("pics", pics);//评论图片url
                    OkGo.post(Urls.addComment)
                            .tag(this)
                            .params(params)
                            .execute(new DialogCallback<RsultBean>(this) {
                                @Override
                                public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                                    decodeData(rsultBean);
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            private void requestData(String pic){

                try {
                    String time = DateUtils.getLinuxTime();
                    String token = MD5Utils.MD5(Constants.appKey + time);
                    HttpParams params = new HttpParams();
                    params.put("time", time);
                    params.put("token", token);
                    params.put("uid", "16");
                    params.put("nickname", "去够不");
                    params.put("shop_id", shopid);
                    params.put("comment", "这家不好");
                    params.put("score", "2");//评价
                    params.put("jg", "2");//价格评分
                    params.put("hj", "2");//环境评分
                    params.put("fw", "2");//服务评分
                    params.put("price", "2");//平均价格
                    params.put("pics", pic);//评论图片url
                    OkGo.post(Urls.addComment)
                            .tag(this)
                            .params(params)
                            .execute(new DialogCallback<RsultBean>(this) {
                                @Override
                                public void onSuccess(RsultBean rsultBean, Call call, Response response) {
                                    decodeData(rsultBean);
                                }
                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    TUtils.showShort(EvaluationMerchantActivity.this, "数据获取失败，请检查网络后重试");
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



            private void decodeData(RsultBean rsultBean){
                String err_code = rsultBean.getErr_code();
                String err_msg = rsultBean.getErr_msg();
                if("200".equals(err_code)){
                  finish();
                }else{
                    TUtils.showShort(EvaluationMerchantActivity.this,err_msg);
                }
            }



            private void upLoadImage(ArrayList<File> files){
                try {
                    String time = DateUtils.getLinuxTime();
                    String token = MD5Utils.MD5(Constants.appKey + time);
                    HttpParams params = new HttpParams();
                    params.put("time", time);
                    params.put("token", token);
                    //params.put("uploadPic",files);//评论图片url
                    OkGo.post(Urls.UploadPic)
                            .tag(this)
                            .params(params)
                            .addFileParams("uploadPic", files)
                            .execute(new StringDialogCallback(this) {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    LogUtil.log(LogType.ERROR, getClass(), s);
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            private void upLoadImage(File file){
                try {
                    String time = DateUtils.getLinuxTime();
                    String token = MD5Utils.MD5(Constants.appKey + time);
                    HttpParams params = new HttpParams();
                    params.put("time", time);
                    params.put("token", token);
                    //params.put("uploadPic",files);//评论图片url
                    OkGo.post(Urls.UploadPic)
                            .tag(this)
                            .params(params)
                           .params("uploadPic",file)
                            .execute(new DialogCallback<UploadImageBean>(this) {
                                @Override
                                public void onSuccess(UploadImageBean uploadImageBean, Call call, Response response) {
                                    decodeImageData(uploadImageBean);
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    TUtils.showShort(EvaluationMerchantActivity.this, "数据获取失败，请检查网络后重试");
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            private void upload(){

                File file;
                ArrayList<File> files = new ArrayList<File>();
                for (int i = 0; i < img_uri.size()-1; i++) {
                    file = new File(img_uri.get(i).getUrl());
                    upLoadImage(file);
                }

            }

            private int index =0;
            String url;
            private void decodeImageData(UploadImageBean uploadImageBean){
                String code = uploadImageBean.getErr_code();
                String msg = uploadImageBean.getErr_msg();
                if("200".equals(code)){
                    index ++;
                    imgUrls.add(uploadImageBean.getData().get(0));
                   if(index == imgUrls.size()){
                       String url = ListResultToString.ToString(imgUrls);
                       if(url.contains(" ")){
                           url = url.replaceAll(" ", "");
                       }
                       requestData(url);
                   }



                }else{
                    TUtils.showShort(EvaluationMerchantActivity.this,msg);
                }

            }

            private void initImagePicker() {
                ImagePicker imagePicker = ImagePicker.getInstance();
                imagePicker.setImageLoader(new PicassoImageLoaderByLzy());   //设置图片加载器
                imagePicker.setShowCamera(true);                      //显示拍照按钮
                imagePicker.setCrop(true);                           //允许裁剪（单选才有效）
                imagePicker.setSaveRectangle(true);                   //是否按矩形区域保存
                imagePicker.setSelectLimit(8);              //选中数量限制
                imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
                imagePicker.setFocusWidth(800);                       //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
                imagePicker.setFocusHeight(800);                      //裁剪框的高度。单位像素（圆形自动取宽高最小值）
                imagePicker.setOutPutX(1000);                         //保存文件的宽度。单位像素
                imagePicker.setOutPutY(1000);                         //保存文件的高度。单位像素
            }


            class GridImgAdapter extends BaseAdapter implements ListAdapter {
                @Override
                public int getCount() {
                    return img_uri.size();
                }

                @Override
                public Object getItem(int position) {
                    return null;
                }

                @Override
                public long getItemId(int position) {
                    return position;
                }

                @Override
                public View getView(final int position, View convertView, ViewGroup parent) {
                    convertView = inflater.inflate(R.layout.activity_addstory_img_item, null);
                    add_IB = (ImageView) convertView.findViewById(R.id.add_IB);
                    ImageView delete_IV = (ImageView) convertView.findViewById(R.id.delete_IV);
                    AbsListView.LayoutParams param = new AbsListView.LayoutParams(screen_widthOffset, screen_widthOffset);
                    convertView.setLayoutParams(param);
                    if (img_uri.get(position) == null) {
                        delete_IV.setVisibility(View.GONE);
                        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.iv_add_the_pic, add_IB);
                        add_IB.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View arg0) {
                                Intent intent = new Intent(EvaluationMerchantActivity.this, PhotoSelectorActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                intent.putExtra("limit", 3);
                                startActivityForResult(intent, 0);
                            }
                        });

                    } else {
                        ImageLoader.getInstance().displayImage("file://" + img_uri.get(position).getUrl(), add_IB);
                        delete_IV.setOnClickListener(new View.OnClickListener() {
                            private boolean is_addNull;
                            @Override
                            public void onClick(View arg0) {
                                is_addNull = true;
                                String img_url = img_uri.remove(position).getUrl();
                                for (int i = 0; i < img_uri.size(); i++) {
                                    if (img_uri.get(i) == null) {
                                        is_addNull = false;
                                        continue;
                                    }
                                }
                                if (is_addNull) {
                                    img_uri.add(null);
                                }
//						FileUtils.DeleteFolder(img_url);
                                gridImgsAdapter.notifyDataSetChanged();
                            }
                        });

                        add_IB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("photos", (Serializable) single_photos);
                                bundle.putInt("position", position);
                                bundle.putString("save", "save");


                                CommonUtils.launchActivity(EvaluationMerchantActivity.this, PhotoPreviewActivity.class, bundle);
                            }
                        });

                    }
                    return convertView;
                }

                class ViewHolder {
                    ImageView add_IB;
                    ImageView delete_IV;
                }
            }


}
