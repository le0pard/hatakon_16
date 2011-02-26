package ua.com.hakaton;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationFinder {
	public static double lat = 50.421213;
	public static double lng = 30.504870;

	 public LocationFinder(Context context) {
		LocationManager locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location != null){
			LocationFinder.lat = location.getLatitude();
			LocationFinder.lng = location.getLongitude();
		}
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				LocationFinder.lat = location.getLatitude();
				LocationFinder.lng = location.getLongitude();
			}

			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}

		});
	}
}
