package com.baofeng.carebay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.MessageInboxDAO;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.MessageInbox;
import com.baofeng.carebay.service.IMessageInboxService;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("messageInboxService")
public class MessageInboxServiceImpl implements IMessageInboxService {

	@Autowired
	private ChamberDAO chamberDAO;
	@Autowired
	private MessageInboxDAO messageInboxDAO;

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	public MessageInboxDAO getMessageInboxDAO() {
		return messageInboxDAO;
	}

	public void setMessageInboxDAO(MessageInboxDAO messageInboxDAO) {
		this.messageInboxDAO = messageInboxDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult $rows = this.messageInboxDAO.readAllPages(pageSize, currentPage, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<MessageInbox> list = new ArrayList<MessageInbox>();
			for (Object o : $rows.getRows()) {
				MessageInbox details = (MessageInbox) o;
				String cid = details.getUser().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if (chamber != null)
					details.setChamber(chamber.getName());
				list.add(details);
			}
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public boolean updateDealWith(String id) {
		MessageInbox inbox = this.messageInboxDAO.readMessageInbox(id);
		if (inbox != null) {
			if (inbox.getMarkRead() == null || inbox.getMarkRead().intValue() == Integer.valueOf(0).intValue()) {
				inbox.setMarkRead(Integer.valueOf(1).intValue());
			} else {
				inbox.setMarkRead(Integer.valueOf(0).intValue());
			}
			return this.messageInboxDAO.saveMessageInbox(inbox);
		}
		return false;
	}
}
