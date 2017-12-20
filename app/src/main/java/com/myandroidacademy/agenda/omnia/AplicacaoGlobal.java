package com.myandroidacademy.agenda.omnia;

import android.app.Application;

/**
 * Created by pedro on 20/12/2017.
 */

public class AplicacaoGlobal extends Application {

    private static AplicacaoGlobal singleton;
    public static String NomeDeUsuario = "";
    public static AplicacaoGlobal getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
    }
}
