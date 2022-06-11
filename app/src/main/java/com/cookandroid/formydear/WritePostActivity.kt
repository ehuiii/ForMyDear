package com.cookandroid.formydear

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
//import com.google.firebase.database.R
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.text.SimpleDateFormat
import java.util.*
import java.io.File

class WritePostActivity: AppCompatActivity()  {
    lateinit var btnBack: Button

    lateinit var photoButton: Button
    lateinit var soundButton: Button
    lateinit var btnEnd: Button

    lateinit var edtTitle: EditText
    lateinit var edtContent: EditText
    lateinit var ivPhoto: ImageView

    var imgUrl : String = ""

    private var mFirebaseAuth: FirebaseAuth? = null //파이어베이스 인증
    private lateinit var mDatabaseRef: DatabaseReference //실시간 데이터베이스
    private lateinit var fbStorage: FirebaseStorage
    private lateinit var storageRef: StorageReference
    private var GALLEY_CODE : Int = 10

    var timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        var intent : Intent = getIntent()

        var selectedItem : String? = intent.getStringExtra("SELECTED_ITEM")

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        //파이어베이스 계정, 리얼타임 데이터베이스
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ForMyDear")
        fbStorage = FirebaseStorage.getInstance()

        btnBack = findViewById(R.id.btnBack)
        photoButton = findViewById(R.id.photoButton)
        soundButton = findViewById(R.id.soundButton)
        btnEnd = findViewById(R.id.btnEnd)
        edtTitle = findViewById(R.id.edtTitle)
        edtContent = findViewById(R.id.edtContent)
        ivPhoto = findViewById(R.id.ivPhoto)

        photoButton.setOnClickListener {
            //앨범 열기
            var intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)

            startActivityForResult(intent, GALLEY_CODE)
        }

        mDatabaseRef.child("UserAccount").child("${mFirebaseAuth!!.currentUser!!.uid}")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        //파이어베이스의 데이터를 가져옴
                        var user: UserAccount? = snapshot.getValue(UserAccount::class.java)
                        Log.d("택", "${user!!.userId.toString()}")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("Tag", "Failed")
                    }
                })

        btnEnd.setOnClickListener {
            try {
                var storageReference : StorageReference = fbStorage.getReference()

                var file : Uri = Uri.fromFile(File(imgUrl))
                var riversRef : StorageReference = storageReference.child("images/"+file.lastPathSegment)
                var uploadTask : UploadTask = riversRef.putFile(file)

                var urlTask : Task<Uri> = uploadTask.continueWithTask(Continuation {
                    if(!it.isSuccessful){
                        it.exception
                    }
                    riversRef.downloadUrl
                }).addOnCompleteListener {
                    if(it.isSuccessful)
                    {
                        var downloadUrl : Uri? = it.result

                        val hashMap : HashMap<String, String> = HashMap()

                        var strTitle: String = edtTitle.text.toString()
                        var strContent = edtContent.text.toString()

                        hashMap.put("IMGURL", downloadUrl.toString())
                        hashMap.put("uid", mFirebaseAuth!!.currentUser!!.uid)
                        hashMap.put("postTitle", strTitle)
                        hashMap.put("postContent", strContent)
                        hashMap.put("timestamp", timestamp)

                        mDatabaseRef.ref.child("PostData").child("${mFirebaseAuth!!.currentUser!!.uid}").child("${selectedItem}").push().setValue(hashMap)
                                .addOnCompleteListener {
                                    if(it.isSuccessful){
                                        Toast.makeText(this, "업로드", Toast.LENGTH_SHORT).show()
                                    }
                                }

                        Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, PostListActivity::class.java)
                        intent.putExtra("SELECTED_ITEM", selectedItem)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener {

                    val hashMap : HashMap<String, String> = HashMap()

                    var strTitle: String = edtTitle.text.toString()
                    var strContent = edtContent.text.toString()

                    hashMap.put("uid", mFirebaseAuth!!.currentUser!!.uid)
                    hashMap.put("postTitle", strTitle)
                    hashMap.put("postContent", strContent)
                    hashMap.put("timestamp", timestamp)

                    mDatabaseRef.ref.child("PostData").child("${mFirebaseAuth!!.currentUser!!.uid}").child("${selectedItem}").push().setValue(hashMap)

                    Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, PostListActivity::class.java)
                    intent.putExtra("SELECTED_ITEM", selectedItem)
                    startActivity(intent)
                    finish()

                }
            }catch (e : NullPointerException){
                Toast.makeText(this, "이미지 선택 안함", Toast.LENGTH_SHORT).show();
            }
        }

        //뒤로가기 버튼
        btnBack.setOnClickListener {
            onBackPressed()
        }
    }




    //이미지 받아와서 화면에 출력
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == GALLEY_CODE) {
            imgUrl = getRealPathFromUri(data!!.data)
            var cropOptions : RequestOptions = RequestOptions()
            Glide.with(applicationContext)
                    .load(imgUrl)
                    .into(ivPhoto)

            super.onActivityResult(requestCode, resultCode, data)
        }
    }



    //절대경로 받아오기
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