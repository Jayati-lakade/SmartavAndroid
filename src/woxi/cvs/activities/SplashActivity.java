package woxi.cvs.activities;

import java.util.Timer;
import java.util.TimerTask;

import woxi.cvs.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

	private static int SPLASH_DELAY = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.splash);

		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				Intent intent = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
		};

		Timer timer = new Timer();
		timer.schedule(timerTask, SPLASH_DELAY);

	}
}
