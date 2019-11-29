package cosmic.com.pkwprj.Retrofit

import android.app.Application
import cosmic.com.pkwprj.model.GitHubService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory

//싱글톤 구현?
object  NewGitHubReposApplication : Application() {
    private var retrofit: Retrofit? = null
    var gitHubService: GitHubService? = null
        private set

    override fun onCreate() {
        super.onCreate()
        // 어느 Activity에서나 API를 이용할 수 있도록, 이 클래스로 API를 이용한다
        setupAPIClient()
    }

    private fun setupAPIClient() {

        retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.github.com")
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()

        gitHubService = retrofit!!.create(GitHubService::class.java)
    }
}
