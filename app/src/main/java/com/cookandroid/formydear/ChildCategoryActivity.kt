package com.cookandroid.formydear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_category.*


class ChildCategoryActivity : AppCompatActivity(){

    lateinit var backBtn : Button

    private var categoryList = arrayListOf<CategoryData>(
        CategoryData("즐겨찾기", "imgstar"),
        CategoryData("인사•예절", "imgmanner"),
        CategoryData("감정", "imgemotion"),
        CategoryData("음식", "imgfood"),
        CategoryData("식기", "imgcutlery"),
        CategoryData("놀이", "imgplay"),
        CategoryData("아플 때", "imghurt"),
        CategoryData("신체", "imgleg"),
        CategoryData("가족•사람", "imgfamily"),
        CategoryData("장소", "imgplace"),
        CategoryData("숫자", "imgnumber"),
        CategoryData("돈", "imgmoney"),
        CategoryData("색깔", "imgcolor"),
        CategoryData("모양", "imgshape"),
        CategoryData("날씨", "imgweather"),
        CategoryData("계절", "imgseason"),
        CategoryData("동물", "imganimal"),
        CategoryData("위치", "imgposition"),
        CategoryData("도구", "imgpencil"),
        CategoryData("가구", "imgtv"),
        CategoryData("기타", "imgetc")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        backBtn = findViewById(R.id.btnBack)

        backBtn.setOnClickListener{
            finish()
        }

        val mAdapter = ChildCategoryAdapter(this, categoryList)
        categoryRv.adapter = mAdapter

        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        categoryRv.layoutManager = gridLayoutManager
    }


}