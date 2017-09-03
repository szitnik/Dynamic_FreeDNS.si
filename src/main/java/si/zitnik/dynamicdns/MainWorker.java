package si.zitnik.dynamicdns;

import si.zitnik.dynamicdns.util.FreeDNSManager;
import si.zitnik.dynamicdns.util.PublicIPManager;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by slavkoz on 03/09/2017.
 */
public class MainWorker {

    private static void runWorker(int repeatDelayMinutes, String domain, String domainEditPage, String freednsUsername, String freednsPassword) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                String myPublicIP = PublicIPManager.getMyPublicIP();
                String freeDNSAValue = FreeDNSManager.getARecordValue(domain);

                if (!myPublicIP.equals(freeDNSAValue)) {
                    System.out.println(String.format("My public IP: '%s', FreeDNS A record: '%s'", myPublicIP, freeDNSAValue));
                    System.out.println("Updating FreeDNS record ...");
                    FreeDNSManager.updateARecord(domainEditPage, freednsUsername, freednsPassword, myPublicIP);
                    System.out.println("Updating succeded!");
                } else {
                    System.out.println("Record matches the current public IP -> OK for now!");
                }
            }
            catch (Exception e) {
                System.err.println("Error happened, will see what happens in the next iteration.");
                e.printStackTrace();
            }
        };

        executor.scheduleWithFixedDelay(task, 0, repeatDelayMinutes, TimeUnit.MINUTES);
    }


    public static void main(String[] args) {
        int repeatDelayMinutes = Integer.parseInt(args[0]);
        String domain = args[1];
        String domainEditPage = args[2];
        String freednsUsername = args[3];
        String freednsPassword = args[4];

        runWorker(repeatDelayMinutes, domain, domainEditPage, freednsUsername, freednsPassword);
    }
}
