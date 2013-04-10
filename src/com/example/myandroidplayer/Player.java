package com.example.myandroidplayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Player extends VideoView {

	//private VideoView vv;
	private static final String TAG = "PLAYER";
	private MediaController mc;
	private Mediator mediator;
	private TextView _logField;
	
	public Player(Context context, AttributeSet attrs){
		super(context, attrs);
		setListeners();
	}
	
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(415, 300);
    }
	
    @Override
    public void start(){
    	super.start();
    	mediator.onPlay();
    }
    
    @Override
    public void pause(){
    	super.pause();
    	mediator.onPause();
    }
    
    @Override
    public void stopPlayback(){
    	super.stopPlayback();
    	mediator.onStop();
    }
    
    @Override
    public void resume(){
    	super.resume();
    	mediator.onResume();
    }
    
    @Override
    public void seekTo(int mSec){
    	super.seekTo(mSec);
    	mediator.onSeek();
    }
    
    
    public void playVideo(String path){
		
		Log.d(TAG,"path: "+path);
		try{
			if(path == null){
				path = "http://video-js.zencoder.com/oceans-clip.mp4";
			}
			this.setVideoPath(getDataSource(path)); // http://video-js.zencoder.com/oceans-clip.mp4 "http://daily3gp.com/vids/747.3gp"
		}catch(Exception e){
			Log.e(TAG, "error: " + e.getMessage(), e);
		}
		Log.d(TAG,"about to start");
		setMediaControl();
		this.start();
		Log.d(TAG,"started");
		this.requestFocus();
		Log.d(TAG,"focused");
	}
    
    public void setLogArea(TextView logField){
    	_logField = logField;
    	mediator = new Mediator(_logField);	
    }
    
    private void setMediaControl(){
		mc = new MediaController(getContext());
		mc.setMediaPlayer(this);
		mc.setAnchorView(this);
		mc.setScaleX(1.7f);
		mc.setScaleY(0.56f);
		mc.setPadding(80, 250, 100, 300);
		mc.setAlpha(0.8f);
		this.setMediaController(mc);
	}
    
    private void setListeners(){
    	
    	this.setOnPreparedListener(new OnPreparedListener() {
			
			@Override
			public void onPrepared(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mediator.onLoad();
			}
		});
    	
    	this.setOnInfoListener(new OnInfoListener() {
			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {
				mediator.onPlayHeadUpdate();
				return false;
			}
		});
    	this.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mediator.onStop();				
			}
		});
    }
	
	private String getDataSource(String path) throws IOException {
        if (!URLUtil.isNetworkUrl(path)) {
            return path;
        } else {
            URL url = new URL(path);
            URLConnection cn = url.openConnection();
            cn.connect();
            InputStream stream = cn.getInputStream();
            if (stream == null)
                throw new RuntimeException("stream is null");
            File temp = File.createTempFile("mediaplayertmp", "dat");
            temp.deleteOnExit();
            String tempPath = temp.getAbsolutePath();
            FileOutputStream out = new FileOutputStream(temp);
            byte buf[] = new byte[128];
            do {
                int numread = stream.read(buf);
                if (numread <= 0)
                    break;
                out.write(buf, 0, numread);
            } while (true);
            try {
                stream.close();
            } catch (IOException ex) {
                Log.e(TAG, "error: " + ex.getMessage(), ex);
            }
            out.close();
            return tempPath;
        }
    }

}
