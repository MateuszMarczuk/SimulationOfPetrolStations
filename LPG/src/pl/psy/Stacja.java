package pl.psy;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;
import dissimlab.simcore.SimControlException;

import java.util.*;
import java.util.logging.Logger;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class Stacja extends BasicSimObj {

    private Map<TypPaliwa, List<Dystrybutor>> dystrybutory;
    private Kasa kasa;
    private Myjnia myjnia;

    public PojawienieSieSamochodu pojawienieSieSamochodu;

    public MonitoredVar czasTankowania;
    public MonitoredVar czasMycia;

    public Stacja() throws SimControlException
    {
        this.pojawienieSieSamochodu = new PojawienieSieSamochodu(this);
        this.czasTankowania = new MonitoredVar();
        this.czasMycia = new MonitoredVar();

        this.kasa = new Kasa();
        this.myjnia = new Myjnia(this);

        this.dystrybutory = new HashMap<>();
        this.dystrybutory.put(TypPaliwa.BENZYNA, new ArrayList<>());
        this.dystrybutory.put(TypPaliwa.LPG, new ArrayList<>());
        this.dystrybutory.put(TypPaliwa.ON, new ArrayList<>());

        for(int i = 0; i < Config.getInstance().ILOSC_BENZYNA; i ++)
            this.dystrybutory.get(TypPaliwa.BENZYNA).add(new Dystrybutor(this, TypPaliwa.BENZYNA));

        for(int i = 0; i < Config.getInstance().ILOSC_LPG; i ++)
            this.dystrybutory.get(TypPaliwa.LPG).add(new Dystrybutor(this, TypPaliwa.LPG));

        for(int i = 0; i < Config.getInstance().ILOSC_ON; i ++)
            this.dystrybutory.get(TypPaliwa.ON).add(new Dystrybutor(this, TypPaliwa.ON));
    }

    public List<Dystrybutor> getDistList(TypPaliwa typ)
    {
        return this.dystrybutory.get(typ);
    }

    public Kasa getKasa()
    {
        return this.kasa;
    }

    public Myjnia getMyjnia()
    {
        return this.myjnia;
    }


    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }

    public void pokazWyniki() {
        final Logger LOGGER=  Logger.getLogger(Main.class .getName());
        for(Map.Entry<TypPaliwa, List<Dystrybutor>> dl : this.dystrybutory.entrySet())
        {
            LOGGER.info("Dystrybutor " + dl.getKey());
            for(Dystrybutor d : dl.getValue())
            {
                LOGGER.info(" -Oczekiwana graniczna liczba samochodów do dystrybutora " + d.id + ": " + d.granicznaLiczbaSamochodow());
            }
            LOGGER.info("");
        }

        LOGGER.info("Myjnia");
        LOGGER.info(" - Oczekiwana graniczna liczaba samochodów do myjni : " + myjnia.granicznaLiczbaSamochodow());
        LOGGER.info("");
    }
}
