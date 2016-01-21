package HttpNetwork;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.util.List;

import AppLogic.GooglePlacesService;
import model.Place;

/**
 * Created by AndySchluenz on 21.01.16.
 */
public class GetPlacesAsyncRunner extends AsyncTask<Void, Void, Void> {

    private ProgressBar progbar;
    private Context context;
    private String[] placeName;
    private String[] imageUrl;

    public GetPlacesAsyncRunner(Context context){
        this.context = context;
    }
    @Override
    protected Void doInBackground(Void...arg0){
        GooglePlacesService service = new GooglePlacesService();
        List<Place> findPlaces = service.search();

        placeName = new String[findPlaces.size()];
        imageUrl = new String[findPlaces.size()];

        for(int i = 0; i < findPlaces.size(); i++){
            Place placeDetail = findPlaces.get(i);
            //
            //TODO get lat long

            System.out.println( placeDetail.getName());
            
        }

    }


}
