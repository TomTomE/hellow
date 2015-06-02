package com.example.hyukmin.hellow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;


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

    private static final String DEBUG_TAG = "HTTP - Beach List";
    private static final String SERVER_IP = "14.50.109.225";
    private static final String SERVER_PORT = "9000";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 키보드가 바로 뜨는 것을 방지
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        ImageView downloadButton;
        downloadButton = (ImageView) findViewById(R.id.search_icon);

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

                new GetBeachList().execute();
            }
        });

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

    private class GetBeachList extends AsyncTask<Void, Void, JSONObject> {

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
        protected JSONObject doInBackground(Void... params) {
            JSONObject _json = new JSONObject();
            try {
                _json = loadBeachList();
                return _json;
            } catch (IOException e) {
                return _json;
            } catch (JSONException e) {
                Log.d(DEBUG_TAG, "The msg is : " + e.getMessage());
            }
            return _json;
        }

        private JSONObject loadBeachList() throws IOException, JSONException {
            String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/beach/속초";

            String result = GetHttpResponseString(strUrl, false, null);
            return new JSONObject(result);
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

        public String GetHttpResponseString(String location, Boolean is_post, String form_values) {
            String result = "";
            String input_line = null;

            try
            {
                InputStream stream = GetHttpResponseStream(location, is_post, form_values);
                InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
                BufferedReader buffer = new BufferedReader(reader);

                while ((input_line = buffer.readLine()) != null) {
                    result += input_line + "/n";
                }
                buffer.close();
                reader.close();
                stream.close();

                return result;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;

        }

        public InputStream GetHttpResponseStream(String location, Boolean is_post, String form_values) {
            InputStream stream = null;

            try {
                URL url = new URL(location);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod(is_post ? "POST" : "GET");
                connection.setRequestProperty("Accept", "application/json");
                connection.setUseCaches(false);
                if (form_values != null) {
                    Log.d("BaseActivity", form_values);
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    os.write(form_values.getBytes("utf-8"));
                    os.flush();
                    os.close();
                }
                stream = connection.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stream;
        }
    }
}
