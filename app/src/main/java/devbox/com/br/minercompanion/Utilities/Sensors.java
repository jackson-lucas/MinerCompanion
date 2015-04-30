package devbox.com.br.minercompanion.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sensors {
    public float[] acelerometer;
    public float[] gyroscope;
    public float[] orientation;
    public float luminosity;
    String matricula;

    public Sensors(String matricula) {
        this.matricula = matricula;
    }

    public JSONObject getAsJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("idDispositivo", matricula);

            jsonObject.put("valorLuminosidade", luminosity);

            JSONArray jsonArray = new JSONArray();

            if(acelerometer != null && acelerometer.length > 2) {
                jsonArray.put(acelerometer[0]);
                jsonArray.put(acelerometer[1]);
                jsonArray.put(acelerometer[2]);
                jsonObject.put("valorAcelerometro", jsonArray);
            }

            if(gyroscope != null && gyroscope.length > 2) {
                jsonArray = new JSONArray();
                jsonArray.put(gyroscope[0]);
                jsonArray.put(gyroscope[1]);
                jsonArray.put(gyroscope[2]);
                jsonObject.put("valorGiroscopio", jsonArray);
            }

            if(orientation != null && orientation.length > 2) {
                jsonArray = new JSONArray();
                jsonArray.put(orientation[0]);
                jsonArray.put(orientation[1]);
                jsonArray.put(orientation[2]);
                jsonObject.put("valorOrientacao", jsonArray);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
