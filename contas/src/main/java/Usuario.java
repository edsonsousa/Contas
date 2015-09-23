import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;


@Entity
public class Usuario extends EntidadeBase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Usuario(Long codigo) {
		this.codigo = codigo;
	}

	public Usuario() {

	}

	@Id
	@SequenceGenerator(name="gen_usuario", sequenceName="seq_usuario", allocationSize = 1)
	@GeneratedValue(generator="gen_usuario",strategy=GenerationType.SEQUENCE)
	private Long codigo;
	
	@NotNull
	private String login;
	
	@NotNull
	private String senha;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String email;
	
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}


