package com.example.hyukmin.hellow;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/*
 * Created by hyukmmin on 2015-06-01.
 */
public class DetailActivity extends BaseActivity {
    Intent in = null;

    private ListView comment_LV;
    private ArrayList<Paper> papers = new ArrayList<>();
    private CommentViewAdapter adapter;

    private InputMethodManager mInputMethodManager;
    private EditText key_send_btn;

    private TextView temp_text; // 오늘의 기온 텍스트
    private ImageView today_img; // 오늘의 날씨 이미지
    private TextView today_text; // 오늘의 날씨 텍스트
    private TextView rainfall_text; // 오늘의 강수량 텍스트
    private TextView parking_text; // 주차가능 여부 텍스트

    private long backKeyPressedTime = 0;
    private Toast toast;

    public String[] comment_list = {
            "거기 날씨 어때요?",
            "개구림",
            "낙산 쪽은 괜찮아요",
            "ㅎㅎ",
            "저거 거짓말",
            "뭐가 진짜임?",
            "날씨 좋아용~",
            "입수는 가능한가요?",
            "네 다들 잘놀고 있어요~",
            "숙소는 어디가 좋아요?",
            "이 앞에 널린게 숙소임",
            "아하",
            "ㅋㅋㅋㅋㅋㅋ"
    };

    Integer[] comment_Img = {
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1,
            R.drawable.man2,
            R.drawable.man3,
            R.drawable.man4,
            R.drawable.man5,
            R.drawable.man6,
            R.drawable.man1
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        // 광고 추가
        AdView mAdView = (AdView) findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        in = getIntent();

        temp_text = (TextView) findViewById(R.id.temp_text);
        today_text = (TextView) findViewById(R.id.today_text);
        rainfall_text = (TextView) findViewById(R.id.rainfall_text);
        parking_text = (TextView) findViewById(R.id.parking_text);
        today_img = (ImageView) findViewById(R.id.today_img);

        // Header에 이름(임시)
        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText((CharSequence) in.getExtras().get("title"));

        new GetWeather().execute(in.getExtras().get("id").toString());

        // 댓글 작업(임시)
        adapter = new CommentViewAdapter(this, papers, R.layout.comment_list);
        comment_LV = (ListView)findViewById(R.id.comment_LV);
        comment_LV.setAdapter(adapter);

        new GetPapers().execute(in.getExtras().get("id").toString());

        mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        key_send_btn = (EditText) findViewById(R.id.comment_input);

        // 댓글레이아웃 전송 버튼 눌렀을 때 처리
        Button send_btn = (Button) findViewById(R.id.comment_send_btn);
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("SEND_BTN","-------ok-----------");
                // 내용 전송 처리

                //전송 후 처리
                key_send_btn.setText(null);
                mInputMethodManager.hideSoftInputFromWindow(key_send_btn.getWindowToken(), 0);// 키보드 내리기
            }
        });

        // 키보드 전송 버튼 눌렀을 때 처리
        key_send_btn.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Log.d("SEND", "-------ok-----------");
                    // 내용 전송 처리

                    new PostPapers().execute(in.getExtras().get("id").toString(), key_send_btn.getText().toString());

                    // 전송 후 처리
                    key_send_btn.setText(null);
                    mInputMethodManager.hideSoftInputFromWindow(key_send_btn.getWindowToken(), 0); // 키보드 내리기
                }
                return false;
            }
        });

        /*
        comment_LV.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                sv.requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
        */

    }

    // 뒤로가기 버튼 눌렀을 경우 - 클릭시 메인페이지로 이동
    public void onBackPressed() {
        Intent i = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    // 날씨 받아오는 함수
    private class GetWeather extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject weather = new JSONObject();
            try {
                String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT +
                        "/beaches/" + ((params.length != 0) ? URLEncoder.encode(params[0], "UTF-8") : "");
                Log.d(DEBUG_TAG_HTTP, "URL result : " + strUrl);
                String result = GetHttpResponseString(strUrl, false, null);
                Log.d(DEBUG_TAG_HTTP, "String result : " + result);
                weather = new JSONObject(result);

            } catch (JSONException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            try {
                JSONObject weather = (JSONObject) jsonObject.get("weather");

                // 오늘 날씨, 기온, 강수량, 주차가능

                String rainfall = weather.get("RN1").toString();
                String sky = weather.get("SKY").toString();
                if(rainfall.equals("1mm 미만") || rainfall.equals("1~4mm") || rainfall.equals("5~9mm")){
                    rainfall = "얕은 비";
                    if(sky.equals("맑음")){
                        today_text.setText(rainfall);
                        today_img.setImageResource(R.drawable.weather5);
                    }
                    else if(sky.equals("구름조금")){
                        today_text.setText(rainfall+"와 "+sky);
                        today_img.setImageResource(R.drawable.weather6);
                    }
                    else if(sky.equals("구름많음")){
                        today_text.setText(rainfall+"와 "+ sky);
                        today_img.setImageResource(R.drawable.weather7);
                    }
                    else {
                        today_text.setText("흐리고 "+rainfall);
                        today_img.setImageResource(R.drawable.weather8);
                    }
                }

                else if(rainfall.equals("10~19mm") || rainfall.equals("20~39mm") || rainfall.equals("40~69mm")){
                    rainfall = "비";
                    today_text.setText(rainfall);
                    if(sky.equals("맑음")){
                        today_img.setImageResource(R.drawable.weather9);
                    }
                    else if(sky.equals("구름조금")){
                        today_img.setImageResource(R.drawable.weather10);
                    }
                    else if(sky.equals("구름많음")){
                        today_img.setImageResource(R.drawable.weather11);
                    }
                    else {
                        today_img.setImageResource(R.drawable.weather12);
                    }
                }

                else if(rainfall.equals("70mm 이상")){
                    rainfall = "폭우";
                    today_text.setText(rainfall);
                    if(sky.equals("맑음")){
                        today_img.setImageResource(R.drawable.weather13);
                    }
                    else if(sky.equals("구름조금")){
                        today_img.setImageResource(R.drawable.weather14);
                    }
                    else if(sky.equals("구름많음")){
                        today_img.setImageResource(R.drawable.weather15);
                    }
                    else {
                        today_img.setImageResource(R.drawable.weather16);
                    }
                }

                else {
                    rainfall = "";
                    today_text.setText(sky);
                    if(sky.equals("맑음")){
                        today_img.setImageResource(R.drawable.weather1);
                    }
                    else if(sky.equals("구름조금")){
                        today_img.setImageResource(R.drawable.weather2);
                    }
                    else if(sky.equals("구름많음")){
                        today_img.setImageResource(R.drawable.weather3);
                    }
                    else {
                        today_img.setImageResource(R.drawable.weather4);
                    }
                }

                temp_text.setText(weather.get("T1H").toString());
                rainfall_text.setText(weather.get("RN1").toString());
                parking_text.setText(R.string.parking);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class PostPapers extends AsyncTask<String, Void, ArrayList<Paper>> {

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
        protected ArrayList<Paper> doInBackground(String... params) {
            JSONObject body = new JSONObject();
            try {
                body.put("boardId", params[0]);
                body.put("contents", params[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/chat/";
            Log.d(DEBUG_TAG_HTTP, "URL result : " + strUrl);
            String result = GetHttpResponseString(strUrl, true, body);
            Log.d(DEBUG_TAG_HTTP, "String result : " + result);

            try {
                JSONObject obj = new JSONObject(result);
                papers = Paper.fromJSON(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return papers;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param object The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(ArrayList<Paper> object) {
            super.onPostExecute(object);

            adapter.clear();

            adapter.addAll(papers);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(DEBUG_TAG_HTTP, "Runnable Called");
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private class GetPapers extends AsyncTask<String, Void, ArrayList<Paper>> {
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
        protected ArrayList<Paper> doInBackground(String... params) {
            String strUrl = "http://" + SERVER_IP + ":" + SERVER_PORT + "/chat/" + params[0];
            Log.d(DEBUG_TAG_HTTP, "URL result : " + strUrl);
            String result = GetHttpResponseString(strUrl, false, null);
            Log.d(DEBUG_TAG_HTTP, "String result : " + result);


            try {
                JSONObject obj = new JSONObject(result);
                papers = Paper.fromJSON(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return papers;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p/>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param papers The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(ArrayList<Paper> papers) {
            super.onPostExecute(papers);
            adapter.clear();

            adapter.addAll(papers);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(DEBUG_TAG_HTTP, "Runnable Called");
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
}
