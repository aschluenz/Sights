package sights.sights.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import AppLogic.PreferenceData;
import HttpNetwork.NetworkHelper;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import sights.sights.R;

public class CommentsActivity extends AppCompatActivity {

    ListView Comments;
    Button addComment;
    EditText commentText;
    private String objektId;
    Boolean isSight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        objektId = getIntent().getStringExtra("id");
        isSight = getIntent().getBooleanExtra("isSight", true);


        addComment = (Button) findViewById(R.id.Btn_comment_post);
        commentText = (EditText) findViewById(R.id.Edt_comment);
        Comments = (ListView) findViewById(R.id.ListView_allComments);

        new AllCommentsAsyncTask().execute(objektId);


        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            new SendComment().execute(PreferenceData.getPrefLoggedinUserId(getBaseContext()),objektId,commentText.getText().toString(),isSight.toString());
                finish();
            }
        });


        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }


    public class SendComment extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {

            String _url;

            JSONObject object = new JSONObject();
            String url = "http://nodejs-sightsapp.rhcloud.com/";

            if(params[3] == "true") {
                 _url = url + "sight/addcomment";

                try {
                   // object.put("routeID", )
                    object.put("placeID",params[1]);
                    object.put("userID", params[0]);
                    object.put("comment", params[2]);
                } catch (JSONException e) {

                }
            }else{
                 _url = url + "route/addcomment";
                try {
                    // object.put("routeID", )
                    object.put("routeID",params[1]);
                    object.put("userID", params[0]);
                    object.put("comment", params[2]);
                } catch (JSONException e) {

                }
            }

            NetworkHelper nh = new NetworkHelper();
            nh.post(_url, object.toString(), new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {

                }
            });
            return null;
        }

    }


    public class AllCommentsAsyncTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... params) {
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

           // new Thread()
        }
    }


}
