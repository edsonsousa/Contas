package br.com.edson;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;

public class ContasCliente {

	private final Context context;
	private final HttpClient httpClient;
	private final Long CODIGO_USUARIO = 3L;
	private static final String NAMESPACE = "http://contas_webservice/";
	private static String URL="http://contas-confere.rhcloud.com/ws/ContasWSService?wsdl";
	//private static String URL="http://10.0.0.2:8080/contas/ws/ContasWSService?wsdl";
	private static final String METHOD_NAME_REGISTRAR = "registrar";
	private static final String METHOD_NAME_PLANEJAMENTOS = "planejamentos";
	private static final String METHOD_NAME_DESPESAS = "despesas";
	private static final String METHOD_NAME_LOGIN = "login";
	private static final String SOAP_ACTION =  "http://contas_webservice/planejamentos";
	private final String URL_SERVIDOR = "http://contas-confere.rhcloud.com";
	//private final String URL_SERVIDOR = "http://localhost:8080";

	public ContasCliente(Context context) {
		this.context = context;

		BasicHttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, false);
		httpClient = new DefaultHttpClient(params);
	}

	public String registrar(Long codigoDespesa, String valor, String descricao) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_REGISTRAR); 


		PropertyInfo cdDespesa = new PropertyInfo();
		PropertyInfo valorSOAP = new PropertyInfo();
		PropertyInfo descricaoSOAP = new PropertyInfo();

		cdDespesa.setName("codigoDespesa");
		cdDespesa.setType(PropertyInfo.LONG_CLASS);

		valorSOAP.setName("valor");
		valorSOAP.setType(valor.getClass());

		descricaoSOAP.setName("descricao");
		descricaoSOAP.setType(PropertyInfo.STRING_CLASS);

		request.addProperty(cdDespesa.getName(), codigoDespesa);
		request.addProperty(valorSOAP.getName(), valor);
		request.addProperty(descricaoSOAP.getName(), descricao);


		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(NAMESPACE.concat(METHOD_NAME_REGISTRAR), envelope);
			return ((SoapPrimitive) envelope.getResponse()).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public List<PeriodoPlanejamento> getPlanejamentos(Long codigoUsuario) {

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_PLANEJAMENTOS); 


		PropertyInfo cdDespesa = new PropertyInfo();

		cdDespesa.setName("codigoUsuario");
		cdDespesa.setType(PropertyInfo.LONG_CLASS);

		request.addProperty(cdDespesa.getName(), codigoUsuario);		  

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		List<PeriodoPlanejamento> l = null;
		try {
			androidHttpTransport.call(NAMESPACE.concat(METHOD_NAME_PLANEJAMENTOS), envelope);
			l = populaPlanejamento((Vector<SoapObject>) envelope.getResponse());
		} catch (SoapFault e) {
			e.printStackTrace();
			return new ArrayList<PeriodoPlanejamento>();
		} catch (IOException e) {
			e.printStackTrace();
			return new ArrayList<PeriodoPlanejamento>();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return new ArrayList<PeriodoPlanejamento>();
		}catch (Exception e){
			return new ArrayList<PeriodoPlanejamento>();
		}

		return l;

	}

	private List<PeriodoPlanejamento> populaPlanejamento(
			Vector<SoapObject> vector) {

		List<PeriodoPlanejamento> l = new ArrayList<PeriodoPlanejamento>();
		for(SoapObject v : vector){
			l.add(new PeriodoPlanejamento(Long.valueOf(v.getProperty("codigo").toString()), 
					v.getProperty("nome").toString()));
		}
		return l;
	}

	public List<Despesa> getDespesasPlanejamento(Long codigoPlanejamento) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_DESPESAS); 


		PropertyInfo cdPlanejamento = new PropertyInfo();

		cdPlanejamento.setName("codigoPlanejamento");
		cdPlanejamento.setType(PropertyInfo.LONG_CLASS);

		request.addProperty(cdPlanejamento.getName(), codigoPlanejamento);	  

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		List<Despesa> l = null;

		try {
			androidHttpTransport.call(NAMESPACE.concat(METHOD_NAME_DESPESAS), envelope);
			l = populaDespesas((Vector<SoapObject>) envelope.getResponse());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}


		return l;
	}

	private List<Despesa> populaDespesas(Vector<SoapObject> vector) {
		List<Despesa> l = new ArrayList<Despesa>();
		Despesa d = null;
		for(SoapObject v : vector){
			d = new Despesa();
			d.setCodigo(Long.valueOf(v.getProperty("codigo").toString()));
			d.setNome(v.getProperty("nome").toString());
			d.setValorGasto(new BigDecimal(v.getProperty("valorGasto").toString()));
			d.setValorPlanejado(new BigDecimal(v.getProperty("valorPlanejado").toString()));
			l.add(d);
		}
		return l;
	}

	private URI getRequestURI(String path) throws URISyntaxException {
		//SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		//String hostKey = context.getString(R.string.pref_host_key);
		//String host = prefs.getString(hostKey, context.getString(R.string.pref_host_default));
		//String portKey = context.getString(R.string.pref_port_key);
		//String port = prefs.getString(portKey, context.getString(R.string.pref_port_default));
		URI requestURI = new URI(URL_SERVIDOR+"//ws/" + path);
		//Log.i(LOG_TAG, "requestURI: " + requestURI);
		return requestURI;
	}

	public String login(String login, String senha) {
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_LOGIN); 


		PropertyInfo pLogin = new PropertyInfo();
		PropertyInfo pSenha = new PropertyInfo();

		pLogin.setName("login");
		pLogin.setType(PropertyInfo.STRING_CLASS);

		pSenha.setName("senha");
		pSenha.setType(PropertyInfo.STRING_CLASS);

		request.addProperty(pLogin.getName(), login);	  
		request.addProperty(pSenha.getName(), senha);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11); 

		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		Usuario u = null;

		try {
			androidHttpTransport.call(NAMESPACE.concat(METHOD_NAME_LOGIN), envelope);
			u = populaUsuario((SoapObject) envelope.getResponse());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		if(u.getCodigo() != null)
			return u.getCodigo().toString();
		
		return null;


	}

	private Usuario populaUsuario(SoapObject object) {
		Usuario u = new Usuario();
		u.setCodigo(Long.valueOf(object.getProperty("codigo").toString()));
		u.setNome(object.getProperty("nome").toString());

		return u;
	}
}
