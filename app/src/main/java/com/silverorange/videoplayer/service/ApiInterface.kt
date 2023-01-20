
import com.silverorange.videoplayer.model.VideoList
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @GET("videos")
    fun getVideoListCall(
    ): Call<VideoList>

}




