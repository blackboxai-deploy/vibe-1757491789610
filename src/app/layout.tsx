import type { Metadata } from "next";
import { Geist, Geist_Mono } from "next/font/google";
import { Toaster } from "sonner";
import "./globals.css";

const geist = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});

export const metadata: Metadata = {
  title: "Mobile Network Field Test",
  description: "Professional Mobile Network Field Testing Application",
  keywords: ["mobile", "network", "field test", "sim", "operator"],
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body
        className={`${geist.variable} ${geistMono.variable} antialiased bg-gradient-to-br from-blue-50 to-indigo-100 min-h-screen`}
      >
        {children}
        <Toaster position="top-center" richColors />
      </body>
    </html>
  );
}