package br.com.edson;
import java.io.Serializable;
import java.math.BigDecimal;

public class Despesa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Long codigo;
	
	BigDecimal valorPlanejado;
	
	BigDecimal valorGasto;
	
	String nome;
		
	public Despesa() {

	}
	
	public Despesa(String nome, BigDecimal valorPlanejado, PeriodoPlanejamento periodoPlanejamento) {
		this.nome = nome;
		this.valorPlanejado = valorPlanejado;
	}

	public Despesa(Long codigo, String nome) {
		this.codigo = codigo;
		this.nome = nome;
	}

	public BigDecimal getValorPlanejado() {
		return valorPlanejado;
	}

	public void setValorPlanejado(BigDecimal valorPlanejado) {
		this.valorPlanejado = valorPlanejado;
	}

	public BigDecimal getValorGasto() {
		return valorGasto;
	}

	public void setValorGasto(BigDecimal valorGasto) {
		this.valorGasto = valorGasto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
	
}
