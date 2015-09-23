import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.event.ValueChangeEvent;


//@Path( "/trans" )
@ManagedBean
@RolesAllowed({"user", "admin"})
public class DespesaMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB 
	DespesaServico despesaServico;
	
	@EJB
	PeriodoServico periodoServico;
	
	@EJB
	UsuarioServico usuarioServico;

	private List<Despesa> despesasPeriodo;
	private List<RealizacaoDespesa> realizacoesDespesaPeriodo;
	private Despesa despesaSelecionada;
	private BigDecimal valor;
	private BigDecimal totalRealizacoes;
	private String mensagemRetorno;

	public String iniciar(){
		setDespesasPeriodo(
				despesaServico.getDespesasPeriodo(periodoServico.
						getPeriodoAtualUsuario(usuarioServico.getUsuario())));
		if(getDespesasPeriodo().size() > 0){
			setDespesaSelecionada(getDespesasPeriodo().get(0));
			atualizaRealizacoes();
		}
		
		return "realizacao";
	}
	
	private void atualizaRealizacoes() {
		setRealizacoesDespesaPeriodo(despesaServico.getRealizacoesDespesaPeriodo(getDespesaSelecionada()));
		setTotalRealizacoes(new BigDecimal(0L));
		for(RealizacaoDespesa r : getRealizacoesDespesaPeriodo())
			setTotalRealizacoes(getTotalRealizacoes().add(r.getValor()));
	}

	@PermitAll
	public String registrar(){

		if(getDespesaSelecionada() == null || getValor() == null){
			setMensagemRetorno("Os campos obrigatários devem ser informados.");
		} 
		else{
					
			setMensagemRetorno(despesaServico.registrarDespesa(getDespesaSelecionada(), getValor()));
			atualizaRealizacoes();
			setValor(null);
		}
		return getMensagemRetorno();
	}
	
	public void alteraDespesa(
			final ValueChangeEvent valueChangeEvent) {
		if (valueChangeEvent != null) {
			for(Despesa tf : getDespesasPeriodo()){
				if (((Despesa) valueChangeEvent.getNewValue()).getCodigo().equals(tf.getCodigo())){
					setDespesaSelecionada(tf);
					atualizaRealizacoes();
					break;
				}
			}
		}
	}
	
	public String atualizaValoresDespesa(){
		
		return "realizacao";
	}
	
	public String cancelar(){
		return "dashboard";
	}

	public List<Despesa> getDespesasPeriodo() {
		return despesasPeriodo;
	}

	public void setDespesasPeriodo(List<Despesa> despesasPeriodo) {
		this.despesasPeriodo = despesasPeriodo;
	}

	public Despesa getDespesaSelecionada() {
		return despesaSelecionada;
	}

	public void setDespesaSelecionada(Despesa despesaSelecionada) {
		this.despesaSelecionada = despesaSelecionada;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
	}

	public List<RealizacaoDespesa> getRealizacoesDespesaPeriodo() {
		return realizacoesDespesaPeriodo;
	}

	public void setRealizacoesDespesaPeriodo(
			List<RealizacaoDespesa> realizacoesDespesaPeriodo) {
		this.realizacoesDespesaPeriodo = realizacoesDespesaPeriodo;
	}

	public BigDecimal getTotalRealizacoes() {
		return totalRealizacoes;
	}

	public void setTotalRealizacoes(BigDecimal totalRealizacoes) {
		this.totalRealizacoes = totalRealizacoes;
	}

}
