package org.ibit.rol.form.persistence.plugins;

import org.mozilla.javascript.NativeArray;

public class DominioSistraPluginCache extends DominioSistraPlugin
{
	
	public DominioSistraPluginCache( String nombreDominio, NativeArray params,String idKeyColumn,String valueKeyColumn )
	{
		super(nombreDominio,params,idKeyColumn,valueKeyColumn);
		this.setCaching(true);		
	}
		
	
	public DominioSistraPluginCache( String nombreDominio,String idKeyColumn,String valueKeyColumn )
	{
		this( nombreDominio,null,idKeyColumn,valueKeyColumn);
	}
		  
	public DominioSistraPluginCache( String nombreDominio, NativeArray params )
	{
		this( nombreDominio,params,null,null);
	}
 	
	}
