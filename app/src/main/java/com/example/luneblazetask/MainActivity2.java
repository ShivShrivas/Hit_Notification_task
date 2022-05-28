package com.example.luneblazetask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.luneblazetask.RetrofitApi.ApiService;
import com.example.luneblazetask.RetrofitApi.RestClient;
import com.example.luneblazetask.databinding.ActivityMain2Binding;
import com.example.luneblazetask.databinding.ActivityMainBinding;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
int info=0;
    Uri myUri;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityMain2Binding activityMain2Binding= DataBindingUtil.setContentView(this,R.layout.activity_main2);
        Intent i=getIntent();
try {
    myUri = Uri.parse(i.getStringExtra("KEY"));
}catch (Exception e){

}

try {
    bitmap= i.getParcelableExtra("bmp_img");
}catch (Exception e){

}


      String name=  i.getStringExtra("name");
       String desc= i.getStringExtra("desc");
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
        String date = df.format(Calendar.getInstance().getTime());

        activityMain2Binding.textView2.setText(name);
        activityMain2Binding.textView52.setText(name);
        activityMain2Binding.textView4.setText(desc);
        activityMain2Binding.textView3.setText(date);

        if (bitmap!=null){
            activityMain2Binding.imageviewmain.setImageBitmap(bitmap);
        }
        if (myUri!=null){
            activityMain2Binding.imageviewmain.setImageURI(myUri);
        }
        activityMain2Binding.btnImnformtive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (info==0){
                    info++;
                    activityMain2Binding.btnImnformtive.setImageDrawable(getResources().getDrawable(R.drawable.book_ic2));
                    RestClient restClient=new RestClient();
                    ApiService apiService=restClient.getApiService();
                    Call<JsonObject> call=apiService.getResponse("97","article","142","0","user");
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.d("TAG", "onResponse: "+response.body());
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.d("TAG", "onFailure: "+t.getMessage());
                        }
                    });


                }else if (info==1){
                    info--;
                    activityMain2Binding.btnImnformtive.setImageDrawable(getResources().getDrawable(R.drawable.ic_book_ic1));
                }
            }
        });
    }
}