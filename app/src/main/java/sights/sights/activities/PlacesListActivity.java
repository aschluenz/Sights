package sights.sights.activities;

import android.app.ListActivity;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import HttpNetwork.GetPlacesAsyncRunner;
import sights.sights.R;

public class PlacesListActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         new GetPlacesAsyncRunner(this, getListView()).execute();


    }

    /*

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
            default: {
                break;
            }
        }
        return true;
    }

    public static class MyFragment extends ListFragment {
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
        }

        public void onListItemClick(ListView listView, View view, int position, long id){

        }
    }

    */
}
