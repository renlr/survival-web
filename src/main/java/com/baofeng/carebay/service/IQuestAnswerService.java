package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.QuestAnswer;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface IQuestAnswerService {

	QuestAnswer readQuestAnswer(String userId, String inds);

	PageResult readAllPages(int rows, int page, String userId, String inds, SearchFilter filter);

}
