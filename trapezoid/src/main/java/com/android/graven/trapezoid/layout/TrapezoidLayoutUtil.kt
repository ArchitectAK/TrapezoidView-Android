package com.android.graven.trapezoid.layout


import android.content.Context
import android.support.annotation.IntDef
import android.util.AttributeSet
import com.android.graven.trapezoid.R


/**
 * @author Ankit Kumar (ankitdroiddeveloper@gmail.com) on 26/04/2018 (MM/DD/YYYY)
 */
class TrapezoidLayoutUtil(context: Context, attrs: AttributeSet?) {
    companion object{
        const val LEFT = 1
        const val RIGHT = 2
        const val BOTTOM = 4
        const val TOP = 8


        const val DIRECTION_LEFT = 1
        const val DIRECTION_RIGHT = 2

    }

    @kotlin.annotation.Retention
    @IntDef(LEFT, RIGHT, BOTTOM, TOP)
    annotation class Position

    @kotlin.annotation.Retention
    @IntDef(DIRECTION_LEFT, DIRECTION_RIGHT)
    annotation class Direction


    private var angle = 15f
    private var elevation: Float = 0.toFloat()
    private var position = BOTTOM
    private var direction = DIRECTION_LEFT
    private var handleMargins: Boolean = false


    fun getElevation(): Float {
        return elevation
    }

    fun setElevation(elevation: Float) {
        this.elevation = elevation
    }

    fun getAngle(): Float {
        return angle
    }

    fun setAngle(angle: Float) {
        this.angle = angle
    }

    fun isHandleMargins(): Boolean {
        return handleMargins
    }

    fun setHandleMargins(handleMargins: Boolean) {
        this.handleMargins = handleMargins
    }

    fun setPosition(position: Int) {
        this.position = position
    }

    fun getPosition(): Int {
        return position
    }

    fun setDirection(direction: Int) {
        this.direction = direction
    }

    fun getDirection(): Int {
        return direction
    }

    fun isDirectionLeft(): Boolean {
        return direction == DIRECTION_LEFT
    }

    init {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.TrapezoidLayout, 0, 0)
        try {
            angle = styledAttributes.getFloat(R.styleable.TrapezoidLayout_layout_angle, 0f)
            position = styledAttributes.getInt(R.styleable.TrapezoidLayout_layout_position, BOTTOM)
            handleMargins = styledAttributes.getBoolean(R.styleable.TrapezoidLayout_layout_margins, false)
            direction = styledAttributes.getInt(R.styleable.TrapezoidLayout_layout_direction, DIRECTION_LEFT)
        } finally {
            styledAttributes.recycle()
        }
    }
}