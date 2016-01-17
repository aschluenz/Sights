package HttpNetwork;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by andyschlunz on 16.01.16.
 */
 public class LoginAsyncRunner extends AsyncTask<String, String, String> {

    private String resp;

    private String url = "http://nodejs-sightsapp.rhcloud.com/login";

    @Override
    protected String doInBackground(String... params) {
        int count = params.length;
        if (count == 2) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("userToken", params[0]);
                obj.put("userId", params[1]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String response = null;
            NetworkHelper nethelper = new NetworkHelper();
            nethelper.post(url, obj.toString(), new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code: " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }
                    if(response.isSuccessful()) {
                        String resp = response.body().toString();
                    }
                }
            });
        }
        return resp;
    }


}
