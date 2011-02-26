package ua.com.hakaton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class JsonAgregator {
	public static String get_url = "";
	
	public static void setMarkers(Context context, MapView map){
		List<Overlay> mapOverlays = map.getOverlays();
		RoadItemizedOverlay roadItemizedOverlay = new RoadItemizedOverlay(context.getResources().getDrawable(R.drawable.icon), context);
		try{
			JSONObject data = JsonAgregator.getData(JsonAgregator.get_url);
			JSONArray points_array = data.getJSONArray("points");
			int count = points_array.length();
			for (int i = 0; i < count; ++i) {
			    JSONObject row = points_array.getJSONObject(i);
			    long lat = row.getLong("lat");
			    long lng = row.getLong("lng");
			    String amplitude = row.getString("aplitude");
			    GeoPoint point = new GeoPoint((int)(lat*1e6),(int)(lng*1e6));
			    RoadOverlayItem overlayitem = new RoadOverlayItem(point, context.getResources().getString(R.string.ammplitude_title), amplitude);
			    roadItemizedOverlay.addOverlay(overlayitem);
			}
		} catch (JSONException e) {
			//Log.i("dataCollector", "error data");
			//e.printStackTrace();
		} catch (Exception e) { 
	    	//e.printStackTrace();
	    	//Log.v("dataCollector","Exception");
	    }
		mapOverlays.add(roadItemizedOverlay);
	}
	
	public static void setData(int amplitude){
		
	}
	
	public static JSONObject getData(String url){
		JSONObject return_data = null;
		HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Accept", "application/json");
        httpget.setHeader("Content-type", "application/json");
        
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            //Log.i("JsonClient",response.getStatusLine().toString());
 
            // Get hold of the response entity
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            // to worry about connection release
 
            if (entity != null) {
                // A Simple JSON Response Read
                InputStream instream = entity.getContent();
                String result= convertStreamToString(instream);
                // A Simple JSONObject Creation
                return_data=new JSONObject(result);
                // Closing the input stream will trigger connection release
                instream.close();
            }

        } catch (ClientProtocolException e) {
            //e.printStackTrace();
            //Log.v("JsonClient","ClientProtocolException");
        } catch (IOException e) {
            //e.printStackTrace();
            //Log.v("JsonClient","IOException");
        } catch (JSONException e) {
            //e.printStackTrace();
            //Log.v("JsonClient","JSONException");
        } catch (Exception e) { 
        	//e.printStackTrace();
        	//Log.v("JsonClient","Exception");
        }
        
        return return_data;
	}
	
	private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        return sb.toString();
    }

}