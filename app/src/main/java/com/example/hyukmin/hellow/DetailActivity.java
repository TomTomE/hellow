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

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

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

    public String[] weatherName = {
            "맑음",
            "태풍1",
            "여우비",
            "태풍2",
            "태풍3",
            "여우비",
            "여우비",
            "맑음",
            "맑음",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14",
            "15",
            "16",
            "17",
            "18",
            "19",
            "20"

    };

    Integer[] weatherImage = {
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

        // 서버 통신



        // Header에 이름(임시)

        TextView title = (TextView) findViewById(R.id.detail_title);
        title.setText((CharSequence) in.getExtras().get("title"));

        // 오늘 날씨, 기온, 강수량, 주차가능
        TextView temp_text = (TextView) findViewById(R.id.temp_text);
        ImageView today_img = (ImageView) findViewById(R.id.today_img);
        TextView today_text = (TextView) findViewById(R.id.today_text);
        TextView rainfall_text = (TextView) findViewById(R.id.rainfall_text);
        TextView parking_text = (TextView) findViewById(R.id.parking_text);

        today_img.setImageResource(R.drawable.image3);
        today_text.setText(R.string.today_weather);
        temp_text.setText("29" + "℃");
        rainfall_text.setText("120" + "mm");
        parking_text.setText(R.string.parking);

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
                if(actionId == EditorInfo.IME_ACTION_SEND || event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                    Log.d("SEND","-------ok-----------");
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

    // 뒤로가기 버튼 눌렀을 경우 - 한 번 클릭시 페이지 리프레쉬하고 두 번 클릭시 메인페이지로 이동
    public void onBackPressed() {
        Intent i = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
