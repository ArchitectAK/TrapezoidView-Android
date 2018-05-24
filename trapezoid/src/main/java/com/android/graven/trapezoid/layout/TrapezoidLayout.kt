package com.android.graven.trapezoid.layout

import android.annotation.TargetApi
import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.FrameLayout


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 26/04/2018 (MM/DD/YYYY)
 */
class TrapezoidLayout : FrameLayout {

    private val EPSILON = 0.5f

    private var settings: TrapezoidLayoutUtil? = null


    private var clipPath: Path? = null
    var outlinePath: Path? = null

    private var paint: Paint? = null

    private var defaultMargin_forPosition: Int = 0

    private var pdMode: PorterDuffXfermode? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }


    private fun init(context: Context, attrs: AttributeSet?) {
        settings = TrapezoidLayoutUtil(context, attrs)
        settings?.setElevation(ViewCompat.getElevation(this))

        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint?.color = Color.WHITE

        pdMode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun setBackgroundColor(color: Int) {
        paint?.color = color
        postInvalidate()
    }

    fun setPosition(@TrapezoidLayoutUtil.Position position: Int) {
        settings?.setPosition(position)
        postInvalidate()
    }

    fun setDirection(@TrapezoidLayoutUtil.Direction direction: Int) {
        settings?.setDirection(direction)
        postInvalidate()
    }

    fun setAngle(angle: Float) {
        settings?.setAngle(angle)
        calculateLayout()
        postInvalidate()
    }

    private var heightt: Int = 0

    private var widthh: Int = 0

    private fun calculateLayout() {
        if (settings == null) {
            return
        }
        heightt = measuredHeight
        widthh = measuredWidth
        if (width > 0 && height > 0) {

            val perpendicularHeight = (width * Math.tan(Math.toRadians(settings?.getAngle()?.toDouble()!!))).toFloat()

            clipPath = createClipPath(perpendicularHeight)
            outlinePath = createOutlinePath(perpendicularHeight)

            handleMargins(perpendicularHeight)

            settings?.getElevation()?.let { ViewCompat.setElevation(this, it) }

            //this needs to be fixed for 25.4.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && ViewCompat.getElevation(this) > 0f) {
                try {
                    outlineProvider = outlineProvider
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }

    private fun createClipPath(perpendicularHeight: Float): Path {
        val path = Path()
        when (settings?.getPosition()) {
            TrapezoidLayoutUtil.BOTTOM -> if (settings?.isDirectionLeft()!!) {
                path.moveTo(width - paddingRight + EPSILON, height - perpendicularHeight - paddingBottom + EPSILON)
                path.lineTo(width - paddingRight + EPSILON, height - paddingBottom + EPSILON)
                path.lineTo(paddingLeft - EPSILON, height - paddingBottom + EPSILON)
                path.close()
            } else {
                path.moveTo(width - paddingRight + EPSILON, height - paddingBottom + EPSILON)
                path.lineTo(paddingLeft - EPSILON, height - paddingBottom + EPSILON)
                path.lineTo(paddingLeft - EPSILON, height - perpendicularHeight - paddingBottom + EPSILON)
                path.close()
            }
            TrapezoidLayoutUtil.TOP -> if (settings?.isDirectionLeft()!!) {
                path.moveTo(width - paddingRight + EPSILON, paddingTop + perpendicularHeight - EPSILON)
                path.lineTo(paddingLeft - EPSILON, paddingTop - EPSILON)
                path.lineTo(width - paddingRight + EPSILON, paddingTop - EPSILON)
                path.close()
            } else {
                path.moveTo(width - paddingRight + EPSILON, paddingTop - EPSILON)
                path.lineTo(paddingLeft - EPSILON, paddingTop + perpendicularHeight - EPSILON)
                path.lineTo(paddingLeft - EPSILON, paddingTop - EPSILON)
                path.close()
            }
            TrapezoidLayoutUtil.RIGHT -> if (settings?.isDirectionLeft()!!) {
                path.moveTo(width - paddingRight + EPSILON, paddingTop - EPSILON)
                path.lineTo(width - paddingRight + EPSILON, height - paddingBottom + EPSILON)
                path.lineTo(width - perpendicularHeight - paddingRight + EPSILON, height - paddingBottom + EPSILON)
                path.close()
            } else {
                path.moveTo(width - perpendicularHeight - paddingRight - EPSILON, paddingTop - EPSILON)
                path.lineTo(width - paddingRight + EPSILON, paddingTop - EPSILON)
                path.lineTo(width - paddingRight + EPSILON, height - paddingBottom + EPSILON)
                path.close()
            }
            TrapezoidLayoutUtil.LEFT -> if (settings?.isDirectionLeft()!!) {
                path.moveTo(paddingLeft - EPSILON, paddingTop - EPSILON)
                path.lineTo(paddingLeft + perpendicularHeight + EPSILON, paddingTop - EPSILON)
                path.lineTo(paddingLeft - EPSILON, height - paddingBottom + EPSILON)
                path.close()
            } else {
                path.moveTo(paddingLeft - EPSILON, paddingTop - EPSILON)
                path.lineTo(paddingLeft + perpendicularHeight + EPSILON, height - paddingBottom + EPSILON)
                path.lineTo(paddingLeft - EPSILON, height - paddingBottom + EPSILON)
                path.close()
            }
        }
        return path
    }

    private fun createOutlinePath(perpendicularHeight: Float): Path {
        val path = Path()
        when (settings?.getPosition()) {

            TrapezoidLayoutUtil.BOTTOM -> if (settings?.isDirectionLeft()!!) {
                moveTo(paddingLeft, paddingRight)
                lineTo(width - paddingRight, paddingTop)
                lineTo(width - paddingRight, (height - perpendicularHeight - paddingBottom).toInt())
                lineTo(paddingLeft, height - paddingBottom)
                path.close()
            } else {
                moveTo(width - paddingRight, height - paddingBottom)
                lineTo(paddingLeft, (height - perpendicularHeight - paddingBottom).toInt())
                lineTo(paddingLeft, paddingTop)
                lineTo(width - paddingRight, paddingTop)
                path.close()
            }
            TrapezoidLayoutUtil.TOP -> if (settings?.isDirectionLeft()!!) {
                moveTo(width - paddingRight, height - paddingBottom)
                lineTo(width - paddingRight, (paddingTop + perpendicularHeight).toInt())
                lineTo(paddingLeft, paddingTop)
                lineTo(paddingLeft, height - paddingBottom)
                path.close()
            } else {
                moveTo(width - paddingRight, height - paddingBottom)
                lineTo(width - paddingRight, paddingTop)
                lineTo(paddingLeft, (paddingTop + perpendicularHeight).toInt())
                lineTo(paddingLeft, height - paddingBottom)
                path.close()
            }
            TrapezoidLayoutUtil.RIGHT -> if (settings?.isDirectionLeft()!!) {
                moveTo(paddingLeft, paddingTop)
                lineTo(width - paddingRight, paddingTop)
                lineTo((width - paddingRight - perpendicularHeight).toInt(), height - paddingBottom)
                lineTo(paddingLeft, height - paddingBottom)
                path.close()
            } else {
                moveTo(paddingLeft, paddingTop)
                lineTo((width - paddingRight - perpendicularHeight).toInt(), paddingTop)
                lineTo(width - paddingRight, height - paddingBottom)
                lineTo(paddingLeft, height - paddingBottom)
                path.close()
            }
            TrapezoidLayoutUtil.LEFT -> if (settings?.isDirectionLeft()!!) {
                moveTo((paddingLeft + perpendicularHeight).toInt(), paddingTop)
                lineTo(width - paddingRight, paddingTop)
                lineTo(width - paddingRight, height - paddingBottom)
                lineTo(paddingLeft, height - paddingBottom)
                path.close()
            } else {
                moveTo(paddingLeft, paddingTop)
                lineTo(width - paddingRight, paddingTop)
                lineTo(width - paddingRight, height - paddingBottom)
                lineTo((paddingLeft + perpendicularHeight).toInt(), height - paddingBottom)
                path.close()
            }
        }
        return path
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun getOutlineProvider(): ViewOutlineProvider {
        return object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setConvexPath(outlinePath)
            }
        }
    }

    private fun handleMargins(perpendicularHeight: Float) {
        if (settings?.isHandleMargins()!!) {
            val layoutParams = layoutParams
            if (layoutParams is ViewGroup.MarginLayoutParams) {

                when (settings?.getDirection()) {
                    TrapezoidLayoutUtil.BOTTOM -> {
                        defaultMargin_forPosition = 0
                        layoutParams.bottomMargin = (this.defaultMargin_forPosition - perpendicularHeight).toInt()
                    }
                    TrapezoidLayoutUtil.TOP -> {
                        defaultMargin_forPosition = 0
                        layoutParams.topMargin = (defaultMargin_forPosition - perpendicularHeight).toInt()
                    }
                }

                setLayoutParams(layoutParams)
            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            calculateLayout()
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (!isInEditMode) {
            val saveCount = canvas.saveLayer(0F, 0F, getWidth().toFloat(), getHeight().toFloat(), null, Canvas.ALL_SAVE_FLAG)

            super.dispatchDraw(canvas)

            paint?.xfermode = pdMode
            canvas.drawPath(clipPath, paint)

            canvas.restoreToCount(saveCount)
            paint?.xfermode = null
        } else {
            super.dispatchDraw(canvas)
        }
    }

}

private fun lineTo(a: Int, b: Int) {
    Path().lineTo(a.toFloat(), b.toFloat())
}

private fun moveTo(paramOne: Int, paramTwo: Int) {
    Path().moveTo(paramOne.toFloat(), paramTwo.toFloat())
}
