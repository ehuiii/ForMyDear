package com.cookandroid.formydear

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class GuardianmainActivity : AppCompatActivity() {

    private lateinit var btnEdit: Button
    lateinit var btnBack: Button
    private lateinit var tvName: TextView
    private lateinit var tvChildName: TextView
    private lateinit var tvAge: TextView
    private lateinit var tvGender: TextView
    private lateinit var btnCategory: Button
    private lateinit var btnFaq: Button
    private lateinit var ivProfile: ImageView

    //파이어베이스에서 인스턴스 가져오기
    private var mFirebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Firebase")
    private var storage: FirebaseStorage? = FirebaseStorage.getInstance()

    // context
    private lateinit var activitys: Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guardianmain)

        btnEdit = findViewById(R.id.btnEdit)
        btnBack = findViewById(R.id.btnBack)
        tvName = findViewById(R.id.tvName)
        tvChildName = findViewById(R.id.tvChildName)
        tvAge = findViewById(R.id.tvAge)
        tvGender = findViewById(R.id.tvGenderInfo)
        btnCategory = findViewById(R.id.btnCategory)
        btnFaq = findViewById(R.id.btnFaq)
        ivProfile = findViewById(R.id.ivProfile)

        val mFirebaseUser : FirebaseUser? = mFirebaseAuth?.currentUser
        val userId:String = mFirebaseUser!!.uid

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ForMyDear")
                .child("UserAccount").child(userId)

        mFirebaseAuth = FirebaseAuth.getInstance()

        mDatabaseRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            //데이터 스냅샷으로 받아오기
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.value ==null){ // 널이면 아무것도하지마

                }
                else {
                    var user: UserAccount? = snapshot.getValue(UserAccount::class.java)

                    tvChildName.text = "${user?.userChildName}"
                    tvName.text = "${user?.userName}"
                    tvGender.text = "${user?.userChildGender}"
                    tvAge.text = "${user?.userChildAge}"

                    var cropOptions : RequestOptions = RequestOptions()
                    // 사진 url 추가 후 load하는 코드
                    if (user!!.userPhotoUri == "") {
                        ivProfile.setImageResource(R.drawable.man)

                    } else { // userPhotoUri가 있으면 그 사진 로드하기
                        Glide.with(applicationContext)
                                .load(user!!.userPhotoUri)
                                .apply(cropOptions.optionalCircleCrop())
                                .into(ivProfile)
                    }
                }
            }
        })

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