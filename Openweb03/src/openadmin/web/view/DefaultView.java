package openadmin.web.view;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;

import org.primefaces.component.outputpanel.OutputPanel;

import lombok.Getter;
import openadmin.model.control.Action;
import openadmin.util.lang.LangType;
import openadmin.web.components.JSFComponents;
import openadmin.web.components.PFMenuBar;
import openadmin.action.ObjectAction;
import openadmin.annotations.Default;

public class DefaultView extends ObjectAction implements Serializable, ViewFacade{

	private static final long serialVersionUID = 23031001L;
	
	private LangType lang;
	
	//Out container
	@Getter
	private OutputPanel outPanel;
	
	private JSFComponents pJSFComponents = new JSFComponents();
	
	public void execute(LangType pLang, Integer numberView, List<Action> lstAction) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		
		
		lang = pLang;
			
		createExitComponent();
		
		//Menu actions
		PFMenuBar pfMenuBar = new PFMenuBar(lang);				
		outPanel.getChildren().add(pfMenuBar.menuAccions(numberView, lstAction));
		
		//Messages
		outPanel.getChildren().add(pJSFComponents.message01());
		
		//Title View
		outPanel.getChildren().add(pJSFComponents.labelTitle(lang.msgLabels(null, super.getBase().getClass().getSimpleName())));
		
		HtmlPanelGroup panelGroup = pJSFComponents.panelGroup("idDatatp1", "panelGroupTp1");
		
		HtmlPanelGrid panelView = pJSFComponents.panelGrid(1, "panelGridTp1");	
		
		//Create view
		for (Field f: super.getBase().getClass().getDeclaredFields()){
			
			//Exclusions
			if (f.getName().equals ("serialVersionUID") ) continue;		
			if (f.isAnnotationPresent(Default.class) && !f.getAnnotation(Default.class).visible()) continue;
				
			//Label
			panelView.getChildren().add (pJSFComponents.HtmlLabel01(lang.msgLabels(super.getBase().getClass().getSimpleName(), f.getName())));
			
			//Component			
			UIComponent pUicomponent = InterceptorComponent.execute(f, numberView);			
			if (null != pUicomponent) panelView.getChildren().add(pUicomponent);	
		
		}
				
		panelGroup.getChildren().add(panelView);
		outPanel.getChildren().add(panelGroup);
	
	}

	private void createExitComponent(){
		
		outPanel = new OutputPanel();
		outPanel.setStyleClass("caixaViewTp1");
		outPanel.setId("idviewdefault");
		
	}

}
