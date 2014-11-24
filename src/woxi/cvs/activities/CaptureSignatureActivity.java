package woxi.cvs.activities;

import java.io.ByteArrayOutputStream;

import woxi.cvs.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class CaptureSignatureActivity extends Activity {
	SignatureView mSignature;
	Paint paint;
	LinearLayout mContent;
	Button clear, save;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signature_new);

		save = (Button) findViewById(R.id.save);
		save.setEnabled(false);
		clear = (Button) findViewById(R.id.clear);
		mContent = (LinearLayout) findViewById(R.id.mysignature);

		mSignature = new SignatureView(this, null);
		mContent.addView(mSignature);

		save.setOnClickListener(onButtonClick);
		clear.setOnClickListener(onButtonClick);
	}

	Button.OnClickListener onButtonClick = new Button.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == clear) {
				mSignature.clear();
			} else if (v == save) {
				mSignature.save();
			}
		}
	};

	public void save() {
		Bitmap returnedBitmap = Bitmap.createBitmap(mContent.getWidth(),
				mContent.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(returnedBitmap);
		Drawable bgDrawable = mContent.getBackground();
		if (bgDrawable != null)
			bgDrawable.draw(canvas);
		else
			canvas.drawColor(Color.WHITE);
		mContent.draw(canvas);

		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		returnedBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
		Intent intent = new Intent();
		intent.putExtra("byteArray", bs.toByteArray());
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

	public class SignatureView extends View {

		private static final float STROKE_WIDTH = 7f;
		/** Need to track this so the dirty region can accommodate the stroke. **/
		private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
		private Paint paint = new Paint();
		private Path path = new Path();
		/** Optimizes painting by invalidating the smallest possible area. */
		private float lastTouchX;
		private float lastTouchY;
		private final RectF dirtyRect = new RectF();

		public SignatureView(Context context, AttributeSet attrs) {
			super(context, attrs);
			paint.setAntiAlias(true);
			paint.setColor(Color.BLACK);
			paint.setStyle(Paint.Style.STROKE);
			paint.setStrokeJoin(Paint.Join.ROUND);
			paint.setStrokeWidth(STROKE_WIDTH);
		}

		public void save() {
			Bitmap returnedBitmap = Bitmap.createBitmap(mContent.getWidth(),mContent.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas canvas = new Canvas(returnedBitmap);
			Drawable bgDrawable = mContent.getBackground();
			
			if (bgDrawable != null)
				bgDrawable.draw(canvas);
			else
				canvas.drawColor(Color.WHITE);
			
			mContent.draw(canvas);
			ByteArrayOutputStream bs = new ByteArrayOutputStream();
			returnedBitmap.compress(Bitmap.CompressFormat.PNG, 50, bs);
			Intent intent = new Intent();
			intent.putExtra("byteArray", bs.toByteArray());
			setResult(1, intent);
			finish();
		}

		/** Erases the signature. */
		public void clear() {
			path.reset();
			invalidate(); // Repaints the entire view.			
		}

		@Override
		protected void onDraw(Canvas canvas) {
			canvas.drawPath(path, paint);
		}

		@Override
		public boolean onTouchEvent(MotionEvent event) {
			float eventX = event.getX();
			float eventY = event.getY();
			save.setEnabled(true);

			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:
				 path.moveTo(eventX, eventY);
				 lastTouchX = eventX;
				 lastTouchY = eventY;
				return true;

			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_UP:
				// Start tracking the dirty region.
				resetDirtyRect(eventX, eventY);

				/* When the hardware tracks events faster than they are
				 delivered, the event will contain a history of those skipped points.*/
				int historySize = event.getHistorySize();
				for (int i = 0; i < historySize; i++) {
					float historicalX = event.getHistoricalX(i);
					float historicalY = event.getHistoricalY(i);
					expandDirtyRect(historicalX, historicalY);
					path.lineTo(historicalX, historicalY);
				}

				// After replaying history, connect the line to the touch point.
				path.lineTo(eventX, eventY);
				break;

			default:
				Log.i("", "Ignored touch event: " + event.toString());
				return false;
			}

			// Include half the stroke width to avoid clipping.
			invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
					(int) (dirtyRect.top - HALF_STROKE_WIDTH),
					(int) (dirtyRect.right + HALF_STROKE_WIDTH),
					(int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

			lastTouchX = eventX;
			lastTouchY = eventY;

			return true;
		}

		/**
		 * Called when replaying history to ensure the dirty region includes all
		 * points.
		 */
		private void expandDirtyRect(float historicalX, float historicalY) {
			if (historicalX < dirtyRect.left) {
				dirtyRect.left = historicalX;
			} else if (historicalX > dirtyRect.right) {
				dirtyRect.right = historicalX;
			}
			if (historicalY < dirtyRect.top) {
				dirtyRect.top = historicalY;
			} else if (historicalY > dirtyRect.bottom) {
				dirtyRect.bottom = historicalY;
			}
		}

		/**
		 * Resets the dirty region when the motion event occurs.
		 */
		private void resetDirtyRect(float eventX, float eventY) {

			// The lastTouchX and lastTouchY were set when the ACTION_DOWN
			// motion event occurred.
			dirtyRect.left = Math.min(lastTouchX, eventX);
			dirtyRect.right = Math.max(lastTouchX, eventX);
			dirtyRect.top = Math.min(lastTouchY, eventY);
			dirtyRect.bottom = Math.max(lastTouchY, eventY);
		}
	}
}