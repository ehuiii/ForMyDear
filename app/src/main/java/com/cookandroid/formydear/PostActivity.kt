package com.cookandroid.formydear

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
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
    lateinit var btnEdit : TextView
    lateinit var tvContent : TextView
    lateinit var tvHitsNum : TextView
    lateinit var ivPhoto : ImageView
    lateinit var ivStar : ImageView
    lateinit var btnBack : Button
    lateinit var btnSound : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //변수와 위젯 id 연결
        tvTitle = findViewById(R.id.tvTitle)
        tvContent = findViewById(R.id.tvContent)
        btnEdit = findViewById(R.id.btnEdit)
        btnBack = findViewById(R.id.btnBack)
        btnSound = findViewById(R.id.btnSound)
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
        var postAudioUri: String? = intent.getStringExtra("postAudioUri")

        //화면에 받아온 값 출력
        tvTitle.setText(title.toString())
        tvContent.setText(content.toString())


        if (postPhotoUri == null) {
            ivPhoto.setImageResource(R.drawable.man)
        } else {
            var cropOptions: RequestOptions = RequestOptions()
            Glide.with(applicationContext)
                    .load(postPhotoUri)
                    .apply(cropOptions.centerCrop())
                    .into(ivPhoto)
        }

        btnSound.setOnClickListener{
            val url = postAudioUri // your URL here
            val mediaPlayer: MediaPlayer? = MediaPlayer().apply {
                setAudioStreamType(AudioManager.STREAM_MUSIC)
                setDataSource(url)
                prepare() // might take long! (for buffering, etc)
                start()
            }

        }

        btnBack.setOnClickListener{
            finish()
        }


    }
}