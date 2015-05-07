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
package devbox.com.br.minercompanion.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Sensors {
    public float[] acelerometer;
    public float[] gyroscope;
    public float[] orientation;
    public float luminosity;
    String matricula, routerName;

    public Sensors(String matricula, String routerName) {
        this.matricula = matricula;
        this.routerName = routerName;
    }

    public void setRouterName(String route) {
        this.routerName = route;
    }

    public JSONObject getAsJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("idDispositivo", matricula);

            jsonObject.put("roteador", routerName);

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
