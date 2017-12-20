package com.myandroidacademy.agenda.omnia.ShowContact;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myandroidacademy.agenda.omnia.Entities.Contato;
import com.myandroidacademy.agenda.omnia.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowContactActivity extends AppCompatActivity implements ShowContactView {

    @BindView(R.id.contactNome) TextView nome;
    @BindView(R.id.contactEmail) TextView email;
    @BindView(R.id.contactTelefone) TextView telefone;
    @BindView(R.id.contactEndereco) TextView endereco;
    @BindView(R.id.contactFoto) ImageView foto;
    boolean MostrandoFoto = false;

    public String caminhoFoto;
    ShowContactPresenter showContactPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contact);
        ButterKnife.bind(this);

        showContactPresenter= new ShowContactPresenter(this);
        showContactPresenter.showContact((Contato) getIntent().getSerializableExtra("exibir_contato"));
    }
    @Override public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_show_contato, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_send_sms:
                sendSMS();
                return true;

            case R.id.action_view_on_map:
                viewOnMap();
                return true;
            case R.id.action_view_delete:
                deletarContato();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deletarContato() {
        Intent resultIntent = new Intent();

        resultIntent.putExtra("contato", (Contato) getIntent().getSerializableExtra("exibir_contato"));
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override public void showInfo(Contato contato) {
        nome.setText(contato.getNome());
        telefone.setText(contato.getTelefone());
        email.setText(contato.getEmail());
        endereco.setText(contato.getEndereco());
        caminhoFoto = contato.getCaminhoFoto();
        if(caminhoFoto!=null  && !caminhoFoto.isEmpty())
        exibeFoto();
    }

    private void exibeFoto(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Picasso.with(this)
                    .load("file://" + caminhoFoto)
                    .placeholder(this.getDrawable(R.drawable.account))
                    .fit()
                    .centerCrop()
                    .into(foto);
        } else {
            Picasso.with(this)
                    .load("file://" + caminhoFoto)
                    .fit()
                    .centerCrop()
                    .into(foto);
        }
    }
    public void viewOnMap(){
        final String end = endereco.getText().toString();
        if(end.isEmpty()) {
            showToast("Esse contato não possui endereço!");
            return;
       }
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + end));

        if(intentMapa.resolveActivity(getPackageManager()) != null) {
            startActivity(intentMapa);
        }else {
            showToast("Impossível abrir o recurso de Mapas");
        }
    }
    public void sendSMS(){
        final String cellphone = telefone.getText().toString();
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + cellphone));
        if(intentSMS.resolveActivity(getPackageManager()) != null) {
            startActivity(intentSMS);
        }else {
            showToast("Impossível abrir o recurso de SMS");
        }
    }
    public void showToast(String mensagem) {
        Toast toast = Toast.makeText(ShowContactActivity.this, mensagem, Toast.LENGTH_LONG);
        toast.show();
    }
}
