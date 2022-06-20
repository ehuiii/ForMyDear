package com.cookandroid.formydear

import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChildPostActivity: AppCompatActivity() {

    //파이어베이스
    private var mFirebaseAuth : FirebaseAuth? = null
    private lateinit var mDatabaseRef : DatabaseReference

    //위젯 연결할 변수 선언
    lateinit var tvChildCategory : TextView
    lateinit var tvChildTitle : TextView
    lateinit var ivPhoto : ImageView
    lateinit var btnBack : Button
    lateinit var btnSound : Button
    lateinit var arrayList: ArrayList<PostData>

    var timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_child_post)

        //변수와 위젯 id 연결
        tvChildCategory = findViewById(R.id.tvChildCategoryName)
        tvChildTitle = findViewById(R.id.tvChildTitle)
        btnBack = findViewById(R.id.btnBack)
        btnSound = findViewById(R.id.btnSound)
        ivPhoto = findViewById(R.id.ivPhoto)

        //파이어베이스 계정, 리얼타임 데이터베이스
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ForMyDear")

        arrayList = ArrayList<PostData>() //PostData 객체를 담을 ArrayList

        //intent로 값 받아옴
        var intent: Intent = getIntent()
        var uid: String? = intent.getStringExtra("UID")
        var title: String? = intent.getStringExtra("postTitle")
        var postPhotoUri: String? = intent.getStringExtra("postPhotoUri")
        var postAudioUri: String? = intent.getStringExtra("postAudioUri")
        var postId: String? = intent.getStringExtra("postId")
        var selectedItem: String? = intent.getStringExtra("categoryName")

        //화면에 받아온 값 출력
        tvChildCategory.setText(selectedItem.toString())
        tvChildTitle.setText(title.toString())


        Log.d("Tag", "${postId.toString()}")
        Log.d("sel", "${selectedItem.toString()}")



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