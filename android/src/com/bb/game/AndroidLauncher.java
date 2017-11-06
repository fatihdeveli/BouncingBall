package com.bb.game;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AndroidLauncher extends AndroidApplication {

	public static int bannerHeight;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//initialize(new BouncingBall(), config);

		RelativeLayout layout = new RelativeLayout(this);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);


		View gameView = initializeForView(new BouncingBall(), config);



		final AdView adView = new AdView(this);
		//adView.setAdSize(AdSize.BANNER);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		// my code: ca-app-pub-4530798094284578/6662760647
		// test code: ca-app-pub-3940256099942544/6300978111
		bannerHeight = adView.getHeight();

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		layout.addView(gameView);

		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(adView, adParams);

		setContentView(layout);

		adView.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				System.out.println("ad banner finished loading!");
				adView.setVisibility(View.GONE);
				adView.setVisibility(View.VISIBLE);
			}
		});



	}


}
