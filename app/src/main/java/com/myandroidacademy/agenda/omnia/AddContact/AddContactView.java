package com.myandroidacademy.agenda.omnia.AddContact;

import com.myandroidacademy.agenda.omnia.Entities.Contato;

public interface AddContactView {
    void showInfo(Contato contato);
    void showToast(String msg);
}
