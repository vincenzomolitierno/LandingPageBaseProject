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

    // public static String USERNAME = "eric_solis07@hotmail.it";
    // public static String PASSWORD = "lr91WrCI";
    public static String USERNAME = "callposti@idealprogetti.com";
    public static String PASSWORD = "posti2019";
    public static String LOGIN_URL = "https://api.xfarm.ag/api/public/v1/auth/login";
    // public static String XSENSE_URL = "https://api.xfarm.ag/api/private/v1/xsense/deviceData";
    public static String XSENSE_URL = "https://api.xfarm.ag/api/private/v1/xsense/devices/186";

    //LOGIN CREDENTIALS POST REQUEST
    public static String postRequest() {

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {
            HttpPost request = new HttpPost(LOGIN_URL);
            StringEntity params = new StringEntity("{\"username\":\"" + USERNAME + "\",\"password\":\""+PASSWORD+"\"} ");

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

    //HttpResponseProxy{HTTP/1.1 200 OK [Cache-Control: no-store, Content-Type: application/json;charset=UTF-8, Date: Mon, 24 Jun 2019 15:35:02 GMT, Pragma: no-cache, Server: Apache-Coyote/1.1, Vary: Accept-Encoding, X-Application-Context: application:production, Connection: keep-alive] org.apache.http.client.entity.DecompressingEntity@6127a7e}
    //{"username":"demo@xfarm.ag","roles":["ROLE_FARM_ADMIN"],"token_type":"Bearer","access_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTTVU4VVFSajk5cndMSmtRRkUwd3NzQkU3TTVkb2VZMEhnc0VzaCtHOEJoUEozTzUzNjhEc3pEb3pDM3NOdVVvS0NveEtZdlF2OEUrMDhRY1lMV3lwYmYxbUQ5alRoampWNUp1Mzc3M3Z2VDA1aFlZMThEQXhYRWpMTXBrblFqR2JHYUVTaTFGdWhCdXkzS0tKMFpXSUp5V3dSeE1ZbjZBR1FRZzFFVHU0R1c3elhkNlVYQ1hOOWY0MlJxNVZHSGlnVFhMR09EQTh4VDF0ZHRnRmQ2UU5cL2lWUVVRZWZhakMxQ2JNOGluU3VYRWVyNVNJVEJ1Tk5tS2xtb1k1MlwvR2d1b2hkVVRuQnBKNkZUcUhoZlloekNOTVwvZEswMnFBcTJERzJPenVST3kyVVhYQ3VGcXhxMGxkXC85czBuWGV1blwvM05oVnQ4QnIyb1Y1a0FSM0s3cDZITXNcL0RsclNVdExYUXlpNzBWS3BqTVJCZW5QaEg4KysrSFgwZTlXb0FsTW45eTcrcDVyY1hZZlRsNWU4N1pkQkI1T0RXaFBVSzFpb3ljak5iTVQ4MzZKV1wvZjN6MlwvdmowNE1VVlV2YUlsZlwvdlk2RjlsdHh3U2FjWk45enBpWTZJZHFcL3U3MFMrZURuNWVRdEQxaFZwSnBIK0tPVXd2cENvaUduZHV0SHlQR1wvcWJHTTlYTjVhYVcrc2JiVWZyNjEyXC9PeGFqS2wrVkF5NFNSbFB5TUwxTWdGZkhRczFGWGY0NiszWG83c1wvaU80cE5IYTV6SkVLbUtsQW5UenRvM2x6Y2p3XC9cL2VIbllibk8yRUxqNEE4allRbm9IZ01BQUE9PSIsInN1YiI6ImRlbW9AeGZhcm0uYWciLCJyb2xlcyI6WyJST0xFX0ZBUk1fQURNSU4iXSwiZXhwIjoxNTYxNTYzMzAyLCJpYXQiOjE1NjEzOTA1MDJ9.vZx39Tae1Tj9zbnkNchjus0P3b6z3qCjQElNf0oKqew","expires_in":172800,"refresh_token":"eyJhbGciOiJIUzI1NiJ9.eyJwcmluY2lwYWwiOiJINHNJQUFBQUFBQUFBSlZTTVU4VVFSajk5cndMSmtRRkUwd3NzQkU3TTVkb2VZMEhnc0VzaCtHOEJoUEozTzUzNjhEc3pEb3pDM3NOdVVvS0NveEtZdlF2OEUrMDhRY1lMV3lwYmYxbUQ5alRoampWNUp1Mzc3M3Z2VDA1aFlZMThEQXhYRWpMTXBrblFqR2JHYUVTaTFGdWhCdXkzS0tKMFpXSUp5V3dSeE1ZbjZBR1FRZzFFVHU0R1c3elhkNlVYQ1hOOWY0MlJxNVZHSGlnVFhMR09EQTh4VDF0ZHRnRmQ2UU5cL2lWUVVRZWZhakMxQ2JNOGluU3VYRWVyNVNJVEJ1Tk5tS2xtb1k1MlwvR2d1b2hkVVRuQnBKNkZUcUhoZlloekNOTVwvZEswMnFBcTJERzJPenVST3kyVVhYQ3VGcXhxMGxkXC85czBuWGV1blwvM05oVnQ4QnIyb1Y1a0FSM0s3cDZITXNcL0RsclNVdExYUXlpNzBWS3BqTVJCZW5QaEg4KysrSFgwZTlXb0FsTW45eTcrcDVyY1hZZlRsNWU4N1pkQkI1T0RXaFBVSzFpb3ljak5iTVQ4MzZKV1wvZjN6MlwvdmowNE1VVlV2YUlsZlwvdlk2RjlsdHh3U2FjWk45enBpWTZJZHFcL3U3MFMrZURuNWVRdEQxaFZwSnBIK0tPVXd2cENvaUduZHV0SHlQR1wvcWJHTTlYTjVhYVcrc2JiVWZyNjEyXC9PeGFqS2wrVkF5NFNSbFB5TUwxTWdGZkhRczFGWGY0NiszWG83c1wvaU80cE5IYTV6SkVLbUtsQW5UenRvM2x6Y2p3XC9cL2VIbllibk8yRUxqNEE4allRbm9IZ01BQUE9PSIsInN1YiI6ImRlbW9AeGZhcm0uYWciLCJyb2xlcyI6WyJST0xFX0ZBUk1fQURNSU4iXSwiaWF0IjoxNTYxMzkwNTAyfQ.xw2AMhHPIjk_gQIjrSY-QtGHzZVops1o6L8UzJi7D8E"}
    //F

    public static String getRequest(String token){
        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

            URIBuilder uriBuilder = new URIBuilder(XSENSE_URL);
            uriBuilder.setParameter("deviceID", "89462038005001892349")  //89462038005001835702 89462038005001892349
                    .setParameter("from", sdf.format(System.currentTimeMillis()))//-604800000
                    // .setParameter("from", "2019-10-01T00:00:00.001Z")
                    .setParameter("periodTo", "7d")
                    .setParameter("limit", "null")
                    .setParameter("onlyImages", "false")
                    .setParameter("lang", "it");
//             System.out.println("Data: "+sdf.format(System.currentTimeMillis()));//-604800000
            HttpGet request = new HttpGet(uriBuilder.build());

            // HttpGet request = new HttpGet("https://api.xfarm.ag/api/private/v1/xsense/devices/186?
            //deviceID=89462038005001892349&from=2019-05-13T14:16:35.471Z&periodTo=1d&limit=null&onlyImages=false&lang=it");
            
            // HttpGet request = new HttpGet("https://api.xfarm.ag/api/private/v1/xsense/devices/186");
            System.out.println(request.toString());
            
            request.addHeader("Authorization", "Bearer " + token);
            HttpResponse response = httpClient.execute(request);

            BufferedReader bufReader = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

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

    public static List<String> getPhoto(){
        List<String> photo = new LinkedList<>();
        String token = postRequest();
        String jsonString = getRequest(token);
//        jsonString = "{k:"+jsonString+"}";
        jsonString = "["+jsonString+"]";
        JSONArray jsonResponse = new JSONArray(jsonString);
        System.out.println("----------------JSONRESPONSE-----------------------------------------------");
        System.out.println(jsonResponse);
        for(Object o : jsonResponse){
            JSONObject jsonObject = new JSONObject(o.toString());
//            System.out.println(jsonObject);
            // String pname = jsonObject.getString("pname");
            // String time = jsonObject.getString("time");
            // photo.add(pname+" "+time);
        }
        System.out.println(photo);
        return photo;
    }

    public static String getCoordinate(){
//        List<String> photo = new LinkedList<>();
        String token = postRequest();
        String jsonString = getRequest(token);
//        jsonString = "{k:"+jsonString+"}";
        jsonString = "["+jsonString+"]";
        JSONArray jsonResponse = new JSONArray(jsonString);

        String lat = "";
        String lng = "";

       for(Object o : jsonResponse){
           JSONObject jsonObject = new JSONObject(o.toString());
           System.out.println(jsonObject);
           lat = Double.toString( jsonObject.getDouble("lat") );
           lng = Double.toString( jsonObject.getDouble("lng") );           
        //    String lat = jsonObject.getString("lat");
        //    String lng = jsonObject.getString("lng");
        //    photo.add(pname+" "+time);
       }
//        System.out.println(photo);
        System.out.println("COORDINATE "+ jsonResponse );

        return lat.replace(".",",") + "/" + lng.replace(".",",");
        
        // return jsonResponse.getJSONObject(0).getString("pos");
    }

    public static String getTempMedia(){
        String token = postRequest();
        String jsonString = getRequest(token);
//        jsonString = "{k:"+jsonString+"}";
        JSONArray jsonResponse = new JSONArray(jsonString);
        double temp = 0;
        for(Object o : jsonResponse){
            JSONObject jsonObject = new JSONObject(o.toString());
            temp+=jsonObject.getDouble("at");
        }
        double tempMedia = temp/jsonResponse.length();
        String tempMediaStr = tempMedia+"";
        return tempMediaStr.substring(0,tempMediaStr.length()-1);
    }



}
