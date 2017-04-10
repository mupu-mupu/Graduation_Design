package com.android.graduation.api;


import com.android.graduation.app.BaseApplication;
import com.android.graduation.model.weather_info.WeatherResult;
import com.android.graduation.utils.Util;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2016/7/10.
 * Email: 18814182294@163.com
 * Describe ：
 */

public class WeatherAPI {

    //EndPoint（路径，终点）
    private static final String WEATHER_INFO_URL = "https://api.heweather.com/x3/";
    public static final String KEY = "0645b150bed04440b9f5acd5244f5947";

    Retrofit retrofit = null;
    private static OkHttpClient okHttpClient = null;
    private final WeatherAPIService mWebservice;

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();



    private static WeatherAPI instance;
    public static WeatherAPI getInstance(){
        if (null == instance) {
            synchronized (WeatherAPI.class) {
                if(null == instance)
                    instance = new WeatherAPI();
            }
        }
        return instance;
    }

    private WeatherAPI(){
//        Cache cache;
//        OkHttpClient okHttpClient = null;
//        try{
//            File cacheDir = new File(BaseApplication.getContext().getCacheDir().getPath(),"weather_cache.jason");
//            //设置缓存区的大小
//            cache = new Cache(cacheDir,1024 * 1024);
//            okHttpClient = new OkHttpClient();
//            okHttpClient.setCache(cache);
//        }catch (Exception e){
//            e.printStackTrace();
//            Log.d("WeatherAPI","okHttpClient 初始化失败");
//        }
        initOkHttp();

        retrofit  =  new Retrofit.Builder()
                //路径又称"终点"（endpoint），表示API的具体网址。
                .baseUrl(WEATHER_INFO_URL)
                .client(okHttpClient)
                //设置转换器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .setConverter(new GsonConverter(gson))
//                .setRequestInterceptor(mRequestInterceptor)
                .build();
        mWebservice = retrofit.create(WeatherAPIService.class);

    }

    private static void initOkHttp() {
        OkHttpClient.Builder builder = new  OkHttpClient.Builder();
        File cacheFile = new File(BaseApplication.getContext().getExternalCacheDir(), "weather_cache1");
        okhttp3.Cache cache = new okhttp3.Cache(cacheFile, 1024 * 1024 * 5);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!Util.isNetworkConnected(BaseApplication.getContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
//                if (Util.isNetworkConnected(BaseApplication.getContext())) {
//                    int maxAge = 0;
//                    // 有网络时 设置缓存超时时间0个小时
//                    response.newBuilder()
//                            .header("Cache-Control", "public, max-age=" + maxAge)
//                            .removeHeader("WuXiaolong")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
//                            .build();
//                } else {
//                    // 无网络时，设置超时为4周
//                    int maxStale = 60 * 60 * 24 * 28;
//                    response.newBuilder()
//                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                            .removeHeader("nyn")
//                            .build();
//                }
                return response;
            }
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
        //设置超时
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        okHttpClient = builder.build();

    }


    /**
     *    手动添加 Http头域，HTTP的头域包括通用头、请求头、响应头和实体头
     *      Cache-Control：指定请求和响应遵循的缓存机制。
     *          Public：指示响应可被任何缓存区缓存，可以用缓存内容回应任何用户。
     *          max-age：缓存无法返回缓存时间长于max-age规定秒的文档
     Content-Type：WEB 服务器告诉浏览器自己响应的对象的类型。例如：Content-Type：application/xml
     */
//    private RequestInterceptor mRequestInterceptor = new RequestInterceptor() {
//        @Override
//        public void intercept(RequestFacade request) {
//            request.addHeader("Cache-Control", "public, max-age=" + 60 * 60 * 4);
//            request.addHeader("Content-Type", "application/json");
//        }
//    };

    //Retrofit turns Http API into Java interface
    public interface WeatherAPIService{
        @GET("https://api.heweather.com/x3/weather")
        Observable<WeatherResult> getWeatherResult(
                @Query("cityid") String city,
                @Query("key") String key

        );
    }


    //暴露接口给调用者
    public  Observable<WeatherResult> getWeatherResult(int cityId){
        String city_id = "CN" + String.valueOf(cityId);
        return mWebservice.getWeatherResult(city_id,KEY);
    }
}

