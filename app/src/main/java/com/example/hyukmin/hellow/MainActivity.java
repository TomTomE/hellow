package com.example.hyukmin.hellow;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends BaseActivity {
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

    private static final String DEBUG_TAG = "HTTP - Beach List";
    private static final String SERVER_IP = "14.50.109.225";
    private static final String SERVER_PORT = "9000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 키보드가 바로 뜨는 것을 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        EditText searchBeach = (EditText) findViewById(R.id.search_beach);
        ImageView downloadButton = (ImageView) findViewById(R.id.search_icon);

        new GetBeachList().execute();


        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*

                //TODO ConnectivityManager 사용법 예시

                String strUrl = mEditText.getText().toString();
				try {
					if (strUrl != null && strUrl.length() > 0) {
						ConnectivityManager conMgr = (ConnectivityManager)
								getSystemService(Context.CONNECTIVITY_SERVICE);
						NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
						if (netInfo != null && netInfo.isConnected()) {
							new DownloadWebpageText().execute(strUrl);
						} else {
							throw new Exception(getString(R.string.network_error));
						}
					} else {
						throw new Exception(getString(R.string.bad_url));
					}
				} catch (Exception e) {
					mTextView.setText(e.getMessage());
				}
                */
            }
        });


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


    private class GetBeachList extends AsyncTask<Void, Void, JSONArray> {
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
        protected JSONArray doInBackground(Void... params) {
            JSONArray jsonData = new JSONArray();
            try {
                jsonData = loadBeachList();
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return jsonData;
        }

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
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            String[] baechList = new String[jsonArray.length()];
            JSONObject tmp =null;
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    baechList[i] = (String) jsonArray.getJSONObject(i).get("_name");
                    Log.d(DEBUG_TAG, "BeachList  : " + baechList[i]);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            CustomAdapter adapter = new CustomAdapter(MainActivity.this, baechList, imageId);
            ListView list=(ListView)findViewById(R.id.beach_LV);
            list.setAdapter(adapter);
        }

        private JSONArray loadBeachList() throws IOException, JSONException {
            String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/list";

            String result = GetHttpResponseString(strUrl, false, null);

            Log.d(DEBUG_TAG, "String result : " + result);
            return new JSONArray(result);
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
                Log.d(DEBUG_TAG, "The response is : " + resp);
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

                Log.i(DEBUG_TAG, "DATA response = " + response);
            } catch (JSONException e) {
                Log.d(DEBUG_TAG, "The msg is : " + e.getMessage());
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
