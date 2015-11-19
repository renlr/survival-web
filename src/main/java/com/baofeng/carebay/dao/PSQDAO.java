package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.Questionnaire;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("pSQDAO")
public class PSQDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(Integer pageSize, Integer currentPage, String inds, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Questionnaire.class);
			detachedCriteria.add(Restrictions.eq("inquiry", Integer.valueOf(inds)));
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("online"));
			detachedCriteria.addOrder(Order.desc("indexs"));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, pageSize, currentPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public Questionnaire readQuestionnaire(String id) {
		if (id != null && id.trim().length() > 0) {
			Questionnaire quest = (Questionnaire) this.baseDAO.get(Questionnaire.class, id);
			if (quest != null)
				return quest;
		}
		return null;
	}

	public boolean saveQuestionnaire(Questionnaire quest) {
		try {
			if (quest != null && quest.getId() != null && quest.getId().length() > 0) {
				this.baseDAO.mrege(quest);
			} else
				this.baseDAO.save(quest);
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public void deleteQuestionnaire(Questionnaire questionnaire) {
		if (questionnaire != null) {
			this.baseDAO.delete(questionnaire);
		}
	}

	public void saveQuestionnaireHql(String hql) {
		if (hql != null && hql.trim().length() > 0) {
			this.baseDAO.execute(hql);
		}
	}
}
