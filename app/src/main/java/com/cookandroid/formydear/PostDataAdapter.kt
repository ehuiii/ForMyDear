package com.cookandroid.formydear

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.collections.ArrayList

/*게시글 상세 화면 리싸이클러뷰에 연결하는 adapter*/
class PostDataAdapter() : RecyclerView.Adapter<PostDataAdapter.CustomViewHolder>(){

    private lateinit var postDataList : ArrayList<PostData>
    private lateinit var context : Context


    constructor(postDataList: ArrayList<PostData>, context: Context) : this() {
        this.postDataList = postDataList
        this.context = context
    }

    //게시글 목록에서 특정 게시글을 누르면 해당 포지션에 있는 정보들을 intent로 PostActivity에 전달
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDataAdapter.CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {
                val curPos : Int = adapterPosition
                val postData : PostData = postDataList.get(curPos)
                if(curPos != RecyclerView.NO_POSITION){
                    var intent = Intent(context, PostActivity::class.java).addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.putExtra("UID", postData.uid)
                    intent.putExtra("postTitle", postData.postTitle)
                    intent.putExtra("postContent", postData.postContent)
                    intent.putExtra("categoryName", postData.categoryName)
                    intent.putExtra("postPhotoUri", postData.postPhotoUri)
                    intent.putExtra("postAudioUri", postData.postAudioUri)

                    Log.d("hey",postData.postTitle)

                    context.startActivity(intent)
                }
            }
        }
    }

    //목록 아이템 뷰에 출력되는 값 설정
    override fun onBindViewHolder(holder: PostDataAdapter.CustomViewHolder, position: Int) {

        if(postDataList[position].postPhotoUri == null){
            holder.image.setImageResource(R.drawable.man)
        }else{
            var cropOptions: RequestOptions = RequestOptions()
            Glide.with(holder.itemView)
                    .load(postDataList[position].postPhotoUri)
                    .apply(cropOptions.centerCrop())
                    .into(holder.image)
        }
        //holder.content.text = postDataList.get(position).categoryName
    }

    //게시글의 개수 반환
    override fun getItemCount(): Int {
        if(postDataList != null){
            return postDataList.size
        }else
        {
            return 0
        }
    }

    //위젯 연결할 변수 선언
    class CustomViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val image = itemView.findViewById<ImageView>(R.id.postPhotoImg)
        //val content = itemView.findViewById<TextView>(R.id.tv_Content)

    }
}