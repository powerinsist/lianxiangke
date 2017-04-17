package com.shanfu.tianxia.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shanfu.tianxia.R;
import com.shanfu.tianxia.base.BaseFragment;
import com.shanfu.tianxia.ui.CaptureTwoActivity;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by qing on 2017/2/26.
 */
//
public class ScanningFragment extends BaseFragment  {

    private final static int REQ_CODE = 1028;
    private static final int REQUEST_PERMISSION_CAMERA_CODE = 1;

    @Override
    public View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view = inflater.inflate(R.layout.fragment_scanning, container, false);
        //initView(view);
      //  checkCamare();
        PermissionGen.needPermission(ScanningFragment.this, 100,
                new String[]{
                        Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
                }
        );
        return view;
    }

    private void initView(View view){

    }
    @PermissionSuccess(requestCode = 100)
    public void doSomething(){
        Intent intent = new Intent(getActivity(), CaptureTwoActivity.class);
        startActivityForResult(intent, REQ_CODE);
    }
    @PermissionFail(requestCode = 100)
    public void doFailSomething(){

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void init() {


    }
    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                     int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }







}
