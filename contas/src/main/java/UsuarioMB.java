import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class UsuarioMB extends PadraoBean <Usuario>{

	@EJB UsuarioServico usuarioBean;

	private String login;
	private String nome;
	private String email;
	private String confirmaEmail;
	private String mensagemRetorno;
	private String chave;
	private String senha;

	@Override
	public String save() {
		if(!getInstance().getEmail().equals(getConfirmaEmail())){
			setMensagemRetorno("Os campos de Email e Confirmação de Email devem ser iguais.");
		}
		else if(!usuarioBean.verificaUnicidadeChave(getInstance().getLogin())){
			setMensagemRetorno("Login já utilizado por outro Usuário, favor informar outro Login.");
		}
		else{			
			getInstance().setSenha(ContasUtil.gerarSenhaHash(getInstance().getSenha()));
			setMensagemRetorno(usuarioBean.salvaUsuario(getInstance()));

			getInstance().setNome(null);
			getInstance().setEmail(null);
			setConfirmaEmail(null);
			getInstance().setLogin(null);
		}
		return "sucessoCadastro";
	}
	
	public String cancelar(){
		return "login";
	}
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public String getMensagemRetorno() {
		return mensagemRetorno;
	}

	public void setMensagemRetorno(String mensagemRetorno) {
		this.mensagemRetorno = mensagemRetorno;
	}

	public String getConfirmaEmail() {
		return confirmaEmail;
	}

	public void setConfirmaEmail(String confirmaEmail) {
		this.confirmaEmail = confirmaEmail;
	}
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
