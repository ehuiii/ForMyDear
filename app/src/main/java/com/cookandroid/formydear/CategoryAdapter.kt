package com.cookandroid.formydear

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage


class CategoryAdapter(val context: Context, val viewDataList: ArrayList<CategoryData>) : RecyclerView.Adapter<CategoryAdapter.CustomViewHolder>(){

    var mPositon = 0
    fun getPosition(): Int{
        return mPositon
    }

    private fun setPosition(position: Int){
        mPositon = position
    }

    fun addItem(categoryData: CategoryData){
        viewDataList.add(categoryData)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        if(position>0){
            viewDataList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    //private var mFirebaseAuth: FirebaseAuth? = FirebaseAuth.getInstance() //파이어베이스 인증
    //private var mDatabaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("ForMyDear")//실시간 데이터베이스
    //private var storage: FirebaseStorage? = FirebaseStorage.getInstance()

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val viewCategoryImg = itemView.findViewById<ImageView>(R.id.postCategoryImg)
        val viewCategoryName = itemView.findViewById<TextView>(R.id.postCategoryName)

        fun bind(categoryData: CategoryData, context: Context) {
            if (categoryData.photo != "") {
                val resourceId = context.resources.getIdentifier(
                    categoryData.photo,
                    "drawable",
                    context.packageName
                )
                if (resourceId > 0) {
                    viewCategoryImg.setImageResource(resourceId)
                } else {
                    viewCategoryImg.setImageResource(R.drawable.man)
                }
            } else {
                viewCategoryImg.setImageResource(R.drawable.man)
            }

            viewCategoryName.text = categoryData.categoryName

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category_recycler, parent, false)
        return CustomViewHolder(view)
    }


    override fun onBindViewHolder(holder: CategoryAdapter.CustomViewHolder, position: Int){
        holder.bind(viewDataList[position], context)

        holder.itemView.setOnClickListener{ view ->
            setPosition(position)
            Toast.makeText(view.context, "$position 아이템 클릭!", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, PostListActivity::class.java)
            intent.putExtra("SELECTED_ITEM", viewDataList[position].photo)
            context.startActivity(intent)
        }

        /*
        holder.itemView.setOnLongClickListener{ view ->
            setPosition(position)
            Toast.makeText(view.context, "$position 아이템 롱클릭!", Toast.LENGTH_SHORT).show()
            return@setOnLongClickListener ture
        }
*/

    }

    override fun getItemCount(): Int {
        return viewDataList.size
    }


}