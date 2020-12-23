package nl.jeremypercy.nicejob.util;

import com.apollographql.apollo.ApolloClient;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class AplClient {
    public static final String BASE_URL = "http://10.0.2.2:4000/graphql";
    public static final String TAG = "CLIENT Android APP";
    private static ApolloClient apolloClient;

    public static ApolloClient getApolloClient(String authHeader) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request.Builder builder = original.newBuilder().method(original.method(), original.body());
                    builder.header("Authorization", authHeader);
                    return chain.proceed(builder.build());
                }).build();

        apolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        return apolloClient;
    }

}
