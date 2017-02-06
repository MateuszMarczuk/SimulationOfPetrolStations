package pl.psy;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;

import java.util.LinkedList;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class Kasa extends BasicSimObj {

    private LinkedList<Samochod> kolejka;
    private boolean wolny;

    public RozpoczeciePlacenia rozpoczeciePlacenia;
    public ZakonczeniePlacenia zakonczeniePlacenia;

    public Samochod curr;

    public Kasa()
    {
        this.kolejka = new LinkedList<>();
        this.wolny = true;
    }

    public boolean isWolny()
    {
        return this.wolny;
    }

    public void setWolny(boolean f)
    {
        this.wolny = f;
    }

    public long liczbaSamochodow()
    {
        return this.kolejka.size();
    }

    public void add(Samochod sam) {
        this.kolejka.add(sam);
    }

    public Samochod usun()
    {
        return this.kolejka.removeFirst();
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
