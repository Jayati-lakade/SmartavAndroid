package woxi.cvs.customwidgets;

import woxi.cvs.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.LruCache;
import android.widget.EditText;

public class EditText_Roboto extends EditText {

	private static LruCache<String, Typeface> sTypefaceCache = new LruCache<String, Typeface>(12);
	
	public EditText_Roboto(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.editTextViewOptions, 0, 0);
        
        try {
            String typefaceName = a.getString(R.styleable.editTextViewOptions_etfontName);
            Log.i("", "@@@@@@@@@ et typefaceName ::: " + typefaceName);
            
//            if (!isInEditMode()) {
            	Typeface typeface = null;
            	
				if(sTypefaceCache.get(typefaceName) != null)
            		 typeface = sTypefaceCache.get(typefaceName);
                
                if (typeface == null) {
                    typeface = Typeface.createFromAsset(context.getAssets(),
                            String.format("%s.ttf", typefaceName));
                    
                    // Cache the Typeface object
                    sTypefaceCache.put(typefaceName, typeface);
                }
                setTypeface(typeface);
//                setTextColor(R.styleable.editTextViewOptions_etfontColor);
                
                // Note: This flag is required for proper typeface rendering
                setPaintFlags(getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
//            }
        } finally {
            a.recycle();
        }
	}

}
