package pl.psy;

import dissimlab.random.SimGenerator;
import javafx.util.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mateusz on 04.02.2017.
 */
public class Config {

    private final String FILE_NAME = "/config.cfg";

    private static Config instance = null;

    private SimGenerator generator;

    public int ILOSC_BENZYNA = 1; // ile dystrybutorow - benzyna
    public int ILOSC_LPG     = 1; // ile dystrybutorow - LPG
    public int ILOSC_ON      = 1; // ile dystrybutorow - ON
    public int LICZBA_MIEJSC = 20;

    private Map<String, String> properites;

    private Config()
    {
        this.properites = new HashMap<>();
        this.load(FILE_NAME);
        this.generator = new SimGenerator();

        System.out.println(LICZBA_MIEJSC);
    }

    private void load(String filename)
    {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream(filename))))
        {
            int lineNum = 1;
            String line;
            while ((line = br.readLine()) != null)
            {
                line = line.replaceAll(" ", "");
                if(!line.isEmpty() && !line.startsWith("#"))
                {
                    String[] key_value = line.split("=");

                    if(key_value.length == 2)
                    {
                        try
                        {
                            if(key_value[0].equals("ILOSC_BENZYNA"))
                                ILOSC_BENZYNA = Integer.parseInt(key_value[1]);
                            else if(key_value[0].equals("ILOSC_LPG"))
                                ILOSC_LPG = Integer.parseInt(key_value[1]);
                            else if(key_value[0].equals("ILOSC_ON"))
                                ILOSC_ON = Integer.parseInt(key_value[1]);
                            else if(key_value[0].equals("LICZBA_MIEJSC"))
                                LICZBA_MIEJSC = Integer.parseInt(key_value[1]);
                            else
                            {
                                this.properites.put(key_value[0], key_value[1]);
                            }
                        }
                        catch(NumberFormatException e)
                        {
                            e.printStackTrace();
                        }
                    }
                    else
                    {
                        System.err.println("Config file parser error. Line: " + lineNum);
                    }
                }

                lineNum ++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public TypPaliwa randTypPaliwa()
    {
        int id = (int)Math.round(Math.random() * 2);
        return TypPaliwa.values()[id];
    }

    private double rand(String name)
    {
        if(this.properites.containsKey(name) && this.properites.containsKey(name + "Args"))
        {
            try
            {
                int roz = Integer.parseInt(this.properites.get(name));

                String[] args = this.properites.get(name+"Args").replaceAll(" ", "").split(",");
                switch (roz)
                {
                    case 0:
                        if(args.length == 2)
                        {
                            double arg1 = Double.parseDouble(args[0]);
                            double arg2 = Double.parseDouble(args[1]);

                            return Math.abs(generator.normal(arg1, arg2));
                        }
                        break;
                    case 1:
                        if(args.length == 2)
                        {
                            double arg1 = Double.parseDouble(args[0]);
                            double arg2 = Double.parseDouble(args[1]);

                            return Math.abs(generator.uniform(arg1, arg2));
                        }
                        break;
                    case 2:
                        if(args.length == 2)
                        {
                            double arg1 = Double.parseDouble(args[0]);
                            double arg2 = Double.parseDouble(args[1]);

                            return Math.abs(generator.gamma(arg1, arg2));
                        }
                        break;
                    case 3:
                        if(args.length == 2)
                        {
                            double arg1 = Double.parseDouble(args[0]);
                            double arg2 = Double.parseDouble(args[1]);

                            return Math.abs(generator.beta(arg1, arg2));
                        }
                        break;
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return Math.abs(generator.normal(5.0, 1.0));
    }

    public double randOdstepSam()
    {
        return rand("randOdstepSam");
    }

    public double randCzasObslugi() {
        return rand("randCzasObslugi");
    }

    public boolean randKasaMyjnia() {
        if(this.properites.containsKey("randKasaMyjnia")) {
            try {
                double arg1 = Double.parseDouble(this.properites.get("randKasaMyjnia"));
                return Math.random() < 1.0-arg1;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

        return Math.random() < 1.0-0.5;
    }

    public double randCzasObslugiON() {
        return rand("randCzasObslugiON");
    }

    public double randCzasObslugiLPG() {
        return rand("randCzasObslugiLPG");
    }

    public double randCzasObslugiBenzyna() {
        return rand("randCzasObslugiBenzyna");
    }

    public double randCzasObslugiMycia() {
        return rand("randCzasObslugiMycia");
    }

    public double randCzasObslugiPlacenia() {
        return rand("randCzasObslugiPlacenia");
    }

    public static Config getInstance() {
        if(Config.instance == null)
            Config.instance = new Config();
        return Config.instance;
    }

}
