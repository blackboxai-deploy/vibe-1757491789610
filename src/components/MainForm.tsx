"use client";

import { useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Checkbox } from "@/components/ui/checkbox";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Separator } from "@/components/ui/separator";
import { Badge } from "@/components/ui/badge";
import { toast } from "sonner";

interface FormData {
  employeeId: string;
  model: string;
  buildVersion: string;
  buildType: string;
  testArea: string;
  operators: string[];
}

const operators = [
  { id: "robi", name: "Robi" },
  { id: "airtel", name: "Airtel" },
  { id: "grameenphone", name: "GrameenPhone" },
  { id: "banglalink", name: "Banglalink" },
  { id: "teletalk", name: "Teletalk" },
];

const buildTypes = ["User", "Debug", "Demo"];

export function MainForm() {
  const [formData, setFormData] = useState<FormData>({
    employeeId: "",
    model: "",
    buildVersion: "",
    buildType: "",
    testArea: "",
    operators: [],
  });

  const [errors, setErrors] = useState<Partial<Record<keyof FormData, string>>>({});

  const validateForm = (): boolean => {
    const newErrors: Partial<Record<keyof FormData, string>> = {};

    // Employee ID validation
    if (!formData.employeeId.trim()) {
      newErrors.employeeId = "Employee ID is required";
    } else if (formData.employeeId.length < 3) {
      newErrors.employeeId = "Employee ID must be at least 3 characters";
    } else if (formData.employeeId.length > 20) {
      newErrors.employeeId = "Employee ID must be less than 20 characters";
    } else if (!/^[A-Za-z0-9]+$/.test(formData.employeeId)) {
      newErrors.employeeId = "Employee ID contains invalid characters";
    }

    // Model validation
    if (!formData.model.trim()) {
      newErrors.model = "Model is required";
    } else if (formData.model.length < 2) {
      newErrors.model = "Model name must be at least 2 characters";
    } else if (formData.model.length > 30) {
      newErrors.model = "Model name must be less than 30 characters";
    }

    // Build Version validation
    if (!formData.buildVersion.trim()) {
      newErrors.buildVersion = "Build Version is required";
    } else if (!/^[0-9.]+$/.test(formData.buildVersion)) {
      newErrors.buildVersion = "Build Version should contain only numbers and dots";
    }

    // Build Type validation
    if (!formData.buildType) {
      newErrors.buildType = "Build Type is required";
    }

    // Test Area validation
    if (!formData.testArea.trim()) {
      newErrors.testArea = "Test Area is required";
    } else if (formData.testArea.length < 2) {
      newErrors.testArea = "Test Area must be at least 2 characters";
    } else if (formData.testArea.length > 50) {
      newErrors.testArea = "Test Area must be less than 50 characters";
    }

    // Operators validation
    if (formData.operators.length === 0) {
      newErrors.operators = "Please select at least one operator";
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleInputChange = (field: keyof FormData, value: string) => {
    setFormData(prev => ({ ...prev, [field]: value }));
    // Clear error when user starts typing
    if (errors[field]) {
      setErrors(prev => ({ ...prev, [field]: undefined }));
    }
  };

  const handleOperatorChange = (operatorId: string, checked: boolean) => {
    setFormData(prev => ({
      ...prev,
      operators: checked
        ? [...prev.operators, operatorId]
        : prev.operators.filter(op => op !== operatorId)
    }));
    // Clear operator error when user selects
    if (errors.operators) {
      setErrors(prev => ({ ...prev, operators: undefined }));
    }
  };

  const handleSubmit = () => {
    if (validateForm()) {
      toast.success("Form validation successful! Ready to proceed.", {
        description: "All fields have been validated successfully.",
      });
      console.log("Form Data:", formData);
    } else {
      toast.error("Please fix the errors and try again", {
        description: "Some fields contain invalid data.",
      });
    }
  };

  const handleReset = () => {
    setFormData({
      employeeId: "",
      model: "",
      buildVersion: "",
      buildType: "",
      testArea: "",
      operators: [],
    });
    setErrors({});
    toast.info("All fields have been cleared");
  };

  return (
    <div className="container mx-auto p-4 max-w-2xl space-y-6">
      {/* Header */}
      <Card className="bg-gradient-to-r from-blue-600 to-indigo-600 text-white border-0">
        <CardHeader className="pb-4">
          <div className="flex items-center space-x-3">
            <div className="w-10 h-10 bg-white/20 rounded-lg flex items-center justify-center">
              📱
            </div>
            <div>
              <CardTitle className="text-xl font-bold">
                Mobile Field Test Prerequisites
              </CardTitle>
              <p className="text-blue-100 text-sm">
                Complete all fields to proceed with network testing
              </p>
            </div>
          </div>
        </CardHeader>
      </Card>

      {/* Form Fields */}
      <Card>
        <CardContent className="p-6 space-y-6">
          {/* Employee ID */}
          <div className="space-y-2">
            <Label htmlFor="employeeId" className="text-sm font-medium">
              Employee ID's *
            </Label>
            <Input
              id="employeeId"
              placeholder="Enter Employee ID"
              value={formData.employeeId}
              onChange={(e) => handleInputChange("employeeId", e.target.value)}
              maxLength={20}
              className={errors.employeeId ? "border-red-500" : ""}
            />
            {errors.employeeId && (
              <p className="text-sm text-red-600">{errors.employeeId}</p>
            )}
          </div>

          {/* Model */}
          <div className="space-y-2">
            <Label htmlFor="model" className="text-sm font-medium">
              Model *
            </Label>
            <Input
              id="model"
              placeholder="Enter Model Name"
              value={formData.model}
              onChange={(e) => handleInputChange("model", e.target.value)}
              maxLength={30}
              className={errors.model ? "border-red-500" : ""}
            />
            {errors.model && (
              <p className="text-sm text-red-600">{errors.model}</p>
            )}
          </div>

          {/* Build Version */}
          <div className="space-y-2">
            <Label htmlFor="buildVersion" className="text-sm font-medium">
              Build Version *
            </Label>
            <Input
              id="buildVersion"
              placeholder="Enter Build Version (e.g., 1.0.0)"
              value={formData.buildVersion}
              onChange={(e) => handleInputChange("buildVersion", e.target.value)}
              maxLength={10}
              className={errors.buildVersion ? "border-red-500" : ""}
            />
            {errors.buildVersion && (
              <p className="text-sm text-red-600">{errors.buildVersion}</p>
            )}
          </div>

          {/* Build Type */}
          <div className="space-y-2">
            <Label className="text-sm font-medium">Build Type *</Label>
            <Select
              value={formData.buildType}
              onValueChange={(value) => handleInputChange("buildType", value)}
            >
              <SelectTrigger className={errors.buildType ? "border-red-500" : ""}>
                <SelectValue placeholder="Select Build Type" />
              </SelectTrigger>
              <SelectContent>
                {buildTypes.map((type) => (
                  <SelectItem key={type} value={type}>
                    {type}
                  </SelectItem>
                ))}
              </SelectContent>
            </Select>
            {errors.buildType && (
              <p className="text-sm text-red-600">{errors.buildType}</p>
            )}
          </div>

          {/* Test Area */}
          <div className="space-y-2">
            <Label htmlFor="testArea" className="text-sm font-medium">
              Test Area *
            </Label>
            <Input
              id="testArea"
              placeholder="Enter Test Area Location"
              value={formData.testArea}
              onChange={(e) => handleInputChange("testArea", e.target.value)}
              maxLength={50}
              className={errors.testArea ? "border-red-500" : ""}
            />
            {errors.testArea && (
              <p className="text-sm text-red-600">{errors.testArea}</p>
            )}
          </div>
        </CardContent>
      </Card>

      {/* Operator Selection */}
      <Card>
        <CardHeader>
          <CardTitle className="text-lg">Operator Select *</CardTitle>
          <p className="text-sm text-gray-600">
            Choose the operators you want to test
          </p>
        </CardHeader>
        <CardContent className="space-y-4">
          <div className="grid grid-cols-1 sm:grid-cols-2 gap-3">
            {operators.map((operator) => (
              <div key={operator.id} className="flex items-center space-x-3 p-3 border rounded-lg hover:bg-gray-50">
                <Checkbox
                  id={operator.id}
                  checked={formData.operators.includes(operator.id)}
                  onCheckedChange={(checked) => 
                    handleOperatorChange(operator.id, checked as boolean)
                  }
                />
                <Label htmlFor={operator.id} className="flex-1 cursor-pointer">
                  {operator.name}
                </Label>
              </div>
            ))}
          </div>

          {/* Selected Operators Display */}
          {formData.operators.length > 0 && (
            <div className="pt-4">
              <Separator className="mb-3" />
              <p className="text-sm text-gray-600 mb-2">Selected operators:</p>
              <div className="flex flex-wrap gap-2">
                {formData.operators.map((operatorId) => {
                  const operator = operators.find(op => op.id === operatorId);
                  return operator ? (
                    <Badge key={operatorId} variant="secondary">
                      {operator.name}
                    </Badge>
                  ) : null;
                })}
              </div>
            </div>
          )}

          {errors.operators && (
            <p className="text-sm text-red-600">{errors.operators}</p>
          )}
        </CardContent>
      </Card>

      {/* Action Buttons */}
      <div className="flex flex-col sm:flex-row gap-3">
        <Button 
          variant="outline" 
          onClick={handleReset}
          className="flex-1"
        >
          Reset Fields
        </Button>
        <Button 
          onClick={handleSubmit}
          className="flex-1 bg-blue-600 hover:bg-blue-700"
        >
          Next
        </Button>
      </div>
    </div>
  );
}