package br.com.edson;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class InicioActivity extends Activity {

	String ARQUIVO = "zigFile";
	String ARQUIVO_PREFERENCIA = "zigFile";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        String usuario = getUsuarioArquivo();
       
        if(usuario != null){
			SharedPreferences settings = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
			settings.edit().putString("usuario", usuario).commit();
			
        	Intent intent = new Intent(this, RegistrarGastoActivity.class);
			startActivity(intent);
		}
    }
    
private String getUsuarioArquivo() {

		FileInputStream fis;
		String usuario = null;
		try {
			fis = new FileInputStream(getFilesDir()+"/"+ARQUIVO);
			int len = 0;
			int pos = 0;
			byte[] data1 = new byte[1024];
			while ( -1 != (len = fis.read(data1)) ){
				pos = new String(data1, 0, len).indexOf("usuario=");
				if(pos != -1){
					usuario =  new String(data1, 0, len).substring(pos+8);
				}

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuario;
	}

    public void entrar(View view){
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	public void cadastrar(View view){
		Intent intent = new Intent(this, RegistrarGastoActivity.class);
		startActivity(intent);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_inicio, menu);
        return true;
    }
    
}
