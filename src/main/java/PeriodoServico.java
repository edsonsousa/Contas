import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PeriodoServico {

	@PersistenceContext
	private EntityManager factory;

	@EJB UsuarioServico usuarioServico;

	public List<PeriodoPlanejamento> getPeriodosUsuario(Long codigoUsuario) {


		String hql = "SELECT p FROM PeriodoPlanejamento p WHERE p.usuario.codigo = :codigoUsuario";

		factory.flush();
		return (List<PeriodoPlanejamento>) factory.createQuery(hql).setParameter("codigoUsuario", codigoUsuario).getResultList();

	}

	public PeriodoPlanejamento getPeriodoAtualUsuario(Usuario usuario){

		String hql = "SELECT p FROM PeriodoPlanejamento p WHERE p.usuario.codigo = :codigoUsuario " +
				"AND p.dataInicial <= :dataAtual AND p.dataFinal >= :dataAtual";

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH,0);
		calendar.setTime(calendar.getTime());

		factory.flush();
		return (PeriodoPlanejamento) factory.createQuery(hql)
				.setParameter("codigoUsuario", usuario.getCodigo())
				.setParameter("dataAtual", calendar.getTime()).
				getSingleResult();
	}

	public PeriodoPlanejamento getPeriodo(PeriodoPlanejamento periodoClone) {
		String hql = "SELECT p FROM PeriodoPlanejamento p WHERE p.codigo = :codigoPeriodo";

		factory.flush();
		return (PeriodoPlanejamento) factory.createQuery(hql).
				setParameter("codigoPeriodo", periodoClone.getCodigo()).getSingleResult();

	}

	public void salvaPeriodo(PeriodoPlanejamento periodoPlanejamento) {
		if(periodoPlanejamento.getCodigo() == null){
			periodoPlanejamento.setUsuario(usuarioServico.getUsuario());
			periodoPlanejamento.setReceitaPeriodo(new BigDecimal(0L));
			factory.persist(periodoPlanejamento);
		}else{
			factory.merge(periodoPlanejamento);
		}
		factory.flush();
	}

}