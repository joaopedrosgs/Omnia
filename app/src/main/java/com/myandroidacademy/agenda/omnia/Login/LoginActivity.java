package com.myandroidacademy.agenda.omnia.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.myandroidacademy.agenda.omnia.Main.MainActivity;
import com.myandroidacademy.agenda.omnia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.text_input_layout_username) TextInputLayout usernameLayout;
    @BindView(R.id.text_input_layout_password) TextInputLayout passwordLayout;
    @BindView(R.id.edit_text_username) TextInputEditText usernameText;
    @BindView(R.id.edit_text_password) TextInputEditText passwordText;
    @BindView(R.id.logo)
    TextView Logo;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    protected void setErroUsuario(){
        usernameLayout.setErrorEnabled(true);
        usernameLayout.setError("Usuário inválido");
    }

    protected void setErroSenha(){
        passwordLayout.setErrorEnabled(true);
        passwordLayout.setError("Senha inválida");
    }

    @OnTextChanged(R.id.edit_text_username) public void validaUsuario(){
        usernameLayout.setErrorEnabled(false);
        usernameLayout.setError("");
    }

    @OnTextChanged(R.id.edit_text_password) public void validaSenha(){
        passwordLayout.setErrorEnabled(false);
        passwordLayout.setError("");
    }

    @OnClick(R.id.buttonlogin) public void fazLogin(){
        if (usernameText.getText().toString().isEmpty()){
            setErroUsuario();
            YoYo.with(Techniques.Shake)
                    .duration(100)
                    .repeat(0)
                    .playOn(findViewById(R.id.buttonlogin));
        } else if(passwordText.getText().toString().isEmpty()){
            setErroSenha();
            YoYo.with(Techniques.Shake)
                    .duration(100)
                    .repeat(0)
                    .playOn(findViewById(R.id.buttonlogin));
        } else {
            Intent intentMain = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intentMain);
        }
    }

    @OnClick(R.id.logo)
    public void AnimarLogo(){
        YoYo.with(Techniques.Pulse)
                .duration(500)
                .repeat(1)
                .playOn(findViewById(R.id.logo));
    }

    @Override
    protected void onPause(){
        super.onPause();
        finish();
    }

}
