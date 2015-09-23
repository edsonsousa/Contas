package br.com.edson;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	String ARQUIVO_INTERNO = "/mnt/sdcard/zigFile.txt";
	String ARQUIVO_PREFERENCIA = "zigFile";
	String ARQUIVO = "zigFile";
	private EditText login;
	private EditText senha;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		login = (EditText) findViewById(R.id.login);
		senha = (EditText) findViewById(R.id.senhas);

		// getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public void entrar(View view){
		if(login.getText().toString().equals("") || senha.getText().toString().equals("")){
			Toast.makeText(LoginActivity.this, "Campos Obrigatários não informados.", Toast.LENGTH_SHORT).show();
		}else{

			String loginUsuario = getCliente().login(login.getText().toString(), senha.getText().toString()); 

			if(loginUsuario != null){

				//File file = new File(getFilesDir()+"/"+ARQUIVO);
				//file.mkdirs();
				//criaPastas(file.getPath());
				try {
					FileOutputStream fos = openFileOutput(ARQUIVO, Context.MODE_PRIVATE);
					//OutputStreamWriter osw = new OutputStreamWriter(fos);
					fos.write(("usuario="+loginUsuario).getBytes());
					//osw.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				SharedPreferences settings = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
				settings.edit().putString("usuario", loginUsuario).commit();
				settings.getString("usuario", null);
				Intent intent = new Intent(this, RegistrarGastoActivity.class);
				startActivity(intent);
			}
			else{
				Toast.makeText(LoginActivity.this, "Usuário ou Senha incorretos.", Toast.LENGTH_SHORT).show();
			}
		}

	}


	public void cancelar(View view){
		Intent intent = new Intent(this, InicioActivity.class);
		startActivity(intent);
	}

	private ContasCliente getCliente() {
		ContasApplication app = (ContasApplication) getApplication();
		return app.getCliente();
	}
}
