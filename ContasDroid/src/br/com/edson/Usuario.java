package br.com.edson;



public class Usuario{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Usuario(Long codigo) {
		this.codigo = codigo;
	}

	public Usuario() {

	}

	private Long codigo;
	
	private String login;
	
	private String senha;
	
	private String nome;
	
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


