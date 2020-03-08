package com.example.mockingapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mtextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startScreenButton = findViewById(R.id.button);

        //The following sets a listener where when we press the button "startScreenButton" it will bring us to the next class "RulesActivity" where we will launch a new layout xml to display to the user
        startScreenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                callOkHTTP();
            }

            private void callOkHTTP() {
                mtextView = findViewById(R.id.githubApi);

                OkHttpClient client = new OkHttpClient();

                //String url = "https://api.github.com/search/repositories?q=test&per_page=1";
                String url = "https://data.smartdublin.ie/cgi-bin/rtpi/realtimebusinformation?stopid=1190&format=json";

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                //enqueue runs in the background
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        if (response.isSuccessful()) {
                            final String myResponse = response.body().string();

                            //still on the background thread from enqueue this will allow us to run the main thread
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mtextView.setText(myResponse);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
