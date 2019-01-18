package com.m4coding.coolhub.business.base.widgets

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import com.chad.library.adapter.base.BaseQuickAdapter
import com.m4coding.business_base.R
import com.m4coding.coolhub.base.base.BaseApplication
import com.m4coding.coolhub.business.base.list.BaseListAdapter

/**
 * @author mochangsheng
 * @description
 */
abstract class BaseDownSelectDialog : PopupWindow() {

    protected lateinit var mRecyclerView: RecyclerView
    protected lateinit var mAdapter: BaseListAdapter<*, *>

    protected var mArrowImageView: ImageView? = null

    init {
        width = ViewGroup.LayoutParams.MATCH_PARENT
        height = ViewGroup.LayoutParams.WRAP_CONTENT

        isFocusable = true
        isOutsideTouchable = true
        // 实例化一个ColorDrawable颜色为半透明
        val dw = ColorDrawable(Color.TRANSPARENT)
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        setBackgroundDrawable(dw)
    }

    fun setOnItemClickListener(listner: BaseQuickAdapter.OnItemClickListener) {
        mAdapter.onItemClickListener = listner
    }

    protected abstract fun initAdapter(): BaseListAdapter<*, *>

    protected fun initRv() {
        mRecyclerView.layoutManager = LinearLayoutManager(contentView.context)
        mAdapter = initAdapter()
        mRecyclerView.adapter = mAdapter
        mRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {

            private val paint: Paint = Paint()
            private val bounds = Rect()
            private var space: Int = 0

            init {
                space = BaseApplication.getAppResources().getDimensionPixelOffset(R.dimen.px_1)
                paint.isAntiAlias = true
                paint.color = ContextCompat.getColor(BaseApplication.getContext(), R.color.divider_color)
            }

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {

                val position = parent.getChildAdapterPosition(view)

                if (position <= -1) {
                    return
                }

                outRect.bottom = space
            }

            override fun onDrawOver(canvas: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
                canvas?.save()
                val left: Int = parent?.paddingLeft ?: 0
                val right: Int = parent?.width?.minus((parent.paddingRight)) ?: 0
                canvas?.clipRect(left, parent?.paddingTop ?: 0, right,
                        parent?.height?.minus((parent.paddingBottom)) ?: 0)

                for (i in 0 until  (parent?.childCount ?: 0)) {
                    val child = parent?.getChildAt(i)
                    var position = parent?.getChildAdapterPosition(child)
                    if (position == mAdapter.data.size - 1) {
                        return
                    }
                    parent?.getDecoratedBoundsWithMargins(child, bounds)
                    val bottom = bounds.bottom + Math.round(ViewCompat.getTranslationY(child))
                    val top = bottom - space
                    canvas?.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
                }
                canvas?.restore()
            }
        })


        setOnDismissListener {
            mArrowImageView?.setImageResource(R.drawable.vc_ic_arrow_down)
        }
    }

    fun setArrowView(view: ImageView) {
        mArrowImageView = view
        mArrowImageView?.setImageResource(R.drawable.vc_ic_arrow_up)
    }

    fun show(anchorView: View) {
        mArrowImageView?.setImageResource(R.drawable.vc_ic_arrow_up)
        showAsDropDown(anchorView)
    }
}