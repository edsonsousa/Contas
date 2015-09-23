

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("despesaConverter")
public class DespesaConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		Despesa despesa = new Despesa(new Long(arg2));
		
		return despesa;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		if(arg2 != null){
			return ((Despesa) arg2).getCodigo().toString();
		}
		return null;
	}

}
