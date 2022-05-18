package com.cookandroid.formydear

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
//import com.google.firebase.database.R

class PostActivity: AppCompatActivity() {

    //파이어베이스
    private var mFirebaseAuth : FirebaseAuth? = null
    private lateinit var mDatabaseRef : DatabaseReference

    //위젯 연결할 변수 선언
    lateinit var tvTitle : TextView
    lateinit var btnEdit : Button
    lateinit var tvCharacteristic : TextView
    lateinit var tvHitsNum : TextView
    lateinit var ivPhoto : ImageView
    lateinit var ivStar : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //변수와 위젯 id 연결
        tvTitle = findViewById(R.id.tvTitle)
        tvCharacteristic = findViewById(R.id.tvCharacteristic)
        btnEdit = findViewById(R.id.btnEdit)
        tvHitsNum = findViewById(R.id.tvHitsNum)
        ivPhoto = findViewById(R.id.ivPhoto)
        ivStar = findViewById(R.id.ivStar)

        //파이어베이스 계정, 리얼타임 데이터베이스
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ForMyDear")


        //var user_nickname: String = ""
        //var post_profileImg: String = ""

        //intent로 값 받아옴
        var intent: Intent = getIntent()
        //var uid: String? = intent.getStringExtra("UID")
        var title: String? = intent.getStringExtra("Title")
        var img_url: String? = intent.getStringExtra("IMGURL")

        //화면에 받아온 값 출력
        tvTitle.setText(title.toString())
        tvCharacteristic.setText(intent.getStringExtra("Characteristic"))
        tvHitsNum.setText(intent.getStringExtra("HitsNum"))

        if (img_url == null) {
            ivPhoto.setImageResource(R.drawable.bg_5)
        } else {
            var cropOptions: RequestOptions = RequestOptions()
            Glide.with(applicationContext)
                    .load(img_url)
                    .apply(cropOptions.optionalCircleCrop())
                    .into(ivPhoto)
        }


    }
}