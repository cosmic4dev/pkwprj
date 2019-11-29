package cosmic.com.pkwprj.model

import com.google.gson.annotations.SerializedName

data class GithubRepo (@SerializedName("name")val name:String,
                       @SerializedName("id")val id:String,
                       @SerializedName("html_url")val url:String,
                       @SerializedName("score")val score:Int,
                       @SerializedName("avatar_url")val avatar:String)
