package ua.com.hakaton;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

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

        
        JsonAgregator jsonAgregator = new JsonAgregator();
        jsonAgregator.setMarkers(this, mapView);
        
//        mapOverlays = mapView.getOverlays();
//        drawable = this.getResources().getDrawable(android.R.drawable.ic_delete);
//        itemizedOverlay = new RoadItemizedOverlay(drawable, this); 
//        
//        GeoPoint point = new GeoPoint(19240000,-99120000);
//        RoadOverlayItem overlayitem = new RoadOverlayItem(point, "dg dg dddfgdfgdfg dgd ", "Snippet");
//        itemizedOverlay.addOverlay(overlayitem);
//
//        GeoPoint point2 = new GeoPoint(35410000, 139460000);
//        RoadOverlayItem overlayitem2 = new RoadOverlayItem(point2, "", "");
//        itemizedOverlay.addOverlay(overlayitem2);
//        
//        mapOverlays.add(itemizedOverlay);	
	}


	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
