
import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ControleEntidade implements Serializable {

	@PersistenceContext
	private EntityManager entityManager;

	public Object updateObject(Object object) {
		return entityManager.merge(object);
	}

	public void createObject(Object object) {
		entityManager.persist(object);
	}

	public void refresh(Object object) {
		entityManager.refresh(object);
	}

	public <T> T find(Class<T> clazz, Long id) {
		return entityManager.find(clazz, id);
	}

	public void deleteObject(Object object) {
		entityManager.remove(object);
	}

}