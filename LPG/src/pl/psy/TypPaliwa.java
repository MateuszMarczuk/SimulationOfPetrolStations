package pl.psy;

/**
 * Created by Mateusz on 04.02.2017.
 */
public enum TypPaliwa {
    BENZYNA(0), LPG(1), ON(2);

    private int id;

    TypPaliwa(int id)
    {
        this.id = id;
    }
}