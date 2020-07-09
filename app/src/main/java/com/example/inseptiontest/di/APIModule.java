package com.example.inseptiontest.di;

import android.content.Context;

import com.example.inseptiontest.R;
import com.example.inseptiontest.base.APIService;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class APIModule {

    @Singleton
    @Provides
    @Named("ApiURL")
    String provideApiURL(Context context) {
        return context.getResources().getString(R.string.api_base_url);
    }

    @Singleton
    @Provides
    @Named("APIHeader")
    Interceptor provideAPIHeader(){
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                return chain.proceed(
                        chain.request().newBuilder()
                                .header("Content-Type","application/json")
                        .build());
            }
        };
    }

    @Provides
    @Singleton
    HttpLoggingInterceptor provideHttpLogginIncrepter(){
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }
    @Singleton
    @Provides
    @Named("APIClient")
    OkHttpClient provideAPIClient(HttpLoggingInterceptor httpLoggingInterceptor,
                                  @Named("APIHeader") Interceptor interceptor){
        return new OkHttpClient().newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
    }

    @Singleton
    @Provides
    @Named("API")
    Retrofit provideAPIRetrofit(@Named("ApiURL") String url,@Named("APIClient") OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    APIService provideAPIResource(@Named("API") Retrofit retrofit){
        return retrofit.create(APIService.class);
    }
}
