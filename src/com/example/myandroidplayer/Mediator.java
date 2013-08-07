package com.example.myandroidplayer;

import java.util.HashMap;

import com.vianet.bento.Bento;
import com.vianet.bento.api.Events;
import com.vianet.bento.interfaces.IListener;
import com.vianet.bento.vo.ConfigSettings;
import com.vianet.bento.vo.FreewheelVO;
import com.vianet.bento.vo.Metadata;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.VideoView;


public class Mediator {

	private static final String TAG = "MEDIATOR";
	private TextView logField;
	private Bento bento = null;
	private VideoView vView = null;
	
	public Mediator(TextView textField, Activity activity){
		logField = textField;
		bento = new Bento();
		//bento.onCreate(activity);
		Events.FW_AD_PLAYEND.addListener(
    			new IListener() {	
					@Override
					public void execute(Object data) {
						Log.d(TAG, "Content is about to begin after Ad.");
						// TODO Auto-generated method stub
						((Player)vView).playVideo();
					}
				}
		);
	}
	
	/*public Mediator(Activity activity, Context context, SurfaceView sView){
		
	}*/
	
	public void onConfig(){
		//Log.d(TAG, "Player is loaded");
		ConfigSettings cs = new ConfigSettings();
		cs.omnitureEnabled = true;
		cs.omnitureSuite = "viarnd";
		cs.omnitureTrackingServer = "sc.mtv.com"; 
		cs.freewheelEnabled = true;
		bento.onConfig(cs);
	}
	
	public void onActivyReady(Activity act){
		bento.onActivityReady(act);
	}
	
	public void onLoad(){
		Metadata mData = new Metadata();
		mData.videoTitle = "Test video";
		mData.duration = 30;
		mData.playItemCount = 1;
		mData.itemIndex = 0;
		mData.appName = "My Android Test App";
		mData.section = "home/PlayerPage";
		bento.onPlayerAvailable(vView);
		bento.onMetadata(mData);
		bento.getStorage().setString("key", "Value from storage === "+mData.videoTitle);
		logField.setText("Player is loaded");
	}
	
	public void onPlay(){
		//bento.onPlayStart(0.0);
		//Log.d(TAG, "Content Start to Play");
		logField.setText(logField.getText() + "\r\nContent Start to Play");
	}
	
	public void onPause(){
		//bento.onPause(0.0);
		//Log.d(TAG, "Content is Paused");
		logField.setText(logField.getText() + "\r\nContent is Paused");
	}
	
	public void onStop(){
		//bento.onPlayEnd(0.0);
		//Log.d(TAG, "Content is Stop");
		logField.setText(logField.getText() + "\r\nContent is Stopped");
		onIsItTimeForAnAd(0, true);
	}
	
	public void onSeek(){
		//Log.d(TAG, "Content is Seeked");
		logField.setText(logField.getText() + "\r\nContent is Seeked");
	}
	
	public void onResume(){
		//Log.d(TAG, "Content is Resumed");
		logField.setText(logField.getText() + "\r\nContent is Resumed");
	}
	
	public void onPlayHeadUpdate(double playHead){
		//Log.d(TAG, "Content playhead update");
		logField.setText(logField.getText() + "\r\nContent playhead update");
		//bento.onPlayheadUpdate(playHead);
	}
	
	public void onIsItTimeForAnAd(int itemIndex, boolean hasContentEnd){
		Log.d(TAG, "Is it time for an Ad");
		logField.setText("\r\nCheck for an Ad");
		FreewheelVO fwVO = new FreewheelVO();
		fwVO.currentItem = itemIndex;
		fwVO.hasContentEnd = hasContentEnd;
		bento.onIsItTimeForAnAd(fwVO);
	}
	
	public void onFWViewAvailable(SurfaceView view){
		bento.onViewAvailable(view);
	}
	public void onFWContextAvailable(Context ctxt){
	//	bento.onFWContextAvailable(ctxt);
	}
	public void onPlayerAvailable(VideoView vv){
		vView = vv;
	}
	public void onPageCall(){
		HashMap<String, String> vo = new HashMap<String, String>();
		vo.put("eVar1", "App title");
		vo.put("prop1", "App Title");
		vo.put("eVar2", "app loaded");
		vo.put("prop2", "App loaded");
		//HashMap<Integer, String> map = new HashMap<Integer, String>();
		vo.put("eVar4", "Four");
		vo.put("eVar5", "Five");
		vo.put("eVar6", "six");
		vo.put("eVar7", "seven");
		vo.put("prop3", bento.getStorage().getString("key"));
		vo.put("events", "event1,even2");
		bento.onTrackPageView(vo);		
	}
	public void onEventCall(String url, String type, String name){
		Log.d(TAG, "onEventCall");
		HashMap<String, String> vo = new HashMap<String, String>();
		//AppMeasureVO vo = new AppMeasureVO();
		vo.put("eVar10", "Go link");
		vo.put("eVar10", "Go link");
		vo.put("events", "event10");
		vo.put("linkURL", url);
		vo.put("linkType", type);
		vo.put("linkName", name);
		bento.onTrackLink(vo);
	}
}
