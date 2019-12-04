package com.umkc.smartqr;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    String id="";
    String email="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        id=getIntent().getStringExtra("id");
        email=getIntent().getStringExtra("email");
        setContentView(mScannerView);
        if(checkPerissions()){

        }else{
            requestPerissions();
        }
    }
    private boolean checkPerissions(){
        return (ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA)== PackageManager.PERMISSION_GRANTED);
    }

    private void requestPerissions(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, 234);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(checkPerissions()){
            mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
            mScannerView.startCamera();
        }else{
            requestPerissions();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        Toast.makeText(this,rawResult.getText(),Toast.LENGTH_SHORT).show();
        Intent redirect = new Intent(ScanQRActivity.this,ResultActivity.class).putExtra("result",rawResult.getText()).putExtra("id",id).putExtra("email",email);
        startActivity(redirect);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 234:
                if(grantResults.length>0){
                    if(grantResults[0]== PackageManager.PERMISSION_DENIED){
                        requestPerissions();
                    }
                }
                break;
        }
    }
}
