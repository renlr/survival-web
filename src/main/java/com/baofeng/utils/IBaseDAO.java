package com.baofeng.utils;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface IBaseDAO {
	/**
	 * 修改实体的状态为 删除状态
	 * 
	 * @param entity
	 */
	public void delete(Object entity);

	/**
	 * 实体物理删除
	 * 
	 * @param entity
	 */
	public void destroy(final Object entity);

	/**
	 * hql方式条件查询
	 * 
	 * @param query
	 * @return
	 */

	public List find(final String query);

	public List find(final String query, final Object[] parameter);

	public List findAll(final Class entity);

	/**
	 * DetachedCriteria方式查询所有
	 * 
	 * @param detachedCriteria
	 * @return
	 */

	public List findAllByCriteria(final DetachedCriteria detachedCriteria);

	/**
	 * DetachedCriteria方式 查询 top N
	 * 
	 * @param detachedCriteria
	 * @param lenght
	 * @return
	 */
	public List findAllByCriteria(final DetachedCriteria detachedCriteria, int lenght);

	public List<?> findByDetachedCriteria(final DetachedCriteria detachedCriteria, final int beginIndex, final int endIndex);

	/**
	 * 分页查询
	 * 
	 * @param detachedCriteria
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public PageResult findByPages(final DetachedCriteria detachedCriteria, final int pageSize, final int currentPage);

	/**
	 * hql 方式分页查询
	 * 
	 * @param inQueryHql
	 * @param inCountHql
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public PageResult findByPages(final String inQueryHql, final String inCountHql, final int pageSize, final int currentPage);

	/**
	 * id方式获取单个实体
	 * 
	 * @param entity
	 * @param id
	 * @return
	 */
	public Object get(final Class entity, final Serializable id);

	/**
	 * Criteria方式获取单个实体
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public Object get(final DetachedCriteria detachedCriteria);

	/**
	 * Criteria方式获取实体数量
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public int getCountByCriteria(final DetachedCriteria detachedCriteria);

	/**
	 * 按实体的属性值获实体
	 * 
	 * @param propertyName
	 * @param propertyValue
	 * @param entityClass
	 * @return
	 * @throws BaseException
	 */
	public Object getEntityByProperty(String propertyName, Object propertyValue, Class entityClass);

	public Object load(final Class entity, final Serializable id);

	/**
	 * 支持级联更新的update
	 * 
	 * @param obj
	 */
	public void mrege(Object entity);

	public void persist(final Object entity);

	public Object save(final Object entity);

	public void saveOrUpdate(final Object entity);

	public void setCacheQueries(boolean cacheQueries);

	public void setQueryCacheRegion(String queryCacheRegion);

	public void update(final Object entity);

	public Integer execute(String hql);

	public Integer execute(String hql, Object[] param);

	public List<?> QueryDetachedCriteria(DetachedCriteria detachedCriteria);

	Integer executeOpenSession(String hql);

	public List<Object> executeQuery(String hql);
}