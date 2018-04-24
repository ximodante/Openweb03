package openadmin.web.view;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlPanelGrid;
import javax.validation.constraints.Size;

import openadmin.annotations.Default;
import openadmin.annotations.OpenScreen;
import openadmin.model.Base;
import openadmin.util.edu.ReflectionUtils;
import openadmin.web.components.JSFComponents;

public class InterceptorComponent {

	
	public static UIComponent execute(Field pField, Integer numberView) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
		
		JSFComponents pJSFComponents = new JSFComponents();
		
		HtmlPanelGrid panelViewObject = null;
		
		boolean readOnly = false;
		
		String objectAction = "#{ctx.getView(" + numberView + ").base." + pField.getName() + "}";
		
		if (pField.isAnnotationPresent(Default.class)){
			
			readOnly = pField.getAnnotation(Default.class).readOnly();
			
		}
		
		/*****************************************  Field type String   *******************************************************/
		if (pField.getType().getSimpleName().endsWith("String")){
			
			if(pField.getAnnotation(Size.class).max()>=80){
				
				return  pJSFComponents.textArea01(readOnly, objectAction , pField.getType());
			
			}
			
			else 
				
				return pJSFComponents.inputText01(pField.getAnnotation(Size.class).max(), readOnly, objectAction, pField.getType());
		}
		

		/****************************************   Field type Integer Or Double  **********************************************/
		else if (pField.getType().getSimpleName().endsWith("Integer") || pField.getType().getSimpleName().endsWith("Double") 
				|| pField.getType().getSimpleName().endsWith("Long") ||pField.getType().getSimpleName().endsWith("Short")){
			
			
			if (pField.isAnnotationPresent(Size.class)){
				
				return pJSFComponents.inputText01(pField.getAnnotation(Size.class).max(), readOnly, objectAction, pField.getType());

				
			}else {
				
				return pJSFComponents.inputText01(9, readOnly, objectAction, pField.getType());
			
			}
			
		}
		
		//If is object
		else if (pField.getType().getName().startsWith("openadmin.model")){
			
			Base object = (Base) ReflectionUtils.createObject(pField.getType().getName());
			
			for (Field fi: object.getClass().getDeclaredFields()){
				
				if (fi.getName().equals("description")){
					
					panelViewObject = pJSFComponents.panelGrid(3, "panelGridTp1");
					
					//Add component
					panelViewObject.getChildren().add(pJSFComponents.inputText01(fi.getAnnotation(Size.class).max(), 
													true, 
													 "#{ctx.getView(" + numberView + ").base." + pField.getName() + ".description}",
													String.class));
					
					//Falta comprobar si es pot dibuixar en funcio de rol (Per fer)
					if (pField.isAnnotationPresent(OpenScreen.class)  && pField.getAnnotation(OpenScreen.class).normal()) {
						
						panelViewObject.getChildren().add(pJSFComponents.button01("#{main.loadScreenRecursive('"+ pField.getType().getName().toLowerCase().replace(".", "_") + "_default')}", String.class));
						
					}
					
					/**
					 * 
					if (f.isAnnotationPresent(Search.class)){
						
						panelViewObject.getChildren().add(TplSimpleComponent.commandFr2("...", "#{ctx.getView(ctx.sizeView()).searchSelect}", f.getType().getName(), f.getType().getName() + "_" + f.getName(), "../resources/icon/search_2.png"));
						
					} else if (f.isAnnotationPresent(NoSearch.class)){
						
					} else{
						
						panelViewObject.getChildren().add(TplSimpleComponent.commandFr2("...", "#{ctx.getView(ctx.sizeView()).searchAll}", f.getType().getName(), f.getType().getName() + "_" + f.getName(), "../resources/icon/search_2.png"));
					
					}
					
					if (!f.isAnnotationPresent(NoEditObject.class)){
						
						//Edit object
						panelViewObject.getChildren().add(TplSimpleComponent.commandFr2(MessagesTypes.msgLabels("","edit"),  "#{ctx.getView(ctx.sizeView()).editObject}", "id_" + f.getType().getName() + "_default", "id_" + f.getName(), "../resources/icon/new_2.png"));
						
					} else if (f.isAnnotationPresent(NoEditObject.class) && f.getAnnotation(NoEditObject.class).custom()){
						
						//Edit object
						panelViewObject.getChildren().add(TplSimpleComponent.commandFr2(MessagesTypes.msgLabels("","edit"),  "#{ctx.getView(ctx.sizeView()).editObject}", "id_" + f.getType().getName() + "_custom", "id_" + f.getName(), "../resources/icon/new_2.png"));
						
					}*/
					
					//panelView.getChildren().add(panelViewObject);
					return panelViewObject;
				}
			}
			
		}
		
		
		return null;
	}
}
