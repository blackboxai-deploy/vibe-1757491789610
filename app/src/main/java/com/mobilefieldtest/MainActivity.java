package com.mobilefieldtest;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity - Contains the form for Mobile Field Test Prerequisites
 * Handles form validation, reset functionality, and data collection
 */
public class MainActivity extends AppCompatActivity {
    
    // Form input fields
    private TextInputEditText etEmployeeId;
    private TextInputEditText etModel;
    private TextInputEditText etBuildVersion;
    private TextInputEditText etTestArea;
    private Spinner spinnerBuildType;
    
    // Operator checkboxes
    private CheckBox cbRobi;
    private CheckBox cbAirtel;
    private CheckBox cbGrameenPhone;
    private CheckBox cbBanglalink;
    private CheckBox cbTeletalk;
    
    // Action buttons
    private Button btnNext;
    private Button btnReset;
    
    // Lists for validation
    private List<TextInputEditText> editTextList;
    private List<CheckBox> checkBoxList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        setupSpinner();
        setupClickListeners();
        initializeLists();
    }
    
    /**
     * Initialize all UI views
     */
    private void initializeViews() {
        // Input fields
        etEmployeeId = findViewById(R.id.etEmployeeId);
        etModel = findViewById(R.id.etModel);
        etBuildVersion = findViewById(R.id.etBuildVersion);
        etTestArea = findViewById(R.id.etTestArea);
        spinnerBuildType = findViewById(R.id.spinnerBuildType);
        
        // Checkboxes
        cbRobi = findViewById(R.id.cbRobi);
        cbAirtel = findViewById(R.id.cbAirtel);
        cbGrameenPhone = findViewById(R.id.cbGrameenPhone);
        cbBanglalink = findViewById(R.id.cbBanglalink);
        cbTeletalk = findViewById(R.id.cbTeletalk);
        
        // Buttons
        btnNext = findViewById(R.id.btnNext);
        btnReset = findViewById(R.id.btnReset);
    }
    
    /**
     * Setup the Build Type spinner with options
     */
    private void setupSpinner() {
        String[] buildTypes = {
            getString(R.string.build_type_user),
            getString(R.string.build_type_debug),
            getString(R.string.build_type_demo)
        };
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            this,
            android.R.layout.simple_spinner_item,
            buildTypes
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBuildType.setAdapter(adapter);
    }
    
    /**
     * Setup click listeners for buttons
     */
    private void setupClickListeners() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleNextButtonClick();
            }
        });
        
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleResetButtonClick();
            }
        });
    }
    
    /**
     * Initialize lists for batch operations
     */
    private void initializeLists() {
        editTextList = new ArrayList<>();
        editTextList.add(etEmployeeId);
        editTextList.add(etModel);
        editTextList.add(etBuildVersion);
        editTextList.add(etTestArea);
        
        checkBoxList = new ArrayList<>();
        checkBoxList.add(cbRobi);
        checkBoxList.add(cbAirtel);
        checkBoxList.add(cbGrameenPhone);
        checkBoxList.add(cbBanglalink);
        checkBoxList.add(cbTeletalk);
    }
    
    /**
     * Handle Next button click - validate and proceed
     */
    private void handleNextButtonClick() {
        if (validateAllInputs()) {
            // All validation passed
            collectFormData();
            proceedToNext();
        } else {
            // Validation failed - show error
            Toast.makeText(this, "Please fix the errors and try again", Toast.LENGTH_SHORT).show();
        }
    }
    
    /**
     * Validate all form inputs
     * @return true if all inputs are valid
     */
    private boolean validateAllInputs() {
        boolean isValid = true;
        
        // Validate text fields
        if (!ValidationUtil.validateEmployeeId(etEmployeeId)) isValid = false;
        if (!ValidationUtil.validateModel(etModel)) isValid = false;
        if (!ValidationUtil.validateBuildVersion(etBuildVersion)) isValid = false;
        if (!ValidationUtil.validateTestArea(etTestArea)) isValid = false;
        
        // Validate operator selection
        if (!ValidationUtil.validateOperatorSelection(checkBoxList)) {
            Toast.makeText(this, getString(R.string.operator_required), Toast.LENGTH_SHORT).show();
            isValid = false;
        }
        
        return isValid;
    }
    
    /**
     * Collect all form data for processing
     */
    private void collectFormData() {
        FormData formData = new FormData();
        
        // Collect text data
        formData.setEmployeeId(etEmployeeId.getText().toString().trim());
        formData.setModel(etModel.getText().toString().trim());
        formData.setBuildVersion(etBuildVersion.getText().toString().trim());
        formData.setBuildType(spinnerBuildType.getSelectedItem().toString());
        formData.setTestArea(etTestArea.getText().toString().trim());
        
        // Collect selected operators
        List<String> selectedOperators = ValidationUtil.getSelectedOperators(checkBoxList);
        formData.setSelectedOperators(selectedOperators);
        
        // Log the collected data (for debugging)
        logFormData(formData);
    }
    
    /**
     * Log form data for debugging purposes
     * @param formData The collected form data
     */
    private void logFormData(FormData formData) {
        System.out.println("=== Form Data Collected ===");
        System.out.println("Employee ID: " + formData.getEmployeeId());
        System.out.println("Model: " + formData.getModel());
        System.out.println("Build Version: " + formData.getBuildVersion());
        System.out.println("Build Type: " + formData.getBuildType());
        System.out.println("Test Area: " + formData.getTestArea());
        System.out.println("Selected Operators: " + formData.getSelectedOperators());
        System.out.println("========================");
    }
    
    /**
     * Proceed to next step (placeholder for now)
     */
    private void proceedToNext() {
        Toast.makeText(this, "Form validation successful! Ready to proceed.", Toast.LENGTH_LONG).show();
        
        // TODO: Implement next step logic
        // This could be:
        // - Navigate to test configuration screen
        // - Start network tests
        // - Save data and proceed to results
        
        // For now, just show success message
        showSuccessMessage();
    }
    
    /**
     * Show success message with form summary
     */
    private void showSuccessMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Form submitted successfully!\n\n");
        message.append("Employee: ").append(etEmployeeId.getText().toString()).append("\n");
        message.append("Model: ").append(etModel.getText().toString()).append("\n");
        message.append("Build: ").append(etBuildVersion.getText().toString()).append("\n");
        message.append("Type: ").append(spinnerBuildType.getSelectedItem().toString()).append("\n");
        message.append("Area: ").append(etTestArea.getText().toString()).append("\n");
        
        List<String> operators = ValidationUtil.getSelectedOperators(checkBoxList);
        message.append("Operators: ").append(operators.toString());
        
        Toast.makeText(this, message.toString(), Toast.LENGTH_LONG).show();
    }
    
    /**
     * Handle Reset button click - clear all fields
     */
    private void handleResetButtonClick() {
        ValidationUtil.clearAllFields(editTextList, checkBoxList);
        
        // Reset spinner to first item
        spinnerBuildType.setSelection(0);
        
        // Show confirmation message
        Toast.makeText(this, getString(R.string.fields_cleared), Toast.LENGTH_SHORT).show();
    }
    
    /**
     * Handle back button press
     */
    @Override
    public void onBackPressed() {
        // Show confirmation dialog before going back
        super.onBackPressed();
        finish();
    }
    
    /**
     * Inner class to hold form data
     */
    private static class FormData {
        private String employeeId;
        private String model;
        private String buildVersion;
        private String buildType;
        private String testArea;
        private List<String> selectedOperators;
        
        // Getters and setters
        public String getEmployeeId() { return employeeId; }
        public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
        
        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        
        public String getBuildVersion() { return buildVersion; }
        public void setBuildVersion(String buildVersion) { this.buildVersion = buildVersion; }
        
        public String getBuildType() { return buildType; }
        public void setBuildType(String buildType) { this.buildType = buildType; }
        
        public String getTestArea() { return testArea; }
        public void setTestArea(String testArea) { this.testArea = testArea; }
        
        public List<String> getSelectedOperators() { return selectedOperators; }
        public void setSelectedOperators(List<String> selectedOperators) { this.selectedOperators = selectedOperators; }
    }
}