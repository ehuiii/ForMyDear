package com.cookandroid.formydear

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_category.*

class PostListActivity: AppCompatActivity()  {

    lateinit var backBtn : Button
    lateinit var rv_post : RecyclerView
    lateinit var adapter : RecyclerView.Adapter<PostDataAdapter.CustomViewHolder>
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var arrayList: ArrayList<PostData>
    lateinit var plusBtn : Button
    lateinit var tvCategoryName: TextView


    //파이어베이스
    private lateinit var database : FirebaseDatabase
    private lateinit var mDatabaseRef : DatabaseReference
    private var mFirebaseAuth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        //var reciveData1 = intent.getStringExtra("SELECTED_ITEM")

        rv_post = findViewById(R.id.categoryRv)
        tvCategoryName = findViewById(R.id.tvCategoryName)
        backBtn = findViewById(R.id.btnBack)
        plusBtn = findViewById(R.id.plusBtn)

        rv_post.setHasFixedSize(true) //리사이클러뷰 성능 강화
        layoutManager = GridLayoutManager(applicationContext, 3)
        rv_post.layoutManager = GridLayoutManager(applicationContext, 3)


        arrayList = ArrayList<PostData>() //PostData 객체를 담을 ArrayList

        database = FirebaseDatabase.getInstance() //파이어베이스 데이터베이스 연동
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("ForMyDear")
        mFirebaseAuth = FirebaseAuth.getInstance()


        var intent : Intent = getIntent()

        var selectedItem : String? = intent.getStringExtra("SELECTED_ITEM")
        var mode : Int? = intent.getIntExtra("MODE", 0)

        //리사이클러뷰에 담을 데이터 가져오기(selectedItem 태그를 통해서 보여줄 게시글 구분)
        mDatabaseRef.child("PostData").child("${mFirebaseAuth!!.currentUser!!.uid}").child("$selectedItem")
                .orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        arrayList.clear()

                        for (data : DataSnapshot in snapshot.getChildren()) {
                            var postData : PostData? = data.getValue(PostData::class.java)

                            arrayList.add(postData!!) //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼 준비

                            Log.d("태그", "$arrayList")
                        }
                        adapter.notifyDataSetChanged() //리스트 저장 및 새로고침

                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })

        tvCategoryName.setText(intent.getStringExtra("SELECTED_ITEM"))

        adapter = PostDataAdapter(arrayList, this, mode)
        rv_post.setAdapter(adapter)

        backBtn.setOnClickListener{
            finish()
        }
        //아동모드일 경우 글쓰기 버튼 안보이게 하기
        if(mode == 1){
            plusBtn.visibility = View.GONE
        }
        plusBtn.setOnClickListener{
            var intent = Intent(this, WritePostActivity::class.java)
            intent.putExtra("SELECTED_ITEM", selectedItem)
            startActivity(intent)
        }
    }

}