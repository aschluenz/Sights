package AppLogic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import sights.sights.R;

/**
 * Created by AndySchluenz on 24.01.16.
 */
public class InfoWindowAdapterMarker implements GoogleMap.InfoWindowAdapter {
    private Marker mMarker;
    private View mContentsView;
    private Context mContext;

    public  InfoWindowAdapterMarker(Context context) {
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {


        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        mMarker = marker;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mContentsView = inflater.inflate(R.layout.sights_info_window, null);

        TextView title = (TextView) mContentsView.findViewById(R.id.title_infowindow);
        TextView hiddenmarkerid = (TextView) mContentsView.findViewById(R.id.snippet_infowindow);
        ImageView imageView = (ImageView) mContentsView.findViewById(R.id.image_infowindow);

        Glide.with(mContext).load(R.drawable.next_arrow).into(imageView);

        title.setText(marker.getTitle());
        hiddenmarkerid.setText(marker.getSnippet());


        return mContentsView;
    }
}
