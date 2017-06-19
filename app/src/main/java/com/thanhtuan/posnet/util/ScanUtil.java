package com.thanhtuan.posnet.util;

import android.app.Activity;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;

public class ScanUtil {

    public static void startScan(Activity activity, MaterialBarcodeScanner.OnResultListener onResultListener) {
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(activity)
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withText("Scanning...")
                .withResultListener(onResultListener)
                .build();
        materialBarcodeScanner.startScan();
    }
}
