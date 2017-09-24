package com.snapgroup.Classes;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.ErrorListener;

/**
 * Created by snapgroup2 on 25/07/17.
 */

public class RequestExample extends Request<String> {
    // ... other methods go here

    private Map<String, String> mParams;

    public RequestExample(String param1, String param2, Response.Listener<String> listener, ErrorListener errorListener) {
        super(Method.POST, "http://test.url", (Response.ErrorListener) errorListener);
        mParams = new HashMap<String, String>();
        mParams.put("paramOne", param1);
        mParams.put("paramTwo", param2);

    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        return null;
    }

    @Override
    protected void deliverResponse(String response) {

    }
}
