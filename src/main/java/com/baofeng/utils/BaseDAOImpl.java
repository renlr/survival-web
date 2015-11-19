package com.baofeng.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;

public class BaseDAOImpl implements IBaseDAO {

	private boolean cacheQueries = false;

	private String queryCacheRegion;

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public List<?> find(final String query) {
		return this.getCurrentSession().createQuery(query).list();
	}

	public List<?> find(final String query, final Object[] parameter) {
		Query q = this.getCurrentSession().createQuery(query);
		if (parameter != null && parameter.length > 0) {
			for (int i = 0; i < parameter.length; i++) {
				q.setParameter(i, parameter[i]);
			}
		}
		return q.list();
	}

	public List<?> findAll(final Class entity) {
		return this.getCurrentSession().createQuery("from " + entity.getName()).list();
	}

	public List<?> findAllByCriteria(final DetachedCriteria detachedCriteria) {
		try {
			Session session = this.getCurrentSession();
			detachedCriteria.setResultTransformer(detachedCriteria.DISTINCT_ROOT_ENTITY);
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			Set<Object> filterSet = new HashSet<Object>();
			for (Object obj : criteria.list()) {
				filterSet.add(obj);
			}
			return new ArrayList<Object>(filterSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Object>();
	}

	public List<Object> findByDetachedCriteria(final DetachedCriteria detachedCriteria, final int beginIndex, final int endIndex) {
		try {
			Session session = this.getCurrentSession();
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			criteria.setProjection(null);
			List items = criteria.setFirstResult(beginIndex).setMaxResults(endIndex).list();
			return items;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Object>();
	}

	public PageResult findByPages(final DetachedCriteria detachedCriteria, final int pageSize, final int currentPage) {
		try {
			Session session = this.getCurrentSession();
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
			int totalCount = count.intValue();
			criteria.setProjection(null);
			criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
			List items = criteria.setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize).list();
			PageResult ps = new PageResult(items, currentPage, totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1, totalCount);
			return ps;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public PageResult findByPages(final String inQueryHql, final String inCountHql, final int pageSize, final int currentPage) {
		try {
			Session session = this.getCurrentSession();
			final int totalCount = DataAccessUtils.intResult(session.createQuery(inCountHql).list());
			Query _query = session.createQuery(inQueryHql).setFirstResult((currentPage - 1) * pageSize).setMaxResults(pageSize);
			List items = _query.list();
			PageResult ps = new PageResult(items, currentPage, totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1, totalCount);
			return ps;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object get(final Class entity, final Serializable id) {
		return this.getCurrentSession().get(entity, id);
	}

	public Object get(DetachedCriteria detachedCriteria) {
		List<?> list = this.findAllByCriteria(detachedCriteria);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
		try {
			Session session = this.getCurrentSession();
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			Integer count = (Integer) criteria.setProjection(Projections.rowCount()).uniqueResult();
			return count.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Integer.valueOf(0);
	}

	public Object getEntityByProperty(String propertyName, Object propertyValue, Class entityClass) throws DataAccessException {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(entityClass);
		detachedCriteria.add(Restrictions.eq(propertyName, propertyValue));
		List<?> list = this.findAllByCriteria(detachedCriteria);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public Object load(final Class entity, final Serializable id) {
		return this.getCurrentSession().load(entity, id);
	}

	public void persist(final Object entity) {
		this.getCurrentSession().save(entity);
	}

	public Object save(final Object entity) {
		return this.getCurrentSession().save(entity);
	}

	public void saveOrUpdate(Object entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	public void setCacheQueries(boolean cacheQueries) {
		this.cacheQueries = cacheQueries;
	}

	public void setQueryCacheRegion(String queryCacheRegion) {
		this.queryCacheRegion = queryCacheRegion;
	}

	@Override
	public List<?> findAllByCriteria(final DetachedCriteria detachedCriteria, final int lenght) {
		try {
			Session session = this.getCurrentSession();
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			criteria.setFirstResult(0);
			criteria.setMaxResults(lenght);
			return criteria.list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Object>();
	}

	@Override
	public void delete(Object entity) {
		this.getCurrentSession().delete(entity);
	}

	@Override
	public void mrege(Object entity) {
		this.getCurrentSession().merge(entity);
	}

	@Override
	public void update(Object entity) {
		this.getCurrentSession().update(entity);
	}

	@Override
	public void destroy(Object entity) {
		this.getCurrentSession().delete(entity);
	}

	@Override
	public Integer execute(final String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	@Override
	public Integer executeOpenSession(final String hql) {
		Session session = this.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		Query q = session.createQuery(hql);
		int count = q.executeUpdate();
		tr.commit();
		session.close();
		return count;
	}

	@Override
	public Integer execute(final String hql, final Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	@Override
	public List<?> QueryDetachedCriteria(DetachedCriteria detachedCriteria) {
		try {
			Session session = this.getSessionFactory().openSession();
			detachedCriteria.setResultTransformer(detachedCriteria.DISTINCT_ROOT_ENTITY);
			Criteria criteria = detachedCriteria.getExecutableCriteria(session);
			Set<Object> filterSet = new HashSet<Object>();
			for (Object obj : criteria.list()) {
				filterSet.add(obj);
			}
			session.close();
			return new ArrayList<Object>(filterSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Object>();
	}

	@Override
	public List<Object> executeQuery(String hql) {
		try {
			Session session = this.getSessionFactory().openSession();
			Query query = session.createQuery(hql);
			List<Object> qlist = query.list();
			session.close();
			return qlist;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
