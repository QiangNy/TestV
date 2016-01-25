package ludeng.com.testvi.login;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import jni.cylan.com.Constants;
import ludeng.com.testvi.service.HeartbeatService;
/**
 * @author chenguang
 * @version 1.1.3 2015.7.16
 * 父类活动.
 * 满足登录要求.填写心跳服务和接收异常广播.
 * */

public class BaseActivity extends Activity {
	private BroadcastReceiver mSessionException;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    mSessionException = new BroadcastReceiver() {
	        @Override
	        public void onReceive(Context context, Intent intent) {
	            String action = intent.getAction();
	            if (ludeng.com.testvi.service.HeartbeatService.SESSION_EXCPETION.equals(action)) {
	                onSessionException(intent.getIntExtra("error",
	                        Constants.ERR_UNSUCCESS));
	            }
	        }
	    };
	    IntentFilter filter = new IntentFilter();
	    filter.addAction(ludeng.com.testvi.service.HeartbeatService.SESSION_EXCPETION);
	    registerReceiver(mSessionException, filter);	
	}
/**
 * 会话无效处理.
 * 心跳检测到登录会话已经无效.将弹出一个重新登录界面
 * 
 * */
	public void onSessionException(int intExtra) {
	      try {
	          AlertDialog.Builder builder = new AlertDialog.Builder(this);
	          AlertDialog dialog = builder.create();
	          dialog.setMessage("当前会话已无效");
	          dialog.setButton(DialogInterface.BUTTON_POSITIVE,"重新登录",
	              new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int which) {
	                  PadQuitForServices(BaseActivity.this.getApplicationContext());
	                }
	              });
	          dialog.setCancelable(false);
	          dialog.setCanceledOnTouchOutside(false);

	          if (!isFinishing()) {
	            dialog.show();
	          }
	        } catch (Exception e) {
	          e.printStackTrace();
	          PadQuitForServices(BaseActivity.this.getApplicationContext());
	        }finally {
	            
	        }
		
	}

	protected void PadQuitForServices(Context applicationContext) {
        Thread t = new Thread() {
            public void run() {
               // 主动断开打开的连接.
		}
        };
        t.start();

        Intent intent = new Intent();
        intent.setComponent(new ComponentName(getPackageName(),
                "com.example.cylanvpn.login.LoginActivity"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
		
	}

	@Override
	protected void onDestroy() {
		stopHeartService();
		super.onDestroy();
	}
/**
 * 停止心跳服务.
 * */
	public void stopHeartService() {
		Intent heartbeatIntent = new Intent(this, HeartbeatService.class);
		stopService(heartbeatIntent);
		
	}
/**
 * 发送心跳服务
 * */
	public void heartService() {
        Intent heartbeatIntent = new Intent(this, HeartbeatService.class);
        this.startService(heartbeatIntent);
	}
	
}
