package com.cookandroid.formydear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_guardianmain.*

class GuardianmainActivity : AppCompatActivity() {

    lateinit var btnEdit: Button
    lateinit var btnBack: Button
    lateinit var tvName: TextView
    lateinit var btnCategory: Button

    private val TAG: String = "GuardianmainActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardianmain)

        btnEdit = findViewById<Button>(R.id.btnEdit)
        //btnBack = findViewById<Button>(R.id.backspace)
        tvName = findViewById<TextView>(R.id.tvName)

        btnCategory = findViewById<Button>(R.id.btnCategory)

        // 편집 버튼 - 아동 관리 편집화면 이동
        btnEdit.setOnClickListener {
            val intent = Intent(this, InfoEditActivity::class.java)
            startActivity(intent)
        }

        // 뒤로가기 버튼
        /*
        btnBack.setOnClickListener {
            finish()
        }*/
        // 카테고리 화면으로 이동
        btnCategory.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }

    }
    fun onDataChange(snapshot: DataSnapshot) {
        var intent = getIntent()
        var strName: String? = intent.getStringExtra("strName")
        tvName.text = strName
    }
}