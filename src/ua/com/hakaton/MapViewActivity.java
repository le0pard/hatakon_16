package ua.com.hakaton;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MapViewActivity extends MapActivity {
	protected MapView mapView;
	protected List<Overlay> mapOverlays;
	protected Drawable drawable;
	protected RoadItemizedOverlay itemizedOverlay;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.map);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        LocationFinder loc = new LocationFinder(this);
        JsonAgregator.setMarkers(this, mapView);
        
        /*
        List<Overlay> mapOverlays = mapView.getOverlays();
        RoadItemizedOverlay roadItemizedOverlay = new RoadItemizedOverlay(getResources().getDrawable(R.drawable.icon), this);
        GeoPoint point = new GeoPoint((int)(LocationFinder.lat*1e6),(int)(LocationFinder.lng*1e6));
	    RoadOverlayItem overlayitem = new RoadOverlayItem(point, getResources().getString(R.string.ammplitude_title), "1");
	    roadItemizedOverlay.addOverlay(overlayitem);
	    mapOverlays.add(roadItemizedOverlay);*/
	}


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
