package es.caib.redose.model;

import java.io.Serializable;
import java.util.List;

import net.sf.hibernate.HibernateException;
import net.sf.hibernate.Query;

public class Page implements Serializable
{
	 private List results;
	 private int pageSize;
	 private int page;
	 private int totalResults = 0;

	 /**
	 * Construct a new Page. Page numbers are zero-based, so the
	 * first page is page 0.
	 *
	 * @param query
	 * the Hibernate Query
	 * @param page
	 * the page number (zero-based)
	 * @param pageSize
	 * the number of results to display on the page
	 * @param queryCount Query para hacer el count.
	 */
	 public Page(Query query, int page, int pageSize, Query queryCount) throws HibernateException
	 {
		 this.page = page;
		 this.pageSize = pageSize;
		 
		 /*
		 * We set the max results to one more than the specfied pageSize to
		 * determine if any more results exist (i.e. if there is a next page
		 * to display). The result set is trimmed down to just the pageSize
		 * before being displayed later (in getList()).
		 */
		 results = query.setFirstResult(page * pageSize).setMaxResults(
		 pageSize + 1).list();
		 
		 /*
		  * We set the total number of results 
		  */
		 totalResults =  Integer.parseInt(queryCount.uniqueResult().toString());
		 
	 }

	 public boolean isFirstPage()
	 {
		 return page == 0;
	 }

	 public boolean isLastPage()
	 {
		 return page >= getLastPageNumber();
	 }

	 public boolean isNextPage()
	 {
		 return results.size() > pageSize;
	 }

	 public boolean isPreviousPage()
	 {
		 return page > 0;
	 }

	 public int getLastPageNumber()
	 {
		 int totalResults = getTotalResults();
		 int iTotalPages = totalResults / pageSize;
		 if ( totalResults % pageSize != 0 ) iTotalPages ++;
		 return  iTotalPages -1;
		 
		 /*
		 * We use the Math.floor() method because page numbers are zero-based
		 * (i.e. the first page is page 0).
		 */
		 /*
		 double totalResults = new Integer(getTotalResults()).doubleValue();
		 return new Double(Math.floor(totalResults / pageSize)).intValue();
		 */
	 }

	 public List getList()
	 {
		 /*
		 * Since we retrieved one more than the specified pageSize when the
		 * class was constructed, we now trim it down to the pageSize if a next
		 * page exists.
		 */
		 return isNextPage() ? results.subList(0, pageSize) : results;
	 }

	 public int getTotalResults()
	 {
		 return totalResults;
	 }

	 public int getFirstResultNumber()
	 {
		 return getList().size() > 0 ? page * pageSize + 1 : 0;
	 }

	 public int getLastResultNumber()
	 {
		 int fullPage = getFirstResultNumber() + pageSize - 1;
		 return getTotalResults() < fullPage ? getTotalResults() : fullPage;
	 }

	 public int getNextPageNumber()
	 {
		 return page + 1;
	 }

	 public int getPreviousPageNumber()
	 {
		 return page - 1;
	 }
}
