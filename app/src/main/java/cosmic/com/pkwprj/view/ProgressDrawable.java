package cosmic.com.pkwprj.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

import java.util.HashMap;

import static cosmic.com.pkwprj.view.SecondActivity.convertedKey;

public class ProgressDrawable extends Drawable {

    final String TAG = "드러블";

    private static final int NUM_SEGMENTS = 18;
    private final String OnBookColor = "#0078ff";
    private final String OffBookColor = "#e8e8e8";
    private final String vBarColor = "#cccccc";
    private final Paint mPaint = new Paint();
    private final RectF mSegment = new RectF();
    private HashMap map;
    private String officeName;

    public ProgressDrawable(HashMap map, String officeName) {
        this.map = map;
        this.officeName = officeName;
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {

        Rect b = getBounds();
        float gapWidth = 0;
        float segmentWidth = (b.width() - (NUM_SEGMENTS - 1) * gapWidth) / NUM_SEGMENTS;
        mSegment.set(0, 0, segmentWidth, b.height() * 0.6f);

        for (int i = 0; i < NUM_SEGMENTS; i++) {

            String makeKey = i + officeName;

            if (map.get(makeKey) != null) {

                mPaint.setColor(Color.parseColor(OffBookColor));
                canvas.drawRect(mSegment.left, 60, mSegment.right, mSegment.bottom, mPaint);

            } else if (map.get(makeKey) == null && i >= convertedKey) {

                mPaint.setColor(Color.parseColor(OnBookColor));
                canvas.drawRect(mSegment.left, 40, mSegment.right, mSegment.bottom, mPaint);

            } else {

                mPaint.setColor(Color.parseColor(OffBookColor));
                canvas.drawRect(mSegment.left, 60, mSegment.right, mSegment.bottom, mPaint);

            }

            if (convertedKey == i) {

                Paint linePaint = new Paint();
                linePaint.setColor(Color.parseColor(vBarColor));
                final float lineWidth = 5f;
                linePaint.setStrokeWidth(lineWidth);
                canvas.drawLine(mSegment.left, 0, mSegment.left, 60, linePaint);
            }

            mSegment.offset(mSegment.width() + gapWidth, 0);
        }

    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}
