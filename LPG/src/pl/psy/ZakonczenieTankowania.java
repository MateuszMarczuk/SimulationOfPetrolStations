package pl.psy;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.logging.Logger;

/**
 * Created by Mateusz on 04.02.2017.
 */

public class ZakonczenieTankowania extends BasicSimEvent<Dystrybutor, Object> {


    private Dystrybutor parent;

    public ZakonczenieTankowania(Dystrybutor parent, double delay) throws SimControlException
    {
        super(parent, delay);
        this.parent = parent;
    }

    @Override
    protected void stateChange() throws SimControlException {
        final Logger LOGGER=  Logger.getLogger(Main.class .getName());

        // Odblokuj gniazdo
        parent.setWolny(true);
        LOGGER.info(simTime()+": Koniec tankowania "  + parent.getType() + "[" + parent.curr.id +"]");

        double czasObs = simTime() - parent.curr.startObs;
        parent.curr.startObs = simTime();
        parent.getStacja().czasTankowania.setValue(czasObs);

        // Zaplanuj dalsza obs³uge
        if (parent.liczbaSamochodow() > 0)
        {
            parent.rozpoczecie = new RozpoczecieTankowania(parent);
        }

        if(Config.getInstance().randKasaMyjnia())
        {
            // do kasy
            parent.getStacja().getKasa().add(parent.curr);
            if (parent.getStacja().getKasa().liczbaSamochodow()==1 && parent.getStacja().getKasa().isWolny()) {
                parent.getStacja().getKasa().rozpoczeciePlacenia = new RozpoczeciePlacenia(parent.getStacja().getKasa());
            }
        }
        else
        {
            // do myjni
            parent.getStacja().getMyjnia().add(parent.curr);
            if (parent.getStacja().getMyjnia().liczbaSamochodow()==1 && parent.getStacja().getMyjnia().isWolny()) {
                parent.getStacja().getMyjnia().rozpoczecieMycia = new RozpoczecieMycia(parent.getStacja().getMyjnia());
            }
        }

        parent.curr = null;
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
