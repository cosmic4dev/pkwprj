package cosmic.com.retrofit2rxjava.Retrofit;


import java.util.List;

import cosmic.com.retrofit2rxjava.model.Post;
import retrofit2.http.GET;

public interface IMyAPI {

    @GET("posts")
    io.reactivex.Observable<List<Post>> getPosts();

}
