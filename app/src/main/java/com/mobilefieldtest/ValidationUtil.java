package com.mobilefieldtest;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.CheckBox;
import java.util.regex.Pattern;
import java.util.List;
import java.util.ArrayList;

/**
 * Utility class for form validation
 * Contains methods to validate all form inputs
 */
public class ValidationUtil {
    
    /**
     * Validate Employee ID field
     * @param editText Employee ID input field
     * @return true if valid, false otherwise
     */
    public static boolean validateEmployeeId(EditText editText) {
        String input = editText.getText().toString().trim();
        
        if (TextUtils.isEmpty(input)) {
            editText.setError("Employee ID is required");
            editText.requestFocus();
            return false;
        }
        
        if (input.length() < 3) {
            editText.setError("Employee ID must be at least 3 characters");
            editText.requestFocus();
            return false;
        }
        
        if (input.length() > 20) {
            editText.setError("Employee ID must be less than 20 characters");
            editText.requestFocus();
            return false;
        }
        
        if (!Pattern.matches(Constants.EMPLOYEE_ID_PATTERN, input)) {
            editText.setError("Employee ID contains invalid characters");
            editText.requestFocus();
            return false;
        }
        
        editText.setError(null);
        return true;
    }
    
    /**
     * Validate Model field
     * @param editText Model input field
     * @return true if valid, false otherwise
     */
    public static boolean validateModel(EditText editText) {
        String input = editText.getText().toString().trim();
        
        if (TextUtils.isEmpty(input)) {
            editText.setError("Model is required");
            editText.requestFocus();
            return false;
        }
        
        if (input.length() < 2) {
            editText.setError("Model name must be at least 2 characters");
            editText.requestFocus();
            return false;
        }
        
        if (input.length() > 30) {
            editText.setError("Model name must be less than 30 characters");
            editText.requestFocus();
            return false;
        }
        
        if (!Pattern.matches(Constants.MODEL_PATTERN, input)) {
            editText.setError("Model name contains invalid characters");
            editText.requestFocus();
            return false;
        }
        
        editText.setError(null);
        return true;
    }
    
    /**
     * Validate Build Version field
     * @param editText Build Version input field
     * @return true if valid, false otherwise
     */
    public static boolean validateBuildVersion(EditText editText) {
        String input = editText.getText().toString().trim();
        
        if (TextUtils.isEmpty(input)) {
            editText.setError("Build Version is required");
            editText.requestFocus();
            return false;
        }
        
        if (!Pattern.matches(Constants.BUILD_VERSION_PATTERN, input)) {
            editText.setError("Build Version should contain only numbers and dots (e.g., 1.0.0)");
            editText.requestFocus();
            return false;
        }
        
        // Check for valid version format (at least one number)
        if (input.equals(".") || input.startsWith(".") || input.endsWith(".")) {
            editText.setError("Invalid version format");
            editText.requestFocus();
            return false;
        }
        
        editText.setError(null);
        return true;
    }
    
    /**
     * Validate Test Area field
     * @param editText Test Area input field
     * @return true if valid, false otherwise
     */
    public static boolean validateTestArea(EditText editText) {
        String input = editText.getText().toString().trim();
        
        if (TextUtils.isEmpty(input)) {
            editText.setError("Test Area is required");
            editText.requestFocus();
            return false;
        }
        
        if (input.length() < 2) {
            editText.setError("Test Area must be at least 2 characters");
            editText.requestFocus();
            return false;
        }
        
        if (input.length() > 50) {
            editText.setError("Test Area must be less than 50 characters");
            editText.requestFocus();
            return false;
        }
        
        editText.setError(null);
        return true;
    }
    
    /**
     * Validate operator selection checkboxes
     * @param checkBoxes List of operator checkboxes
     * @return true if at least one is selected, false otherwise
     */
    public static boolean validateOperatorSelection(List<CheckBox> checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Get list of selected operators
     * @param checkBoxes List of operator checkboxes
     * @return List of selected operator names
     */
    public static List<String> getSelectedOperators(List<CheckBox> checkBoxes) {
        List<String> selectedOperators = new ArrayList<>();
        
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isChecked()) {
                selectedOperators.add(checkBox.getText().toString());
            }
        }
        
        return selectedOperators;
    }
    
    /**
     * Clear all form fields
     * @param editTexts List of EditText fields to clear
     * @param checkBoxes List of CheckBox fields to clear
     */
    public static void clearAllFields(List<EditText> editTexts, List<CheckBox> checkBoxes) {
        // Clear EditText fields
        for (EditText editText : editTexts) {
            editText.setText("");
            editText.setError(null);
        }
        
        // Clear CheckBox fields
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setChecked(false);
        }
    }
    
    /**
     * Validate all form fields at once
     * @param employeeId Employee ID field
     * @param model Model field
     * @param buildVersion Build Version field
     * @param testArea Test Area field
     * @param operatorCheckBoxes Operator checkboxes
     * @return true if all fields are valid, false otherwise
     */
    public static boolean validateAllFields(EditText employeeId, EditText model, 
                                          EditText buildVersion, EditText testArea,
                                          List<CheckBox> operatorCheckBoxes) {
        boolean isValid = true;
        
        // Validate all fields (don't short-circuit to show all errors)
        if (!validateEmployeeId(employeeId)) isValid = false;
        if (!validateModel(model)) isValid = false;
        if (!validateBuildVersion(buildVersion)) isValid = false;
        if (!validateTestArea(testArea)) isValid = false;
        if (!validateOperatorSelection(operatorCheckBoxes)) isValid = false;
        
        return isValid;
    }
}