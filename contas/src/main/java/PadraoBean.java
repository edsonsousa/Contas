
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;


public class PadraoBean<T extends EntidadeBase> implements Serializable {

	@EJB ControleEntidade controleEntidade;
	
	@Inject
	private Conversation conversation;
	
	private Long id;
	private T instance;

	public T getInstance() {
		if (instance == null) {
			if (id != null) {
				instance = loadInstance();
			} else {
				instance = createInstance();
			}
		}
		return instance;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public T loadInstance() {
		return controleEntidade.find(getClassType(), getId());
	}

	public T createInstance() {
		try {
			return getClassType().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Class<T> getClassType() {
		ParameterizedType parameterizedType = (ParameterizedType) getClass()
				.getGenericSuperclass();
		return (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	
	public boolean isManaged() {
		return (getInstance()).getId() != null;
	}
	
	public String save() {
		if (isManaged()) {
			controleEntidade.updateObject(getInstance());
		} else {
			controleEntidade.createObject(getInstance());
		}
		//conversation.end();
		return "Registro Salvo com Sucesso";
	}

	public String cancel() {
		//conversation.end();
		return "cancelled";
	}


	public void initConversation() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

}

