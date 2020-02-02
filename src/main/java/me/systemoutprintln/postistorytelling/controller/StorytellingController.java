package me.systemoutprintln.postistorytelling.controller;

import me.systemoutprintln.postistorytelling.xfarmapi.ApiConnector;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Controller
public class StorytellingController { 

    @GetMapping("/snap-scroll")
    public String index2(){
        return "snap-scroll";
    }

    @GetMapping("/index")
    public String index(HttpServletRequest req){
        System.out.println(req.getRemoteHost());
        return "test";
    }

    @GetMapping("/test2")
    public String indexTest(){
        return "test2";
    }

    @GetMapping("/test3")
    public String test3(){
        return "test3";
    }

    @GetMapping("/test4")
    public String test4(){
        return "test4";
    }

    @GetMapping("/test5")
    public String test5(){
        return "test5";
    }

    @GetMapping("/")
    public String test6(){
        return "index0";
    }

    @GetMapping("/{template}")
    public String template(Model model, @PathVariable("template") String template, @RequestParam(value = "lang" , required = false) String language){
//        String res = ApiConnector.getPhoto().size();
        String[] p = null;
        String lat0 = null;
        String lat1 = null;
        String lat2 = null;

        String lon0 = null;
        String lon1 = null;
        String lon2 = null;

        String time = "";

        double tempMediaRound = 0;

        if(ApiConnector.getPhoto().size()!=0) {
            p = ApiConnector.getPhoto().get(0).split(" ");
            time = p[1].substring(0, 19);
        }
        
        // String[] pos = ApiConnector.getCoordinate().split("/");
        // String[] lat = pos[0].split(",");
        // String[] lon = pos[1].split(",");

        // lat0 = lat[0];
        // lat1 = lat[1].substring(0, 2);
        // lat2 = lat[1].substring(3, 5);

        // lon0 = lon[0];
        // lon1 = lon[1].substring(0, 2);
        // lon2 = lon[1].substring(3, 5);

        // String tempMedia = ApiConnector.getTempMedia();
        // double tempMediaDouble = Double.parseDouble(tempMedia);
        // tempMediaRound = Math.round(tempMediaDouble * 100.0) / 100.0;

        String ph = ApiConnector.getPH();
        String nt1 = ApiConnector.getNT1();
        String sensor_time = ApiConnector.getSensorTime();

        Locale.setDefault(Locale.ITALIAN);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM");
        Date dateToday = new Date(System.currentTimeMillis());
        Date dateAweekAgo = new Date(System.currentTimeMillis()-604800000);
        String today = sdf.format(dateToday);
        String aweekago = sdf.format(dateAweekAgo);

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(dateToday);

        //Data della prima consegna
        Date startdate = new Date(1562976000000L);
        c2.setTime(startdate);

        //Settimane passate
        long weeksPassed = getFullWeeks(c2,c1) + 5;
        long weeksPassed2 = getFullWeeks(c2,c1) + 4;

        // model.addAttribute("lat0",lat0);
        // model.addAttribute("lat1",lat1);
        // model.addAttribute("lat2",lat2);
        // model.addAttribute("lon0",lon0);
        // model.addAttribute("lon1",lon1);
        // model.addAttribute("lon2",lon2);

        // if(p==null) {
        //     model.addAttribute("pname", null);
        // }else {
        //     model.addAttribute("pname", p[0]);
        // }

        // model.addAttribute("time", time);
        // model.addAttribute("tempM", tempMediaRound);
        // model.addAttribute("today", today);
        // model.addAttribute("aweek", aweekago);
        // model.addAttribute("weeksPassed", 13);
        // model.addAttribute("weeksPassed2", 12);

        model.addAttribute("ph",ph);
        model.addAttribute("nt1",nt1);
        model.addAttribute("sensor_time",sensor_time);

       
        if(language!=null && !language.equals("it")) return template+"-"+language;
        return template;
    }


    public long getFullWeeks(Calendar d1, Calendar d2){

        Instant d1i = Instant.ofEpochMilli(d1.getTimeInMillis());
        Instant d2i = Instant.ofEpochMilli(d2.getTimeInMillis());

        LocalDateTime startDate = LocalDateTime.ofInstant(d1i, ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(d2i, ZoneId.systemDefault());

        return ChronoUnit.WEEKS.between(startDate, endDate);
    }

}
