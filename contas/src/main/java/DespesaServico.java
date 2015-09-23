import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Stateless
public class DespesaServico {

	@PersistenceContext
	private EntityManager factory;
	
	public List<Despesa> getDespesasPeriodo(
			PeriodoPlanejamento periodoSelecionado) {
		
		String hql = "SELECT d FROM Despesa d WHERE d.periodoPlanejamento.codigo = :codigoPeriodo";

		factory.flush();
		return (List<Despesa>) factory.createQuery(hql).
				setParameter("codigoPeriodo", periodoSelecionado.getCodigo()).getResultList();

	}

	public String registrarDespesa(Despesa despesaSelecionada, BigDecimal valor) {

		RealizacaoDespesa realizacaoDespesa = new RealizacaoDespesa(despesaSelecionada, valor);
		
		try{
			factory.persist(realizacaoDespesa);
			atualizaDespesa(despesaSelecionada, valor);
		}
		catch (Exception e){
			return "Erro ao salvar Realização";
		}
		
		return "Realização registrada com sucesso";
	}

	private void atualizaDespesa(Despesa despesaSelecionada, BigDecimal valor) {
		Despesa d = (Despesa) factory.createQuery("SELECT d FROM Despesa d WHERE d.codigo = :codigoDespesa").
		setParameter("codigoDespesa", despesaSelecionada.getCodigo()).getSingleResult();
		
		d.setValorGasto(d.getValorGasto().add(valor));
		
		factory.merge(d);
	}

	public List<RealizacaoDespesa> getRealizacoesDespesaPeriodo(
			Despesa despesaSelecionada) {
		String hql = "SELECT r FROM RealizacaoDespesa r WHERE r.despesa.codigo = :codigoDespesa";

		factory.flush();
		return (List<RealizacaoDespesa>) factory.createQuery(hql).
				setParameter("codigoDespesa", despesaSelecionada.getCodigo()).getResultList();
	}

	public void salvaDespesas(List<Despesa> despesasPeriodoClone) {
		for(Despesa d : despesasPeriodoClone)
			factory.persist(d);
		
	}

	public void exluirDespesaPeriodo(Despesa despesaExcluir) {
		factory.remove(despesaExcluir);
		
	}

}
