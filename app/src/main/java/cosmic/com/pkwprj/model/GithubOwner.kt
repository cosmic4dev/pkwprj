package cosmic.com.pkwprj.model

class GithubOwner {

    lateinit var login: String
    lateinit var avatar_url: String
    lateinit var html_url: String
    var score:Float = 0.0f

    constructor() {}

    constructor(login: String, avatar_url: String, html_url: String, score: Float) {
        this.login = login
        this.avatar_url = avatar_url
        this.html_url = html_url
        this.score = score
    }
}
