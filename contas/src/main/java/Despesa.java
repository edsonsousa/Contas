import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
public class Despesa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="gen_despesa", sequenceName="seq_despesa", allocationSize = 1)
	@GeneratedValue(generator="gen_despesa",strategy=GenerationType.SEQUENCE)
	private Long codigo;
	
	@NotNull
	BigDecimal valorPlanejado;
	
	@NotNull
	BigDecimal valorGasto;
	
	@NotNull
	String nome;
	
	@NotNull
	@OneToOne
	PeriodoPlanejamento periodoPlanejamento;
	
	@Transient
	BigDecimal saldoDespesa;
	
	public Despesa() {

	}
	
	public Despesa(Long codigo) {
		this.codigo = codigo;
	}

	public Despesa(String nome, BigDecimal valorPlanejado, PeriodoPlanejamento periodoPlanejamento) {
		this.nome = nome;
		this.valorPlanejado = valorPlanejado;
		setValorGasto(new BigDecimal(0L));
		this.periodoPlanejamento = periodoPlanejamento;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	
	public PeriodoPlanejamento getPeriodoPlanejamento() {
		return periodoPlanejamento;
	}

	public void setPeriodoPlanejamento(PeriodoPlanejamento periodoPlanejamento) {
		this.periodoPlanejamento = periodoPlanejamento;
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
	

	
	public BigDecimal getSaldoDespesa() {
		return saldoDespesa;
	}

	public void setSaldoDespesa(BigDecimal saldoDespesa) {
		this.saldoDespesa = saldoDespesa;
	}

	@Override
	public int hashCode() {
		int h = 31;
		h = 31*h + super.hashCode();
		return h;
	}
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Despesa)) return false;
		Despesa p = (Despesa ) o;
		if(this.getCodigo().equals(p.getCodigo())) return true;
		return super.equals(o);
	}
	
}
