import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
public class PeriodoPlanejamento implements Serializable{

	public PeriodoPlanejamento() {

	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="gen_periodo", sequenceName="seq_periodo", allocationSize = 1)
	@GeneratedValue(generator="gen_periodo",strategy=GenerationType.SEQUENCE)
	private Long codigo;
	
	@NotNull
	@OneToOne
	Usuario usuario;
	
	@NotNull
	String nome;
	
	@NotNull
	@Column(name="datainicial")
	@Temporal(TemporalType.DATE)
	Date dataInicial;
	
	@NotNull
	@Column(name="datafinal")
	@Temporal(TemporalType.DATE)
	Date dataFinal;
	
	@NotNull
	BigDecimal receitaPeriodo;

	public PeriodoPlanejamento(Long codigo) {
		this.codigo = codigo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

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
	
	@Override
	public int hashCode() {
		int h = 31;
		h = 31*h + super.hashCode();
		return h;
	}
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof PeriodoPlanejamento)) return false;
		PeriodoPlanejamento p = (PeriodoPlanejamento ) o;
		if(this.getCodigo().equals(p.getCodigo())) return true;
		return super.equals(o);
	}
	
}
