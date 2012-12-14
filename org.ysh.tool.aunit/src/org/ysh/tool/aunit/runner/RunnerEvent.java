package org.ysh.tool.aunit.runner;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class RunnerEvent {
	public static enum EventType{
		START_RUN_CLASS,
		FINISH_RUN_CLASS,
		START_RUN_METHOD,
		SUCCESS_RUN_METHOD,
		FAIL_RUN_METHOD
	}
	
	public static enum AttrType{
		EXEC_CLASS,
		EXEC_INST,
		EXEC_METHOD,
		EXCEPTION
	}
	
	private static final Map<AttrType, Class<?>> attr2TargetClassMap = new HashMap<AttrType, Class<?>>();
	
	static {
		attr2TargetClassMap.put(AttrType.EXCEPTION, Throwable.class);
		attr2TargetClassMap.put(AttrType.EXEC_CLASS, Class.class);
		attr2TargetClassMap.put(AttrType.EXEC_INST, Object.class);
		attr2TargetClassMap.put(AttrType.EXEC_METHOD, Method.class);
	}
	
	
	private EventType eventType;
	private final Map<AttrType, Object> attributes;
	
	
	public RunnerEvent(){
		eventType = null;
		attributes = new HashMap<AttrType, Object>();
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	public void addAttribute(AttrType attrType, Object attrValue){
		if(attrType != null && attrValue != null){
			Class<?> clz = RunnerEvent.getAttrClassTypeByAttrType(attrType);
			boolean valid = clz.isAssignableFrom(attrValue.getClass());
			
			if(clz != null && valid){
					attributes.put(attrType, attrValue);
			}
		}
	}
	
	public static final Class<?> getAttrClassTypeByAttrType(AttrType attrType){
		if(attrType != null){
			return RunnerEvent.attr2TargetClassMap.get(attrType);
		}
		
		return null;
	}
	
	public Object getAttribute(AttrType attrType){
		if(attrType != null){
			return attributes.get(attrType);
		}
		
		return null;
	}
}
