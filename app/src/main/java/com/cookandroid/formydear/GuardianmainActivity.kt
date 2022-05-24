package com.cookandroid.formydear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class GuardianmainActivity : AppCompatActivity() {

    private lateinit var btnEdit: Button
    lateinit var btnBack: Button
    private lateinit var tvName: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvSex: TextView
    private lateinit var btnCategory: Button
    private lateinit var btnFaq: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardianmain)

        btnEdit = findViewById(R.id.btnEdit)
        btnBack = findViewById(R.id.btnBack)
        tvName = findViewById(R.id.tvName)
        tvAge = findViewById(R.id.tvAge)
        tvSex = findViewById(R.id.tvSex)
        btnCategory = findViewById(R.id.btnCategory)
        btnFaq = findViewById(R.id.btnFaq)


        //데이터 받아오기
        val intent = intent
        tvName.text = intent.getStringExtra("name")
        tvAge.text = intent.getStringExtra("age")


        //뒤로 가기
        btnBack.setOnClickListener{
            finish()
        }

        // 편집 버튼 - 아동 관리 편집화면 이동
        btnEdit.setOnClickListener {
            val intent1 = Intent(this, InfoEditActivity::class.java)
            startActivity(intent1)
        }

        // 카테고리 화면으로 이동
        btnCategory.setOnClickListener {
            val intent2 = Intent(this, CategoryActivity::class.java)
            startActivity(intent2)
        }

        // Faq 화면으로 이동
        btnFaq.setOnClickListener{
            val intent3 = Intent(this, FaqActivity::class.java)//아동 액티비티
            startActivity(intent3)
        }
    }
}