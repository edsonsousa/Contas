
import java.security.Principal;

import javax.ejb.Stateful;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateful
public class UsuarioServico{

	@PersistenceContext
	private static EntityManager entityManager;

	private Usuario usuario;

	public Usuario getUsuario() {
		//usuario = null;
		if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado") != null)
			return (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
		
		Principal principal = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		if (principal != null) {
			usuario = //entityManager.find(Usuario.class, principal.getName()); 
					(Usuario) entityManager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login").
					setParameter("login", principal.getName()).getSingleResult();
			
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
			.put("usuarioLogado", usuario);
			
		}
		return usuario;
	}

	public String salvaUsuario(Usuario usuario){
		try{
			entityManager.persist(usuario);
			incluiPerfil(usuario.getLogin(), "USER");
		}
		catch (Exception e){
			return "Erro ao salvar usuário.";
		}
		return "Usuário salvo com sucesso";

	}

	public void incluiPerfil(String login, String perfil) {
		entityManager.createNativeQuery("INSERT into PERFIL (login, perfil) VALUES (:login, :perfil);").
		setParameter("login", login).setParameter("perfil", perfil).executeUpdate();

	}

	public boolean verificaUnicidadeChave(String login) {

		return entityManager.createQuery("SELECT u FROM Usuario u WHERE u.login = :login").
				setParameter("login", login).getResultList().size() <= 0;

	}

}	