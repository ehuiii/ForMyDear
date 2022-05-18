package com.cookandroid.formydear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class ChildCategoryActivity : AppCompatActivity(){

    lateinit var backBtn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_category)

        backBtn = findViewById(R.id.btnBack)

        backBtn.setOnClickListener{
            finish()
        }
    }


}