package cosmic.com.pkwprj.model;

public class GithubOwner {

    String login;
    String avatar_url;
    String html_url;
    int score;

    public GithubOwner() {
    }

    public GithubOwner(String login, String avatar_url, String html_url, int score) {
        this.login = login;
        this.avatar_url = avatar_url;
        this.html_url = html_url;
        this.score = score;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
