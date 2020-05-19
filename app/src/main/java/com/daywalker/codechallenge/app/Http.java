package com.daywalker.codechallenge.app;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Http extends AsyncTask<String, String, String> {

    public static final String API_URL = "https://itunes.apple.com";
    public static final String POST = "POST";
    public static final String GET = "GET";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";

    private static final String APPLICATION_JSON = "application/json";

    private final HttpClient httpclient = new DefaultHttpClient();
    private HttpResponse response;
    private HttpEntity resEntity;
    private Listener mListener;
    private String method;
    private String body;

    /**
     * Interface Listener
     */
    public interface Listener {
        void onResult(String result);
    }

    /**
     * Set HTTP Method
     * @param method http method
     * @param body can be use post body or get parameters
     */
    public void request(String method, String body) {
        this.method = method;
        this.body = body;
    }

    /**
     * Set Listener
     * @param listener listener
     */
    public void setListener(Listener listener) {
        mListener = listener;
    }

    /**
     * HTTP Get Request
     * @param url api endpoint
     * @return api response
     */
    private String get(String url) {
        String responseBody = null;
        HttpGet get = new HttpGet(url + body);
        get.addHeader("Content-Type", APPLICATION_JSON);
        get.addHeader("Accept", APPLICATION_JSON);

        try {
            response = httpclient.execute(get);
            resEntity = response.getEntity();

            if (resEntity != null) {
                responseBody = EntityUtils.toString(resEntity);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpclient.getConnectionManager().shutdown();
        }
        return responseBody;
    }


    @Override
    protected String doInBackground(String... url) {
        switch (this.method) {
            case GET:
                return this.get(url[0]);
            case POST:
                // To Do Post Request
            case PUT:
                // To do Put Request
            case PATCH:
                // To do Put Request
            case DELETE:
                // To do Put Request
            default:
                return "";
        }
    }

    @Override
    protected void onPostExecute(String response) {
        if (mListener != null) {
            mListener.onResult(response);
        }
    }
}
