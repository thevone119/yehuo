package com.yehuo.comm.http;

import okhttp3.*;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018-08-30.
 *
 * http请求的封装
 * 采用OkHttp的封装
 */
public class MyOkHttp {
    //请求的hread头
    public Map<String,String> hreads;

    //字符编码
    public String defaultCharset = "utf-8";
    private long connectTimeout = 5;//链接超时
    private long readTimeout = 10;//读取超时时间,针对下载
    private long writeTimeout = 10;//写入超时时间，针对上传

    public Map<String, String> getHreads() {
        return hreads;
    }

    public void setHreads(Map<String, String> hreads) {
        this.hreads = hreads;
    }

    public String getDefaultCharset() {
        return defaultCharset;
    }

    public void setDefaultCharset(String defaultCharset) {
        this.defaultCharset = defaultCharset;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(long connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(long readTimeout) {
        this.readTimeout = readTimeout;
    }

    public long getWriteTimeout() {
        return writeTimeout;
    }

    public void setWriteTimeout(long writeTimeout) {
        this.writeTimeout = writeTimeout;
    }

    /**
     * http的get方法封装
     * @param url
     * @return
     */
    public HttpReturn get(String url){
        //1.创建OkHttpClient对象
        HttpReturn ret = new HttpReturn();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)

                .build();
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(url).method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        try {
            //同步调用,返回Response,会抛出IO异常
            Response response = call.execute();
            ret.setCode(response.code());
            if(response.body()!=null){
                ret.setBody(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }



    /**
     * http post方法封装
     * @param url
     * @param postData
     * @return
     */
    public HttpReturn post(String url,Map<String,String> postData){
        //1.创建OkHttpClient对象
        HttpReturn ret = new HttpReturn();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)

                .build();
        //2.通过new FormBody()调用build方法,创建一个RequestBody,可以用add添加键值对
        FormBody.Builder formb = new FormBody.Builder(Charset.forName(defaultCharset));
        if(postData!=null && postData.size()>0){
            for (Map.Entry<String, String> param : postData.entrySet()) {
                formb.add(param.getKey(),param.getValue());
            }
        }
        //加入随机参数，避免缓存
        formb.add("__t",System.currentTimeMillis()+"");
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        Request request = new Request.Builder().url(url).post(formb.build()).build();
        //4.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        try {
            //同步调用,返回Response,会抛出IO异常
            Response response = call.execute();
            ret.setCode(response.code());
            if(response.body()!=null){
                ret.setBody(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 上传文件
     * @param url
     * @param postData
     * @return
     */
    public HttpReturn PostFile(String url,Map<String,String> postData,Map<String,File> files){
        //1.创建OkHttpClient对象
        HttpReturn ret = new HttpReturn();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)

                .build();
        //2.通过new FormBody()调用build方法,创建一个RequestBody,可以用add添加键值对
        MultipartBody.Builder formb =new MultipartBody.Builder().setType(MultipartBody.FORM);

        if(postData!=null && postData.size()>0){
            for (Map.Entry<String, String> param : postData.entrySet()) {
                formb.addFormDataPart(param.getKey(),param.getValue());
            }
        }
        //通用的文件格式
        final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        //MediaType Image = MediaType.parse("image/jpeg; charset=utf-8");
        if(files!=null && files.size()>0){
            for (Map.Entry<String, File> param : files.entrySet()) {
                formb.addFormDataPart(param.getKey(),param.getValue().toString(),RequestBody.create(MEDIA_TYPE_MARKDOWN,param.getValue()));
            }
        }
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        Request request = new Request.Builder().url(url).post(formb.build()).build();
        //4.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        try {
            //同步调用,返回Response,会抛出IO异常
            Response response = call.execute();
            ret.setCode(response.code());
            if(response.body()!=null){
                ret.setBody(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }


    /**
     * 上传图片
     * @param url
     * @param postData
     * @return
     */
    public HttpReturn PostImage(String url,Map<String,String> postData,Map<String,byte[]> files){
        //1.创建OkHttpClient对象
        HttpReturn ret = new HttpReturn();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)

                .build();
        //2.通过new FormBody()调用build方法,创建一个RequestBody,可以用add添加键值对
        MultipartBody.Builder formb =new MultipartBody.Builder().setType(MultipartBody.FORM);

        if(postData!=null && postData.size()>0){
            for (Map.Entry<String, String> param : postData.entrySet()) {
                formb.addFormDataPart(param.getKey(),param.getValue());
            }
        }
        //通用的文件格式
        //final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
        MediaType Image = MediaType.parse("image/jpeg; charset=utf-8");
        if(files!=null && files.size()>0){
            for (Map.Entry<String, byte[]> param : files.entrySet()) {
                formb.addFormDataPart(param.getKey(),param.getValue().toString(),RequestBody.create(Image,param.getValue()));
            }
        }
        //3.创建Request对象，设置URL地址，将RequestBody作为post方法的参数传入
        Request request = new Request.Builder().url(url).post(formb.build()).build();
        //4.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        try {
            //同步调用,返回Response,会抛出IO异常
            Response response = call.execute();
            ret.setCode(response.code());
            if(response.body()!=null){
                ret.setBody(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 下载文件
     * @param url
     * @return
     */
    public InputStream downFile(String url){
        //1.创建OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)

                .build();
        //2.创建Request对象，设置一个url地址,设置请求方式。
        Request request = new Request.Builder().url(url).method("GET",null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = okHttpClient.newCall(request);
        try {
            //同步调用,返回Response,会抛出IO异常
            Response response = call.execute();
            if(response.code()==200){
                return response.body().byteStream();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]){

        String url = "https://www.heimipay.com/comm/test_service";

        Map<String,String> postData =new HashMap<String,String>();
        postData.put("p","123");
        postData.put("pwd","1234567");
        HttpReturn ret = new MyOkHttp().post(url,postData);
        System.out.println(ret.body);
    }

}
