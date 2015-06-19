package com.example.hyukmin.hellow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends BaseActivity {

    private ListviewAdapter adapter;
    private ListView list;
    private ArrayList<Beach> beachList = new ArrayList<>();
    private AlertDialog.Builder alt_bulid;

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 키보드가 바로 뜨는 것을 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        // 광고 추가
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        TextView mainTitle = (TextView) findViewById(R.id.main_title);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "InterparkGothicBold.ttf"); //폰트 적용
        mainTitle.setTypeface(typeface); //폰트 적용

        EditText searchBeach = (EditText) findViewById(R.id.search_beach);

        //리스트뷰
        list = (ListView)findViewById(R.id.beach_LV);

        adapter = new ListviewAdapter(this, beachList, R.layout.beach_list);
        list.setAdapter(adapter);

        Log.d(DEBUG_TAG_SEARCH, "Listview generated");


        new GetBeachList().execute();



        searchBeach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(DEBUG_TAG_SEARCH, "beforeTextChanged : " + s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(DEBUG_TAG_SEARCH, "onTextChanged : " + s);

                new GetBeachList().execute(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(DEBUG_TAG_SEARCH, "afterTextChanged : " + s);
            }
        });



//        searchBeach.setOnKeyListener(new View.OnKeyListener() {
//
//            /**
//             * Called when a hardware key is dispatched to a view. This allows listeners to
//             * get a chance to respond before the target view.
//             * <p>Key presses in software keyboards will generally NOT trigger this method,
//             * although some may elect to do so in some situations. Do not assume a
//             * software input method has to be key-based; even if it is, it may use key presses
//             * in a different way than you expect, so there is no way to reliably catch soft
//             * input key presses.
//             *
//             * @param v       The view the key has been dispatched to.
//             * @param keyCode The code for the physical key that was pressed
//             * @param event   The KeyEvent object containing full information about
//             *                the event.
//             * @return True if the listener has consumed the event, false otherwise.
//             */
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                Log.d(DEBUG_TAG_SEARCH, "Key pressed");
//                if (event.getAction() == KeyEvent.ACTION_UP) {
//                    Log.d(DEBUG_TAG_SEARCH, "Keycode is : " + keyCode);
//                }
//                return false;
//            }
//
//        });

//        CustomAdapter adapter = new CustomAdapter(MainActivity.this, beachlist, imageId);
//        ListView list=(ListView)findViewById(R.id.beach_LV);
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "You Clicked at " + beachlist[position], Toast.LENGTH_SHORT).show();
//            }
//        });

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
    // 뒤로가기 버튼 눌렀을 경우 - 경고메시지 띄우고 두 번 클릭시 앱 종료
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            MainActivity.this.finish();
            toast.cancel();
        }
    }

    public void showGuide() {
        toast = Toast.makeText(MainActivity.this,
                "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
        toast.show();
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



    public void setListBy(final ArrayList<Beach> _beachList) {
        adapter.clear();

        adapter.addAll(_beachList);

        if(_beachList.size() < 9) {
            for (int i = 0; i < (9 - _beachList.size()); i++) {
                adapter.add(new Beach(""));
            }
        }

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(DEBUG_TAG_HTTP, "Runnable Called");
                MainActivity.this.adapter.updateList();
            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > _beachList.size() - 1) {
                    return;
                }
                //Toast.makeText(MainActivity.this, "You Clicked at " + beachlist[position], Toast.LENGTH_SHORT).show();
                Intent in = new Intent(MainActivity.this, DetailActivity.class);

                Beach temp = adapter.getItem(position);

                in.putExtra("id", temp._id);
                in.putExtra("title", temp._name);

                startActivity(in);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
        });
    }

    //TODO 서버 통신체크, 그에따른 액션 집어넣기.

    private class GetBeachList extends AsyncTask<String, Void, ArrayList<Beach>> {
        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param jsonArray The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alt_bulid = new AlertDialog.Builder(MainActivity.this);
        }

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p/>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */


        @Override
        protected ArrayList<Beach> doInBackground(String... params) {
            //TODO params 안들어올경우 어떻게 되는지 확인.
            Log.d(DEBUG_TAG_SEARCH, "Parameter : " + ((params.length != 0) ? Arrays.toString(params) : "Empty"));
            try {
                //String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/beach/" + ((params.length != 0) ? params[0] : "");
                //String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/beach/경포";


                String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT +
                        "/beach/" + ((params.length != 0) ? URLEncoder.encode(params[0], "UTF-8") : "");
                Log.d(DEBUG_TAG_HTTP, "URL result : " + strUrl);

                String result = GetHttpResponseString(strUrl, false, null);

                Log.d(DEBUG_TAG_HTTP, "String result : " + result);
                if(result != null){
                    beachList = Beach.fromJSON(new JSONArray(result));
                }
            } catch (JSONException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return beachList;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param beachList The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(ArrayList<Beach> beachList) {
            super.onPostExecute(beachList);
            if(beachList.size() == 0){
                alt_bulid.setMessage(Html.fromHtml("현재 서버 통신이 원활하지 않습니다.<br>다시 시도해주시기 바랍니다."))
                        .setCancelable(false)
                        .setPositiveButton("종료", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Action for 'Yes' Button
                                dialog.dismiss();
                                MainActivity.this.finish();
                            }
                        });
                AlertDialog alert = alt_bulid.create();
                // Title for AlertDialog
                alert.setTitle("서버 통신 에러");
                // Icon for AlertDialog
                alert.setIcon(R.drawable.ic_launcher);
                alert.show();
            }
            else {
                setListBy(beachList);
            }
        }

        private ArrayList<Beach> loadBeachList(String... param) throws IOException, JSONException {
            String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/list";
            String result = GetHttpResponseString(strUrl, false, null);
            Log.d(DEBUG_TAG_HTTP, "String result : " + result);

            return Beach.fromJSON(new JSONArray(result));


            /*
            InputStream is = null;
            ByteArrayOutputStream baos;
            String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/beach/속초";
            String response ="";
            int length = 1024;

            JSONObject respJSON = new JSONObject();

            try {
                URL url = new URL(strUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(5000);
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.connect();

                int resp = conn.getResponseCode();
                Log.d(DEBUG_TAG_HTTP, "The response is : " + resp);
                //is = conn.getInputStream();
                //reader = new InputStreamReader(is, "UTF-8");
                //char[] buff = new char[length];
                //reader.read(buff);

                ///////////////////

                is = conn.getInputStream();
                baos = new ByteArrayOutputStream();
                byte[] byteBuffer = new byte[length];
                byte[] byteData;
                int nLength;

                while((nLength = is.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                    baos.write(byteBuffer, 0, nLength);
                }
                byteData = baos.toByteArray();
                response = new String(byteData);
                respJSON  = new JSONObject(response);

                Log.i(DEBUG_TAG_HTTP, "DATA response = " + response);
            } catch (JSONException e) {
                Log.d(DEBUG_TAG_HTTP, "The msg is : " + e.getMessage());
            }
            finally {
                if (is != null) {
                    is.close();
                }
            }
            return respJSON;
            */
        }
    }


}
