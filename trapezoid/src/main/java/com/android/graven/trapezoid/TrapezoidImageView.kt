package com.android.graven.trapezoid


import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.AttrRes
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.widget.LinearLayout


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 08/04/2018 (MM/DD/YYYY)
 */
public class TrapezoidImageView : AppCompatImageView {
    //constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
    //(context, attrs, defStyleAttr)
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mDrawable: Drawable? = null
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mShader: BitmapShader? = null
    private var mBitmap: Bitmap? = null
    private var mMatrix: Matrix? = null
    private var mPosition: Int = 0
    private var incline: Int = 0


    private val TYPE_TOP = 0
    private val TYPE_MIDDLE = 1
    private val TYPE_BOTTOM = 2

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }


    private fun init(attrs: AttributeSet?, @AttrRes defStyleAttr: Int) {
        mPaint.style = Paint.Style.FILL
        mDrawable = drawable

        val array = context.theme.obtainStyledAttributes(attrs, R.styleable.TrapezoidImageView, defStyleAttr, 0)
        mPosition = array.getInt(R.styleable.TrapezoidImageView_position, 0)
        incline = array.getDimensionPixelSize(R.styleable.TrapezoidImageView_incline, 0)
        array.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth
        mHeight = measuredHeight
    }

    override fun onDraw(canvas: Canvas?) {
        if (mDrawable == null) return
        else {
            initBitmapShader()
            when (mPosition) {
                TYPE_TOP -> canvasTop(canvas!!)
                TYPE_MIDDLE -> {
                    canvasMiddle(canvas!!)
//                canvas?.let { canvasMiddle(it) }
                    setMargin()
                }
                TYPE_BOTTOM -> {
                    canvasBottom(canvas!!)
//                canvas?.let { canvasBottom(it) }
                    setMargin()
                }
            }
        }
    }

    private fun setMargin() {
        val params = LinearLayout.LayoutParams(measuredWidth, measuredHeight)
        params.setMargins(0, -incline / 6 * 5, 0, 0)
        layoutParams = params
        requestLayout()
    }

    private fun initBitmapShader() {
        mShader = BitmapShader(getSrcBitmap(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val scale = Math.max(width * 1.0f / getSrcBitmap().width, height * 1.0f / getSrcBitmap().height)
        mMatrix = Matrix()
        mMatrix?.setScale(scale, scale)
        mShader?.setLocalMatrix(mMatrix)
        mPaint?.shader = mShader
    }

    private fun canvasTop(canvas: Canvas) {
        canvas.drawPath(getTopPath(), mPaint)
    }

    private fun canvasMiddle(canvas: Canvas) {
        canvas.drawPath(getMiddlePath(), mPaint)
    }

    private fun canvasBottom(canvas: Canvas) {
        canvas.drawPath(getBottomPath(), mPaint)
    }

    private fun getTopPath(): Path {
        val topPath = Path()
        topPath.moveTo(0F, 0F)
        topPath.lineTo(mWidth.toFloat(), 0F)
        topPath.lineTo(mWidth.toFloat(), mHeight.toFloat())
        topPath.lineTo(0F, (mHeight - incline).toFloat())
        topPath.close()
        return topPath
    }

    private fun getMiddlePath(): Path {
        val topPath = Path()
        topPath.moveTo(0F, 0f)
        topPath.lineTo(mWidth.toFloat(), incline.toFloat())
        topPath.lineTo(mWidth.toFloat(), (mHeight - incline).toFloat())
        topPath.lineTo(0F, mHeight.toFloat())
        topPath.close()

        return topPath
    }

    private fun getBottomPath(): Path {
        val topPath = Path()
        topPath.moveTo(0F, incline.toFloat())
        topPath.lineTo(mWidth.toFloat(), 0F)
        topPath.lineTo(mWidth.toFloat(), mHeight.toFloat())
        topPath.lineTo(0F, mHeight.toFloat())
        topPath.close()
        return topPath

    }

    private fun getSrcBitmap(): Bitmap {
        if (mDrawable is BitmapDrawable) {
            return (mDrawable as BitmapDrawable).bitmap
        }
        val bitmapWidth = mDrawable?.intrinsicWidth
        val bitmapHeight = mDrawable?.intrinsicHeight

        mBitmap = bitmapWidth?.let { bitmapHeight?.let { it1 -> Bitmap.createBitmap(it, it1, Bitmap.Config.ARGB_8888) } }
        val canvasBitmap = Canvas(mBitmap)
        bitmapWidth?.let { bitmapHeight?.let { it1 -> mDrawable?.setBounds(0, 0, it, it1) } }
        mDrawable?.draw(canvasBitmap)
        return mBitmap as Bitmap
    }


    fun setDrawable(drawable: Drawable) {
        mDrawable = drawable
    }

}