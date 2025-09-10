"use client";

import { useState, useEffect } from "react";
import { Card } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Smartphone, Wifi, CheckCircle, XCircle } from "lucide-react";

interface SplashScreenProps {
  onComplete: (simDetected: boolean) => void;
}

export function SplashScreen({ onComplete }: SplashScreenProps) {
  const [progress, setProgress] = useState(0);
  const [currentStatus, setCurrentStatus] = useState("Loading...");
  const [simStatus, setSimStatus] = useState<"checking" | "found" | "not-found">("checking");

  useEffect(() => {
    const checkSimProcess = async () => {
      // Initial loading
      setCurrentStatus("Initializing...");
      await simulateDelay(1000);
      setProgress(20);

      // Check permissions
      setCurrentStatus("Checking permissions...");
      await simulateDelay(800);
      setProgress(40);

      // Detect SIM
      setCurrentStatus("Detecting SIM cards...");
      await simulateDelay(1200);
      setProgress(70);

      // Simulate SIM detection (you can modify this logic)
      const hasSimCard = Math.random() > 0.3; // 70% chance of having SIM
      
      if (hasSimCard) {
        setSimStatus("found");
        setCurrentStatus("SIM card detected successfully!");
        setProgress(100);
        await simulateDelay(1000);
        onComplete(true);
      } else {
        setSimStatus("not-found");
        setCurrentStatus("Please insert at least one simcard");
        setProgress(100);
        // Keep showing error message
        setTimeout(() => {
          // Reset and try again
          setProgress(0);
          setSimStatus("checking");
          checkSimProcess();
        }, 3000);
      }
    };

    checkSimProcess();
  }, [onComplete]);

  const simulateDelay = (ms: number) => 
    new Promise(resolve => setTimeout(resolve, ms));

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-600 to-indigo-800 flex items-center justify-center p-4">
      <Card className="w-full max-w-md p-8 bg-white/95 backdrop-blur shadow-2xl">
        <div className="text-center space-y-6">
          {/* App Logo */}
          <div className="flex justify-center">
            <div className="bg-blue-600 rounded-full p-6">
              <Smartphone className="w-12 h-12 text-white" />
            </div>
          </div>

          {/* App Title */}
          <div>
            <h1 className="text-2xl font-bold text-gray-900 mb-2">
              Mobile Network Field Test
            </h1>
            <p className="text-sm text-gray-600">
              Professional Network Testing Tool
            </p>
          </div>

          {/* Progress Section */}
          <div className="space-y-4">
            <div className="flex items-center justify-center space-x-2">
              {simStatus === "checking" && (
                <>
                  <Wifi className="w-5 h-5 text-blue-600 animate-pulse" />
                  <span className="text-sm text-gray-700">Checking...</span>
                </>
              )}
              {simStatus === "found" && (
                <>
                  <CheckCircle className="w-5 h-5 text-green-600" />
                  <span className="text-sm text-green-700">SIM Detected</span>
                </>
              )}
              {simStatus === "not-found" && (
                <>
                  <XCircle className="w-5 h-5 text-red-600" />
                  <span className="text-sm text-red-700">No SIM Found</span>
                </>
              )}
            </div>

            <Progress value={progress} className="h-2" />
            
            <p className="text-sm text-gray-600 min-h-[20px]">
              {currentStatus}
            </p>
          </div>

          {/* Version Info */}
          <div className="pt-4 border-t border-gray-200">
            <p className="text-xs text-gray-500">Version 1.0.0</p>
            <p className="text-xs text-gray-400 mt-1">
              Powered by Mobile Network Solutions
            </p>
          </div>

          {/* SIM Not Found Message */}
          {simStatus === "not-found" && (
            <div className="bg-red-50 border border-red-200 rounded-lg p-3 mt-4">
              <p className="text-sm text-red-800 font-medium">
                ⚠️ Please insert at least one simcard
              </p>
              <p className="text-xs text-red-600 mt-1">
                Retrying in 3 seconds...
              </p>
            </div>
          )}
        </div>
      </Card>
    </div>
  );
}