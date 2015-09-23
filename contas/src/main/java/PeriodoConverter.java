

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter("periodoConverter")
public class PeriodoConverter implements Converter {

	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		
		 if (arg2 == null || arg2.isEmpty()) {
	            return null;
	        }
		
		PeriodoPlanejamento periodo = new PeriodoPlanejamento();
		periodo.setCodigo(new Long(arg2));
		
		return periodo;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		if(arg2 != null){
			return ((PeriodoPlanejamento) arg2).getCodigo().toString();
		}
		return null;
	}

}
