package com.drewsoft.marvelapiapp.core.interceptor

import com.drewsoft.marvelapiapp.BuildConfig
import com.drewsoft.marvelapiapp.core.md5
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val ts = System.currentTimeMillis()
        val hash = "$ts${BuildConfig.PRIVATE_API_KEY}${BuildConfig.PUBLIC_API_KEY}".md5()

        val request = chain.request()

        val url = request.url
            .newBuilder()
            .addQueryParameter("ts", ts.toString())
            .addQueryParameter("apikey", BuildConfig.PUBLIC_API_KEY)
            .addQueryParameter("hash", hash)
            .build()
        val updated = request.newBuilder()
            .url(url)
            .build()

        return chain.proceed(updated)
    }
}