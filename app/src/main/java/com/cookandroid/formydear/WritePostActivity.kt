package com.cookandroid.formydear

import android.Manifest
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
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
/*
    // 이미지 선택시 상수 값
    var PICK_IMAGE_FROM_ALBUM = 0
    var photoUri: Uri? = null

    private var postId: String = ""
    private lateinit var categoryName:String

    private lateinit var mFirebaseAuth: FirebaseAuth // 파이어베이스 인증 처리
    private lateinit var mDatabaseRef: DatabaseReference // 실시간 데이터 베이스
    private var storage : FirebaseStorage? = FirebaseStorage.getInstance()

    private lateinit var mTvCategoryName: TextView//카테고리 이름
    private lateinit var mEtHits: EditText // 조회수
    private lateinit var mEtPostContent: EditText // 글 내용 characteristic
    private lateinit var mEtPostTitle: EditText // 제목
    private lateinit var mBtnPostEnd: Button // 게시글 업로드 버튼
    private lateinit var mBtnBack: Button // 닫기 가기 버튼
    private lateinit var ivPostData:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        //파이어베이스 계정, 리얼타임 데이터베이스
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ForMyDear")

        mTvCategoryName = findViewById(R.id.writePostText)
        //mEtHits = findViewById(R.id.etPostDate)
        mEtPostContent = findViewById(R.id.edtCharacteristic)
        mEtPostTitle = findViewById(R.id.edtTitle)
        mBtnPostEnd = findViewById(R.id.btnEnd)
        mBtnBack = findViewById(R.id.btnBack)
        //ivPostData = findViewById(R.id.ivPostCamera)

        categoryName = intent.getStringExtra("categoryName").toString()
        postId = mDatabaseRef.ref.child("UserPosts").child("${mFirebaseAuth!!.currentUser!!.uid}").push().key.toString()

        //없애면 될 듯..?
        mDatabaseRef.child("UserAccount").child("${mFirebaseAuth?.currentUser!!.uid}").child(categoryName).addValueEventListener(object :
                ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var user:UserAccount? = snapshot.getValue(UserAccount::class.java)
                mTvCategoryName.text = user!!.userName
            }
        })

        ivPostData.setOnClickListener{
            // open the album
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type="image/*"
            startActivityForResult(photoPickerIntent,PICK_IMAGE_FROM_ALBUM)
        }

        mBtnPostEnd.setOnClickListener {
            postAdd()
            finish()
        }

        mBtnBack.setOnClickListener {
            super.onBackPressed()
            finish()
        }
    }

    // onActivityResult
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            if (resultCode == Activity.RESULT_OK) {
                // This is path to the selected image
                photoUri = data?.data
                ivPostData.setImageURI(photoUri)
            } else {
                // Exit the addPhotoActivity if you leave the album without selecting it
            }
        }
    }

    private fun postAdd() {
        var hashMap: HashMap<String, Any> = HashMap()
        var strHits: String = mEtHits.text.toString()
        var strPostContent: String = mEtPostContent.text.toString()
        var strPostTitle: String = mEtPostTitle.text.toString()
        var strPostId: String = postId
        var strPostUri: String= ""
        var timestamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        var imageFileName = "IMAGE_" + strPostId + "_postImage_by"+categoryName+".png"
        var storageRef = storage?.reference?.child("${mFirebaseAuth?.currentUser!!.uid}")?.child(imageFileName)


        hashMap["postId"] = strPostId
        hashMap["postTitle"] = strPostTitle
        hashMap["postHits"] = strHits
        hashMap["postContent"] = strPostContent
        hashMap["categoryName"] = categoryName
        hashMap["star"] = 1
        hashMap["timestamp"] = timestamp


        // Promise method
        if(photoUri != null) { // 사진 선택했을 때
            storageRef?.putFile(photoUri!!)
                    ?.continueWithTask { task: com.google.android.gms.tasks.Task<UploadTask.TaskSnapshot> ->
                        return@continueWithTask storageRef.downloadUrl
                    }?.addOnSuccessListener { uri ->
                        strPostUri = uri.toString()
                        hashMap["postPhotoUri"] = strPostUri
                        mDatabaseRef.ref.child("UserPosts").child("${mFirebaseAuth!!.currentUser!!.uid}").child(postId).setValue(hashMap)
                    }
        }
        else{ // 사진 선택 안했을 때
            hashMap["postPhotoUri"] = strPostUri
            mDatabaseRef.ref.child("UserPosts").child("${mFirebaseAuth!!.currentUser!!.uid}").child(postId).setValue(hashMap)
        }

        Toast.makeText(this, "게시글 추가 완료", Toast.LENGTH_SHORT).show()

    }
*/

 */
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



    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_post)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

        var reciveData1 = intent.getStringExtra("SELECTED_ITEM")

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
                        Log.d("택", "${user!!.userEmail.toString()}")
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
                        var strCharacteristic = edtContent.text.toString()

                        hashMap.put("imgUrl", downloadUrl.toString())
                        hashMap.put("uid", mFirebaseAuth!!.currentUser!!.uid)
                        hashMap.put("title", strTitle)
                        hashMap.put("characteristic", strCharacteristic)
                        hashMap.put("timstamp", timestamp)

                        mDatabaseRef.ref.child("PostCategory").child("${strTitle}").push().setValue(hashMap)
                                .addOnCompleteListener {
                                    if(it.isSuccessful){
                                        Toast.makeText(this, "업로드", Toast.LENGTH_SHORT).show()
                                    }
                                }

                        Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, PostActivity::class.java)
                        intent.putExtra("SELECTED_ITEM", strTitle)
                        startActivity(intent)
                        finish()
                    }
                }.addOnFailureListener {

                    val hashMap : HashMap<String, String> = HashMap()

                    var strTitle: String = edtTitle.text.toString()
                    var strCharacteristic = edtContent.text.toString()

                    hashMap.put("uid", mFirebaseAuth!!.currentUser!!.uid)
                    hashMap.put("title", strTitle)
                    hashMap.put("characteristic", strCharacteristic)
                    hashMap.put("timestamp", timestamp)

                    mDatabaseRef.ref.child("PostData").push().setValue(hashMap)

                    Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this, PostActivity::class.java)
                    intent.putExtra("SELECTED_ITEM", strTitle)
                    startActivity(intent)
                    finish()

                }
            }catch (e : NullPointerException){
                Toast.makeText(this, "이미지 선택 안함", Toast.LENGTH_SHORT).show();
            }
        }

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