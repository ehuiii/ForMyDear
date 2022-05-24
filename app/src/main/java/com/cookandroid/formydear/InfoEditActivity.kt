package com.cookandroid.formydear

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.ArrayAdapter.createFromResource
import androidx.appcompat.app.AppCompatActivity


class InfoEditActivity : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var edtRelation: EditText
    lateinit var edtAge: TextView
    lateinit var btnEditCom: Button
    lateinit var btnEdtimg: Button
    lateinit var btnBoy: Button
    lateinit var btnGirl: Button
    lateinit var btnBack : Button
    lateinit var imgMan: ImageView
    lateinit var spinnerAge: Spinner

    private val Gallery = 1


    //키보드 내려가게 하기
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infoedit)

        imgMan = findViewById(R.id.imgMan)
        edtName = findViewById(R.id.edtName)
        edtRelation = findViewById(R.id.edtRelation)
        btnBack = findViewById(R.id.btnBack)
        btnEditCom = findViewById(R.id.btnEditCom)
        btnBoy = findViewById(R.id.btnBoy)
        btnGirl = findViewById(R.id.btnGirl)
        btnEdtimg = findViewById(R.id.btnEdtimg)
        spinnerAge = findViewById(R.id.spinnerAge)
        edtAge = findViewById(R.id.edtAge)

        //뒤로 가기
        btnBack.setOnClickListener{
            finish()
        }



        // 사진 편집 - 갤러리 들어가기
        btnEdtimg.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, Gallery)
        }

        // 나이
        // 스피너 array 불러오기 - values > agearray
        spinnerAge.adapter =
                createFromResource(this, R.array.age_list, android.R.layout.simple_spinner_item)
        // 나이 TextView
        spinnerAge.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long

            ) {
                when (position) {
                    //미선택
                    0 -> {
                        edtAge.text = "선택안함"
                    }
                    1 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    2 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    3 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    4 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    5 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    6 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    7 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    8 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    9 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    10 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    11 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    12 -> {
                        edtAge.text = spinnerAge.selectedItem.toString()
                    }
                    //일치하는 게 없는 경우
                    else -> {
                        edtAge.text = "선택안함"
                    }
                }
            }
        }
        // 성별
        btnBoy.setOnClickListener {
            btnBoy.isSelected = btnBoy.isSelected != true
            btnGirl.isSelected = false
        }
        btnGirl.setOnClickListener {
            btnGirl.isSelected = btnGirl.isSelected != true
            btnBoy.isSelected = false
        }


        // 편집 완료 버튼 - 데이터 전달 > GuardianmainActivity
        btnEditCom.setOnClickListener {
            val nextIntent = Intent(this, GuardianmainActivity::class.java)
            val strName = edtName.text.toString()
            val spiAge = spinnerAge.selectedItem
            //val selSex =
            intent.putExtra("name", strName)
            intent.putExtra("age", spiAge.toString())
            startActivity(nextIntent)
        }
    }
}

