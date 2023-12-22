package com.example.memoria

import android.app.ActionBar
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.allViews
import androidx.core.view.children
import androidx.core.view.marginTop
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val layout = LinearLayout(applicationContext)
        layout.orientation = LinearLayout.VERTICAL

        val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.weight = 1.toFloat() // единичный вес
        params.rightMargin = 10
        params.leftMargin = 10

        val n = 16

        val Views = ArrayList<ImageView>()
        for (i in 1..n) {
            Views.add( // вызываем конструктор для создания нового ImageView
                ImageView(applicationContext).apply {
                    setImageResource(R.drawable.head_up)
                    layoutParams = params
                    if (i <= n / 2) tag = i else tag = (i) - n / 2
                    setOnClickListener(colorListener)
                })
        }

        Views.shuffle()

        val rows = Array(4, { LinearLayout(applicationContext)})

        var count = 0
        for (view in Views) {
            val row: Int = count / 4
            rows[row].addView(view)
            count ++
        }
        for (row in rows) {
            layout.addView(row)
        }
        setContentView(layout)
    }

    suspend fun setBackgroundWithDelay(v: ImageView) {
        v.setImageResource(
            when (v.tag){
                1 -> R.drawable.img1
                2 -> R.drawable.img2
                3 -> R.drawable.img3
                4 -> R.drawable.img4
                5 -> R.drawable.img5
                6 -> R.drawable.img6
                7 -> R.drawable.img7
                8 -> R.drawable.img8
                else -> R.drawable.head_up
            }
        )
        delay(1000)
        v.visibility = View.INVISIBLE
        v.isClickable = false
    }

    suspend fun openCards() {

    }

    // обработчик нажатия на кнопку
    val colorListener = View.OnClickListener() {
        // запуск функции в фоновом потоке
        GlobalScope.launch (Dispatchers.Main)
        { setBackgroundWithDelay(it as ImageView) }
    }
}