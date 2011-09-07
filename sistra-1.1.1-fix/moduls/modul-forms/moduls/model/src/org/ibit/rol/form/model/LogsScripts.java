package org.ibit.rol.form.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LogsScripts implements Serializable{

	private List logStr = new ArrayList();
	
	public void addLog(LogScript log){		
	    logStr.add(log);
	}
	
	public List getLogs(){
		return logStr;
	}
	
	public void resetLogs(){
		logStr.clear();
	}
}
