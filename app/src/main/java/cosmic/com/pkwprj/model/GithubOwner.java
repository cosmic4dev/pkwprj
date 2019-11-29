package cosmic.com.pkwprj.model

import com.google.gson.annotations.SerializedName

data class GithubOwner(@SerializedName("login")val login:String?=null,

                       @SerializedName("avatar_url")val avatar_url:String?=null,

                       @SerializedName("html_url")val html_url:String?=null,

                       @SerializedName("score")val score:Int?=null)

    constructor() {}

    constructor(login: String, avatar_url: String, html_url: String, score: Int) {
        this.login = login
        this.avatar_url = avatar_url
        this.html_url = html_url
        this.score = score
    }

