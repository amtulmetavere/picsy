package com.example.picsy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class generateimage extends AppCompatActivity {

    EditText inputText;
    MaterialButton generateBtn;
    ProgressBar progressBar;
    ImageView imageView;

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generateimage);

        inputText = findViewById(R.id.input_text);
        generateBtn = findViewById(R.id.generate_btn);
        progressBar = findViewById(R.id.progress_bar);
        imageView = findViewById(R.id.image_view);
        MaterialButton downloadBtn = findViewById(R.id.download_btn);

        generateBtn.setOnClickListener((v) -> {
            String text = inputText.getText().toString().trim();
            if (text.isEmpty()) {
                inputText.setError("Text can't be empty");
                return;
            }
            callAPI(text);
        });



    }

    private void downloadImage(String imageUrl) {

        if (imageUrl != null && !imageUrl.isEmpty()) {

            Uri uri = Uri.parse(imageUrl);

            DownloadManager.Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setTitle("Image Download");
            request.setDescription("Downloading image...");

            DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            if (downloadManager != null) {
                downloadManager.enqueue(request);
            } else {

                Toast.makeText(getApplicationContext(), "DownloadManager not available", Toast.LENGTH_LONG).show();
            }
        } else {

            Toast.makeText(getApplicationContext(), "Image URL not available", Toast.LENGTH_LONG).show();
        }
    }



    void callAPI(String text){
        setInProgress(true);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("prompt",text);
            jsonBody.put("size","256x256");
        }catch(Exception e){
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(jsonBody.toString(),JSON);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/images/generations")
                .header("Authorization", "Bearer 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")
                .post(requestBody)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Toast.makeText(getApplicationContext(),"Failed to generate image",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                try{
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String imageUrl = jsonObject.getJSONArray("data").getJSONObject(0).getString("url");
                    loadImage(imageUrl);
                    downloadImage(imageUrl);
                    setInProgress(false);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    void setInProgress(boolean inProgress){
        runOnUiThread(()->{
            if(inProgress){
                progressBar.setVisibility(View.VISIBLE);
                generateBtn.setVisibility(View.GONE);
            }else{
                progressBar.setVisibility(View.GONE);
                generateBtn.setVisibility(View.VISIBLE);
            }
        });

    }

    void loadImage(String url){
        //load image
        runOnUiThread(()->{
            Picasso.get().load(url).into(imageView);
        });

    }


}

