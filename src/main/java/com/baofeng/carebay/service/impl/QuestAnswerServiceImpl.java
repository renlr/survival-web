package com.baofeng.carebay.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.QuestAnswerDAO;
import com.baofeng.carebay.entity.QuestAnswer;
import com.baofeng.carebay.entity.Questionnaire;
import com.baofeng.carebay.service.IQuestAnswerService;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("questAnswerService")
public class QuestAnswerServiceImpl implements IQuestAnswerService {

	@Autowired
	private QuestAnswerDAO questAnswerDAO;

	public QuestAnswerDAO getQuestAnswerDAO() {
		return questAnswerDAO;
	}

	public void setQuestAnswerDAO(QuestAnswerDAO questAnswerDAO) {
		this.questAnswerDAO = questAnswerDAO;
	}

	@Override
	public PageResult readAllPages(int rows, int page, String userId, String inds, SearchFilter filter) {
		PageResult $rows = this.questAnswerDAO.readAllPages(rows, page, userId, inds, filter);
		QuestAnswer answer = this.readQuestAnswer(userId, inds);
		Map<String, String> map = new HashMap<String, String>();
		if (answer != null && answer.getAnswer() != null && answer.getAnswer().split("\\|").length > 0) {
			String aString = answer.getAnswer();
			String[] as = aString.split("\\|");
			for (String vas : as) {
				String[] values = vas.split(":");
				String key = values[0];
				String value = values[1];
				if (value.endsWith(";"))
					value = value.substring(0, value.length() - 1);
				if (key != null && value != null) {
					map.put(key, value);
				}
			}
		}
		if ($rows != null && $rows.getRows().size() > 0) {
			List<Questionnaire> list = new ArrayList<Questionnaire>();
			for (Object o : $rows.getRows()) {
				Questionnaire details = (Questionnaire) o;
				if (map.containsKey(details.getId())) {
					String value = map.get(details.getId());
					details.setOptions(value);
				} else {
					details.setOptions(String.valueOf(""));
				}
				list.add(details);
			}
			Collections.sort(list, new Comparator<Questionnaire>() {
				@Override
				public int compare(Questionnaire o1, Questionnaire o2) {
					if (o1.getIndexs().intValue() > o2.getIndexs().intValue()) {
						return 1;
					}
					return -1;
				}
			});
			$rows.setRows(list);
		}
		return $rows;
	}

	@Override
	public QuestAnswer readQuestAnswer(String userId, String inds) {
		if (userId != null && inds != null) {
			return this.questAnswerDAO.readQuestAnswer(userId, inds);
		}
		return null;
	}
}
