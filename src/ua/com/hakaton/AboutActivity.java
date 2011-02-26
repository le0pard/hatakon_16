package ua.com.hakaton;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;


public class AboutActivity extends Activity {
	ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.about);
        
        ListView aboutList = (ListView) findViewById(R.id.aboutList); 
        
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("line1", "Артем Витюк");
        item.put("line2", "artem.limp@gmail.com");
        list.add(item);
        item = new HashMap<String, String>();
        item.put("line1", "Иван Гриниченко");
        item.put("line2", "g-r-i-n@yandex.ru");
        list.add(item);
        item = new HashMap<String, String>();
        item.put("line1", "Алексей Устенко");
        item.put("line2", "alexey.ustenko@gmail.com");
        list.add(item);
        item = new HashMap<String, String>();
        item.put("line1", "Борис Працюк");
        item.put("line2", "b.pratsiuk@gmail.com");
        list.add(item);
        item = new HashMap<String, String>();
        item.put("line1", "Алексей Васильев");
        item.put("line2", "leopard_ne@inbox.ru ");
        list.add(item);
        
        SimpleAdapter notes = new SimpleAdapter(
        		this,
        		list,
        		R.layout.list_item,
        		new String[] { "line1","line2" },
        		new int[] { R.id.text1, R.id.text2 } );
        
        
        aboutList.setAdapter(notes);
        
        
        
	}


}
