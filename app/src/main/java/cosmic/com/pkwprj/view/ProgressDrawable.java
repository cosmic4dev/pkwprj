package cosmic.com.progressbartest;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.HashMap;

public class ProgressDrawable extends Drawable {

    final String TAG = "드러블";

    private static final int NUM_SEGMENTS = 18;
    private final int mForeground;
    private final int mBackground;
    private final Paint mPaint = new Paint();
    private final RectF mSegment = new RectF();

    private  HashMap map;

    public ProgressDrawable(int fgColor, int bgColor, HashMap hashMap) {
        mForeground = fgColor;
        mBackground = bgColor;
        map=hashMap;
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {

        //들어온 map  key
        Log.d( TAG,"들어온 맵사이즈->"+map.size() );  //4개 전체가 넘어옴. (추후 필터걸친 수만 나오게 할것)



        float level = getLevel() / 10000f;
        Rect b = getBounds();
        float gapWidth = b.height() / 2f;
        float segmentWidth = (b.width() - (NUM_SEGMENTS - 1) * gapWidth) / NUM_SEGMENTS;
        mSegment.set(0, 0, segmentWidth, b.height());


        for(int i=0; i<NUM_SEGMENTS; i++) {
            float loLevel = i / (float) NUM_SEGMENTS;
            float hiLevel = (i + 1) / (float) NUM_SEGMENTS;
//            if (loLevel <= level && level <= hiLevel) {


               if (map.get( i ) != null) {
                    Log.d( "TAG", "예약:" + i ); //마지막 예약만 찍힘
//                    if (loLevel <= level && level <= hiLevel) {/.
                    float middle = mSegment.left + NUM_SEGMENTS * segmentWidth * (level - loLevel);
                    mPaint.setColor( mBackground );
                    canvas.drawRect( mSegment.left, mSegment.top, mSegment.right, mSegment.bottom, mPaint );
//                    canvas.drawRect( mSegment.left, mSegment.top, middle, mSegment.bottom, mPaint );
//                    canvas.drawRect( middle, mSegment.top, mSegment.right, mSegment.bottom, mPaint );
                } else {
                    Log.d( "TAG", "빈타임:" + i );//마지막 예약만 찍힘
                    mPaint.setColor( mForeground );
                    canvas.drawRect( mSegment.left, mSegment.top, mSegment.right, mSegment.bottom, mPaint );
//                    canvas.drawRect( mSegment, mPaint );
                }

                mSegment.offset( mSegment.width() + gapWidth, 0 ); //왜 마지막것만 그려지는가.
            }

//            }


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
