package cosmic.com.pkwprj.Retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public  class RetrofitClient {

    //얘를 코트린에서는 object 사용해서 싱글톤 처리할수있다. -> object Retrofit
    private static Retrofit ourInstance;

    private RetrofitClient() { }

    public static Retrofit getInstance() {
        if(ourInstance == null)
            ourInstance = new Retrofit.Builder()
                    .baseUrl("https://github.com/"  )
                    .addConverterFactory( GsonConverterFactory.create() )
                    .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                    .build();
        return ourInstance;
    }


}
