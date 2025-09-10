"use client";

import { useState } from "react";
import { SplashScreen } from "@/components/SplashScreen";
import { MainForm } from "@/components/MainForm";

export default function Home() {
  const [currentScreen, setCurrentScreen] = useState<"splash" | "main">("splash");
  const [simDetected, setSimDetected] = useState<boolean>(false);

  // Simulate SIM detection process
  const handleSplashComplete = (simStatus: boolean) => {
    setSimDetected(simStatus);
    if (simStatus) {
      setCurrentScreen("main");
    }
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      {currentScreen === "splash" && (
        <SplashScreen onComplete={handleSplashComplete} />
      )}
      {currentScreen === "main" && simDetected && (
        <MainForm />
      )}
    </div>
  );
}