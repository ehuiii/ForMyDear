package com.cookandroid.formydear

import android.os.Bundle
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.android.synthetic.main.activity_faq.*


class FaqActivity : AppCompatActivity() {

    lateinit var backBtn: Button

    private var questionList = arrayListOf<FaqData>(
        FaqData("FomD로 어떤 역량을 기를 수 있나요?", "폼디는 아이가 일상 속에서 쓰고 보는 것들을 사진 찍어, 내 주변 것들을 통해 맞춤으로 쉽게 언어를 익힐 수 있습니다. \n 즉, 언어 능력을 기를 수 있으며, 학습 콘텐츠를 찍고 만들며 주변 사람들과의 교류를 통해 사회성을 기르고 주변 사물들을 주의 깊게 관찰하는 능력을 기를 수 있습니다. "),
        FaqData("FomD는 어떤 학습 기대 효과가 있나요?", ""),
        FaqData("효과적으로 앱을 이용하는 방법이 있나요?", ""),
        FaqData("어떤 것을 찍으면 좋을까요?", ""),
        FaqData("연령별로 맞는 단어가 있나요?", ""),
        FaqData("아이의 수준을 어떻게 알 수 있을까요?", ""),
        FaqData("인사나 감정 같은 경우는 어떻게 찍어야 할까요?", ""),
        FaqData("앱 내부에 있는 사진을 그냥 사용해도 괜찮나요?", "")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)


        //뒤로 가기
        backBtn = findViewById(R.id.btnBack)
        backBtn.setOnClickListener {
            finish()
        }
        val mAdapter = FaqAdapter(this, questionList)
        faqRv.adapter = mAdapter

        val gridLayoutManager = GridLayoutManager(applicationContext, 1)
        faqRv.layoutManager = gridLayoutManager
        /*
        faqRv.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )*/
    }





}
