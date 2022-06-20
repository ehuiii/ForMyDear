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
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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
    lateinit var arrayList: ArrayList<PostData>

    var timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //변수와 위젯 id 연결
        tvTitle = findViewById(R.id.tvChildCategoryName)
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

        arrayList = ArrayList<PostData>() //PostData 객체를 담을 ArrayList

        //intent로 값 받아옴
        var intent: Intent = getIntent()
        var uid: String? = intent.getStringExtra("UID")
        var title: String? = intent.getStringExtra("postTitle")
        var content: String? = intent.getStringExtra("postContent")
        var postPhotoUri: String? = intent.getStringExtra("postPhotoUri")
        var postAudioUri: String? = intent.getStringExtra("postAudioUri")
        var postId: String? = intent.getStringExtra("postId")
        var selectedItem: String? = intent.getStringExtra("categoryName")
        var star: Int? = intent.getIntExtra("star", 0)

        //화면에 받아온 값 출력
        tvTitle.setText(title.toString())
        tvContent.setText(content.toString())


        Log.d("Tag", "${postId.toString()}")
        Log.d("sel", "${selectedItem.toString()}")

        //별 1이 없는거, 0이 있는거
        if (star == 1) {
            ivStar.setImageResource(R.drawable.imgstar_empty)
        }else{
            ivStar.setImageResource(R.drawable.imgstar)
        }

        ivStar.setOnClickListener {
            if (star == 1) {
                ivStar.setImageResource(R.drawable.imgstar)
                star = 0
                starEvent(1)
                addStarEvent(1)
            }else{
                ivStar.setImageResource(R.drawable.imgstar_empty)
                star = 1
                starEvent(0)
                addStarEvent(0)
            }


        }


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

    private fun starEvent(starData: Int){
        Log.d("별", "스타이벤트 실행")
        var star:Int?

        var intent: Intent = getIntent()
        var selectedItem: String? = intent.getStringExtra("categoryName")
        var postId: String? = intent.getStringExtra("postId")

        if(starData==1){//별이 비어있는데 클릭된경우
            star=0
        }else{
            star=1
        }
        val hashMap: HashMap<String, Any> = HashMap()
        hashMap.put("star", star)
        mDatabaseRef.ref.child("PostData").child("${mFirebaseAuth!!.currentUser!!.uid}").child("${selectedItem}").child("${postId}").updateChildren(
            hashMap
        )


    }

    private fun addStarEvent(starData: Int){
        Log.d("별", "즐겨찾기에 추가")
        var star:Int?

        var intent: Intent = getIntent()
        var uid: String? = intent.getStringExtra("UID")
        var title: String? = intent.getStringExtra("postTitle")
        var content: String? = intent.getStringExtra("postContent")
        var postPhotoUri: String? = intent.getStringExtra("postPhotoUri")
        var postAudioUri: String? = intent.getStringExtra("postAudioUri")
        var postId: String? = intent.getStringExtra("postId")
        var selectedItem: String? = intent.getStringExtra("categoryName")
        //var star: Int? = intent.getIntExtra("star", 0)

        if(starData==1){//별이 비어있는데 클릭된경우
            star=0
            val hashMap: HashMap<String, Any> = HashMap()
            hashMap.put("star", star)
            hashMap.put("postId", postId.toString())
            hashMap.put("postPhotoUri", postPhotoUri.toString())
            hashMap.put("postAudioUri", postAudioUri.toString())
            hashMap.put("uid", mFirebaseAuth!!.currentUser!!.uid)
            hashMap.put("postTitle", title.toString())
            hashMap.put("postContent", content.toString())
            hashMap.put("categoryName", selectedItem.toString())
            hashMap.put("timestamp", timestamp)
            mDatabaseRef.ref.child("PostData").child("${mFirebaseAuth!!.currentUser!!.uid}").child("즐겨찾기").child("${postId}").setValue(hashMap)

        }else{
            star=1
            mDatabaseRef.ref.child("PostData").child("${mFirebaseAuth!!.currentUser!!.uid}").child("즐겨찾기").child("${postId}").removeValue()

        }


    }
}