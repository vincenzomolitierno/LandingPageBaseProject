package me.systemoutprintln.postistorytelling.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class IssueAutomation {

    public static Logger logger = LoggerFactory.getLogger("IssueAutomation");

//    @Scheduled(cron = "0/5 * * ? * *")
    public static void issue(){
        System.out.println("every 5 sec.");
    }

//    @Scheduled(cron = "0 0 0 ? * SUN")
//    @Scheduled(cron = "0/5 * * ? * *")
    public static void issueProduction(){
        String s = null;
        try {
            Process p = Runtime.getRuntime().exec("/home/eric/scripts/test.sh");
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            // read the output from the command
            //System.out.println("Here is the standard output of the command:\n");
            while ((s = stdInput.readLine()) != null) {
                logger.info(s);
                System.out.println(s);
            }

            // read any errors from the attempted command
            //System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }
        }catch (IOException e){
            logger.error("IOException!\n" +"Cause: "+ e.getCause() +"\nMessage"+e.getMessage());
            //System.out.println("ERROR!");
            e.printStackTrace();
        }
    }


}
