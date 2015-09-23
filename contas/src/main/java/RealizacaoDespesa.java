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
public class RealizacaoDespesa implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="gen_registro", sequenceName="seq_registro", allocationSize = 1)
	@GeneratedValue(generator="gen_registro",strategy=GenerationType.SEQUENCE)
	private Long codigo;
	
	@NotNull
	@OneToOne
	Despesa despesa;
	
	@NotNull
	BigDecimal valor;
	
	@NotNull
	@Column(name="datahora")
	@Temporal(TemporalType.TIMESTAMP)
	private
	Date datahora;
	
	@NotNull
	private
	String descricao;

	public RealizacaoDespesa() {
		// TODO Auto-generated constructor stub
	}
	
	public RealizacaoDespesa(Despesa despesa, BigDecimal valor) {
		this.despesa = despesa;
		this.valor = valor;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
