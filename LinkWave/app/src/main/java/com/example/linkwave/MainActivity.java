package com.example.linkwave;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN , WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {






                if (Build.VERSION.SDK_INT >= 21){

                    Intent myInternt_Error = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(myInternt_Error);

                }else {
                    Intent myInternt_Error = new Intent(MainActivity.this,ErrorActivity.class);
                    myInternt_Error.putExtra("Error_Title","SDK ERROR");
                    myInternt_Error.putExtra("Error_Des","Sorry , Your Android Version does not Supported This App! ");
                    startActivity(myInternt_Error);
                }







            }
        }, 4000);








    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
