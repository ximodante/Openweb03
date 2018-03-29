package openadmin.web.components;

import java.io.Serializable;
import java.util.Set;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultSubMenu;

import openadmin.model.control.MenuItem;
import openadmin.util.lang.LangType;

public class PFPanelMenu implements Serializable {

	private static final long serialVersionUID = 11081501L;
	
	private LangType langType;
	
	public PFPanelMenu(LangType pLangType) {
		
		langType = pLangType;
		
	}
	
	public DefaultSubMenu itemPare(MenuItem pMenuItem, Set<MenuItem> lstMenuItem) {
		 
		 DefaultSubMenu submenu = new DefaultSubMenu(langType.msgGenerals(pMenuItem.getDescription()));
		 
		 for (MenuItem vr: lstMenuItem ){
			 
			 if (pMenuItem.equals(vr.getParent()) && vr.getTypeNode().equals("c")){
				 
				//Calls the method loadChild
				 submenu.addElement(itemFill(vr));
			 }
			 
			 else if (pMenuItem.equals(vr.getParent()) && vr.getTypeNode().equals("p")){
				 
				//Calls again the method loadParent
				 submenu.addElement(itemPare(vr, lstMenuItem));
			 }
		 }
		 
		 return submenu;
	 }
	
		
	 /**
	  * <desc>class that load the panel menu item</desc>
	  * @author 			Alfred Oliver
	  * @param pMenuItem    Class MenuItem
	  * @param pRol  		id rol
	  */
	 public DefaultMenuItem itemFill(MenuItem pMenuItem) {
		 
		 
		 DefaultMenuItem item = new DefaultMenuItem(langType.msgGenerals(pMenuItem.getDescription())); 
		 		 
		 item.setIcon("ui-icon-newwin");
		 item.setId("ida" + pMenuItem.getDescription());
		 item.setCommand("#{main.loadScreen(" + pMenuItem.getId() + ")}");
		 item.setUpdate("form1:idContingut");
		 //item.setUpdate("frmplan1:rutaPrograma frmplan1:idContingut");

		 return item;
		 
	 } 
	 
}
