package pl.psy;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class PojawienieSieSamochodu extends BasicSimEvent<Stacja, Object> {


    private Stacja parent;

    public PojawienieSieSamochodu(Stacja parent, double delay) throws SimControlException
    {
        super(parent, delay);
    }

    public PojawienieSieSamochodu(Stacja parent) throws SimControlException
    {
        super(parent);
    }

    @Override
    protected void stateChange() throws SimControlException {
        final Logger LOGGER=  Logger.getLogger(Main.class.getName());

        parent = getSimObj();

        TypPaliwa typPaliwa = Config.getInstance().randTypPaliwa();
        Samochod sam = new Samochod(typPaliwa);
        sam.startObs = simTime();

        LOGGER.info("Nowe zgloszenie " + typPaliwa + " czas: " + simTime()  + "[" + sam.id +"]");

        List<Dystrybutor> dyst = parent.getDistList(typPaliwa);
        Dystrybutor dystrybutor = null;
        for(Dystrybutor d : dyst)
        {
            if(d.isWolny())
            {
                dystrybutor = d;
                break;
            }
        }

        if(dystrybutor == null)
        {
            for(Dystrybutor d : dyst)
            {
                if(d.liczbaSamochodow() < Config.getInstance().LICZBA_MIEJSC)
                {
                    dystrybutor = d;
                    break;
                }
            }
        }

        if(dystrybutor != null)
        {
            dystrybutor.add(sam);
            if (dystrybutor.liczbaSamochodow()==1 && dystrybutor.isWolny()) {
                //parent.smo.rozpocznijObsluge = new RozpocznijObsluge(parent.smo);
                dystrybutor.rozpoczecie = new RozpoczecieTankowania(dystrybutor);
            }
        }

        // Wygeneruj czas do kolejnego zg³oszenia
        double odstep = Config.getInstance().randOdstepSam();
        //parent.MVczasy_miedzy_zgl.setValue(odstep);
        parent.pojawienieSieSamochodu = new PojawienieSieSamochodu(parent, odstep);
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
