package com.cookandroid.formydear

import android.content.Intent
import android.os.Bundle
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
    lateinit var tvContent : TextView
    lateinit var tvHitsNum : TextView
    lateinit var ivPhoto : ImageView
    lateinit var ivStar : ImageView
    lateinit var btnBack : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //변수와 위젯 id 연결
        tvTitle = findViewById(R.id.tvTitle)
        tvContent = findViewById(R.id.tvContent)
        btnEdit = findViewById(R.id.btnEdit)
        btnBack = findViewById(R.id.btnBack)
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
        var uid: String? = intent.getStringExtra("UID")
        var title: String? = intent.getStringExtra("postTitle")
        var content: String? = intent.getStringExtra("postContent")
        var postPhotoUri: String? = intent.getStringExtra("postPhotoUri")

        //화면에 받아온 값 출력
        tvTitle.setText(title.toString())
        tvContent.setText(content.toString())
        //tvHitsNum.setText(title.toString())
        //tvTitle.setText(intent.getStringExtra("postTitle"))
        //tvContent.setText(intent.getStringExtra("postContent"))
        //tvHitsNum.setText(intent.getStringExtra("HitsNum"))

        if (postPhotoUri == null) {
            ivPhoto.setImageResource(R.drawable.man)
        } else {
            var cropOptions: RequestOptions = RequestOptions()
            Glide.with(applicationContext)
                    .load(postPhotoUri)
                    .apply(cropOptions.centerCrop())
                    .into(ivPhoto)
        }

        btnBack.setOnClickListener{
            finish()
        }


    }
}