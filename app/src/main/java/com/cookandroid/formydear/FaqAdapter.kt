package com.cookandroid.formydear

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class FaqAdapter(val context: Context, val viewDataList: ArrayList<FaqData>) : RecyclerView.Adapter<FaqAdapter.CustomViewHolder>() {

    var mPositon = 0
    fun getPosition(): Int{
        return mPositon
    }

    private fun setPosition(position: Int){
        mPositon = position
    }

    fun addItem(faqData: FaqData){
        viewDataList.add(faqData)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        if(position>0){
            viewDataList.removeAt(position)
            notifyDataSetChanged()
        }
    }




    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val faqQ = itemView.findViewById<TextView>(R.id.faqQ)

        fun bind(faqData: FaqData, context: Context) {
            faqQ.text = faqData.faqQ
            val resourceId = context.resources.getIdentifier(
                faqData.faqA,
                "text",
                context.packageName
            )

        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_faq_recycler, parent, false)
        return CustomViewHolder(view)
    }


    override fun onBindViewHolder(holder: FaqAdapter.CustomViewHolder, position: Int){
        holder.bind(viewDataList[position], context)

        holder.itemView.setOnClickListener{ view ->
            setPosition(position)
            Toast.makeText(view.context, "$position 아이템 클릭!", Toast.LENGTH_SHORT).show()

            val intent = Intent(context, FaqanswerActivity::class.java)
            intent.putExtra("faqQuestion", viewDataList[position].faqQ)
            intent.putExtra("faqAnswer", viewDataList[position].faqA)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return viewDataList.size
    }


}


