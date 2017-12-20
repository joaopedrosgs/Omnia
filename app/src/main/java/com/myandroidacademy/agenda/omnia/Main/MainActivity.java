package com.myandroidacademy.agenda.omnia.Main;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;

import com.myandroidacademy.agenda.omnia.AddContact.AddContactActivity;
import com.myandroidacademy.agenda.omnia.AplicacaoGlobal;
import com.myandroidacademy.agenda.omnia.Entities.Contato;
import com.myandroidacademy.agenda.omnia.R;
import com.myandroidacademy.agenda.omnia.ShowContact.ShowContactActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainView {

    @BindView(R.id.rv_contatos)
    RecyclerView recyclerView;
    private MainPresenter mainPresenter;
    private final int CODIGO_NOVO_CONTATO = 123;
    private final int CODIGO_DELETA_CONTATO = 321;

    SharedPreferences sharedPreferences;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(!AplicacaoGlobal.NomeDeUsuario.isEmpty()) {
            sharedPreferences = getSharedPreferences(AplicacaoGlobal.NomeDeUsuario, MODE_PRIVATE);
            gson = new Gson();
            mainPresenter = new MainPresenter(this, sharedPreferences);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contatos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent adicionarContato = new Intent(MainActivity.this, AddContactActivity.class);
                startActivityForResult(adicionarContato, CODIGO_NOVO_CONTATO);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODIGO_NOVO_CONTATO && resultCode == Activity.RESULT_OK) {
            Contato contato = (Contato) data.getSerializableExtra("contato");
            if (contato != null) {
                mainPresenter.addContact(contato);

            }
        } else if (requestCode == CODIGO_DELETA_CONTATO && resultCode == Activity.RESULT_OK) {
            Contato contato = (Contato) data.getSerializableExtra("contato");
            mainPresenter.removeContact(contato);
        }
    }

    @Override
    public void updateList(final List<Contato> contactList) {
        Adapter adapter = new Adapter(contactList, this);
        adapter.setRecyclerInterface(new RecyclerInterface() {
            @Override
            public void onClick(View view, int position) {
                Intent exibirContato = new Intent(MainActivity.this, ShowContactActivity.class);
                exibirContato.putExtra("exibir_contato", contactList.get(position));
                startActivityForResult(exibirContato, CODIGO_DELETA_CONTATO);
            }
        });

        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        String contatos_string = gson.toJson(contactList);
        if(!AplicacaoGlobal.NomeDeUsuario.isEmpty())
        sharedPreferences.edit().putString(AplicacaoGlobal.NomeDeUsuario, contatos_string).commit(); // Salva o json no shared preferences

    }

}
