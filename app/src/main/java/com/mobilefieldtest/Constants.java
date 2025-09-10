package com.mobilefieldtest;

/**
 * Constants class for Mobile Field Test application
 * Contains all constant values used throughout the app
 */
public class Constants {
    
    // Build Types
    public static final String BUILD_TYPE_USER = "User";
    public static final String BUILD_TYPE_DEBUG = "Debug";
    public static final String BUILD_TYPE_DEMO = "Demo";
    
    // Operators
    public static final String OPERATOR_ROBI = "Robi";
    public static final String OPERATOR_AIRTEL = "Airtel";
    public static final String OPERATOR_GRAMEENPHONE = "GrameenPhone";
    public static final String OPERATOR_BANGLALINK = "Banglalink";
    public static final String OPERATOR_TELETALK = "Teletalk";
    
    // Request Codes
    public static final int PERMISSION_REQUEST_READ_PHONE_STATE = 1001;
    public static final int SPLASH_DISPLAY_LENGTH = 3000; // 3 seconds
    
    // Validation Patterns
    public static final String EMPLOYEE_ID_PATTERN = "^[A-Za-z0-9]{3,20}$";
    public static final String MODEL_PATTERN = "^[A-Za-z0-9\\s\\-_]{2,30}$";
    public static final String BUILD_VERSION_PATTERN = "^[0-9.]+$";
    
    // SharedPreferences Keys
    public static final String PREF_NAME = "MobileFieldTestPrefs";
    public static final String KEY_EMPLOYEE_ID = "employee_id";
    public static final String KEY_MODEL = "model";
    public static final String KEY_BUILD_VERSION = "build_version";
    public static final String KEY_BUILD_TYPE = "build_type";
    public static final String KEY_TEST_AREA = "test_area";
    public static final String KEY_SELECTED_OPERATORS = "selected_operators";
    
    // Error Messages
    public static final String ERROR_NO_SIM = "No SIM card detected";
    public static final String ERROR_PERMISSION_DENIED = "Permission required to check SIM status";
    public static final String ERROR_INVALID_INPUT = "Please check your input";
    
    // Success Messages
    public static final String SUCCESS_SIM_DETECTED = "SIM card detected successfully";
    public static final String SUCCESS_FORM_VALIDATED = "All fields validated successfully";
    
    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Constants class should not be instantiated");
    }
}