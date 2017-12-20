package com.myandroidacademy.agenda.omnia.AddContact;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.myandroidacademy.agenda.omnia.CircleTransform;
import com.myandroidacademy.agenda.omnia.Entities.Contato;
import com.myandroidacademy.agenda.omnia.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddContactActivity extends AppCompatActivity implements AddContactView{

    AddContactPresenter addContactPresenter;

    @BindView(R.id.txtNome) public EditText nome;
    @BindView(R.id.txtEmail) public EditText email;
    @BindView(R.id.txtEndereco) public EditText endereco;
    @BindView(R.id.txtTelefone) public EditText telefone;
    @BindView(R.id.imgFoto) public ImageView foto;

    private static final int CODIGO_CAMERA = 123;
    public String caminhoFoto;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Allowing Strict mode policy for Nougat support
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        telefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        addContactPresenter = new AddContactPresenter(this);
        addContactPresenter.showContact((Contato) getIntent().getSerializableExtra("exibir_contato"));
    }
    @Override public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_contato, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_save:
                addContact();
                return true;

            case R.id.action_send_sms:
                sendSMS();
                return true;

            case R.id.action_take_picture:
                takeAPhoto2();
                return true;

            case R.id.action_view_on_map:
                viewOnMap();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override public void showInfo(Contato contato) {
        nome.setText(contato.getNome());
        telefone.setText(contato.getTelefone());
        email.setText(contato.getEmail());
        endereco.setText(contato.getEndereco());
        caminhoFoto = contato.getCaminhoFoto();
        exibeFoto();
    }
    @Override public void showToast(String msg) {
        Toast toast = Toast.makeText(AddContactActivity.this, msg, Toast.LENGTH_LONG);
        toast.show();
    }
    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && resultCode == Activity.RESULT_OK) {
            exibeFoto();
        }
    }

    public void viewOnMap(){
        final String address = endereco.getText().toString();
        if(address.isEmpty()){
            showToast("O campo de endereço está vazio");
            return;
        }
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=" + address));

        if(intentMapa.resolveActivity(getPackageManager()) != null) {
            startActivity(intentMapa);
        }else {
            showToast("Não foi possivel abrir o aplicativo de Mapa");
        }
    }
    public void sendSMS(){
        final String cellphone = telefone.getText().toString();
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + cellphone));
        if(intentSMS.resolveActivity(getPackageManager()) != null) {
            startActivity(intentSMS);
        }else {
            showToast("Não foi possivel abrir o aplicativo de SMS");
        }
    }
    public void addContact(){
        if(addContactPresenter.validarDados(nome.getText().toString(),telefone.getText().toString() ))
            return;
        Intent resultIntent = new Intent();

        resultIntent.putExtra("contato", addContactPresenter.getContato(nome.getText().toString(), telefone.getText().toString(), email.getText().toString(), endereco.getText().toString(), caminhoFoto));
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }
    private void exibeFoto(){
        Picasso.with(this)
                .load("file://" + caminhoFoto)
                .fit()
                .centerCrop()
                .transform(new CircleTransform())
                .into(foto);
    }
    public void takeAPhoto2(){
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
        startActivityForResult(intentCamera, CODIGO_CAMERA);
    }
}
