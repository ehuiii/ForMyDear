package com.cookandroid.formydear

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PostListActivity: AppCompatActivity()  {

    lateinit var backBtn : Button
    lateinit var plusBtn : Button
    //lateinit var postToolbar: Toolbar




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        var reciveData1 = intent.getStringExtra("SELECTED_ITEM")

        backBtn = findViewById(R.id.btnBack)
        plusBtn = findViewById(R.id.plusBtn)

        backBtn.setOnClickListener{
            finish()
        }
        plusBtn.setOnClickListener{
            var intent = Intent(this, WritePostActivity::class.java)
            startActivity(intent)
        }

        //postToolbar = findViewById(R.id.postToolbar)

        //setSupportActionBar(toolbar)
        //supportActionBar?.setDisplayShowTitleEnabled(false)
        //toolbar.title = "카테고리 이름"





    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        var mInflater = menuInflater
        mInflater.inflate(R.menu.plus, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId){
            R.id.action_plus ->{
                //게시글 쓰기 화면 이동

                return super.onOptionsItemSelected(item)
            }
            else-> return super.onOptionsItemSelected(item)
        }
    }*/
}