package com.mobilefieldtest;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.telephony.SubscriptionManager;
import android.telephony.SubscriptionInfo;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import java.util.List;

/**
 * Utility class for SIM card detection and management
 * Handles both single and dual SIM scenarios
 */
public class SimDetectionUtil {
    
    private Context context;
    private TelephonyManager telephonyManager;
    
    public SimDetectionUtil(Context context) {
        this.context = context;
        this.telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }
    
    /**
     * Check if at least one SIM card is present and ready
     * @return true if at least one SIM is available, false otherwise
     */
    public boolean isSimCardAvailable() {
        if (!hasPhoneStatePermission()) {
            return false;
        }
        
        try {
            // Check basic SIM state
            int simState = telephonyManager.getSimState();
            if (simState == TelephonyManager.SIM_STATE_READY) {
                return true;
            }
            
            // For dual SIM devices, check subscription manager
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                return checkSubscriptionManager();
            }
            
            return simState != TelephonyManager.SIM_STATE_ABSENT && 
                   simState != TelephonyManager.SIM_STATE_UNKNOWN;
                   
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Check SIM cards using SubscriptionManager for dual SIM support
     * @return true if any active SIM found
     */
    private boolean checkSubscriptionManager() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) 
                    == PackageManager.PERMISSION_GRANTED) {
                    
                    List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                    return subscriptionInfoList != null && !subscriptionInfoList.isEmpty();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    /**
     * Get SIM state description for debugging
     * @return String description of SIM state
     */
    public String getSimStateDescription() {
        if (!hasPhoneStatePermission()) {
            return "Permission not granted";
        }
        
        try {
            int simState = telephonyManager.getSimState();
            switch (simState) {
                case TelephonyManager.SIM_STATE_ABSENT:
                    return "No SIM card";
                case TelephonyManager.SIM_STATE_UNKNOWN:
                    return "Unknown SIM state";
                case TelephonyManager.SIM_STATE_READY:
                    return "SIM Ready";
                case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                    return "SIM PIN Required";
                case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                    return "SIM PUK Required";
                case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                    return "SIM Network Locked";
                case TelephonyManager.SIM_STATE_NOT_READY:
                    return "SIM Not Ready";
                default:
                    return "Unknown state: " + simState;
            }
        } catch (Exception e) {
            return "Error checking SIM: " + e.getMessage();
        }
    }
    
    /**
     * Get number of active SIM cards
     * @return count of active SIM cards
     */
    public int getActiveSimCount() {
        if (!hasPhoneStatePermission()) {
            return 0;
        }
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            try {
                SubscriptionManager subscriptionManager = SubscriptionManager.from(context);
                List<SubscriptionInfo> subscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                return subscriptionInfoList != null ? subscriptionInfoList.size() : 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // Fallback for older devices
        return isSimCardAvailable() ? 1 : 0;
    }
    
    /**
     * Check if READ_PHONE_STATE permission is granted
     * @return true if permission granted
     */
    public boolean hasPhoneStatePermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) 
               == PackageManager.PERMISSION_GRANTED;
    }
    
    /**
     * Get carrier name if available
     * @return carrier name or "Unknown"
     */
    public String getCarrierName() {
        if (!hasPhoneStatePermission()) {
            return "Permission required";
        }
        
        try {
            String carrierName = telephonyManager.getNetworkOperatorName();
            return (carrierName != null && !carrierName.isEmpty()) ? carrierName : "Unknown";
        } catch (Exception e) {
            return "Unknown";
        }
    }
}