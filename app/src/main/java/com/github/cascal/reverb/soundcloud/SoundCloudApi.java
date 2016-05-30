package com.github.cascal.reverb.soundcloud;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class SoundCloudApi {

    private static final String API_ENDPOINT = "https://api.soundcloud.com";
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_PAGE_LIMIT = "limit";

    private SoundCloudService soundCloud;
    private String clientId;
    private int limit = 50;

    public SoundCloudApi(String clientId) {
        this.clientId = clientId;
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(API_ENDPOINT)
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setRequestInterceptor(new SoundCloudRequestInterceptor())
                .setConverter(new GsonConverter(gson))
                .build();
        soundCloud = adapter.create(SoundCloudService.class);
    }

    public SoundCloudService getService() {
        return soundCloud;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setPageLimit(int limit) {
        if (limit < 50 || limit > 200)
            throw new IllegalArgumentException("Limit must be between 50 and 200 inclusive");
        this.limit = limit;
    }

    public class SoundCloudRequestInterceptor implements RequestInterceptor {
        @Override
        public void intercept(RequestFacade request) {
            if (clientId != null) {
                request.addQueryParam(PARAM_CLIENT_ID, clientId);
            }
            request.addQueryParam(PARAM_PAGE_LIMIT, Integer.toString(limit));
        }
    }
}
