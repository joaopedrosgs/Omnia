package com.myandroidacademy.agenda.omnia.ShowContact;

import com.myandroidacademy.agenda.omnia.Entities.Contato;

public class ShowContactPresenter {
    ShowContactView showContactView;

    public ShowContactPresenter (ShowContactView showContactView){
        this.showContactView = showContactView;
    }

    public void showContact(Contato contato){
        if (contato != null){
            showContactView.showInfo(contato);
        }
    }
}
