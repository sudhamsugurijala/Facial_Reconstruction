package com.example.miniproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MorphActivity extends AppCompatActivity {

    Bitmap input_bmp;
    byte[] input_byte_array;
    EditText url;
    EditText port;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.morph);
        ImageView img = findViewById(R.id.imageView);
        url = findViewById(R.id.editText2);
        port = findViewById(R.id.portNummorph);

        Bundle extras = getIntent().getExtras();
        input_byte_array = extras.getByteArray("image");
        input_bmp = BitmapFactory.decodeByteArray(input_byte_array,0, input_byte_array.length);

        img.setImageBitmap(input_bmp);
    }

    void connectServer(View v) {
        String addr = url.getText().toString();
        String portnum = port.getText().toString();

        String postURL = "http://"+addr+":"+portnum+"/phi_morph";

        RequestBody postBodyImg = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("image", "input.jpg", RequestBody.create(MediaType.parse("image/*jpg"), input_byte_array))
                .build();

        postRequest(postURL, postBodyImg);
    }

    void postRequest(String postURL, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();

        Request req = new Request.Builder().url(postURL).post(postBody).build();

        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });

    }
}
