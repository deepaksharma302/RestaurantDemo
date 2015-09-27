package mentobile.restaurantdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.payu.custombrowser.CustomProgressBar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mentobile.utils.JsonParser;

public class MobileConfirmActivity extends Activity {


    private int inAnimation, outAnimation;
    private CProgressDialog cProgressDialog;
    private String TAG = "MobileConfirmActivity";

    private ArrayList<NameValuePair> listValue;
    private Button btnConfirm;
    private EditText edVerifyCode;
    private String className = "SendSMS";

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(inAnimation, outAnimation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_confirm);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        inAnimation = R.anim.slide_in_left;
        outAnimation = R.anim.slide_out_right;
        btnConfirm = (Button) findViewById(R.id.mobconfirm_btn_confirm);
        edVerifyCode = (EditText) findViewById(R.id.mobconfirm_ed_verifycode);
        className = "SendSMS";
        MyAsynchTask myAsynchTask = new MyAsynchTask();
        myAsynchTask.execute("918826510669", "");
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifiCode = edVerifyCode.getText().toString().trim();
                if (verifiCode != "" && verifiCode.length() != 4) {
                    Toast.makeText(getApplicationContext(), "Invalid Verification code", Toast.LENGTH_SHORT).show();
                } else {
                    className = "VerifyCode";
                    Intent intent = new Intent(MobileConfirmActivity.this, OrderCompleteActivity.class);
                    startActivity(intent);
                    MyAsynchTask myAsynchTask = new MyAsynchTask();
                    myAsynchTask.execute(AddressItem.getAddressItem().getPhone(), verifiCode);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private class MyAsynchTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cProgressDialog = new CProgressDialog(MobileConfirmActivity.this);
            cProgressDialog.setMessage("Please wait...");
            cProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.d(TAG, "::::::Json 1");
            listValue = new ArrayList<NameValuePair>();
            Log.d(TAG,":::::Phone "+params[0]);
            listValue.add(new BasicNameValuePair("phone", params[0]));
            listValue.add(new BasicNameValuePair("code", params[1]));
            JsonParser jsonParser = new JsonParser();
            JSONObject json = jsonParser.makeHttpRequest(className, listValue);
            Log.d(TAG, "::::::Json "+json);
            try {
                return ""+json.getString("description");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return "Invalid";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            cProgressDialog.hide();
            Log.d(TAG, "::::::Result " + result);
            Toast.makeText(getApplicationContext(), "" + result, Toast.LENGTH_SHORT).show();
        }
    }
}