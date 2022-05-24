package com.cookandroid.formydear

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FaqAdapter(private val context: Context) : RecyclerView.Adapter<FaqAdapter.ViewHolder>() {

    var datas = mutableListOf<FaqData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_faq_recycler,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])

        //클릭 시
        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            private val faqDQ: TextView = itemView.findViewById(R.id.faqDQ)
            //private val faqDA: TextView = itemView.findViewById(R.id.faqDA)

            fun bind(item: FaqData) {
                faqDQ.text = item.faqQ
                //faqDA.text = item.faqDA

                itemView.setOnClickListener {
                    Intent(context, FaqanswerActivity::class.java).apply {
                        //putExtra("data", item)
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }.run { context.startActivity(this) }
                }

            }
        }
    }



    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val faqQ: TextView = itemView.findViewById(R.id.faqQ)

        fun bind(item: FaqData) {
            faqQ.text = item.faqQ
        }


    }


}


