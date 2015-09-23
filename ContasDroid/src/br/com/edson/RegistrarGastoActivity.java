package br.com.edson;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrarGastoActivity extends Activity implements Runnable{

	private EditText valor;
	private EditText descricao;
	private TextView valorPlanejado;
	private TextView valorGasto;
	private Spinner planejamentos;
	private Spinner despesas;
	private Button registrar;
	private Button cancelar;

	List<PeriodoPlanejamento> listaPlanejamentos;
	List<Despesa> listaDespesas;
	private Long codigoDespesa;
	private Long codigoPlanejamento;
	private LinearLayout linProgressBar;
	private ScrollView principal;
	private final Handler uiHandler=new Handler();
	private boolean isUpdateRequired=false;
	String ARQUIVO_PREFERENCIA = "zigFile";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registrar_gasto);
		descricao = (EditText) findViewById(R.id.descricao);
		valor = (EditText) findViewById(R.id.valor);
		planejamentos = (Spinner) findViewById(R.id.planejamentos);
		despesas = (Spinner) findViewById(R.id.despesas);
		//		valorPlanejado = (TextView) findViewById(R.id.valorPlanejado);
		//		valorGasto = (TextView) findViewById(R.id.valorGasto);
		registrar = (Button) findViewById(R.id.registrar);
		cancelar = (Button) findViewById(R.id.cancelar);


		linProgressBar = (LinearLayout) findViewById(R.id.lin_progress_bar);
		principal = (ScrollView) findViewById(R.id.layoutPrincipal);
		linProgressBar.setVisibility(View.VISIBLE);
		principal.setVisibility(View.INVISIBLE);

		Thread thread = new Thread(this);
		thread.start();



		registerButtonListeners();

	}

	@Override
	public void run() {
		consultaListas();
		handler.sendEmptyMessage(0);
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			populaPlanejamentosDespesas();
			linProgressBar.setVisibility(View.GONE);
			principal.setVisibility(View.VISIBLE);

		}
	};

	@Override
	public void onBackPressed() {

		if(retornaUsuarioPreferencia() == null){
			super.onBackPressed();
		}else{
			finish();
		}
	}

	private String retornaUsuarioPreferencia() {
		SharedPreferences settings = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
		settings.edit().commit();
		return settings.getString("usuario", null);
	}

	private void consultaListas() {
		String c = retornaUsuarioPreferencia();

		listaPlanejamentos = getCliente().getPlanejamentos(Long.valueOf(c));
		if(listaPlanejamentos.size() > 0){
			listaDespesas = getCliente().getDespesasPlanejamento(listaPlanejamentos.get(0).getCodigo());

		}
	}

	private void populaPlanejamentosDespesas() {

		ArrayAdapter <CharSequence> adapterPlanejamentos =
				new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item);
		adapterPlanejamentos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		//listaPlanejamentos = getCliente().getPlanejamentos();


		for(PeriodoPlanejamento p : listaPlanejamentos){
			adapterPlanejamentos.add(p.getNome());
		}

		planejamentos.setAdapter(adapterPlanejamentos);

		if(listaPlanejamentos.size() > 0){
			populaDespesas(listaPlanejamentos.get(0).getCodigo());

		}
		else{
			listaDespesas = new ArrayList<Despesa>();
			Toast.makeText(RegistrarGastoActivity.this, "Nenhum Planejamento Cadastrado", Toast.LENGTH_SHORT).show();
		}
		isUpdateRequired = true;
	}


	private void populaDespesas(Long codigoPlanejamento) {
		//listaDespesas = getCliente().getDespesasPlanejamento(codigoPlanejamento);

		if(listaDespesas.size() > 0){
			codigoDespesa = listaDespesas.get(0).getCodigo();
			ArrayAdapter <CharSequence> adapterDespesas =
					new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item);
			adapterDespesas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			for(Despesa d : listaDespesas)
				adapterDespesas.add(retornaNomeeValor(d));

			despesas.setAdapter(adapterDespesas);
		}

		//		if(listaDespesas.size() > 0){
		//			valorPlanejado.setText(listaDespesas.get(0).getValorPlanejado().toString());
		//			valorGasto.setText(listaDespesas.get(0).getValorGasto().toString());
		//		}
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}

	private void registerButtonListeners() {

		registrar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

				if(codigoDespesa == null || valor.getText().toString().equals("")){
					Toast.makeText(RegistrarGastoActivity.this, "Campos Obrigat�rios n�o foram informados.", Toast.LENGTH_SHORT).show();
				}else{
					String s = getCliente().registrar(codigoDespesa, valor.getText().toString(), 
							descricao.getText().toString());


					Toast.makeText(RegistrarGastoActivity.this, s, Toast.LENGTH_SHORT).show();
					populaDespesas(codigoPlanejamento);
					valor.setText("");
					descricao.setText("");

				}
			}

		});

		despesas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
				for(Despesa d : listaDespesas){
					if(retornaNomeeValor(d).equals((String) parent.getItemAtPosition(pos)))
						codigoDespesa = d.getCodigo();
				}	

			}

			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		planejamentos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

				for(PeriodoPlanejamento p : listaPlanejamentos){
					if(p.getNome().equals((String) parent.getItemAtPosition(pos)))
						codigoPlanejamento = p.getCodigo();
					populaDespesas(codigoPlanejamento);
				}	

			}
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

	}
	private String retornaNomeeValor(Despesa d) {
		return d.getNome() + " ( "+
				d.getValorPlanejado().subtract(d.getValorGasto()).toString() + " )";
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_registrar_gasto, menu);
		return true;
	}

	private ContasCliente getCliente() {
		ContasApplication app = (ContasApplication) getApplication();
		return app.getCliente();
	}


}