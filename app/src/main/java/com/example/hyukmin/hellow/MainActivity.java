package com.example.hyukmin.hellow;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity {

    public String[] beachlist = {
            "해운대해수욕장",
            "경포대해수욕장",
            "중문해수욕장",
            "격포해수욕장",
            "만리포해수욕장",
            "광안리해수욕장",
            "대천해수욕장",
            "해운대해수욕장",
            "경포대해수욕장",
            "중문해수욕장",
            "격포해수욕장",
            "만리포해수욕장",
            "광안리해수욕장",
            "대천해수욕장",
            "해운대해수욕장",
            "경포대해수욕장",
            "중문해수욕장",
            "격포해수욕장",
            "만리포해수욕장",
            "광안리해수욕장",
            "대천해수욕장"
    };

    Integer[] imageId = {
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7,
            R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4,
            R.drawable.image5,
            R.drawable.image6,
            R.drawable.image7

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 키보드가 바로 뜨는 것을 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        CustomAdapter adapter = new CustomAdapter(MainActivity.this, beachlist, imageId);
        ListView list=(ListView)findViewById(R.id.beach_LV);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "You Clicked at " + beachlist[position], Toast.LENGTH_SHORT).show();
            }
        });

        // 해수욕장 리스트 동적 생성(임시리스트)
        /*
        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrlist);
        ListView beach_list = (ListView)findViewById(R.id.beach_LV);
        beach_list.setAdapter(Adapter);
        */
        // 해수욕장 클릭시
        /*
        beach_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //intent 변환
                Intent i = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(i);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
