package com.ebox.ex.ui;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.ebox.AppApplication;
import com.ebox.R;
import com.ebox.pub.ui.RemoteInvokeService;

public class DialogWebActivity extends CommonActivity {
	private Context mContext;
	private WebView mWebView;
	private Button quit;
	private String mUrl;
	private String title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		View view = LayoutInflater.from(this).inflate(
				R.layout.ex_activity_dialog_web, null);

		// WindowManager m = getWindowManager();
		// Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
		// android.view.WindowManager.LayoutParams p =
		// getWindow().getAttributes(); /
		// p.height = (int) (d.getHeight() * 0.8); // 高度设置为屏幕的0.8
		// p.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.7
		// getWindow().setAttributes(p);

		setContentView(view);

		WindowManager m = getWindowManager();
		Display d = m.getDefaultDisplay(); // 获得屏幕的宽高
		android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
		p.height = (int) (d.getHeight() * 0.7);
		p.width = (int) (d.getWidth() * 0.7);
		getWindow().setAttributes(p);

		mUrl = getIntent().getStringExtra("web_url");
		title = getIntent().getStringExtra("title");
		// mUrl="http://192.168.1.4:4300/ebox/new_year";
		mContext = DialogWebActivity.this;

		Log.i("WebPageActivity", "mUrl:" + mUrl);
		initView();
	}

	private void initView() {
		mWebView = (WebView) this.findViewById(R.id.d_webview);
		quit = (Button) findViewById(R.id.d_bt_quit);
		quit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();
			}
		});
		initWebView();
	}

	private void initWebView() {

		this.mWebView.addJavascriptInterface(new RemoteInvokeService(
				DialogWebActivity.this, mWebView, mUrl), "js_invoke");

		final WebSettings localWebSettings = this.mWebView.getSettings();
		localWebSettings.setSupportZoom(true);
		localWebSettings.setBuiltInZoomControls(true);
		localWebSettings.setJavaScriptEnabled(true);
		localWebSettings.setLoadsImagesAutomatically(true);
		localWebSettings.setDomStorageEnabled(true); // 开启DOM Storage api功能

		localWebSettings.setAllowFileAccess(true);
		localWebSettings.setLoadWithOverviewMode(true);

		// /**快递柜webview模块的优化
		// * 1，加快Html网页装载完成的速度
		// * 等网页加载完成，再对图片进行加载
		// *
		// * 2,缓存策略： 判断是否有网络 有网络：
		// * 使用LOAD_DEAULT(根据cache-control决定是否从网络上取数据) 无网络：
		// * 使用LOAD_CACHE_ELSE_NETWORK（只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据）
		// */
		//
		// localWebSettings.setLoadsImagesAutomatically(true);

		// 设置缓存模式
//		if (NetworkCtrl.IsConnectedToNetwork(mContext)) {
//			
//			localWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//		}else {
//			localWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//		}
		localWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

		// 设置app-cache
		localWebSettings.setAppCacheEnabled(true);
		String appCaceDir = this.getApplicationContext()
				.getDir("cache", Context.MODE_PRIVATE).getPath();
		localWebSettings.setAppCachePath(appCaceDir);
		localWebSettings.setAppCacheMaxSize(5 * 1024 * 1024);

		// user-agent 传递给服务器需要的User-Agent
		String ua = localWebSettings.getUserAgentString();
		PackageManager pm = mContext.getPackageManager();
		PackageInfo pi = null;
		try {
			pi = pm.getPackageInfo(mContext.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String versionName = pi.versionName;
		String terminal_code = AppApplication.getInstance().getTerminal_code();

		localWebSettings.setUserAgentString(ua + " ebox/" + versionName
				+ " terminal/" + terminal_code);

		localWebSettings.setDisplayZoomControls(false);
		this.mWebView.setOverScrollMode(2);
		this.mWebView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView paramAnonymousWebView,
					String paramAnonymousString) {
				// mTitle.setText(paramAnonymousWebView.getTitle());
				if (!localWebSettings.getLoadsImagesAutomatically()) {
					localWebSettings.setLoadsImagesAutomatically(true);
				}
			}

			public void onPageStarted(WebView paramAnonymousWebView,
					String paramAnonymousString, Bitmap paramAnonymousBitmap) {
				super.onPageStarted(paramAnonymousWebView,
						paramAnonymousString, paramAnonymousBitmap);
			}

			public void onReceivedError(WebView paramAnonymousWebView,
					int paramAnonymousInt, String paramAnonymousString1,
					String paramAnonymousString2) {
				super.onReceivedError(paramAnonymousWebView, paramAnonymousInt,
						paramAnonymousString1, paramAnonymousString2);
			}

			public boolean shouldOverrideUrlLoading(
					WebView paramAnonymousWebView, String paramAnonymousString) {
				Uri localUri = Uri.parse(paramAnonymousString);
				String str = localUri.getScheme();
				if ((TextUtils.isEmpty(str)) || ("http".equals(str))
						|| ("https".equals(str))) {
					mWebView.loadUrl(paramAnonymousString);
					return false;
				}
				return true;
			}
		});

		mWebView.loadUrl(mUrl);
//		 mWebView.loadUrl("http://www.baidu.com");
	}

	@Override
	public void onBackPressed() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
			return;
		}
		finish();
	}
}
