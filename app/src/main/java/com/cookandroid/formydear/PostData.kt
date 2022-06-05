package com.cookandroid.formydear

//게시글 정보 내용을 담는 모델
data class PostData(
        var uid : String? = null,
        var categoryName:String="",
        var postPhotoUri:String="",
        var postId: String ="",
        var postTitle: String ="",
        var postHits: String ="",
        var postContent : String = "",
        var star:Int = 1,
        var timestamp: String = ""
)