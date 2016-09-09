package com.yzsl.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yzsl.util.Page;
import com.yzsl.util.Reflections;

@Repository
public class CommonDao<T>  {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	/**
	 * 实体类类型(由构造方法自动赋值)
	 */
	private Class<?> entityClass;
	
	/**
	 * 构造方法，根据实例类自动获取实体类类型
	 */
	public CommonDao() {
		entityClass = Reflections.getClassGenricType(getClass());
	}
	
	

	/**
	 * 获得当前事物的session
	 * 
	 * @return org.hibernate.Session
	 */
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	 
	public void save(T o) {
		if (o != null) {
			 getCurrentSession().saveOrUpdate(o);
		}
	}

	 
	public T getById(Class<T> c, Serializable id) {
		return (T) getCurrentSession().get(c, id);
	}

	 
	public T getByHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	 
	public T getByHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		List<T> l = q.list();
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	 
	public void delete(T o) {
		if (o != null) {
			getCurrentSession().delete(o);
		}
	}

	 
	public void update(T o) {
		if (o != null) {
			getCurrentSession().update(o);
		}
	}

	 
	public void saveOrUpdate(T o) {
		if (o != null) {
			getCurrentSession().saveOrUpdate(o);
		}
	}

	 
	public List<T> find(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.list();
	}
	public List<T> find(String hql, Class<T> resultClass) {
		Query q = getCurrentSession().createQuery(hql);
		q.setResultTransformer(Transformers.aliasToBean(resultClass));
		
		return q.list();
	}

	 
	public List<T> find(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}
	 
	public <E> List<E> findme(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	 
	public List<T> find(String hql, Map<String, Object> params, int page, int rows) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	 
	public List<T> find(String hql, int page, int rows) {
		Query q = getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}
	
	
	  /** 
     * 去除hql的orderBy子句。 
     * @param qlString
     * @return 
     */  
    private String removeOrders(String qlString) {  
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);  
        Matcher m = p.matcher(qlString);  
        StringBuffer sb = new StringBuffer();  
        while (m.find()) {  
            m.appendReplacement(sb, "");  
        }
        m.appendTail(sb);
        return sb.toString();  
    } 
	
	
    /** 
     * 去除qlString的select子句。 
     * @param qlString
     * @return 
     */  
    private String removeSelect(String qlString){  
        int beginPos = qlString.toLowerCase().indexOf("from");  
        return qlString.substring(beginPos);  
    }  
    
    
    /**
	 * 创建 QL 查询对象
	 * @param qlString
	 * @param parameter
	 * @return
	 */
	public Query createQuery(String qlString,  Map<String,Object> parameter){
		Query query = getCurrentSession().createQuery(qlString);
		setParameter(query, parameter);
		return query;
	}
    /**
	 * 设置查询参数
	 * @param query
	 * @param parameter
	 */
	private void setParameter(Query query, Map<String,Object> parameter){
		if (parameter != null) {
            Set<String> keySet = parameter.keySet();
            for (String string : keySet) {
                Object value = parameter.get(string);
                //这里考虑传入的参数是什么类型，不同类型使用的方法不同  
                if(value instanceof Collection<?>){
                    query.setParameterList(string, (Collection<?>)value);
                }else if(value instanceof Object[]){
                    query.setParameterList(string, (Object[])value);
                }else{
                    query.setParameter(string, value);
                }
            }
        }
	}
    
	/**
	 * QL 分页查询
	 * @param page
	 * @param qlString
	 * @param parameter
	 * @return
	 */
    @SuppressWarnings("unchecked")
	public  Page<T> find(Page<T> page, String qlString, Map<String,Object> parameter){
		// get count
	        String countQlString = "select count(*) " + removeSelect(removeOrders(qlString));  
	        Query query = createQuery(countQlString, parameter);
	        List<Object> list = query.list();
	        if (list.size() > 0){
	        	page.setTotal(Integer.valueOf(list.get(0).toString()));
	        }else{
	        	page.setTotal(list.size());
	        }
			if (page.getTotal() < 1) {
				return page;
			}
			
			
    	// order by
    	String ql = qlString;
		if (StringUtils.isNotBlank(page.getOrderBy())){
			ql += " order by " + page.getOrderBy();
		}
        Query query1 = createQuery(ql, parameter); 
    	// set page
        query1.setFirstResult(page.getFirstResult());
        query1.setMaxResults(page.getLimit()); 
        page.setDataList(query1.list());
		return page;
    }

	 
	public Long count(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return (Long) q.uniqueResult();
	}

	 
	public Long count(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	 
	public int executeHql(String hql) {
		Query q = getCurrentSession().createQuery(hql);
		return q.executeUpdate();
	}

	 
	public int executeHql(String hql, Map<String, Object> params) {
		Query q = getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	 
	public List<Map> findBySql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	 
	public List<Map> findBySql(String sql, int page, int rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	 
	public List<Map> findBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	 
	public List<Map> findBySql(String sql, Map<String, Object> params, Integer page, Integer rows) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
	}

	 
	public int executeSql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return q.executeUpdate();
	}

	 
	public int executeSql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.executeUpdate();
	}

	 
	public BigInteger countBySql(String sql) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		return (BigInteger) q.uniqueResult();
	}

	 
	public BigInteger countBySql(String sql, Map<String, Object> params) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (BigInteger) q.uniqueResult();
	}

	 
	public List<T> findListBySql(String sql,T t) {
		
		SQLQuery q = getCurrentSession().createSQLQuery(sql).addEntity( t.toString(),t.getClass());
		return q.list();
		
		
	}
	
	 
	public List<T> findListBySql(String sql, int page, int rows,T t) {
		SQLQuery q = getCurrentSession().createSQLQuery(sql).addEntity( t.toString(),t.getClass());
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	 
	public void saveme(Object o) {
		if (o != null) {
			 getCurrentSession().saveOrUpdate(o);
		}
	
	}
	
	 
	public void updateme(Object o) {
		if (o != null) {
			getCurrentSession().update(o);
		}
	}
	
	
	/**
	 * 使用检索标准对象查询
	 * @param detachedCriteria
	 * @return
	 */
	public List<T> find(DetachedCriteria detachedCriteria) {
		return find(detachedCriteria, Criteria.DISTINCT_ROOT_ENTITY);
	}
	
	
	/**
	 * 使用检索标准对象查询
	 * @param detachedCriteria
	 * @param resultTransformer
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(DetachedCriteria detachedCriteria, ResultTransformer resultTransformer) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getCurrentSession());
		criteria.setResultTransformer(resultTransformer);
		return criteria.list(); 
	}
	
	
	/**
	 * 物理删除
	 * @param id
	 * @return
	 */
	public int delete(Serializable id){
		String hql = "delete from " +entityClass.getSimpleName()+" where id=:p1";
		return getCurrentSession().createQuery(hql).setParameter("p1", id).executeUpdate();
	}

}
