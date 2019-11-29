package cosmic.com.pkwprj.Retrofit

import cosmic.com.pkwprj.model.GithubApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class GithubClient {

    fun getApi():GithubApi = Retrofit.Builder()
        .baseUrl("http://api.github.com")
        .client(OkHttpClient())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GithubApi::class.java)
}