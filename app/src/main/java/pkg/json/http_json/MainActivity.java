package pkg.json.http_json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView textViewUrl;
    EditText editTextUrl;
    Button buttonGet;
    EditText editTextResponse;
    TextView textViewTime;
    long startTime;
    long endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btClick = findViewById(R.id.buttonGet);
        HTTP_BTN listener = new HTTP_BTN();
        btClick.setOnClickListener(listener);

        //textViewUrl = (TextView)findViewById(R.id.textViewUrl);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        editTextUrl = (EditText) findViewById(R.id.editTextUrl);
        buttonGet = (Button) findViewById(R.id.buttonGet);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        editTextResponse = (EditText) findViewById(R.id.editTextResponse);

        editTextUrl.setText("https://drive-rcd.tk/test.php");
    }

    private class HTTP_BTN implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(editTextUrl.getText().toString());
                        // 処理開始時刻
                        startTime = System.currentTimeMillis();
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        final String str = InputStreamToString(con.getInputStream());
                        // 処理終了時刻
                        endTime = System.currentTimeMillis();
                        Log.d("HTTP", str);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                editTextResponse.setText(
                                        Json_Ana(String.valueOf(str))
                                );
                                textViewTime.setText("処理時間：" + (endTime - startTime) + "ms");
                            }
                        });
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            }).start();
        }

        String InputStreamToString(InputStream is) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        }

        public String Json_Ana(String str) {
            String display = "";

            try {
                JSONObject json = new JSONObject(str);
                display = json.toString(4);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return display;
        }


    }
}