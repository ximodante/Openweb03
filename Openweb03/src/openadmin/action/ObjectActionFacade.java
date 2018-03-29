package openadmin.action;

import openadmin.model.Base;

public interface ObjectActionFacade {
	
	public Base getBase();
	public void setBase(Base pBase);
	public ContextAction getCtx();
	public void setCtx(ContextAction ctx);
	public String getMetodo();
	
}
