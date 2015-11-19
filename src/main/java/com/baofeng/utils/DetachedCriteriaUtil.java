package com.baofeng.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author RENLIANGRONG
 * 
 */
public class DetachedCriteriaUtil {
	public static DetachedCriteria addSearchFilter(DetachedCriteria detachedCriteria, SearchFilter filter) throws BaseException {
		if (filter != null) {
			for (SearchRule rule : filter.getRules()) {
				if (!rule.getData().equals("")) {
					if (rule.getOp().equals("cn")) {
						if (rule.getField().indexOf(".") != -1) {
							String[] alias = rule.getField().split("\\.");
							String tempAlia = "";
							int i = alias.length;
							for (String alia : alias) {
								i--;
								if (!tempAlia.equals("")) {
									tempAlia = tempAlia + "." + alia;
								} else {
									tempAlia = alia;
								}
								if (i > 0) {
									detachedCriteria.createAlias(tempAlia, alia);
									tempAlia = alia;
								}
							}
							detachedCriteria.add(Restrictions.like(tempAlia, "%" + rule.getData() + "%"));
						} else {
							detachedCriteria.add(Restrictions.like(rule.getField(), "%" + rule.getData() + "%"));
						}
					} else if (rule.getOp().equals("eq")) {
						if (rule.getField().indexOf(".") != -1) {
							String[] alias = rule.getField().split("\\.");
							String tempAlia = "";
							int i = alias.length;
							for (String alia : alias) {
								i--;
								if (!tempAlia.equals("")) {
									tempAlia = tempAlia + "." + alia;
								} else {
									tempAlia = alia;
								}
								if (i > 0) {
									detachedCriteria.createAlias(tempAlia, alia);
									tempAlia = alia;
								}
							}
							detachedCriteria.add(Restrictions.eq(tempAlia, rule.getData()));
						} else {
							detachedCriteria.add(Restrictions.eq(rule.getField(), rule.getData()));
						}
					} else if (rule.getOp().equals("le")) {
						SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
						try {
							detachedCriteria.add(Restrictions.le(rule.getField(), dateformat.parse(rule.getData().toString())));
						} catch (ParseException e) {
							e.printStackTrace();
							throw new BaseException("DetachedCriteriaUtil解析日期格式异常");
						}
					} else if (rule.getOp().equals("ge")) {
						SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
						try {
							detachedCriteria.add(Restrictions.ge(rule.getField(), dateformat.parse(rule.getData().toString())));
						} catch (ParseException e) {
							e.printStackTrace();
							throw new BaseException("DetachedCriteriaUtil解析日期格式异常");
						}
					}
				}
			}
		}
		return detachedCriteria;
	}
}
