package com.cookandroid.formydear

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_faq.*


class FaqActivity : AppCompatActivity() {

    lateinit var backBtn: Button
    private lateinit var faqAdapter: FaqAdapter
    val datas = mutableListOf<FaqData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        //뒤로 가기
        backBtn = findViewById(R.id.btnBack)
        backBtn.setOnClickListener {
            finish()
        }
        initRecycler()
    }

    private fun initRecycler() {
        faqAdapter = FaqAdapter(this)
        faqRv.adapter = faqAdapter

        datas.apply {
            add(FaqData("FomD로 어떤 역량을 기를 수 있나요?"))
            add(FaqData("FomD는 어떤 학습 기대 효과가 있나요?"))
            add(FaqData("효과적으로 앱을 이용하는 방법이 있나요?"))
            add(FaqData("어떤 것을 찍으면 좋을까요?"))
            add(FaqData("연령별로 맞는 단어가 있나요?"))
            add(FaqData("아이의 수준을 어떻게 알 수 있을까요?"))
            add(FaqData("인사나 감정 같은 경우는 어떻게 찍어야 할까요?"))
            add(FaqData("앱 내부에 있는 사진을 그냥 사용해도 괜찮나요?"))

            faqAdapter.datas = datas
            faqAdapter.notifyDataSetChanged()
        }
    }


}
