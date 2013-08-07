package com.example.myandroidplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.R.layout;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {

	private static final String TAG = "MAIN";
	private Player vv;
	private Button btn;
	private TextView txtView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		runOnUiThread(new Runnable(){
			
            public void run() {
            	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        		StrictMode.setThreadPolicy(policy);
        		txtView = (TextView)findViewById(R.id.text);
        		btn = ((Button)findViewById(R.id.btn));
        		btn.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						vv = (Player)findViewById(R.id.vv);
						vv.setLogArea(txtView);
		        		vv.setFWView((SurfaceView)findViewById(R.id.fw));
		        		vv.setFWContext(getApplicationContext());
						vv.loadVideo(((EditText)findViewById(R.id.txtInput)).getText().toString());
					}
				});
        		vv = (Player)findViewById(R.id.vv);
        		txtView.setMovementMethod(new ScrollingMovementMethod());
        		vv.setLogArea(txtView);
        		vv.setFWView((SurfaceView)findViewById(R.id.fw));
        		vv.setFWContext(getApplicationContext());
                vv.loadVideo(null);
                //vv.appLoaded();
            }
             
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
