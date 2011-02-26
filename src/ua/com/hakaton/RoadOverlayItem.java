package ua.com.hakaton;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class RoadOverlayItem extends OverlayItem {

		public RoadOverlayItem(GeoPoint point, String title, String snippet) {
			super(point, title, snippet);
		}
}
