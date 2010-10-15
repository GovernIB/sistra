package es.caib.bantel.model;

import java.util.ArrayList;
import java.util.List;

import net.sf.hibernate.Criteria;
import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class Page implements java.io.Serializable
{
	private List results;
	private int pageSize;
	private int page;
   
	public Page(Query query, int page, int pageSize) throws HibernateException
	{
       
		this.page = page;
		this.pageSize = pageSize;
		results = query.setFirstResult(page * pageSize)
           .setMaxResults(pageSize+1)
           .list();
	}
		
	public Page( Criteria criteria, int page, int pageSize ) throws HibernateException
	{
		this.page = page;
		this.pageSize = pageSize;
				
		 results = criteria.setFirstResult(page * pageSize)
		.setMaxResults(pageSize+1)
        .list();        	
	}
	
   
	public boolean isNextPage() 
	{
		return results.size() > pageSize;
	}
   
	public boolean isPreviousPage() 
	{
		return page > 0;
	}
   
	public List getList() 
	{
		return isNextPage() ?
           new ArrayList( results.subList(0, pageSize) ) :
           results;
	}
	
	public int getPage()
	{
		return page;
	}
}
