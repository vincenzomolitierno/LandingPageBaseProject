package me.systemoutprintln.postistorytelling.xfarmapi;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

public class ApiConnector {

    public static String USERNAME = "iot@idealprogetti.com";
    public static String PASSWORD = "idealprogetti2018";
    public static String LOGIN_URL = "https://api.xfarm.ag/api/public/v1/auth/login";
    public static String XSENSE_URL = "https://api.xfarm.ag/api/private/v1/xsense/deviceData";

    /*
     * https://api.xfarm.ag/api/private/v1/xsense/deviceData
     * deviceID=A646423769113934 from=2020-02-01 periodTo=1h limit=2
     * onlyImages=false lang=it
     */

    // LOGIN CREDENTIALS POST REQUEST
    public static String postRequest() {

        HttpClient httpClient = HttpClientBuilder.create().build(); // Use this instead

        try {
            HttpPost request = new HttpPost(LOGIN_URL);
            StringEntity params = new StringEntity(
                    "{\"username\":\"" + USERNAME + "\",\"password\":\"" + PASSWORD + "\"} ");

            request.addHeader("content-type", "application/json");
            request.setEntity(params);

            System.out.println("making request");
            HttpResponse response = httpClient.execute(request);
            System.out.println(response);

            String json = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(json);

            JSONObject jsonObject = new JSONObject(json);
            String token = jsonObject.getString("access_token");
            int expire = jsonObject.getInt("expires_in");

            return token;

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();
            System.out.println("unsupportedencoding");

        } catch (IOException e) {

            e.printStackTrace();
            System.out.println("ioexception");

        }

        System.out.println("Error retrieving token!");
        return null;
    }

    /*
    *   
    */
    public static String getRequest(String token) {
        HttpClient httpClient = HttpClientBuilder.create().build(); // Use this instead
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            URIBuilder uriBuilder = new URIBuilder(XSENSE_URL);
            uriBuilder.setParameter("deviceID", "89462038005001892349")
                    // .setParameter("from", sdf.format(System.currentTimeMillis()))//-604800000
                    .setParameter("from", "2020-02-01T00:00:00.001Z").setParameter("periodTo", "1h")
                    .setParameter("limit", "2").setParameter("onlyImages", "false").setParameter("lang", "it");
            // System.out.println("Data:
            // "+sdf.format(System.currentTimeMillis()));//-604800000
            HttpGet request = new HttpGet(uriBuilder.build());

            // HttpGet request = new
            // HttpGet("https://api.xfarm.ag/api/private/v1/xsense/devices/186?
            // deviceID=89462038005001892349&from=2019-05-13T14:16:35.471Z&periodTo=1d&limit=null&onlyImages=false&lang=it");

            System.out.println(request.toString());

            request.addHeader("Authorization", "Bearer " + token);
            HttpResponse response = httpClient.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            System.out.println(builder);

            return builder.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> getPhoto() {
        List<String> photo = new LinkedList<>();
        String token = postRequest();
        String jsonString = getRequest(token);
        // jsonString = "{k:"+jsonString+"}";
        jsonString = "[" + jsonString + "]";
        JSONArray jsonResponse = new JSONArray(jsonString);
        System.out.println("----------------JSONRESPONSE-----------------------------------------------");
        System.out.println(jsonResponse);
        try {
            for (Object o : jsonResponse) {
                JSONObject jsonObject = new JSONObject(o.toString());
                System.out.println(jsonObject);
                String pname = jsonObject.getString("pname");
                String time = jsonObject.getString("time");
                photo.add(pname + " " + time);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        System.out.println(photo);
        return photo;
    }

    public static String getCoordinate() {

        try {

            // List<String> photo = new LinkedList<>();
            String token = postRequest();
            String jsonString = getRequest(token);
            // jsonString = "{k:"+jsonString+"}";
            jsonString = "[" + jsonString + "]";
            JSONArray jsonResponse = new JSONArray(jsonString);

            String lat = "";
            String lng = "";

            for (Object o : jsonResponse) {
                JSONObject jsonObject = new JSONObject(o.toString());
                System.out.println(jsonObject);
                lat = Double.toString(jsonObject.getDouble("lat"));
                lng = Double.toString(jsonObject.getDouble("lng"));
                // String lat = jsonObject.getString("lat");
                // String lng = jsonObject.getString("lng");
                // photo.add(pname+" "+time);
            }
            // System.out.println(photo);
            System.out.println("COORDINATE " + jsonResponse);

            return lat.replace(".", ",") + "/" + lng.replace(".", ",");

            // return jsonResponse.getJSONObject(0).getString("pos");

        } catch (Exception e) {
            // TODO: handle exception
            return "KO";
        }
    }

    public static String getTempMedia() {
        String token = postRequest();
        String jsonString = getRequest(token);
        // jsonString = "{k:"+jsonString+"}";
        JSONArray jsonResponse = new JSONArray(jsonString);
        double temp = 0;
        for (Object o : jsonResponse) {
            JSONObject jsonObject = new JSONObject(o.toString());
            temp += jsonObject.getDouble("at");
        }
        double tempMedia = temp / jsonResponse.length();
        String tempMediaStr = tempMedia + "";
        return tempMediaStr.substring(0, tempMediaStr.length() - 1);
    }

    public static String getPH() {
        try {
            String token = postRequest();
            String jsonString = getIdealProgettiRequest(token);
            JSONArray jsonResponse = new JSONArray(jsonString);
            String PH = "";
            for (Object o : jsonResponse) {
                JSONObject jsonObject = new JSONObject(o.toString());
                System.out.println(jsonObject);
                PH = Double.toString(jsonObject.getDouble("ph"));
            }
            return PH;
        } catch (Exception e) {
            return "ERRORE - PH non letto";
        }
    }

    public static String getNT1() {
        try {
            String token = postRequest();
            String jsonString = getIdealProgettiRequest(token);
            JSONArray jsonResponse = new JSONArray(jsonString);
            String NT1 = "";
            for (Object o : jsonResponse) {
                JSONObject jsonObject = new JSONObject(o.toString());
                System.out.println(jsonObject);
                NT1 = Double.toString(jsonObject.getDouble("nt1"));
            }
            return NT1;
        } catch (Exception e) {
            return "ERRORE - NT1 non letto";
        }
    }

    public static String getSensorTime() {
        try {
            String token = postRequest();
            String jsonString = getIdealProgettiRequest(token);
            JSONArray jsonResponse = new JSONArray(jsonString);
            String sensor_time = "";
            for (Object o : jsonResponse) {
                JSONObject jsonObject = new JSONObject(o.toString());
                System.out.println(jsonObject);
                sensor_time = jsonObject.getString("time");
            }
            return sensor_time;
        } catch (Exception e) {
            return "ERRORE - Tempo non letto";
        }
    }    

    public static String getIdealProgettiRequest(String token) {
        HttpClient httpClient = HttpClientBuilder.create().build(); // Use this instead
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            URIBuilder uriBuilder = new URIBuilder(XSENSE_URL);
            uriBuilder.setParameter("deviceID", "A646423769113934")
                    // .setParameter("from", sdf.format(System.currentTimeMillis()))//-604800000
                    .setParameter("from", "2020-02-01T00:00:00.001Z").setParameter("periodTo", "1h")
                    .setParameter("limit", "1").setParameter("onlyImages", "false").setParameter("lang", "it");
            HttpGet request = new HttpGet(uriBuilder.build());

            // HttpGet request = new
            // HttpGet("https://api.xfarm.ag/api/private/v1/xsense/devices/186?
            // deviceID=89462038005001892349&from=2019-05-13T14:16:35.471Z&periodTo=1d&limit=null&onlyImages=false&lang=it");

            System.out.println(request.toString());
            request.addHeader("Authorization", "Bearer " + token);
            HttpResponse response = httpClient.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = bufReader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            System.out.println(builder);

            return builder.toString();

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
