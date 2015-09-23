import java.io.IOException;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@RolesAllowed({"user", "admin"})
public class AutenticacaoMB {

	@PermitAll
	public void logout() throws IOException {
		// FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		//this.name = null;
		FacesContext fc = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession)fc.getExternalContext().getSession(false);
		session.invalidate();
		FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/contas");
	}

}
