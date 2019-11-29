package cosmic.com.pkwprjjava

/**
 * 각자의 역할이 가진 Contract(계약)를 정의해 둘 인터페이스
 */
interface RepositoryListContract {

    /**
     * MVP의 View가 구현할 인터페이스
     * Presenter가 View를 조작할 때 이용한다
     */
    interface View {
        val selectedLanguage: String
        fun showProgress()
        fun hideProgress()
        fun showRepositories(repositories: GitHubService.Repositories)
        fun showError()
        fun startDetailActivity(fullRepositoryName: String)
    }

    /**
     * MVP의 Presenter가 구현할 인터페이스
     * View를 클릭했을 때 등 View가 Presenter에 알릴 때 이용한다
     */
    interface UserActions {
        fun selectLanguage(language: String)
        fun selectRepositoryItem(item: GitHubService.RepositoryItem)
    }

}


