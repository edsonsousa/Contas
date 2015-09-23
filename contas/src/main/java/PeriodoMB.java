
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

//@Path( "/trans" )
@ManagedBean
@RolesAllowed({"user", "admin"})
public class PeriodoMB implements Serializable{

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
	private PeriodoPlanejamento periodoEdicao;
	private PeriodoPlanejamento periodoClone;
	private String mensagemRetorno;
	private List<Despesa> despesasPeriodoEdicao;
	private List<Despesa> despesasPeriodoClone;
	private Despesa despesaExcluir;

	
	public String iniciar(){
		setPeriodoEdicao(null);
		setPeriodosUsuario(periodoServico.getPeriodosUsuario(usuarioServico.getUsuario().getCodigo()));
		return "consultaPeriodo";
	}


	public String salvarPeriodo(){
		if(getPeriodoClone() != null && getPeriodoClone().getNome() != null && getPeriodoClone().getNome().equals(getPeriodoEdicao().getNome())){
			setMensagemRetorno("Novo Período deve possuir nome diferente de Período original.");
		}
		periodoServico.salvaPeriodo(getPeriodoEdicao());
		
		if(getPeriodoClone() != null){
			criaDespesas();
		}
		
		setMensagemRetorno("Período Salvo com sucesso");
		return "cadastroFonte";
	}

	
	private void criaDespesas() {
		setDespesasPeriodoEdicao(despesaServico.getDespesasPeriodo(getPeriodoEdicao()));
		
		setDespesasPeriodoClone(new ArrayList<Despesa>());
		
		for(Despesa d : getDespesasPeriodoEdicao()){
			getDespesasPeriodoClone().add(new Despesa(d.getNome(), d.getValorPlanejado(), getPeriodoEdicao()));
		}
		
		despesaServico.salvaDespesas(getDespesasPeriodoClone());
		
	}


	public String clonarPeriodo(){
		setPeriodoEdicao(periodoServico.getPeriodo(getPeriodoClone()));
		setDespesasPeriodoEdicao(despesaServico.getDespesasPeriodo(getPeriodoEdicao()));
		getPeriodoEdicao().setCodigo(null);
		
		return "cadastroPeriodo";
	}
	
	public String excluirDespesa(){
		despesaServico.getRealizacoesDespesaPeriodo(getDespesaExcluir());
		setMensagemRetorno("Despesa excluída com Sucesso");
		return "cadastroPeriodo";
	}
	
	public String editarPeriodo(){
		if(getPeriodoEdicao() == null){
			PeriodoPlanejamento p = new PeriodoPlanejamento();
			setPeriodoEdicao(p);
		}
		else{
			setDespesasPeriodoEdicao(despesaServico.getDespesasPeriodo(getPeriodoEdicao()));
		}

		return "cadastroPeriodo";
	}

	public String cancelar(){
		return "dashboard";
	}

	public List<PeriodoPlanejamento> getPeriodosUsuario() {
		return periodosUsuario;
	}

	public void setPeriodosUsuario(List<PeriodoPlanejamento> periodosUsuario) {
		this.periodosUsuario = periodosUsuario;
	}
	public PeriodoPlanejamento getPeriodoEdicao() {
		return periodoEdicao;
	}
	public void setPeriodoEdicao(PeriodoPlanejamento periodoEdicao) {
		this.periodoEdicao = periodoEdicao;
	}


	public PeriodoPlanejamento getPeriodoClone() {
		return periodoClone;
	}


	public void setPeriodoClone(PeriodoPlanejamento periodoClone) {
		this.periodoClone = periodoClone;
	}


	public String getMensagemRetorno() {
		return mensagemRetorno;
	}


	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
	}

	public List<Despesa> getDespesasPeriodoClone() {
		return despesasPeriodoClone;
	}


	public void setDespesasPeriodoClone(List<Despesa> despesasPeriodoClone) {
		this.despesasPeriodoClone = despesasPeriodoClone;
	}


	public List<Despesa> getDespesasPeriodoEdicao() {
		return despesasPeriodoEdicao;
	}


	public void setDespesasPeriodoEdicao(List<Despesa> despesasPeriodoEdicao) {
		this.despesasPeriodoEdicao = despesasPeriodoEdicao;
	}


	public Despesa getDespesaExcluir() {
		return despesaExcluir;
	}


	public void setDespesaExcluir(Despesa despesaExcluir) {
		this.despesaExcluir = despesaExcluir;
	}

}
