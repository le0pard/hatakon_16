package ua.com.hakaton;

import java.util.ArrayList;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;

public class RoadItemizedOverlay extends ItemizedOverlay<RoadOverlayItem> {
	private ArrayList<RoadOverlayItem> mOverlays = new ArrayList<RoadOverlayItem>();
	private Context myContext = null;
	
	
	public RoadItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.myContext = context;
	}
	
	public void addOverlay(RoadOverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	@Override
	protected RoadOverlayItem createItem(int i) {
		return mOverlays.get(i);
	}
	
	@Override
	protected boolean onTap(int index) {
		RoadOverlayItem item = mOverlays.get(index);
		AlertDialog.Builder dialog = new AlertDialog.Builder(this.myContext);
		dialog.setTitle(item.getTitle());
		dialog.setMessage(item.getSnippet());
		dialog.show();
		return true;
	}

	@Override
	public int size() {
		return mOverlays.size();
	}
}
