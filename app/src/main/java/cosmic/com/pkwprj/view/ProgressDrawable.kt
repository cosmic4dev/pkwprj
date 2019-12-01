package cosmic.com.pkwprj.view

import android.graphics.*
import android.graphics.drawable.Drawable
import cosmic.com.pkwprj.view.SecondActivity.convertedKey
import java.util.*

class ProgressDrawable(private val map: HashMap<*, *>, private val officeName: String) :
    Drawable() {

    private val OnBookColor = "#0078ff"
    private val OffBookColor = "#e8e8e8"
    private val vBarColor = "#cccccc"
    private val mPaint = Paint()
    private val mSegment = RectF()

    override fun onLevelChange(level: Int): Boolean {
        invalidateSelf()
        return true
    }

    override fun draw(canvas: Canvas) {

        val b = bounds
        val gapWidth = 0f
        val segmentWidth = (b.width() - (NUM_SEGMENTS - 1) * gapWidth) / NUM_SEGMENTS
        mSegment.set(0f, 0f, segmentWidth, b.height() * 0.6f)

        for (i in 0 until NUM_SEGMENTS) {

            val makeKey = i.toString() + officeName

            if (map[makeKey] != null) {

                mPaint.color = Color.parseColor(OffBookColor)
                canvas.drawRect(mSegment.left, 60f, mSegment.right, mSegment.bottom, mPaint)

            } else if (map[makeKey] == null && i >= convertedKey) {

                mPaint.color = Color.parseColor(OnBookColor)
                canvas.drawRect(mSegment.left, 40f, mSegment.right, mSegment.bottom, mPaint)

            } else {

                mPaint.color = Color.parseColor(OffBookColor)
                canvas.drawRect(mSegment.left, 60f, mSegment.right, mSegment.bottom, mPaint)

            }

            if (convertedKey == i) {

                val linePaint = Paint()
                linePaint.color = Color.parseColor(vBarColor)
                val lineWidth = 5f
                linePaint.strokeWidth = lineWidth
                canvas.drawLine(mSegment.left, 0f, mSegment.left, 60f, linePaint)
            }

            mSegment.offset(mSegment.width() + gapWidth, 0f)
        }

    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(cf: ColorFilter?) {}

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    companion object {

        private val NUM_SEGMENTS = 18
    }

}
