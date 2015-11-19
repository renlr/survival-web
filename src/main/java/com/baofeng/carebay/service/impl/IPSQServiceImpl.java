package com.baofeng.carebay.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.PSQDAO;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.Questionnaire;
import com.baofeng.carebay.service.IPSQService;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("pSQService")
public class IPSQServiceImpl implements IPSQService {

	@Autowired
	private PSQDAO pSQDAO;
	@Autowired
	private ChamberDAO chamberDAO;

	public void setpSQDAO(PSQDAO pSQDAO) {
		this.pSQDAO = pSQDAO;
	}

	public PSQDAO getpSQDAO() {
		return pSQDAO;
	}

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, String inds, SearchFilter filter) {
		PageResult $rows = this.pSQDAO.readAllPages(rows, page, inds, filter);
		if ($rows != null && $rows.getRows().size() > 0) {
			List<Questionnaire> list = new ArrayList<Questionnaire>();
			for (Object o : $rows.getRows()) {
				Questionnaire details = (Questionnaire) o;
				String cid = details.getOperator().getChamber();
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
	public boolean addQusetionnaire(String id, String inds, Questionnaire quest) {
		if (id != null && id.trim().length() > 0) {
			Questionnaire $quest = this.readQuestionnaire(id);
			if ($quest != null) {
				$quest.setSubject(quest.getSubject());
				$quest.setType(quest.getType());
				$quest.setOptions(quest.getOptions());
				$quest.setIndexs(quest.getIndexs());
				quest = $quest;
			}
		} else {
			quest.setInquiry(Integer.valueOf(inds));
			quest.setOnline(Integer.valueOf(0));
		}
		return this.pSQDAO.saveQuestionnaire(quest);
	}

	@Override
	public Questionnaire readQuestionnaire(String id) {
		if (id != null && id.trim().length() > 0) {
			return this.pSQDAO.readQuestionnaire(id);
		}
		return null;
	}

	@Override
	public boolean deleteQuestionnaire(String id) {
		if (id != null && id.trim().length() > 0) {
			Questionnaire questionnaire = this.readQuestionnaire(id);
			if (questionnaire != null) {
				this.pSQDAO.deleteQuestionnaire(questionnaire);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onLineQuestionnaire(String id, Integer valueOf) {
		Questionnaire questionnaire = this.readQuestionnaire(id);
		if (questionnaire != null) {
			if (questionnaire.getOnline() == null) {
				questionnaire.setOnline(Integer.valueOf(0));
			}
			if (questionnaire.getOnline().intValue() == Integer.valueOf(0)) {
				this.pSQDAO.saveQuestionnaireHql("update Questionnaire set online = 1 where id = '" + id + "'");
			} else {
				this.pSQDAO.saveQuestionnaireHql("update Questionnaire set online = 0 where id = '" + id + "'");
			}
			return true;
		}
		return false;
	}

}
