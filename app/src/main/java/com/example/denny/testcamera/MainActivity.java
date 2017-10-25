package com.example.denny.testcamera;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    private CameraView mCameraView;
    private Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mCameraView = findViewById(R.id.camera_view);

        if (PermissionUtils.hasCameraPermission(this)) {
            init();
        } else {
            PermissionUtils.reqCameraPermission(this);
        }
    }

    private void init() {
        mCamera = openCamera(0);
        mCameraView.setCamera(mCamera);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] results) {
        if (requestCode == PermissionUtils.REQ_CODE_PERM_CAMERA) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                Toast.makeText(this, "请打开相机权限！", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    public static Camera openCamera(int cameraId) {
        try {
            return Camera.open(cameraId);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
