package com.cookandroid.formydear

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.finishAffinity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var mFirebaseAuth: FirebaseAuth // 파이어베이스 인증 처리
    private lateinit var mDatabaseRef: DatabaseReference // 실시간 데이터 베이스

    private lateinit var mEtEmail: EditText // 로그인 입력 필드(이메일)
    private lateinit var mEtPwd: EditText // 로그인 입력 필드(비밀번호)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        mFirebaseAuth = FirebaseAuth.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Firebase")

        mEtEmail = findViewById(R.id.etEmail)
        mEtPwd = findViewById(R.id.etPwd)


        var btn_login = findViewById<Button>(R.id.btnLogin)
        btn_login.setOnClickListener(View.OnClickListener {
            // 로그인 요청
            var strEmail: String = mEtEmail.getText().toString()
            var strPwd: String = mEtPwd.getText().toString()

            mFirebaseAuth?.signInWithEmailAndPassword(strEmail,strPwd)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 로그인 성공
                        moveMainPage(task.result?.user)

                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    }
                }
        })

        val btnRegister = findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener(View.OnClickListener{

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)


        })
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(mFirebaseAuth?.currentUser)
    }

    //자동 로그인
    fun moveMainPage(user: FirebaseUser?){
        if(user != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}