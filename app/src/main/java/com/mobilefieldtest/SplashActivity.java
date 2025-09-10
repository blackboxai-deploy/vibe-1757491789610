package com.mobilefieldtest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Splash Activity - Entry point of the application
 * Handles SIM detection and permission requests
 */
public class SplashActivity extends AppCompatActivity {
    
    private SimDetectionUtil simDetectionUtil;
    private TextView tvLoadingStatus;
    private Handler handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        initializeViews();
        initializeUtils();
        checkPermissionsAndProceed();
    }
    
    /**
     * Initialize UI views
     */
    private void initializeViews() {
        tvLoadingStatus = findViewById(R.id.tvLoadingStatus);
        handler = new Handler(Looper.getMainLooper());
    }
    
    /**
     * Initialize utility classes
     */
    private void initializeUtils() {
        simDetectionUtil = new SimDetectionUtil(this);
    }
    
    /**
     * Check required permissions and proceed with SIM detection
     */
    private void checkPermissionsAndProceed() {
        if (hasRequiredPermissions()) {
            startSimDetection();
        } else {
            requestPermissions();
        }
    }
    
    /**
     * Check if all required permissions are granted
     * @return true if all permissions granted
     */
    private boolean hasRequiredPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) 
               == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * Request required permissions
     */
    private void requestPermissions() {
        updateLoadingStatus("Requesting permissions...");
        
        ActivityCompat.requestPermissions(this,
            new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_PHONE_NUMBERS
            },
            Constants.PERMISSION_REQUEST_READ_PHONE_STATE);
    }
    
    /**
     * Handle permission request results
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == Constants.PERMISSION_REQUEST_READ_PHONE_STATE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startSimDetection();
            } else {
                handlePermissionDenied();
            }
        }
    }
    
    /**
     * Handle permission denied scenario
     */
    private void handlePermissionDenied() {
        updateLoadingStatus("Permission required to check SIM status");
        
        Toast.makeText(this, Constants.ERROR_PERMISSION_DENIED, Toast.LENGTH_LONG).show();
        
        // Delay and try again or exit
        handler.postDelayed(() -> {
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                // Show explanation and request again
                requestPermissions();
            } else {
                // Permission permanently denied, exit app
                finish();
            }
        }, 2000);
    }
    
    /**
     * Start SIM detection process
     */
    private void startSimDetection() {
        updateLoadingStatus(getString(R.string.checking_sim));
        
        // Simulate loading time for better UX
        handler.postDelayed(() -> {
            performSimDetection();
        }, 1500);
    }
    
    /**
     * Perform actual SIM detection
     */
    private void performSimDetection() {
        try {
            boolean simAvailable = simDetectionUtil.isSimCardAvailable();
            
            if (simAvailable) {
                handleSimDetected();
            } else {
                handleNoSimDetected();
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            handleSimDetectionError(e.getMessage());
        }
    }
    
    /**
     * Handle successful SIM detection
     */
    private void handleSimDetected() {
        updateLoadingStatus("SIM detected successfully!");
        
        // Show success message briefly
        handler.postDelayed(() -> {
            proceedToMainActivity();
        }, 1000);
    }
    
    /**
     * Handle no SIM detected scenario
     */
    private void handleNoSimDetected() {
        updateLoadingStatus("No SIM card detected");
        
        // Show toast message as required
        Toast.makeText(this, getString(R.string.no_sim_error), Toast.LENGTH_LONG).show();
        
        // Optionally retry after delay
        handler.postDelayed(() -> {
            startSimDetection();
        }, 3000);
    }
    
    /**
     * Handle SIM detection error
     */
    private void handleSimDetectionError(String error) {
        updateLoadingStatus("Error checking SIM: " + error);
        
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_LONG).show();
        
        // Retry after delay
        handler.postDelayed(() -> {
            startSimDetection();
        }, 2000);
    }
    
    /**
     * Proceed to main activity
     */
    private void proceedToMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Close splash activity
        
        // Add transition animation
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    
    /**
     * Update loading status text
     * @param status Status message to display
     */
    private void updateLoadingStatus(String status) {
        if (tvLoadingStatus != null) {
            tvLoadingStatus.setText(status);
        }
    }
    
    /**
     * Cleanup when activity is destroyed
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
    
    /**
     * Prevent back button on splash screen
     */
    @Override
    public void onBackPressed() {
        // Do nothing - prevent going back from splash
    }
}