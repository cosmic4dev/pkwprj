package cosmic.com.pkwprj.model

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("users/{owner}/repos")
    fun getRepos(@Path("owner")owner: String) : Single<ArrayList<GithubRepo>>

    @GET("users/{username}")
    fun getOwners(@Path("username")username: String): Single<GithubOwner> //Observable으로 변환가능?


}