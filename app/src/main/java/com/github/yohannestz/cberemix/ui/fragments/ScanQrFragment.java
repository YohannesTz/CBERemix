package com.github.yohannestz.cberemix.ui.fragments;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.camera.core.Camera;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.LifecycleCameraController;
import androidx.core.content.ContextCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.lifecycle.LifecycleOwner;

import com.github.yohannestz.cberemix.databinding.FragmentScanQrBinding;
import com.github.yohannestz.cberemix.util.QrcodeAnalyzer;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class ScanQrFragment extends Fragment {

    private static final int DEFAULT_IMAGE_ANALYSIS_WIDTH = 1080;
    private static final int DEFAULT_IMAGE_ANALYSIS_HEIGHT = 1920;
    private BarcodeScanner mScanner;
    private Executor mMainExecutor;
    private ExecutorService mCameraExecutor;
    private ImageAnalysis mImageAnalysis;

    private FragmentScanQrBinding binding;

    private final ActivityResultLauncher<String> getPermission = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean isGranted) {
            if (isGranted) {
                startCamera();
            } else {
                showPermissionExplanationDialog();
            }
        }
    });
    private boolean flashOn = false;

    private void showPermissionExplanationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Camera Permission Required")
                .setMessage("This feature requires camera access. Please grant the camera permission in order to proceed.")
                .setPositiveButton("OK", (dialog, which) -> getPermission.launch(Manifest.permission.CAMERA))
                .setNegativeButton("Cancel", null)
                .show();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPermission.launch(Manifest.permission.CAMERA);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        WindowInsetsControllerCompat windowInsetsController = new WindowInsetsControllerCompat(
                requireActivity().getWindow(),
                requireActivity().getWindow().getDecorView()
        );

        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());

        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        new WindowInsetsControllerCompat(requireActivity().getWindow(), requireActivity().getWindow().getDecorView())
                .show(WindowInsetsCompat.Type.systemBars());
        requireActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public void startCamera() {
        PreviewView previewView = binding.previewView;
        LifecycleCameraController cameraController = new LifecycleCameraController(requireContext());


        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(requireContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();

                ImageAnalysis.Builder builder = new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1080, 1920));

                mImageAnalysis = builder.build();
                mImageAnalysis.setAnalyzer(mCameraExecutor, new QrcodeAnalyzer(barcode -> {
                    Toast.makeText(requireContext(), barcode, Toast.LENGTH_SHORT).show();
                }));

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider());

                try {
                    cameraProvider.unbindAll();
                    Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, mImageAnalysis, preview);

                    if (camera.getCameraInfo().hasFlashUnit()) {
                        camera.getCameraControl().enableTorch(flashOn);
                    }
                } catch (Exception ex) {
                    Toast.makeText(requireContext(), "Something was wrong!", Toast.LENGTH_SHORT).show();
                    Log.e("ex: ", ex.toString());
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        binding = FragmentScanQrBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}