package com.silverorange.videoplayer.viewmodel

import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silverorange.videoplayer.model.VideoList
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

public class VideoListViewModel(activity: Activity): ViewModel() {
    var videoResponse: MutableLiveData<VideoList>? = MutableLiveData()
    var isLoading: MutableLiveData<Boolean>? = MutableLiveData()
    var responseError: MutableLiveData<ResponseBody>? = MutableLiveData()
    var context = activity
    fun getAllVideo() {
        isLoading?.value = true
        ApiClient.getClient(context).getVideoListCall(

        ).enqueue(object : Callback<VideoList> {
            override fun onFailure(call: Call<VideoList>, t: Throwable) {
                isLoading?.value = false
            }

            override fun onResponse(
                call: Call<VideoList>,
                response: Response<VideoList>
            ) {
                isLoading?.value = false
                if (response.isSuccessful) {
                    videoResponse?.value = response.body()

                } else {
                    responseError?.value = response.errorBody()
                }
            }


        })

    }

}