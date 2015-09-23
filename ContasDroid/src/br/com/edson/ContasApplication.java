package br.com.edson;

import android.app.Application;

public class ContasApplication extends Application {

	private ContasCliente cliente;
	public static String LOG_TAG = "JaxrsSample";

	@Override
	public void onCreate() {
		super.onCreate();
		cliente = new ContasCliente(this);
	}


//	public static String getRequestURI(Context context) {
//		String requestURI = "http://localhost:8080/trans/ws/" + path;
//		//Log.i(LOG_TAG, "requestURI: " + requestURI);
//		return requestURI;
//	}


	public ContasCliente getCliente() {
		return cliente;
	}


	public void setCliente(ContasCliente cliente) {
		this.cliente = cliente;
	}
}
