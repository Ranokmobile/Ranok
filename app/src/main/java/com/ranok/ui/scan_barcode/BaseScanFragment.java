package com.ranok.ui.scan_barcode;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.databinding.BaseScanFragmentBinding;
import com.ranok.mlkit.CameraSource;
import com.ranok.mlkit.CameraSourcePreview;
import com.ranok.mlkit.GraphicOverlay;
import com.ranok.mlkit.barcodescanning.BarcodeScanningProcessor;
import com.ranok.network.response.PackageBarcodeResponseData;
import com.ranok.ui.base.BaseFragment;
import com.ranok.ui.main.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ranok.mvvm.binding.ViewModelBindingConfig;


public class BaseScanFragment extends BaseFragment<BaseScanIView, BaseScanVM, BaseScanFragmentBinding>
        implements BaseScanIView, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String BARCODE_DETECTION = "Barcode Detection";
    private static final String TAG = "BaseScanFragment";
    private static final int PERMISSION_REQUESTS = 1;
    private CameraSource cameraSource = null;
    private CameraSourcePreview preview;
    private GraphicOverlay graphicOverlay;
    private String selectedModel = BARCODE_DETECTION;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setModelView(this);
        preview = getBinding().firePreview;// (CameraSourcePreview) findViewById(R.id.firePreview);
        if (preview == null) {
            Log.d(TAG, "Preview is null");
        }
        graphicOverlay = getBinding().fireFaceOverlay; // (GraphicOverlay) findViewById(R.id.fireFaceOverlay);
        if (graphicOverlay == null) {
            Log.d(TAG, "graphicOverlay is null");
        }

        if (allPermissionsGranted()) {
            createCameraSource(selectedModel);
        } else {
            getRuntimePermissions();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        startCameraSource();
    }

    @Override
    public void exit() {
        mActivity.onBackPressed();
    }

    @Override
    public void pauseScan() {
        preview.stop();
    }

    @Override
    public void resumeScan() {
        startCameraSource();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        preview.stop();
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private void createCameraSource(String model) {
        if (cameraSource == null) {
            cameraSource = new CameraSource(mActivity, graphicOverlay);
        }

        cameraSource.setMachineLearningFrameProcessor(new BarcodeScanningProcessor(getViewModel()));
    }

    /**
     * Starts or restarts the camera source, if it exists. If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {
        if (cameraSource != null) {
            try {
                if (preview == null) {
                    Log.d(TAG, "resume: Preview is null");
                }
                if (graphicOverlay == null) {
                    Log.d(TAG, "resume: graphOverlay is null");
                }
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }


    private String[] getRequiredPermissions() {
        try {
            PackageInfo info =
                    mActivity.getPackageManager()
                            .getPackageInfo(mActivity.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(mActivity, permission)) {
                return false;
            }
        }
        return true;
    }

    private void getRuntimePermissions() {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(mActivity, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            requestPermissions(allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(
        int requestCode, String[] permissions, int[] grantResults) {
        Log.i(TAG, "Permission granted!");
        if (allPermissionsGranted()) {
            createCameraSource(selectedModel);
            startCameraSource();
        } else {
            mActivity.onBackPressed();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission granted: " + permission);
            return true;
        }
        Log.i(TAG, "Permission NOT granted: " + permission);
        return false;
    }



    @Nullable
    @Override
    public Class<BaseScanVM> getViewModelClass() {
        return BaseScanVM.class;
    }

    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.base_scan_fragment, BR.viewModel, getContext());
    }

    @Override
    public void barcodeAccepted(PackageBarcodeResponseData data) {
        ((MainActivity)mActivity).addScanPackagesResult(data);
    }
}
