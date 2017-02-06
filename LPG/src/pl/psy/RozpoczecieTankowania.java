package pl.psy;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.logging.Logger;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class RozpoczecieTankowania extends BasicSimEvent<Dystrybutor, Object> {


    private Dystrybutor parent;

    public RozpoczecieTankowania(Dystrybutor parent, double delay) throws SimControlException
    {
        super(parent, delay);
        this.parent = parent;
    }

    public RozpoczecieTankowania(Dystrybutor parent) throws SimControlException
    {
        super(parent);
        this.parent = parent;
    }

    @Override
    protected void stateChange() throws SimControlException {
        final Logger LOGGER=  Logger.getLogger(Main.class .getName());

        if (parent.liczbaSamochodow() > 0)
        {
            // Zablokuj gniazdo
            parent.setWolny(false);
            // Pobierz zg³oszenie
            Samochod sam = parent.usun();
            parent.curr = sam;

            // Wygeneruj czas obs³ugi
            double czasObslugi = 0;
            if(sam.getTypPaliwa() == TypPaliwa.BENZYNA)
                czasObslugi = Config.getInstance().randCzasObslugiBenzyna();
            else if(sam.getTypPaliwa() == TypPaliwa.LPG)
                czasObslugi = Config.getInstance().randCzasObslugiLPG();
            else if(sam.getTypPaliwa() == TypPaliwa.ON)
                czasObslugi = Config.getInstance().randCzasObslugiON();

            LOGGER.info(simTime()+": Poczatek tankowania " + parent.getType() + "[" + sam.id +"]");
            // Zaplanuj koniec obs³ugi
            parent.zakoncz = new ZakonczenieTankowania(parent, czasObslugi);
        }
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

    @Override
    public Object getEventParams() {
        return null;
    }
}
