package com.john.realtimedb.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.john.realtimedb.di.qualifier.ApplicationContext;
import com.john.realtimedb.repository.HostSelectionInterceptor;
import com.john.realtimedb.repository.RetrofitService;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by john on 3/5/18.
 */
@Module
public class NetworkModule {

    @Singleton
    @Provides
    HttpLoggingInterceptor provideHttpLoggingInterceptor(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Timber.i(message));
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return httpLoggingInterceptor;
    }

    @Singleton
    @Provides
    HostSelectionInterceptor provideHostSelectionInterceptor(){
        return new HostSelectionInterceptor();
    }

    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient(HostSelectionInterceptor interceptor, HttpLoggingInterceptor httpLoggingInterceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @Singleton
    @Provides
    RetrofitService provideRetrofitService(Retrofit retrofit){
        return retrofit.create(RetrofitService.class);
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient, Gson gson){
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                //TODO: add url
                .baseUrl(""/*BuildConfig.BASE_URL*/)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .build();
    }

    @Singleton
    @Provides
    Gson provideGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Singleton
    @Provides
    Picasso providePicasso(@ApplicationContext Context context){
        return new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(context, 100 * 1024 * 1024))//100MB max size
                .build();
    }

}
