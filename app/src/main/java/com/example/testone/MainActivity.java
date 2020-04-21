package com.example.testone;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.edit_zhongwen)
    EditText editZhongwen;
    @BindView(R.id.fanyi)
    TextView fanyi;
    @BindView(R.id.start_fanyi)
    Button startFanyi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        startFanyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("test",editZhongwen.getText().toString());
                Gson gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd hh:mm:ss")
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://fy.iciba.com/")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();
                String a="fy";
                String f="zh-CN";
                String t="en";
                String w=editZhongwen.getText().toString().trim();
                APi api = retrofit.create(APi.class);
                Call<testBean> call=api.getExchange(a,f,t,w);
                call.enqueue(new Callback<testBean>() {
                    @Override
                    public void onResponse(Call<testBean> call, Response<testBean> response) {
                        if (response == null) {
                            return;
                        }
                        testBean data = response.body();
                        if(data.getStatus()!=1){
                            Toast.makeText(MainActivity.this,"太难啦，我都不知道这是不是中文",Toast.LENGTH_LONG).show();
                            fanyi.setText("");
                        }else{
                            fanyi.setText(data.getContent().getOut());
                        }
                    }

                    @Override
                    public void onFailure(Call<testBean> call, Throwable t) {
                        Log.e("TAG", "回调失败：" + t.getMessage() + "," + t.toString());
                        Toast.makeText(MainActivity.this, "回调失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        editZhongwen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


    }
}

