
import android.app.Activity
import android.content.Context

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
     var BASE_URL = "http://10.0.2.2:4000/"  //Temp dev


    private var retrofit: Retrofit? = null

    fun getClient(context: Activity): ApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(Constants.connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(ConnectivityInterceptor(context))
            .readTimeout(Constants.readTimeOut, TimeUnit.SECONDS).build()

        if (retrofit == null) {
            // <-- this is the important line!
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return retrofit!!.create(ApiInterface::class.java)

    }

    fun getDataClient(context: Context): ApiInterface {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .connectTimeout(Constants.connectionTimeOut, TimeUnit.SECONDS)
            .addInterceptor(logging)
          //  .addInterceptor(ConnectivityInterceptor(context))
            .readTimeout(Constants.readTimeOut, TimeUnit.SECONDS).build()

        if (retrofit == null) {
            // <-- this is the important line!
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return retrofit!!.create(ApiInterface::class.java)

    }

}
