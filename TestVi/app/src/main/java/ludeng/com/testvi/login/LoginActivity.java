package ludeng.com.testvi.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.VpnService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opentest.R;

import java.io.File;
import java.io.IOException;

import jni.cylan.com.LoginReturn;
import ludeng.com.testvi.opentest.DisconnectVPN;
import ludeng.com.testvi.vpnspl.VpnProfile;
import ludeng.com.testvi.vpnspl.core.ProfileManager;
import ludeng.com.testvi.vpnspl.core.VPNLaunchHelper;
import ludeng.com.testvi.vpnspl.core.VpnStatus;
import ludeng.com.testvi.vpnspl.utils.VpnUtils;

/**
 * @author chenguang
 * @version 1.1.3 2015.7.16

 * 一个简单的VPN通道调用类.

 * <p>首先我们新建了新建ICABLib项目,等待作为库被调用.现在所用到的ICABLib.jar便是由此生成的.

 * 在ICABLib项目项目中,jni.cylan.com是库中源码src下程序包的一个分支,主要是涉及到jni中静态方法的引用,由其命名亦可知.<br>

 * 该src分支下几个类及其方法被引用 ,涉及的类有cylanlib.java NativeFunction.java ProxyManager.java

 * SessionManager.java LoginReturn.java<br>

 */

public class LoginActivity extends BaseActivity {

	private static final int START_VPN_PROFILE =70 ;
	private ProgressDialog mprogress;
	protected static final int MSG_OPEN_WEB = 0;
	protected static final int MSG_CLOSE_VPN = 1;
	protected static final int MSG_SEND_HTTP_RESULT = 2;
	protected static final int MSG_LOGIN_WRONG = 3;
	private static boolean isLogin = false;
	private Thread mThreadFri;
	private boolean requstIsRun;
//	private WebView mwebView;
	private Button bt_login;
	private Button bt_logout;
	private EditText server;
	private EditText name;
	private EditText passwd;
	private EditText et_htto_request;
	private TextView tv_http_Result;
	private LoginActivity mContext;
	private VpnProfile profile;

/*	static{
		jni.cylan.com.cylanlib.loadcylanso();
	}*/
	/**
	 * 应用初始化.

	 * */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mContext = LoginActivity.this;

		initviews();

		initDatas();

	}

	private void initDatas() {
		View.OnClickListener clickListener = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
					case R.id.bt_login:
						boolean mIniRes = initResource();
						if (isAvailableNetWork(mContext)) {
							if (mIniRes)
								startVPN();
						} else {
							Toast.makeText(mContext, "没有连接网络...",
									Toast.LENGTH_SHORT).show();
						}
						break;
					case R.id.bt_logout:
						stopVPN();
						break;
					default:
						break;
				}
			}

		};
		bt_login.setOnClickListener(clickListener);
		bt_logout.setOnClickListener(clickListener);


		addVpnProfile();
		editProfile();
		ProfileManager.getInstance(this).addProfile(profile);
	}

	private void editProfile() {
		profile.mUseLzo = true;
		profile.mPKCS12Password = "";
		try {
			profile.mCaFilename = VpnUtils.getFileData(this,100);
			profile.mClientCertFilename = VpnUtils.getFileData(this,101);
			profile.mClientKeyFilename = VpnUtils.getFileData(this,102);
		} catch (IOException e) {
			Log.i("ICABVIEW","add client wrong");
			e.printStackTrace();
		}
	}

	private void addVpnProfile() {
		profile = new VpnProfile("first");
	}

	private void initviews() {
		jni.cylan.com.cylanlib.initsystem(mContext);
		File f = android.os.Environment.getExternalStorageDirectory();
		if (!f.exists()) {
			f = android.os.Environment.getDownloadCacheDirectory();
		}
		File dir = new File(f.getAbsolutePath() + "/cloud/ICAB_PhoneLog");

		jni.cylan.com.cylanlib.SetDebugOutputFile(dir.getAbsolutePath()
				+ "/ClientCoreLog-" + ".txt");


		bt_login = (Button) findViewById(R.id.bt_login);
		bt_logout = (Button) findViewById(R.id.bt_logout);
		server = (EditText) findViewById(R.id.et_login_server);
		name = (EditText) findViewById(R.id.et_login_name);
		passwd = (EditText) findViewById(R.id.et_login_passwd);
		et_htto_request = (EditText) findViewById(R.id.et_htto_request);
		tv_http_Result = (TextView) findViewById(R.id.tv_http_Result);
//		mwebView = (WebView) findViewById(R.id.web1);
		Log.i("CylanTest","onCreate");
		et_htto_request.setText("http://www.hao123.com/");
//		webinit();


	}

	/**
 * 检测网络.
 *
 * 检测网络是否开启
 * */
	public boolean isAvailableNetWork(Context mCon) {
		final ConnectivityManager cm = (ConnectivityManager) mCon
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		try {
			return cm.getActiveNetworkInfo().isAvailable();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
/**
 * 检测登录信息.
 *
 * 检测登录信息是否完整
 *
 * @return true 已填写登录信息. false 未填写完整
 * */
	public boolean initResource() {
		if (TextUtils.isEmpty(server.getText().toString())) {
			Toast.makeText(mContext, "请输入服务器地址", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(name.getText().toString())) {
			Toast.makeText(mContext, "请输入用户名", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(passwd.getText().toString())) {
			Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (TextUtils.isEmpty(et_htto_request.getText().toString())) {
			Toast.makeText(mContext, "请输入访问链接的地址", Toast.LENGTH_SHORT).show();
			return false;
		}

		return true;
	}
/**
 * webview 初始化.
 *
 * 简单的webview 初始化设置
 * */
/*	public void webinit() {
		mwebView.getSettings().setUseWideViewPort(true);
		mwebView.getSettings().setBuiltInZoomControls(true);
		mwebView.getSettings().setJavaScriptEnabled(true);
		mwebView.getSettings().setSupportMultipleWindows(true);
		mwebView.getSettings().setPluginState(PluginState.ON);
		WebSettings s = mwebView.getSettings();
		s.setBuiltInZoomControls(true);
		s.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
		s.setUseWideViewPort(true);
		s.setLoadWithOverviewMode(true);
		s.setGeolocationEnabled(true);
		s.setDomStorageEnabled(true);
		mwebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		});
	}*/

	private ProgressDialog showDialog(ProgressDialog progress, String str) {
		progress = new ProgressDialog(this);
		progress.setCanceledOnTouchOutside(true);
		progress.setCancelable(true);
		progress.setMessage(str);
		if (!isFinishing()) {
			progress.show();
		}
		progress.setOnCancelListener(new DialogInterface.OnCancelListener() {

			public void onCancel(DialogInterface dialog) {
				// mBtnLogin.setEnabled(true);
			}
		});
		return progress;
	}

	/**
	 * 停止VPN并清除通道.

	 * 最后是VPN通道的初始化,启动,关闭,和清除函数.VPN调用的是静态方法,通过jni访问C代码,其原理过程不再给出.

	 * */
	public void stopVPN() {
		// TODO Auto-generated method stub
		if (!isLogin) {
			Toast.makeText(mContext, "请先登录..", Toast.LENGTH_SHORT).show();
			return;
		}
		isLogin = false;
		mprogress = showDialog(mprogress, "VPN is stopping");
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
	/*			jni.cylan.com.NativeFunction.VPNStop();
				jni.cylan.com.NativeFunction.VPNCleanup();*/
				stopvpn();
				jni.cylan.com.cylanlib.Logout();
			}
		}).start();
		if (mprogress.isShowing()) {
			Message msg = myHandler.obtainMessage(MSG_CLOSE_VPN, 0);
			myHandler.sendMessage(msg);
		}
	}

	/**
	 * 启动VPN通道.

	 * 启动用户登录函数nativeUserLogin得到一个从SGA中返回的LoginReturn类返回值,具体可查看该类<br>

	 * 登录成功后,则首先创建并获取到一个有效的用户session.以便下面的函数能正常访问.


	 * */

	public void startVPN() {
		if(isLogin) {
			Toast.makeText(mContext, "已经登录..", Toast.LENGTH_SHORT).show();
			return;
		}
		mprogress = showDialog(mprogress, "VPN is starting");
		new Thread(new Runnable() {

			@SuppressWarnings("static-access")
			public void run() {
				// 登录
				LoginReturn retValue = jni.cylan.com.NativeFunction
						.nativeUserLogin(server.getText().toString(), name
								.getText().toString(), passwd.getText()
								.toString(), "", 2, "3.2.0", "cylan", "", "",
								"", "");
				if (retValue != null
						&& retValue.mLoginState == retValue.RESULT_LOGIN_SUCCESS) {
					jni.cylan.com.ProxyManager.getSessionManager().setSession(
							retValue.mSession);

/*
					jni.cylan.com.NativeFunction.VPNInit(retValue.mSession);
					jni.cylan.com.NativeFunction.VPNStart();*/

					if (mprogress.isShowing()) {
						Message msg = myHandler.obtainMessage(MSG_OPEN_WEB,
								et_htto_request.getText().toString());
						myHandler.sendMessage(msg);
					}
				} else {
					if (mprogress.isShowing()) {
						Message msg = myHandler.obtainMessage(MSG_LOGIN_WRONG,
								0);
						myHandler.sendMessage(msg);
					}
				}
			}


		}).start();
	}

	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_OPEN_WEB:
				mprogress.dismiss();
				myVPN();
	/*			if (mwebView.isShown()) {
					mwebView.loadUrl(msg.obj.toString());
				}*/
		//		heartService();
		//		httprequst();
				isLogin = true;
				break;
			case MSG_CLOSE_VPN:
				mprogress.dismiss();
		//		stophttprequst();
		//		stopHeartService();

				break;
			case MSG_SEND_HTTP_RESULT:
				outputHttpResult(msg.obj);
				break;
			case MSG_LOGIN_WRONG:
				mprogress.dismiss();
				Toast.makeText(mContext, "登录失败,请检查登录信息", Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	private void stopvpn() {
		Intent disconnectVPN = new Intent(this, DisconnectVPN.class);
		startActivity(disconnectVPN);;
	}


	private void myVPN() {
		Intent intent = VpnService.prepare(this);
		if (intent != null) {

			try {
				startActivityForResult(intent, START_VPN_PROFILE);
			} catch (ActivityNotFoundException ane) {
				VpnStatus.logError(R.string.no_vpn_support_image);
			}
		} else {
			onActivityResult(START_VPN_PROFILE, Activity.RESULT_OK, null);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if(requestCode==START_VPN_PROFILE) {
			if(resultCode == Activity.RESULT_OK) {

				new startOpenVpnThread().start();
			} else if (resultCode == Activity.RESULT_CANCELED) {
				// User does not want us to start, so we just vanish
				VpnStatus.updateStateString("USER_VPN_PERMISSION_CANCELLED", "", R.string.state_user_vpn_permission_cancelled,
						VpnStatus.ConnectionStatus.LEVEL_NOTCONNECTED);

				finish();
			}
		}
	}

	@Override
	protected void onDestroy() {
		stopHeartService();
		isLogin = false;
		myHandler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}

	StringBuffer sb = null;
	int n = 0;
	private void outputHttpResult(Object obj) {
		n++;
		if (sb == null) {
			sb = new StringBuffer();
		}
		if (n > 4) {
			n = 0;
			sb.delete(0, sb.length());
		}
		sb.append(obj.toString());
		tv_http_Result.setText(sb);
	}

	private void httprequst() {
	//	mThreadFri = new Thread(logHttprequset);
	//	requstIsRun = true;
	//	mThreadFri.start();


	}

	private void stophttprequst() {
		requstIsRun = false;
	}

	Runnable logHttprequset = new Runnable() {

		@Override
		public void run() {

			while (requstIsRun) {
				/*String url = et_htto_request.getText().toString();
				HttpGet httpRequest = new HttpGet(url);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = null;
				try {
					response = httpclient.execute(httpRequest);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				int code = response.getStatusLine().getStatusCode();
				String str_answer = null;
				if (code == 200) {
					str_answer = "http 连接正常,返回StatusCode:200\n";
					Log.i("ICABVIEW","network is working");
				} else {
					str_answer = "http 连接异常,返回StatusCode:" + code + "\n";
					Log.i("ICABVIEW","network is breaking");
				}
				Message msg = myHandler.obtainMessage(MSG_SEND_HTTP_RESULT,
						str_answer);
				myHandler.sendMessage(msg);*/
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	};

	private class startOpenVpnThread extends Thread {

		@Override
		public void run() {

			VPNLaunchHelper.startOpenVpn(profile, getBaseContext());
			//  finish();
		}

	}
}
