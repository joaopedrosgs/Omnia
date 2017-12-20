package com.myandroidacademy.agenda.omnia.Entities;

import java.io.Serializable;
import java.util.Objects;

public class Contato implements Serializable{

    private String nome;
    private String endereco;
    private String email;
    private String telefone;
    private String caminhoFoto;

    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setCaminhoFoto(String caminhoFoto){ this.caminhoFoto = caminhoFoto;}

    public String getEndereco() {
        return endereco;
    }
    public String getNome() {
        return nome;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getEmail() {
        return email;
    }
    public String getCaminhoFoto(){ return caminhoFoto;}


    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Contato)) {
            return false;
        }
        Contato user = (Contato) o;
        return Objects.equals(nome, user.nome) && Objects.equals(telefone, user.telefone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, telefone);
    }
}
