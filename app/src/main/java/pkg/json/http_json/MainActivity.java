package pkg.json.http_json;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        //textViewUrl = (TextView)findViewById(R.id.textViewUrl);
        textViewTime = (TextView)findViewById(R.id.textViewTime);
        editTextUrl = (EditText)findViewById(R.id.editTextUrl);
        buttonGet = (Button)findViewById(R.id.buttonGet);
        textViewTime = (TextView)findViewById(R.id.textViewTime);
        editTextResponse = (EditText)findViewById(R.id.editTextResponse);

        editTextUrl.setText("https://drive-rcd.tk/test.php");
    }

    public void onButtonGetTest(View view) {
        Toast.makeText(this,"click test",Toast.LENGTH_SHORT).show();
    }

    public void onButtonGet(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(editTextUrl.getText().toString());
                    // 処理開始時刻
                    startTime = System.currentTimeMillis();
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    final String str = InputStreamToString(con.getInputStream());
                    // 処理終了時刻
                    endTime = System.currentTimeMillis();
                    Log.d("HTTP", str);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            editTextResponse.setText(String.valueOf(str));
                            textViewTime.setText("処理時間：" + (endTime - startTime) + "ms");
                        }
                    });
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }
        }).start();
    }

    static String InputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
