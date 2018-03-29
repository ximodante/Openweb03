package openadmin.web.components;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.outputpanel.OutputPanel;
import org.primefaces.context.RequestContext;

public class PFDialog {

	private OutputPanel panelDialogo;	
	
	public void panel01() {
		
		FacesContext _context = FacesContext.getCurrentInstance();	
		UIComponent outView = _context.getViewRoot().findComponent("form1");
		Dialog dialog = new Dialog();
		dialog.setId("dialogo02");
		dialog.setWidgetVar("witdget");
		dialog.setHeader("Carles");
		dialog.setVisible(true);
		dialog.setMinimizable(true);
		dialog.setDynamic(true);
		dialog.setFooter("Alex");
		dialog.setDraggable(true);
		dialog.setMinWidth(600);
		dialog.setClosable(true);
		dialog.setModal(true);
	
		outView.getChildren().add(dialog);
		
		RequestContext request = RequestContext.getCurrentInstance();
		request.execute("PF('witdget').show()");
		
		
	}
	
}
