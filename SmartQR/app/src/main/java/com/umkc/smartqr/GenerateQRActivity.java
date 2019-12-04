package com.umkc.smartqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GenerateQRActivity extends AppCompatActivity {

    private EditText txtInput;
    private ImageView qrView;
    String email="";
    String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_qr);
        txtInput = findViewById(R.id.txtInput);
        qrView = findViewById(R.id.qrView);
        email=getIntent().getStringExtra("email");
        id=getIntent().getStringExtra("id");
    }

    public void generateCode(View view) {
        String input = txtInput.getText().toString();
        if(input!=null){
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(input, BarcodeFormat.QR_CODE,500,500);
                BarcodeEncoder barcodeEncoder =  new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qrView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
    }

    public void home(View view) {
        Intent redirect = new Intent(GenerateQRActivity.this,HomeActivity.class).putExtra("email",email).putExtra("id",id);
        startActivity(redirect);
    }
}
