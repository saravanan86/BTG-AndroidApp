package com.example.myandroidplayer;

import android.util.Log;
import android.widget.TextView;

public class Mediator {

	private static final String TAG = "MEDIATOR";
	private TextView logField;
	
	public Mediator(TextView textField){
		logField = textField; 
	}
	
	public void onLoad(){
		//Log.d(TAG, "Player is loaded");
		logField.setText("Player is loaded");
	}
	
	public void onPlay(){
		//Log.d(TAG, "Content Start to Play");
		logField.setText(logField.getText() + "\r\nContent Start to Play");
	}
	
	public void onPause(){
		//Log.d(TAG, "Content is Paused");
		logField.setText(logField.getText() + "\r\nContent is Paused");
	}
	
	public void onStop(){
		//Log.d(TAG, "Content is Stop");
		logField.setText(logField.getText() + "\r\nContent is Stopped");
	}
	
	public void onSeek(){
		//Log.d(TAG, "Content is Seeked");
		logField.setText(logField.getText() + "\r\nContent is Seeked");
	}
	
	public void onResume(){
		//Log.d(TAG, "Content is Resumed");
		logField.setText(logField.getText() + "\r\nContent is Resumed");
	}
	
	public void onPlayHeadUpdate(){
		//Log.d(TAG, "Content playhead update");
		logField.setText(logField.getText() + "\r\nContent playhead update");
	}
}
