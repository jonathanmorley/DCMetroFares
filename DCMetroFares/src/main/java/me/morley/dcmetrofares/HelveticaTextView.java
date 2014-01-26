package me.morley.dcmetrofares;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Jonathan on 25/01/14.
 */
public class HelveticaTextView extends TextView {

    public HelveticaTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFont(context);
    }

    public HelveticaTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFont(context);
    }

    public HelveticaTextView(Context context) {
        super(context);
        setFont(context);
    }

    private void setFont(Context context) {
        if (!this.isInEditMode()) {
            Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/helvetica_bold.ttf");
            this.setTypeface(face);
        }
    }
}
