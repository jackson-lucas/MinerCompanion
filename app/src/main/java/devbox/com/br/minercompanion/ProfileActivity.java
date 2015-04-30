/**
 *MinerCompanion - Sistema de Alerta para Mineradoras
 *Copyright (C) <2015>  <Jackson Lima, Jean Figueiredo, Victor Valente>
 *
 *This program is free software: you can redistribute it and/or modify
 *it under the terms of the GNU General Public License as published by
 *the Free Software Foundation, either version 3 of the License, or
 *(at your option) any later version.
 *
 *This program is distributed in the hope that it will be useful,
 *but WITHOUT ANY WARRANTY; without even the implied warranty of
 *MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *GNU General Public License for more details.
 *
 *You should have received a copy of the GNU General Public License
 *along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package devbox.com.br.minercompanion;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import devbox.com.br.minercompanion.Utilities.ProfileListAdapter;
import devbox.com.br.minercompanion.Utilities.Sensors;

// Need develop and test connection with server
public class ProfileActivity extends ActionBarActivity implements SensorEventListener {

    //String url = "http://192.168.49.254/miner_companion/admin_server/hash.php";
    String url = "http://32f35102.ngrok.com/miner_companion/admin_server/requests.php";

    final static String TAG = "ProfileActivity";

    private ArrayList<String> strings = new ArrayList<String>();
    private String matricula;

    private SensorManager sensorManager;
    private Sensors sensors;

    private final long startTime = 45 * 1000;
    private final long interval = 1 * 1000;
    private boolean timerHasStarted = false;
    private StringBuilder msg = new StringBuilder(2048);

    SensorCounter sensorCounter;
    ProfileListAdapter profileListAdapter;
    boolean isLoggingOut;
    HttpAsyncTask httpAsyncTask;
    String routerName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {
                //if (connectionInfo != null && !StringUtil.isBlank(connectionInfo.getSSID())) {
                routerName = connectionInfo.getSSID();
            }
        }

        sensorCounter = new SensorCounter(3000, 3000);
        sensorCounter.start();

        Intent intent = getIntent();

        if(intent != null) {
            matricula = intent.getStringExtra("MATRICULA");

            TextView textView = (TextView) findViewById(R.id.textView);

            textView.setText("Matrícula: " + matricula);

            sensors = new Sensors(matricula, routerName);
        }

        /* Get a SensorManager instance */
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        ListView listView = (ListView) findViewById(R.id.listView);
        profileListAdapter = new ProfileListAdapter(this, strings);
        listView.setAdapter(profileListAdapter);

        profileListAdapter.addItem("Conectado ao servidor!");
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), sensorManager.SENSOR_DELAY_GAME);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE), sensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), sensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            JSONObject jsonObject = new JSONObject();
            try {
                isLoggingOut = true;
                jsonObject.put("idDispositivo", matricula);
                jsonObject.put("logout", true);
            } catch (JSONException e) {
                e.printStackTrace();
                isLoggingOut = false;
            }

            new HttpAsyncTask().execute(url, jsonObject.toString());
        }

        return super.onOptionsItemSelected(item);
    }

    /* Alert Depreceated
    public void createAlertDialog() {
        final EditText editText = new EditText(this);
        editText.setId(R.id.editText);
        editText.setText("Você está bem?");



        AlertDialog.Builder observacaoDialog = new AlertDialog.Builder(this);
        observacaoDialog.setTitle("Alerta")
                .setView(editText)
                // Send to answer to server
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }
    */

    @Override
    public void onSensorChanged(SensorEvent event) {

        synchronized (this) {

            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    float[] sensor1 = event.values;
                    sensors.acelerometer = sensor1;
                    //acelerText.setText("x: " + sensor1[0] + " y: " + sensor1[1] + " z: " + sensor1[2]);
                    break;

                case Sensor.TYPE_ORIENTATION:
                    float[] sensor2 = event.values;
                    sensors.orientation = sensor2;
                    //gyroText.setText("x: " + sensor2[0] + " y: " + sensor2[1] + " z: " + sensor2[2]);
                    break;

                case Sensor.TYPE_GYROSCOPE:
                    float[] sensor3 = event.values;
                    sensors.gyroscope = sensor3;
                    //orienText.setText("x: " + sensor3[0] + " y: " + sensor3[1] + " z: " + sensor3[2]);
                    break;

                case Sensor.TYPE_LIGHT:
                    sensors.luminosity = event.values[0];
                    //ligthText.setText("Luminosidade: " + event.values[0]);
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        msg.insert(0, sensor.getName() + " - " + accuracy + (accuracy==1 ? " (LOW)":(accuracy==2 ? " (MED)":(accuracy==2 ? " (HIGH)" : "NULL"))) + "\n");
        //ligthText.setText(msg);
        //ligthText.invalidate();
    }

    private class SensorCounter extends CountDownTimer {

        public SensorCounter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            Log.d("ProfileActivity", "Timer Completed.");

            if(!isLoggingOut) {
                httpAsyncTask = new HttpAsyncTask();
                httpAsyncTask.execute(url, sensors.getAsJson().toString());
                sensorCounter.start();
            } else {
                if(httpAsyncTask != null) {
                    httpAsyncTask.cancel(true);
                }
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //timeText.setText((millisUntilFinished/1000)+"");
            Log.d("ProfileActivity", "Timer  : " + (millisUntilFinished / 1000));
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... data) {

            return POST(data[0], data[1], isLoggingOut);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Log.i("HTTP REQUEST RESULTADO", result);

            if(result.contains("OK")) {
                profileListAdapter.addItem("Dados enviados com sucesso!");
            } else {
                profileListAdapter.addItem("Erro ao tentar conectar-se com o servidor!");
            }

            if(isLoggingOut) {
                profileListAdapter.addItem("Saindo...");
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        sensorCounter.cancel();

        super.onDestroy();
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        Log.d("HTTP REQUEST RESULTADO", "");
        while((line = bufferedReader.readLine()) != null) {
            result = line;
            Log.d(TAG, result);
        }
        Log.d("HTTP REQUEST RESULT FIM", "");

        inputStream.close();
        return result;
    }

    public static String POST(String url, String data, boolean isLoggingOut){
        InputStream inputStream = null;
        String result = "";

        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);


            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                //String usuarios = jsonParser.getAllUsuariosAsJson().toString();

                //Log.i("USUARIOS", usuarios);
                Log.d(TAG, "Dados sendo enviado: " + data);

                //params.add(new BasicNameValuePair("usuarios", usuarios));
                if(isLoggingOut) {
                    params.add(new BasicNameValuePair("logout", data));

                } else {
                    params.add(new BasicNameValuePair("log", data));
                }


                httpPost.setEntity(new UrlEncodedFormEntity(params));

                HttpResponse response = httpclient.execute(httpPost);

                inputStream = response.getEntity().getContent();

            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Resultado Nulo";

        } catch (Exception e) {
            Log.i(TAG, e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }
}
