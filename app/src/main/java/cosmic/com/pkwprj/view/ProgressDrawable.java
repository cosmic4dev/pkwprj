package cosmic.com.pkwprj.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import static cosmic.com.pkwprj.view.SecondActivity.convertedKey;

public class ProgressDrawable extends Drawable {

    final String TAG = "드러블";

    private static final int NUM_SEGMENTS = 18;
    private final String OnBookColor="#0078ff";
    private final String OffBookColor="#e8e8e8";
    private final Paint mPaint = new Paint();
    private final RectF mSegment = new RectF();
    //막대기
    private  Rect mSegmentEx=new Rect();

    private  HashMap map;
    private String officeName;
    private ArrayList<Integer> rList;

    int cTimePoint;


    public ProgressDrawable(HashMap map,String officeName) {
        this.map=map;
        this.officeName=officeName;
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {

        //막대기
        mSegmentEx = new Rect();
        // View의 사이즈 만큼 Rect를 그려 준다.
//        mSegmentEx.set(0,10,10,0);

        Paint paint = new Paint();

        canvas.drawRect(mSegmentEx, paint);

        //
        Set set=map.keySet();
        Iterator iterator=set.iterator();

        Rect b = getBounds();
        float gapWidth=0;
        float segmentWidth = (b.width() - (NUM_SEGMENTS - 1) * gapWidth) / NUM_SEGMENTS;
        mSegment.set(0, 0, segmentWidth, b.height());

        for(int i=0; i<NUM_SEGMENTS; i++) {

            String makeKey=i+officeName;
            Log.d(TAG,"스케쥴+오피스네임:"+makeKey);
            if(map.get(makeKey)!=null){
                    Log.d( "TAG", "예약됨.:" + i );
                    mPaint.setColor( Color.parseColor( OffBookColor ) );

                       canvas.drawRect(mSegment.left, mSegment.top, mSegment.right, mSegment.bottom, mPaint);

                } else if(map.get(makeKey )==null&&i>=convertedKey){
                    Log.d( "TAG", "예약가능포인트.:" + i );

                    if(convertedKey==i){
                        Log.d(TAG,"현재시간과 예약가능 중첩포인트: "+i);
                    }

                    mPaint.setColor( Color.parseColor( OnBookColor ) );
                    canvas.drawRect( mSegment.left, mSegment.top, mSegment.right, mSegment.bottom, mPaint );
                }else{
                   mPaint.setColor( Color.parseColor( OffBookColor ) );
               }

               mSegment.offset( mSegment.width() + gapWidth, 0 );
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
