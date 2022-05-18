package com.cookandroid.formydear

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.widget.Button
import android.widget.EditText
import java.io.IOException
import kotlinx.android.synthetic.main.activity_category.*

class CategoryActivity: AppCompatActivity() {

    lateinit var backBtn : Button

    private var categoryList = arrayListOf<CategoryData>(
        CategoryData("즐겨찾기", "imgstar"),
        CategoryData("인사/예절", "imgmanner"),
        CategoryData("감정", "imgemotion"),
        CategoryData("음식", "imgfood"),
        CategoryData("놀이", "imgplay"),
        CategoryData("전자기기", "imgtv"),
        CategoryData("장소", "imgplace")


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        backBtn = findViewById(R.id.btnBack)

        backBtn.setOnClickListener{
            finish()
        }

        val mAdapter = CategoryAdapter(this, categoryList)
        categoryRv.adapter = mAdapter

        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        categoryRv.layoutManager = gridLayoutManager
    }




}