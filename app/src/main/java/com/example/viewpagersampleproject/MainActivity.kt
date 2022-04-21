package com.example.viewpagersampleproject

import android.animation.Animator
import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.*
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var bannerText: TextView

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: ArrayList<DataPage> = ArrayList<DataPage>().let {
            it.apply {
                add(DataPage(android.R.color.holo_red_light, "1 Page"))
                add(DataPage(android.R.color.holo_orange_dark, "2 Page"))
                add(DataPage(android.R.color.holo_green_dark, "3 Page"))
                add(DataPage(android.R.color.holo_blue_light, "4 Page"))
                add(DataPage(android.R.color.holo_blue_bright, "5 Page"))
                add(DataPage(android.R.color.black, "6 Page"))
            }
        }

//        viewPager = findViewById(R.id.view_pager)
//        viewPager.adapter = ViewPagerAdapter(this)
//        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = BannerAdapter(list)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        bannerText = findViewById(R.id.txt_current_banner)
        bannerText.text = getString(R.string.viewpager_banner, 1, list.size)

//        val bannerPosition = Int.MAX_VALUE / 2 - ceil(list.size.toDouble() / 2).toInt()
//        viewPager.setCurrentItem(bannerPosition, false)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bannerText.text =
                    getString(R.string.viewpager_banner, (position % list.size) + 1, list.size)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when (state) {
                    ViewPager2.SCROLL_STATE_IDLE -> {
                        if (!job.isActive) scrollJobCreate()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING -> {}
                    ViewPager2.SCROLL_STATE_SETTLING -> {}
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        scrollJobCreate()
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

//    override fun onBackPressed() {
//        if (viewPager.currentItem == 0) super.onBackPressed()
//        else viewPager.currentItem = viewPager.currentItem - 1
//    }

    private fun scrollJobCreate() {
        // Auto Scrolling
        job = GlobalScope.launch {
            delay(1500)
            withContext(Dispatchers.Main) {
//                viewPager.setCurrentItem(viewPager.currentItem + 1, true)
                viewPager.setCurrentItemWithDuration(viewPager.currentItem + 1, 2500)
            }
        }
    }

    // Custom animator for auto scrolling
    private fun ViewPager2.setCurrentItemWithDuration(
        item: Int,
        duration: Long,
        interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
        pagePxWidth: Int = width
    ) {
        val pxToDrag: Int = pagePxWidth * (item - currentItem)
        val animator = ValueAnimator.ofInt(0, pxToDrag)
        var previousValue = 0

        animator.addUpdateListener { valueAnimator ->
            val currentValue = valueAnimator.animatedValue as Int
            val currentPxToDrag = (currentValue - previousValue).toFloat()
            fakeDragBy(-currentPxToDrag)
            previousValue = currentValue
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) { beginFakeDrag() }

            override fun onAnimationEnd(p0: Animator?) { endFakeDrag() }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationRepeat(p0: Animator?) {}
        })

        animator.interpolator = interpolator
        animator.duration = duration
        animator.start()

    }
}