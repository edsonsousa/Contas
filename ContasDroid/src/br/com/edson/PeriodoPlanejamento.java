package br.com.edson;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PeriodoPlanejamento implements Serializable{

	public PeriodoPlanejamento() {

	}
	public PeriodoPlanejamento(Long codigoPlanejamento, String nomePlanejamento) {
		this.codigo = codigoPlanejamento;
		this.nome = nomePlanejamento;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Long codigo;
	
	String nome;
	
	Date dataInicial;
	
	Date dataFinal;
	
	BigDecimal receitaPeriodo;

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getReceitaPeriodo() {
		return receitaPeriodo;
	}

	public void setReceitaPeriodo(BigDecimal receitaPeriodo) {
		this.receitaPeriodo = receitaPeriodo;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}	
	
	
}
