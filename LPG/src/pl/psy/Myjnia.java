package pl.psy;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.monitors.Change;
import dissimlab.monitors.MonitoredVar;
import dissimlab.simcore.BasicSimObj;

import java.util.LinkedList;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class Myjnia extends BasicSimObj {

    private Stacja stacja;
    private LinkedList<Samochod> kolejka;
    private boolean wolny;

    private double lastChangeTime = 0;
    private MonitoredVar carsNumber;
    private MonitoredVar time;

    public RozpoczecieMycia rozpoczecieMycia;
    public ZakonczenieMycia zakonczenieMycia;

    public Samochod curr;

    public Myjnia(Stacja stacja)
    {
        this.stacja = stacja;
        this.kolejka = new LinkedList<>();
        this.wolny = true;

        this.carsNumber = new MonitoredVar();
        this.time = new MonitoredVar();
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

    public Stacja getStacja()
    {
        return this.stacja;
    }

    public void add(Samochod sam) {
        double dt = simTime()-this.lastChangeTime;
        this.carsNumber.setValue(this.kolejka.size()+1);
        this.time.setValue(dt);
        this.lastChangeTime=simTime();

        this.kolejka.add(sam);
    }

    public Samochod usun()
    {
        double dt = simTime()-this.lastChangeTime;
        this.carsNumber.setValue(this.kolejka.size()-1);
        this.time.setValue(dt);
        this.lastChangeTime=simTime();

        return this.kolejka.removeFirst();
    }

    public double granicznaLiczbaSamochodow()
    {
        if(this.time.numberOfSamples() != this.carsNumber.numberOfSamples()) return -1;
        if(this.time.numberOfSamples() == 0) return 0;

        double result = 0;
        int numberOfSamples = time.numberOfSamples();

        double p = 0;
        Change changeNumber;
        Change changeTime;
        for (int i = 0; i < numberOfSamples; i++) {
            changeNumber = carsNumber.getChanges().get(i);
            changeTime = time.getChanges().get(i);

            result += changeNumber.getValue()*changeTime.getValue();
            p += changeTime.getValue();
        }
        return result/p;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
