package pl.psy;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.logging.Logger;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class ZakonczenieMycia extends BasicSimEvent<Myjnia, Object> {


    private Myjnia parent;

    public ZakonczenieMycia(Myjnia parent, double delay) throws SimControlException
    {
        super(parent, delay);
        this.parent = parent;
    }

    @Override
    protected void stateChange() throws SimControlException {
        final Logger LOGGER=  Logger.getLogger(Main.class .getName());

        // Odblokuj gniazdo
        parent.setWolny(true);
        LOGGER.info(simTime()+": Koniec mycia " + "[" + parent.curr.id +"]");

        double czasObs = simTime() - parent.curr.startObs;
        parent.getStacja().czasMycia.setValue(czasObs);

        // Zaplanuj dalsza obs³uge
        if (parent.liczbaSamochodow() > 0)
        {
            parent.rozpoczecieMycia = new RozpoczecieMycia(parent);
        }

        // do kasy
        parent.getStacja().getKasa().add(parent.curr);
        if (parent.getStacja().getKasa().liczbaSamochodow()==1 && parent.getStacja().getKasa().isWolny()) {
            parent.getStacja().getKasa().rozpoczeciePlacenia = new RozpoczeciePlacenia(parent.getStacja().getKasa());
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
