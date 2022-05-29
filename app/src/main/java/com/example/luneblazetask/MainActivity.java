package com.example.luneblazetask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.luneblazetask.databinding.ActivityMainBinding;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Bitmap bitmap;
    Uri ImageURI;
    ActivityMainBinding bindingUtil;
    private String[] Items = {"Camera", "Gallery"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindingUtil = DataBindingUtil.setContentView(this, R.layout.activity_main);

        bindingUtil.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Choose Any option");
                builder.setItems(Items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Items[which].equals("Camera")) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                                {
                                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                                }
                                else
                                {
                                    Intent CameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    if (CameraIntent.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(CameraIntent, 7);
                                    }
                                }
                            }



                        } else if (Items[which].equals("Gallery")) {

                            Log.i("GalleryCode", "" + 9);
                            Intent GalleryIntent = null;
                            GalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            GalleryIntent.setType("image/*");
                            GalleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(GalleryIntent, 9);
                        }


                    }
                });
                builder.show();
            }
        });

        bindingUtil.subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bitmap!=null || ImageURI!=null){

                    if (!TextUtils.isEmpty(bindingUtil.editText.getText().toString()) && !TextUtils.isEmpty(bindingUtil.edtInput.getText().toString())){
                    Intent i=new Intent(MainActivity.this,MainActivity2.class);

                        try {
                            i .putExtra("KEY", ImageURI.toString());
                        }catch (Exception e){

                        }

                        try {
                            i.putExtra("bmp_img", bitmap);
                        }catch (Exception e){

                        }

                        i.putExtra("name", bindingUtil.editText.getText().toString().trim());
                        i.putExtra("desc", bindingUtil.edtInput.getText().toString().trim());
                    startActivity(i);
                }else{
                    Toast.makeText(MainActivity.this, "Please Fill All details properly", Toast.LENGTH_SHORT).show();
                }

                }else{
                    Toast.makeText(MainActivity.this, "Upload one image", Toast.LENGTH_SHORT).show();
                }
               
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, 7);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 7:
                    Log.i("CameraCode", "" + 7);
                    Bundle bundle = data.getExtras();
                    bitmap = (Bitmap) bundle.get("data");
                    bindingUtil.imageviewmain.setVisibility(View.VISIBLE);
                    bindingUtil.imageviewmain.setImageBitmap(bitmap);
                    break;
                case 9:
                    Log.i("GalleryCode", "" + requestCode);
                     ImageURI = data.getData();
                    bindingUtil.imageviewmain.setVisibility(View.VISIBLE);
                    bindingUtil.imageviewmain.setImageURI(ImageURI);
                    break;
            }
        }
    }
}