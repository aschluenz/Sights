package HttpNetwork;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.android.gms.maps.model.Marker;

import java.util.List;

import AppLogic.GooglePlacesService;
import model.Place;

/**
 * Created by AndySchluenz on 21.01.16.
 */
public class GetPlacesAsyncRunner extends AsyncTask<Void, Void, Void> {


    private ProgressDialog dialog;
    private Context context;
    private String[] placeName;
    private String[] imageUrl;
    private ListView  listView;

    public GetPlacesAsyncRunner(){

    };

    public GetPlacesAsyncRunner(Context context, ListView listView){
        this.context = context;
        this.listView = listView;

    }
    @Override
    protected void onPostExecute(Void result){
        super.onPreExecute();
        dialog.dismiss();

        listView.setAdapter(new ArrayAdapter<String>(context,android.R.layout.simple_expandable_list_item_1,placeName));
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setCancelable(true);
        dialog.setMessage("Loading...");
        dialog.isIndeterminate();
        dialog.show();
    }

    @Override
    protected Void doInBackground(Void...arg0){



        GooglePlacesService service = new GooglePlacesService();
        List<Place> findPlaces = service.findPlaces(52.5074588,13.2847137,1600, "museum");

        placeName = new String[findPlaces.size()];
        imageUrl = new String[findPlaces.size()];

        for(int i = 0; i < findPlaces.size(); i++){
            Place placeDetail = findPlaces.get(i);
            placeDetail.getIcon();

            System.out.println( placeDetail.getName());
            placeName[i] = placeDetail.getName();

            imageUrl[i] = placeDetail.getIcon();
        }
        return null;
    }


}
