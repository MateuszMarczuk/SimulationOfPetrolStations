package pl.psy;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.logging.Logger;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class RozpoczeciePlacenia extends BasicSimEvent<Kasa, Object> {


    private Kasa parent;

    public RozpoczeciePlacenia(Kasa parent, double delay) throws SimControlException
    {
        super(parent, delay);
        this.parent = parent;
    }

    public RozpoczeciePlacenia(Kasa parent) throws SimControlException
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
            double czasObslugi = Config.getInstance().randCzasObslugiPlacenia();

            LOGGER.info(simTime()+": Rozpoczecie placenia " + "[" + sam.id +"]");
            // Zaplanuj koniec obs³ugi
            parent.zakonczeniePlacenia = new ZakonczeniePlacenia(parent, czasObslugi);
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
