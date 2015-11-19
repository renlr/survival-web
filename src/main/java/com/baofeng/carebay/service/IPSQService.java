package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.Questionnaire;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

public interface IPSQService {

	boolean addQusetionnaire(String id, String inds, Questionnaire quest);

	Questionnaire readQuestionnaire(String id);

	boolean deleteQuestionnaire(String id);

	boolean onLineQuestionnaire(String id, Integer valueOf);

	PageResult readAllPages(int rows, int page, String inds, SearchFilter filter);

}
