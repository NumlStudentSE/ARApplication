package com.example.arapplication;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.ar.core.Anchor;
import com.google.ar.core.Camera;
import com.google.ar.core.Frame;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.ux.ArFragment;



public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 123;
    private ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_CODE);
        } else {
            setupAR();
        }

    }

    private void setupAR() {
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arSceneView);

        assert arFragment != null;
        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            handleFrameUpdate(arFragment.getArSceneView().getArFrame());
        });
    }
    private void handleFrameUpdate(Frame frame) {
        if (frame != null) {
            Camera camera = frame.getCamera();
            if (camera.getTrackingState() == TrackingState.TRACKING) {
                // Do any necessary cleanup or confirmation before exiting AR view
                super.onBackPressed();
            } else {
                // AR is not tracking, so prevent exiting directly
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupAR();
            } else {
                // Handle permission denied
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (arFragment != null && arFragment.getArSceneView() != null) {
            Frame frame = arFragment.getArSceneView().getArFrame();

            if (frame != null && frame.getCamera().getTrackingState() == TrackingState.TRACKING) {
                // Do any necessary cleanup or confirmation before exiting AR view
                super.onBackPressed();
            } else {
                // AR is not tracking, so prevent exiting directly
            }
        } else {
            super.onBackPressed();
        }
    }


    private void createModel(Anchor anchor) {
        // Your model creation code
    }
}
