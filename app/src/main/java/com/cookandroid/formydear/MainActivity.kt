package com.cookandroid.formydear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btnGuardian: Button // 보호자모드 버튼
    lateinit var btnChild: Button // 아동모드 버튼

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGuardian = findViewById<Button>(R.id.btnGuardian)
        btnChild = findViewById<Button>(R.id.btnChild)

        btnGuardian.setOnClickListener{
            //val intent = Intent(this, GuardianActivity::class.java)//보호자 액티비티
            startActivity(intent)
        }
        btnChild.setOnClickListener{
            val intent = Intent(this, ChildCategoryActivity::class.java)//아동 액티비티
            startActivity(intent)
        }

    }
}