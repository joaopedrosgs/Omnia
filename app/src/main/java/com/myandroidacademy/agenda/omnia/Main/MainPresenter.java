package com.myandroidacademy.agenda.omnia.Main;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myandroidacademy.agenda.omnia.AplicacaoGlobal;
import com.myandroidacademy.agenda.omnia.Entities.Contato;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;


public class MainPresenter {

    MainView mainView;
    private List<Contato> contactList = new ArrayList<>();
    Gson gson;

    public MainPresenter(MainView mainView, SharedPreferences sharedPreferences) {
        this.mainView = mainView;
        gson = new Gson();
        if (sharedPreferences.contains(AplicacaoGlobal.NomeDeUsuario)) {
            String contatos_json = sharedPreferences.getString(AplicacaoGlobal.NomeDeUsuario, "[{}]");
            contactList = gson.fromJson(contatos_json, new TypeToken<ArrayList<Contato>>(){}.getType());
            mainView.updateList(contactList);
        }
    }

    void addContact(Contato contato) {
        contactList.add(contato);
        mainView.updateList(contactList);
    }

    public void removeContact(Contato contato) {
        Iterator contatoIterator = contactList.iterator();
        while (contatoIterator.hasNext()) {
            Contato encontrado = (Contato) contatoIterator.next();
            if (Objects.equals(contato, encontrado)) {
                contatoIterator.remove();
                if(encontrado.getCaminhoFoto()!=null && !encontrado.getCaminhoFoto().isEmpty()) {
                    File foto = new File(encontrado.getCaminhoFoto());
                    if(foto.delete())
                        Log.d("Info", "Foto deletada");
                    else
                        Log.d("Error", "Foto não deletada");

                }
                mainView.updateList(contactList);
                return;
            }
        }


        Log.d("Warn", "Falha ao deletar contato");
        Log.d("Warn", contato.getNome());


    }
}