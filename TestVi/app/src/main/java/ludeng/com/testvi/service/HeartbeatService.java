package ludeng.com.testvi.service;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;





public class HeartbeatService extends Service {
	private static String TAG = "HeartbeatService";
    private Handler handler;
    MyTimerTask ttask = null;
    private int nLostTime = 0;
    private Timer timer = null;
    public static final String SESSION_EXCPETION = "com.cylan.cloud.pad.SessionExpception";
    private final static int HBMSG_SHORTTIMER = 1;
    private final static int HBMSG_LONGTIMER = 2;
    private final static int HBMSG_SIGNAL_BAD = 3;
    private final static int HBMSG_LOSTTIME = 5;
    private int count = 0;
    private boolean isfinish = false;
    

    @Override
	public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        startHeartbeat();
 
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        try
        {
        stop();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        handler = null;
        ttask = null;
        nLostTime = 0;
        timer = null;
        count = 0;
        isfinish = false;
        super.onDestroy();
    }

    private void startHeartbeat() {
        try {
        System.setProperty("keepAlive", "false");
        timer = new Timer();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                case HBMSG_SHORTTIMER:
                    ttask = new MyTimerTask();
                    timer.schedule(ttask, 5 * 1000);
                    break;
                case HBMSG_LONGTIMER:
                    ttask = new MyTimerTask();
                    timer.schedule(ttask, 50 * 1000);
                    break;
                case HBMSG_SIGNAL_BAD:
                    Toast.makeText(HeartbeatService.this,"网络连接异常",
                            Toast.LENGTH_LONG).show();
                    ttask = new MyTimerTask();
                    timer.schedule(ttask, 5 * 1000);
                    break;
                case 0:
                    if (msg.obj != null) {
                    }
                    sendBroadcast(new Intent(SESSION_EXCPETION).putExtra(
                            "error", msg.arg1));                    
                    break;
                default:
                    break;

                }
            }
        };
        
        handler.sendEmptyMessage(HBMSG_LONGTIMER);
        }catch (Exception e){
            e.printStackTrace();
            }
    }

    public interface AsycListener {
        void run();
    }
    private class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
            if (isfinish)
                return;

            int nRet = -1;
                try {
                    nRet = jni.cylan.com.cylanlib.CSessionSendHeartbeat();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            
            if (!isfinish) {
                if (nRet == 0) {
                    handler.sendEmptyMessage(HBMSG_LONGTIMER);
                    nLostTime = 0;
                } else {
              
                    if (nRet == 4) {
                        handler.removeCallbacksAndMessages(null);
                        isfinish = true;
                        handler.sendMessage(handler.obtainMessage(0, null));
                        return;
                    }
                    
                    if (++nLostTime < HeartbeatService.HBMSG_LOSTTIME) 
                    {
                        if (nLostTime == 3) {//网咯信号不好,第三次还没反应.
                            handler.sendEmptyMessage(HBMSG_SIGNAL_BAD);
                        }else {
                           handler.sendEmptyMessage(HBMSG_SHORTTIMER); 
                        }
                        
                    }
                        
                }
            }
            if (nLostTime >= HeartbeatService.HBMSG_LOSTTIME) {
                String sError = null;
/*                if (cylanConstants.SessionInvalid == false) {
                        return;*/

                    sError = jni.cylan.com.cylanlib
                            .GetErrorInfo(HeartbeatService.this
                                    .getApplicationContext(), nRet);

              //  }
                handler.removeCallbacksAndMessages(null);
                isfinish = true;
                handler.sendMessage(handler.obtainMessage(0, sError));

            }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        ttask.cancel();
        timer.cancel();
        isfinish = true;
        handler.removeCallbacks(ttask);
        handler.removeCallbacksAndMessages(null);
    }

}
