import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;

@ManagedBean
@RolesAllowed({"user", "admin"})
public class ExtratoMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	DespesaServico despesaServico;
	@EJB
	UsuarioServico usuarioServico;

	@EJB
	PeriodoServico periodoServico;

	private List<PeriodoPlanejamento> periodosUsuario;
	private PeriodoPlanejamento periodoSelecionado;
	private List<Despesa> despesasUsuario;
	private BigDecimal totalPlanejado;
	private BigDecimal totalRealizado;
	private BigDecimal saldoTotal;

	public String iniciar(){
		setPeriodosUsuario(periodoServico.getPeriodosUsuario(usuarioServico.getUsuario().getCodigo()));
		if(getPeriodosUsuario().size() > 0){
			setPeriodoSelecionado(getPeriodosUsuario().get(0));
			return consultaExtrato();
		}
		return "dashboard";
	}

	@PermitAll
	public String consultaExtrato(){

		setDespesasUsuario(despesaServico.getDespesasPeriodo(getPeriodoSelecionado()));
		totalizacao(getDespesasUsuario());
		setPeriodoSelecionado(getPeriodosUsuario().get(0));
		return "extrato";
	}
	
	public void alteraPeriodo(
			final ValueChangeEvent valueChangeEvent) {
		if (valueChangeEvent != null) {
			for(PeriodoPlanejamento tf : getPeriodosUsuario()){
				if (((PeriodoPlanejamento) valueChangeEvent.getNewValue()).getCodigo().equals(tf.getCodigo())){
					setPeriodoSelecionado(tf);
					consultaExtrato();
					break;
				}
			}
		}
	}


	private void totalizacao(List<Despesa> despesasUsuario) {
		BigDecimal somaPlanejado = new BigDecimal(0);
		BigDecimal somaRealizado = new BigDecimal(0); 
		BigDecimal saldoTotal = new BigDecimal(0);
		for (Despesa d: despesasUsuario){
		    somaPlanejado = somaPlanejado.add(d.getValorPlanejado());
		    somaRealizado = somaRealizado.add(d.getValorGasto());
		    d.setSaldoDespesa(d.getValorPlanejado().subtract(d.getValorGasto()));
		    saldoTotal = saldoTotal.add(d.getSaldoDespesa());
		}
		setTotalDespesas(somaPlanejado);
		setTotalRealizado(somaRealizado);
		setSaldoTotal(saldoTotal);
	}

	public List<PeriodoPlanejamento> getPeriodosUsuario() {
		return periodosUsuario;
	}

	public void setPeriodosUsuario(List<PeriodoPlanejamento> periodosUsuario) {
		this.periodosUsuario = periodosUsuario;
	}

	public List<Despesa> getDespesasUsuario() {
		return despesasUsuario;
	}

	public void setDespesasUsuario(List<Despesa> despesasUsuario) {
		this.despesasUsuario = despesasUsuario;
	}

	public PeriodoPlanejamento getPeriodoSelecionado() {
		return periodoSelecionado;
	}

	public void setPeriodoSelecionado(PeriodoPlanejamento periodoSelecionado) {
		this.periodoSelecionado = periodoSelecionado;
	}

	public BigDecimal getTotalRealizado() {
		return totalRealizado;
	}

	public void setTotalRealizado(BigDecimal totalRealizado) {
		this.totalRealizado = totalRealizado;
	}

	public BigDecimal getTotalDespesas() {
		return totalPlanejado;
	}

	public void setTotalDespesas(BigDecimal totalDespesas) {
		this.totalPlanejado = totalDespesas;
	}

	public BigDecimal getSaldoTotal() {
		return saldoTotal;
	}

	public void setSaldoTotal(BigDecimal saldoTotal) {
		this.saldoTotal = saldoTotal;
	}


}
