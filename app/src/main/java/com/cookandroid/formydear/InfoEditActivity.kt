package com.cookandroid.formydear

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.ArrayAdapter.createFromResource
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.loader.content.CursorLoader
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_infoedit.*
import java.io.File
import java.util.HashMap


class InfoEditActivity : AppCompatActivity() {

    lateinit var edtName: EditText
    lateinit var edtRelation: EditText
    lateinit var edtAge: TextView
    lateinit var edtChildChar: EditText
    lateinit var btnEditCom: Button
    lateinit var btnEdtimg: Button
    lateinit var btnBoy: Button
    lateinit var btnGirl: Button
    lateinit var btnBack : Button
    lateinit var imgProfile: ImageView
    lateinit var spinnerAge: Spinner
    lateinit var tvGenderInfo: TextView


    //파이어베이스
    private var mFirebaseAuth : FirebaseAuth? = null
    private lateinit var mDatabaseRef : DatabaseReference

    lateinit var user: UserAccount

    //이미지 업로드 시 필요한 변수 선언
    var imgUrl : String = ""
    private lateinit var fbStorage: FirebaseStorage
    private var GALLEY_CODE : Int = 10

    //키보드 내려가게 하기
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_infoedit)

        ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

        //파이어베이스에서 인스턴스 가져오기
        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ForMyDear")
        fbStorage = FirebaseStorage.getInstance()

        imgProfile = findViewById(R.id.imgProfile)
        edtChildChar = findViewById(R.id.edtChildChar)
        edtName = findViewById(R.id.edtName)
        edtRelation = findViewById(R.id.edtRelation)
        btnBack = findViewById(R.id.btnBack)
        btnEditCom = findViewById(R.id.btnEditCom)
        btnBoy = findViewById(R.id.btnBoy)
        btnGirl = findViewById(R.id.btnGirl)
        btnEdtimg = findViewById(R.id.btnEdtimg)
        spinnerAge = findViewById(R.id.spinnerAge)
        edtAge = findViewById(R.id.edtAge)
        tvGenderInfo = findViewById(R.id.tvGenderInfo)

        //사용자의 이름, 닉네임, 전화번호 기본 출력 (이름, 닉네임은 수정 불가능)
        mDatabaseRef.child("UserAccount").child("${mFirebaseAuth?.currentUser!!.uid}").addValueEventListener(object :
            ValueEventListener {

            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                var user: UserAccount? = snapshot.getValue(UserAccount::class.java)
                edtName.setText("${user!!.userChildName.toString()}")
                edtRelation.setText("${user!!.userChildRel.toString()}")
                edtChildChar.setText("${user!!.userChildChar.toString()}")
                edtAge.setText("${user!!.userChildAge.toString()}")

                if(user!!.userChildGender.equals("")){
                    btnGirl.isSelected = false
                    btnBoy.isSelected = false
                }else if(user!!.userChildGender.equals("남아")){
                    btnBoy.isSelected = btnBoy.isSelected != true
                    btnGirl.isSelected = false
                }else{
                    btnGirl.isSelected = btnGirl.isSelected != true
                    btnBoy.isSelected = false
                }

                if(user!!.userPhotoUri.equals("")){
                    imgProfile.setImageResource(R.drawable.man)
                }else{
                    var cropOptions : RequestOptions = RequestOptions()
                    Glide.with(applicationContext)
                        .load(user!!.userPhotoUri)
                        .apply(cropOptions.optionalCircleCrop())
                        .into(imgProfile)
                }
            }
        })

        //뒤로 가기
        btnBack.setOnClickListener{
            finish()
        }



        // 사진 편집 - 갤러리 들어가기
        btnEdtimg.setOnClickListener {
            //앨범 열기
            var intent = Intent(Intent.ACTION_PICK)
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE)

            startActivityForResult(intent, GALLEY_CODE)
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
            tvGenderInfo.text = "남아"
        }
        btnGirl.setOnClickListener {
            btnGirl.isSelected = btnGirl.isSelected != true
            btnBoy.isSelected = false
            tvGenderInfo.text = "여아"
        }


        //정보 수정 버튼
        btnEditCom.setOnClickListener{

            //사진 업로드 시 스토리지에 저장하고 내 정보 화면에 보여줌, 비밀번호 변경 시 비밀번호 변경 완료
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
                    //이미지 업로드 성공 시
                    if(it.isSuccessful)
                    {
                        var downloadUrl : Uri? = it.result

                        var strName : String = edtName.text.toString()
                        var strRelation : String = edtRelation.text.toString()
                        var strAge : String =  edtAge.text.toString()
                        var strChildChar : String = edtChildChar.text.toString()
                        var strGender : String = tvGenderInfo.text.toString()

                        //파이어베이스에 정보 변경 내용 업데이트
                        mDatabaseRef.child("UserAccount").child("${mFirebaseAuth?.currentUser!!.uid}")
                            .addValueEventListener(object : ValueEventListener {

                                override fun onCancelled(error: DatabaseError) {

                                }
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    var user: UserAccount? = snapshot.getValue(UserAccount::class.java)

                                    var strName : String = edtName.text.toString()
                                    var strRelation : String = edtRelation.text.toString()
                                    var strAge : String =  edtAge.text.toString()
                                    var strChildChar : String = edtChildChar.text.toString()
                                    var strGender : String = tvGenderInfo.text.toString()

                                    val hashMap : HashMap<String, String> = HashMap()

                                    hashMap.put("userPhotoUri", downloadUrl.toString())
                                    hashMap.put("userChildAge", strAge)
                                    hashMap.put("userChildChar",strChildChar)
                                    hashMap.put("userChildGender", strGender)
                                    hashMap.put("userChildName", strName)
                                    hashMap.put("userChildRel", strRelation)


                                    mDatabaseRef.child("UserAccount")
                                        .child("${mFirebaseAuth?.currentUser!!.uid}").setValue(hashMap)

                                }
                            })
                        Toast.makeText(this, "등록완료", Toast.LENGTH_SHORT).show()
                        Log.d("성별태그", strGender)
                        finish()
                    }
                }.addOnFailureListener {
                    //이미지 업로드 하지 않고 다른 정보만 수정하거나 아무것도 수정하지 않았을 경우
                    var strName : String = edtName.text.toString()
                    var strRelation : String = edtRelation.text.toString()
                    var strAge : String =  edtAge.text.toString()
                    var strChildChar : String = edtChildChar.text.toString()
                    var strGender : String = tvGenderInfo.text.toString()

                    //파이어베이스에 정보 변경 내용 업데이트
                    mDatabaseRef.child("UserAccount").child("${mFirebaseAuth?.currentUser!!.uid}")
                        .addValueEventListener(object : ValueEventListener {

                            override fun onCancelled(error: DatabaseError) {

                            }
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var user: UserAccount? = snapshot.getValue(UserAccount::class.java)

                                var strName : String = edtName.text.toString()
                                var strRelation : String = edtRelation.text.toString()
                                var strAge : String =  edtAge.text.toString()
                                var strChildChar : String = edtChildChar.text.toString()
                                var strGender : String = tvGenderInfo.text.toString()

                                val hashMap : HashMap<String, String> = HashMap()

                                hashMap.put("userChildAge", strAge)
                                hashMap.put("userChildChar",strChildChar)
                                hashMap.put("userChildGender", strGender)
                                hashMap.put("userChildName", strName)
                                hashMap.put("userChildRel", strRelation)

                                mDatabaseRef.child("UserAccount")
                                    .child("${mFirebaseAuth?.currentUser!!.uid}").setValue(hashMap)
                            }
                        })
                    finish()
                }
            }catch (e : NullPointerException){
            }
        }


        /*
        // 편집 완료 버튼 - 데이터 전달 > GuardianmainActivity
        btnEditCom.setOnClickListener {
            val nextIntent = Intent(this, GuardianmainActivity::class.java)
            val strName = edtName.text.toString()
            val spiAge = spinnerAge.selectedItem

            intent.putExtra("name", strName)
            intent.putExtra("age", spiAge.toString())
            startActivity(nextIntent)
        }*/
    }
    //갤러리 코드 확인해서 열고 사진 가져와서 이미지뷰에 출력
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == GALLEY_CODE) {
            if(resultCode == Activity.RESULT_OK){
                imgUrl = getRealPathFromUri(data!!.data)
                var cropOptions : RequestOptions = RequestOptions()
                Glide.with(applicationContext)
                    .load(imgUrl)
                    .apply(cropOptions.optionalCircleCrop())
                    .into(imgProfile)
            }
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    //절대 경로 구하기
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

