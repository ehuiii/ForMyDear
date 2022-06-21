package com.cookandroid.formydear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_faqanswer.*
import kotlinx.android.synthetic.main.activity_faq.*

class FaqanswerActivity : AppCompatActivity() {

    lateinit var question: TextView
    lateinit var answer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faqanswer)

        question = findViewById(R.id.faqDQ)
        answer = findViewById(R.id.faqDA)

        var intent : Intent = getIntent()

        question.setText(intent.getStringExtra("faqQuestion"))
        answer.setText(intent.getStringExtra("faqAnswer"))

    }


}