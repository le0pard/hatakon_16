package ua.com.hakaton;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Team16 extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        ((Button)findViewById(R.id.btnStart)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(new Intent(Team16.this, Graph.class));
			}
        });
        
        ((Button)findViewById(R.id.btnMap)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO: Modifi it
				//startActivity(new Intent(Team16.this, Graph.class));
			}
        });
    }
}