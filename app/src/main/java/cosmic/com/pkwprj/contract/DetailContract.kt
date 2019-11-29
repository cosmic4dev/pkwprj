package cosmic.com.pkwprjjava

/**
 * 각각 역할을 가진 Contract(계약)를 정의해두는 인터페이스
 */
interface DetailContract {

    /**
     * MVP의 View가 구현할 인터페이스
     * Presenter가 View를 조작할 때 이용한다
     */
    interface View {
        val fullRepositoryName: String

        fun showRepositoryInfo(response: GitHubService.RepositoryItem)

        fun startBrowser(url: String)

        fun showError(message: String)
    }

    /**
     * MVP의 Presenter가 구현할 인터페이스
     * View를 클릭했을 때 등 View가 Presenter에 알리기 위해 이용한다
     */
    interface UserActions {
        fun titleClick()

        fun prepare()
    }
}


