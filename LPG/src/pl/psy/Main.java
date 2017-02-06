package pl.psy;

import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters.SimControlStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

   // private final static Logger LOGGER=  Logger.getLogger(Main.class .getName());
    public static void main(String[] args) {
        final Logger LOGGER=  Logger.getLogger(Main.class .getName());
        try
        {

            try{
                FileHandler fh;
                int limit = 1000000;
                fh = new FileHandler("C:/Users/Admin/Desktop/ProjektPSY/Logi2.log",limit,1,true);
                LOGGER.addHandler(fh);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            }catch (IOException e){
                e.printStackTrace();
            }

            //logger.setLevel(Level.ALL);
            SimManager model = SimManager.getInstance();

            // Utworzenie otoczenia
            Stacja generatorZgl = new Stacja();
            // Dwa sposoby zaplanowanego końca symulacji
            //model.setEndSimTime(10000);
            // lub
            SimControlEvent stopEvent = new SimControlEvent(50000.0, SimControlStatus.STOPSIMULATION);

            // Uruchomienie symulacji za pośrednictwem metody "start"
            model.startSimulation();


            generatorZgl.pokazWyniki();


            double wynik = new BigDecimal(Statistics.arithmeticMean(generatorZgl.czasTankowania)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
           LOGGER.info("Oczekiwany graniczny czas tankowania : " + wynik);

            wynik = new BigDecimal(Statistics.arithmeticMean(generatorZgl.czasMycia)).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            LOGGER.info("Oczekiwany graniczny czas mycia : " + wynik);
        }
        catch (SimControlException e)
        {
            e.printStackTrace();
        }
    }
}
