package pl.psy;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.logging.Logger;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class ZakonczeniePlacenia extends BasicSimEvent<Kasa, Object> {


    private Kasa parent;

    public ZakonczeniePlacenia(Kasa parent, double delay) throws SimControlException
    {
        super(parent, delay);
        this.parent = parent;
    }

    public ZakonczeniePlacenia(Kasa parent) throws SimControlException
    {
        super(parent);
        this.parent = parent;
    }

    @Override
    protected void stateChange() throws SimControlException {
        final Logger LOGGER=  Logger.getLogger(Main.class .getName());

        // Odblokuj gniazdo
        parent.setWolny(true);
        LOGGER.info(simTime()+": Koniec placenia "  + "[" + parent.curr.id +"]");

        // Zaplanuj dalsza obs³uge
        if (parent.liczbaSamochodow() > 0)
        {
            parent.rozpoczeciePlacenia = new RozpoczeciePlacenia(parent);
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
