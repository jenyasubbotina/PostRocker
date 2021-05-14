package com.jenyasubbotina.postrocker.network;

import android.content.Context;

import com.jenyasubbotina.postrocker.SessionManager;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class SessionInterceptor implements Interceptor {

    private final Context mContext;

    public SessionInterceptor(Context context) {
        this.mContext = context;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain)
            throws IOException {
        Request request = chain.request();
        SessionManager sm = new SessionManager(mContext);
        if (sm.fetchAuthToken() != null) {
            request = request.newBuilder()
                    .addHeader("token", sm.fetchAuthToken())
                    .build();
        }
        return chain.proceed(request);
    }
}
