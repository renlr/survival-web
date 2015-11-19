package com.baofeng.carebay.dao;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baofeng.carebay.entity.MessageInbox;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.DetachedCriteriaUtil;
import com.baofeng.utils.IBaseDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Repository("messageInboxDAO")
public class MessageInboxDAO {

	@Autowired
	private IBaseDAO baseDAO;

	public IBaseDAO getBaseDAO() {
		return baseDAO;
	}

	public void setBaseDAO(IBaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	public PageResult readAllPages(Integer rows, Integer page, SearchFilter filter) {
		try {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MessageInbox.class);
			detachedCriteria.add(Restrictions.eq("status", EntityStatus.NORMAL));
			detachedCriteria.addOrder(Order.desc("createDT"));
			DetachedCriteriaUtil.addSearchFilter(detachedCriteria, filter);
			return baseDAO.findByPages(detachedCriteria, rows, page);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public MessageInbox readMessageInbox(String id) {
		if(id != null && id.trim().length() > 0){
			MessageInbox inbox = (MessageInbox)this.baseDAO.get(MessageInbox.class, id);
			return inbox;
		}
		return null;
	}

	public boolean saveMessageInbox(MessageInbox inbox) {
		try {
			if (inbox != null && inbox.getId() != null && inbox.getId().length() > 0)
				this.baseDAO.mrege(inbox);
			else {
				this.baseDAO.save(inbox);
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
