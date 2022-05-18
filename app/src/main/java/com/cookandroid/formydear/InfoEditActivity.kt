package com.cookandroid.formydear

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.ArrayAdapter.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide


class InfoEditActivity : AppCompatActivity() {

    private val TAG: String = "InfoEditActivity"

    lateinit var edtName: EditText
    lateinit var edtRelation: EditText
    lateinit var edtAge: TextView
    lateinit var btnEditCom: Button
    lateinit var btnEdtimg: Button
    lateinit var btnBoy: Button
    lateinit var btnGirl: Button
    lateinit var imgMan: ImageView
    lateinit var spinnerAge: Spinner

    private val Gallery = 1

    var imgUrl : String = ""



    //키보드 내려가게 하기
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infoedit)

        imgMan = findViewById<ImageView>(R.id.imgMan)
        edtName = findViewById<EditText>(R.id.edtName)
        edtRelation = findViewById<EditText>(R.id.edtRelation)
        btnEditCom = findViewById<Button>(R.id.btnEditCom)
        btnBoy = findViewById<Button>(R.id.btnBoy)
        btnGirl = findViewById<Button>(R.id.btnGirl)
        btnEdtimg = findViewById<Button>(R.id.btnEdtimg)
        spinnerAge = findViewById<Spinner>(R.id.spinnerAge)
        edtAge = findViewById<TextView>(R.id.edtAge)



        // 사진 편집 - 갤러리 들어가기
        btnEdtimg.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.setType("image/*")
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
                        edtAge.setText("선택안함")
                    }
                    1 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    2 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    2 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    3 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    4 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    5 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    6 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    7 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    8 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    9 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    10 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    11 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    12 -> {
                        edtAge.setText(spinnerAge.selectedItem.toString())
                    }
                    //일치하는 게 없는 경우
                    else -> {
                        edtAge.setText("선택안함")
                    }
                }

            }
        }

        // 성별
        btnBoy.setOnClickListener {
            btnBoy.isSelected = btnBoy.isSelected != true
        }
        btnGirl.setOnClickListener {
            btnGirl.isSelected = btnGirl.isSelected !=true
        }

        // 편집 완료 버튼 - 데이터 전달 > GuardianmainActivity
        btnEditCom.setOnClickListener {
            if (edtName.text.isEmpty()) {
                Toast.makeText(this, "이름을 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, "편집 완료", Toast.LENGTH_SHORT).show()
                val strName = edtName.text.toString()
                val spiAge = spinnerAge.selectedItem
                val intent = Intent(this, GuardianmainActivity::class.java)
                intent.putExtra("msgName", strName)
                intent.putExtra("age", spiAge.toString())
                startActivity(intent)
            }
        }
    }

    //프로필 사진
    //이미지 화면 출력
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Gallery) {
            if(resultCode == Activity.RESULT_OK){
                imgUrl = getRealPathFromUri(data!!.data)
                Glide.with(applicationContext)
                        .load(imgUrl)
                        .into(imgMan)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    //절대경로
    private fun getRealPathFromUri(uri: Uri?) : String{
        var proj : Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var cursorLoader : CursorLoader = CursorLoader(this,uri!!,proj,null,null,null)
        var cursor : Cursor? = cursorLoader.loadInBackground()
        var columIndex : Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        var url : String = cursor.getString(columIndex)
        cursor.close()
        return url
    }
}

