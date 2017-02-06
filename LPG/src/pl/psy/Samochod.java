package pl.psy;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class Samochod extends BasicSimObj {

    public static int GLOBAL_ID = 0;
    public int id;

    public double startObs;

    private TypPaliwa typPaliwa;

    public Samochod(TypPaliwa type)
    {
        this.typPaliwa = type;
        this.id = Samochod.GLOBAL_ID++;
    }

    public TypPaliwa getTypPaliwa()
    {
        return this.typPaliwa;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
