package com.example.android.amor_em_leite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class TelaLogin extends AppCompatActivity {

    EditText edtEmailLogin, edtSenhaLogin;
    Button btnLogin;
    TextView txtCadastro;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmailLogin = (EditText) findViewById(R.id.edittxt_email_login);
        edtSenhaLogin = (EditText) findViewById(R.id.edittxt_senha_login);
        btnLogin = (Button) findViewById(R.id.btn_login);
        txtCadastro = (TextView) findViewById(R.id.txt_cadastrar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
    }

    private void Login(){

        String url = "http://192.168.1.42:8080/login/logar.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                if(response.trim().contains("login_ok")){
                    Toast.makeText(getApplicationContext(), "Login realizado com sucesso", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Login falhou", Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "erro:" + error.toString(), Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("email", edtEmailLogin.getText().toString().trim());
                params.put("senha", edtSenhaLogin.getText().toString().trim());


                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

        /*//criando um evento para o txtCadastro
        txtCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //criando uma intenção para chamar a tela de cadastro
                Intent abreCadastro = new Intent(TelaLogin.this, TelaCadastro.class);
                startActivity(abreCadastro);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectivityManager connMgr = (ConnectivityManager)//verifica conexao
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); //verifica o estado da rede, rede conectada ou não
                if (networkInfo != null && networkInfo.isConnected()) { //primeiro if verifica o estado da rede

                    //a partir daqui até o primeiro else faz a verificação dos campos e realiza o login
                    String email = edtEmailLogin.getText().toString();
                    String senha = edtSenhaLogin.getText().toString();

                    if (email.isEmpty() || senha.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Nenhum campo pode estar vazio", Toast.LENGTH_LONG).show();
                    } else {

                        url = "http://192.168.1.42/login/login.php";

                        parametros = "email=" + email + "&senha=" + senha;

                        new SolicitaDados().execute(url);//esse DownloadWebpageTask(agora SolicitaDados) é uma classe que faztodo processamento em segundo plano não comprometendo a activity atual
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Nenhuma conexão foi detectada", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    public class SolicitaDados extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return Conexao.postDados(urls[0], parametros);

        }

        @Override
        protected void onPostExecute(String resultado) {

            if (resultado.contains("login_ok")) {
                Intent abreInicio = new Intent(TelaLogin.this, TelaInicial.class);
                startActivity(abreInicio);
            } else {
                Toast.makeText(getApplicationContext(), "Usuário ou senha estão incorretos", Toast.LENGTH_LONG).show();
            }

        }
    }

   /* @Override
    protected void onPause() {
        super.onPause();
        finish();
    }*/
}
