package devbox.com.br.minercompanion;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import devbox.com.br.minercompanion.Utilities.ProfileListAdapter;
import devbox.com.br.minercompanion.Utilities.Sensors;

// Need develop and test connection with server
public class ProfileActivity extends ActionBarActivity implements SensorEventListener {

    private ArrayList<String> strings = new ArrayList<String>();
    private String matricula;

    private SensorManager sensorManager = null;
    private Sensors sensors = new Sensors();

    private final long startTime = 45 * 1000;
    private final long interval = 1 * 1000;
    private boolean timerHasStarted = false;
    private StringBuilder msg = new StringBuilder(2048);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();

        if(intent != null) {
            matricula = intent.getStringExtra("MATRICULA");

            TextView textView = (TextView) findViewById(R.id.textView);

            textView.setText("Matrícula: " + matricula);
        }

        strings.add("Conectando-se ao servidor...");

        /* Get a SensorManager instance */
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        ListView listView = (ListView) findViewById(R.id.listView);
        ProfileListAdapter profileListAdapter = new ProfileListAdapter(this, strings);
        listView.setAdapter(profileListAdapter);
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
        if (id == R.id.action_settings) {
            return true;
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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

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
            //timeText.setText("Timer Completed.");
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //timeText.setText((millisUntilFinished/1000)+"");
            Log.d("ProfileActivity", "Timer  : " + (millisUntilFinished / 1000));
        }
    }
}
