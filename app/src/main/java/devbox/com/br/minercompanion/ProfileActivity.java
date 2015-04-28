package devbox.com.br.minercompanion;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import devbox.com.br.minercompanion.Utilities.ProfileListAdapter;

// Need develop and test connection with server
public class ProfileActivity extends ActionBarActivity {

    private ArrayList<String> strings = new ArrayList<String>();
    private String matricula;

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


        ListView listView = (ListView) findViewById(R.id.listView);
        ProfileListAdapter profileListAdapter = new ProfileListAdapter(this, strings);
        listView.setAdapter(profileListAdapter);
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
}
