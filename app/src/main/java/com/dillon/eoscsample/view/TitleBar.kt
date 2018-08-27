package com.dillon.eoscsample.view

/**
 * Created by dillon on 2017/7/7.
 */

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.blankj.utilcode.util.SpanUtils
import java.util.*


class TitleBar : ViewGroup, View.OnClickListener {

    private var mLeftText: TextView? = null
    private var mRightLayout: LinearLayout? = null
    private var mCenterLayout: LinearLayout? = null
    private var mCenterText: TextView? = null
    private var mSubTitleText: TextView? = null
    private var mCustomCenterView: View? = null
    private var mDividerView: View? = null

    private var mImmersive = false//默认浸入式

    private var mScreenWidth: Int = 0
    private var mStatusBarHeight: Int = 0
    private var mActionPadding: Int = 0
    private var mOutPadding: Int = 0
    private var mActionTextColor: Int = 0
    private var mHeight: Int = 0

    /**
     * Returns the number of actions currently registered with the action bar.
     *
     * @return action count
     */
    val actionCount: Int
        get() = mRightLayout!!.childCount

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        if (mImmersive) {
            mStatusBarHeight = statusBarHeight
        }
        mActionPadding = dip2px(5)
        mOutPadding = dip2px(8)
        mHeight = dip2px(DEFAULT_TITLE_BAR_HEIGHT)
        initView(context)
    }

    private fun initView(context: Context) {
        mLeftText = TextView(context)
        mCenterLayout = LinearLayout(context)
        mRightLayout = LinearLayout(context)
        mDividerView = View(context)

        val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)

        mLeftText!!.textSize = DEFAULT_ACTION_TEXT_SIZE.toFloat()
        mLeftText!!.setSingleLine()
        mLeftText!!.gravity = Gravity.CENTER_VERTICAL
        mLeftText!!.setPadding(mOutPadding + mActionPadding, 0, mOutPadding, 0)

        mCenterText = TextView(context)
        mSubTitleText = TextView(context)
        mCenterLayout!!.addView(mCenterText)
        mCenterLayout!!.addView(mSubTitleText)

        mCenterLayout!!.gravity = Gravity.CENTER
        mCenterText!!.textSize = DEFAULT_MAIN_TEXT_SIZE.toFloat()
        mCenterText!!.setSingleLine()
        mCenterText!!.gravity = Gravity.CENTER
        mCenterText!!.ellipsize = TextUtils.TruncateAt.END

        mSubTitleText!!.textSize = DEFAULT_SUB_TEXT_SIZE.toFloat()
        mSubTitleText!!.setSingleLine()
        mSubTitleText!!.gravity = Gravity.CENTER
        mSubTitleText!!.ellipsize = TextUtils.TruncateAt.END

        mRightLayout!!.setPadding(mOutPadding, 0, mOutPadding, 0)

        addView(mLeftText, layoutParams)
        addView(mCenterLayout)
        addView(mRightLayout, layoutParams)
        addView(mDividerView, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1))
    }

    fun setImmersive(immersive: Boolean) {
        mImmersive = immersive
        if (mImmersive) {
            mStatusBarHeight = statusBarHeight
        } else {
            mStatusBarHeight = 0
        }
    }

    fun setHeight(height: Int) {
        mHeight = height
        setMeasuredDimension(measuredWidth, mHeight)
    }

    fun setLeftImageResource(resId: Int) {
        mLeftText!!.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0)
    }

    fun setLeftClickListener(l: View.OnClickListener) {
        mLeftText!!.setOnClickListener(l)
    }

    fun setLeftText(title: CharSequence) {
        mLeftText!!.text = title
    }

    fun setLeftText(resid: Int) {
        mLeftText!!.setText(resid)
    }

    fun setLeftTextSize(size: Float) {
        mLeftText!!.textSize = size
    }

    fun setLeftTextColor(color: Int) {
        mLeftText!!.setTextColor(color)
    }

    fun setLeftVisible(visible: Boolean) {
        mLeftText!!.visibility = if (visible) View.VISIBLE else View.GONE
    }

    fun setTitle(title: CharSequence) {
        var index = title.toString().indexOf("\n")
        if (index > 0) {
            setTitle(title.subSequence(0, index), title.subSequence(index + 1, title.length), LinearLayout.VERTICAL)
        } else {
            index = title.toString().indexOf("\t")
            if (index > 0) {
                setTitle(title.subSequence(0, index), "  " + title.subSequence(index + 1, title.length), LinearLayout.HORIZONTAL)
            } else {
                mCenterText!!.text = title
                mSubTitleText!!.visibility = View.GONE
            }
        }
    }

    private fun setTitle(title: CharSequence, subTitle: CharSequence, orientation: Int) {
        mCenterLayout!!.orientation = orientation
        mCenterText!!.text = title

        mSubTitleText!!.text = subTitle
        mSubTitleText!!.visibility = View.VISIBLE
    }

    fun setCenterClickListener(l: View.OnClickListener) {
        mCenterLayout!!.setOnClickListener(l)
    }

    fun setTitle(resid: Int) {
        setTitle(resources.getString(resid))
    }

    fun setTitleColor(resid: Int) {
        mCenterText!!.setTextColor(resid)
    }

    fun setTitleSize(size: Float) {
        mCenterText!!.textSize = size
    }

    fun setTitleBold(resid: Int, yesNo: Boolean) {
        if (yesNo) {
            mCenterText!!.text = SpanUtils().appendLine(resources.getString(resid)).setBold().create()
        } else {
            setTitle(resources.getString(resid))
        }
    }

    fun setTitleBold(text: String, yesNo: Boolean) {
        if (yesNo) {
            mCenterText!!.text = SpanUtils().appendLine(text).setBold().create()
        } else {
            setTitle(text)
        }
    }

    fun setTitleBackground(resid: Int) {
        mCenterText!!.setBackgroundResource(resid)
    }

    fun setSubTitleColor(resid: Int) {
        mSubTitleText!!.setTextColor(resid)
    }

    fun setSubTitleSize(size: Float) {
        mSubTitleText!!.textSize = size
    }

    fun setCustomTitle(titleView: View?) {
        if (titleView == null) {
            mCenterText!!.visibility = View.VISIBLE
            if (mCustomCenterView != null) {
                mCenterLayout!!.removeView(mCustomCenterView)
            }

        } else {
            if (mCustomCenterView != null) {
                mCenterLayout!!.removeView(mCustomCenterView)
            }
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            mCustomCenterView = titleView
            mCenterLayout!!.addView(titleView, layoutParams)
            mCenterText!!.visibility = View.GONE
        }
    }

    fun setDivider(drawable: Drawable) {
        mDividerView!!.setBackgroundDrawable(drawable)
    }

    fun setDividerColor(color: Int) {
        mDividerView!!.setBackgroundColor(color)
    }

    fun setDividerHeight(dividerHeight: Int) {
        mDividerView!!.layoutParams.height = dividerHeight
    }

    fun setActionTextColor(colorResId: Int) {
        mActionTextColor = colorResId
    }

    /**
     * Function to set a click listener for Title TextView
     *
     * @param listener the onClickListener
     */
    fun setOnTitleClickListener(listener: View.OnClickListener) {
        mCenterText!!.setOnClickListener(listener)
    }

    override fun onClick(view: View) {
        val tag = view.tag
        (tag as? Action)?.performAction(view)
    }

    /**
     * Adds a list of [Action]s.
     *
     * @param actionList the actions to add
     */
    fun addActions(actionList: ActionList) {
        val actions = actionList.size
        for (i in 0 until actions) {
            addAction(actionList[i])
        }
    }

    /**
     * Adds a new [Action].
     *
     * @param action the action to add
     */
    fun addAction(action: Action): View {
        val index = mRightLayout!!.childCount
        return addAction(action, index)
    }

    /**
     * Adds a new [Action] at the specified index.
     *
     * @param action the action to add
     * @param index  the position at which to add the action
     */
    fun addAction(action: Action, index: Int): View {
        val params = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT)
        val view = inflateAction(action)
        mRightLayout!!.addView(view, index, params)
        return view
    }

    /**
     * Removes all action views from this action bar
     */
    fun removeAllActions() {
        mRightLayout!!.removeAllViews()
    }

    /**
     * Remove a action from the action bar.
     *
     * @param index position of action to remove
     */
    fun removeActionAt(index: Int) {
        mRightLayout!!.removeViewAt(index)
    }

    /**
     * Remove a action from the action bar.
     *
     * @param action The action to remove
     */
    fun removeAction(action: Action) {
        val childCount = mRightLayout!!.childCount
        for (i in 0 until childCount) {
            val view = mRightLayout!!.getChildAt(i)
            if (view != null) {
                val tag = view.tag
                if (tag is Action && tag == action) {
                    mRightLayout!!.removeView(view)
                }
            }
        }
    }

    /**
     * Inflates a [View] with the given [Action].
     *
     * @param action the action to inflate
     * @return a view
     */
    private fun inflateAction(action: Action): View {
        var view: View? = null
        if (TextUtils.isEmpty(action.text)) {
            val img = ImageView(context)
            img.setImageResource(action.drawable)
            view = img
        } else {
            val text = TextView(context)
            text.gravity = Gravity.CENTER
            text.text = action.text
            text.textSize = DEFAULT_ACTION_TEXT_SIZE.toFloat()
            if (mActionTextColor != 0) {
                text.setTextColor(mActionTextColor)
            }
            view = text
        }

        view.setPadding(mActionPadding, 0, mActionPadding, 0)
        view.tag = action
        view.setOnClickListener(this)
        return view
    }

    fun getViewByAction(action: Action): View {
        return findViewWithTag(action)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val height: Int
        if (heightMode != View.MeasureSpec.EXACTLY) {
            height = mHeight + mStatusBarHeight
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(mHeight, View.MeasureSpec.EXACTLY)
        } else {
            height = View.MeasureSpec.getSize(heightMeasureSpec) + mStatusBarHeight
        }
        mScreenWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        measureChild(mLeftText, widthMeasureSpec, heightMeasureSpec)
        measureChild(mRightLayout, widthMeasureSpec, heightMeasureSpec)
        if (mLeftText!!.measuredWidth > mRightLayout!!.measuredWidth) {
            mCenterLayout!!.measure(
                    View.MeasureSpec.makeMeasureSpec(mScreenWidth - 2 * mLeftText!!.measuredWidth, View.MeasureSpec.EXACTLY), heightMeasureSpec)
        } else {
            mCenterLayout!!.measure(
                    View.MeasureSpec.makeMeasureSpec(mScreenWidth - 2 * mRightLayout!!.measuredWidth, View.MeasureSpec.EXACTLY), heightMeasureSpec)
        }
        measureChild(mDividerView, widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(View.MeasureSpec.getSize(widthMeasureSpec), height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mLeftText!!.layout(0, mStatusBarHeight, mLeftText!!.measuredWidth, mLeftText!!.measuredHeight + mStatusBarHeight)
        mRightLayout!!.layout(mScreenWidth - mRightLayout!!.measuredWidth, mStatusBarHeight,
                mScreenWidth, mRightLayout!!.measuredHeight + mStatusBarHeight)
        if (mLeftText!!.measuredWidth > mRightLayout!!.measuredWidth) {
            mCenterLayout!!.layout(mLeftText!!.measuredWidth, mStatusBarHeight,
                    mScreenWidth - mLeftText!!.measuredWidth, measuredHeight)
        } else {
            mCenterLayout!!.layout(mRightLayout!!.measuredWidth, mStatusBarHeight,
                    mScreenWidth - mRightLayout!!.measuredWidth, measuredHeight)
        }
        mDividerView!!.layout(0, measuredHeight - mDividerView!!.measuredHeight, measuredWidth, measuredHeight)
    }


    /**
     * A [LinkedList] that holds a list of [Action]s.
     */
    class ActionList : LinkedList<Action>()

    /**
     * Definition of an action that could be performed, along with a icon to
     * show.
     */
    interface Action {
        val text: String

        val drawable: Int

        fun performAction(view: View)
    }

    abstract class ImageAction(override val drawable: Int) : Action {

        override val text: String
            get() = ""
    }

    abstract class TextAction(override val text: String) : Action {

        override val drawable: Int
            get() = 0
    }

    companion object {
        private val DEFAULT_MAIN_TEXT_SIZE = 18
        private val DEFAULT_SUB_TEXT_SIZE = 12
        private val DEFAULT_ACTION_TEXT_SIZE = 15
        private val DEFAULT_TITLE_BAR_HEIGHT = 48

        private val STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height"

        fun dip2px(dpValue: Int): Int {
            val scale = Resources.getSystem().displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 计算状态栏高度高度
         * getStatusBarHeight
         *
         * @return
         */
        val statusBarHeight: Int
            get() = getInternalDimensionSize(Resources.getSystem(), STATUS_BAR_HEIGHT_RES_NAME)

        private fun getInternalDimensionSize(res: Resources, key: String): Int {
            var result = 0
            val resourceId = res.getIdentifier(key, "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
            return result
        }
    }

}