package com.baofeng.carebay.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.carebay.dao.ActivityDAO;
import com.baofeng.carebay.dao.CurseCaseDAO;
import com.baofeng.carebay.dao.CustomMadeDAO;
import com.baofeng.carebay.dao.MonthsMealDAO;
import com.baofeng.carebay.dao.MookDAO;
import com.baofeng.carebay.dao.ProductsDAO;
import com.baofeng.carebay.entity.Activity;
import com.baofeng.carebay.entity.Chamber;
import com.baofeng.carebay.entity.CurseCase;
import com.baofeng.carebay.entity.CustomMadeDetails;
import com.baofeng.carebay.entity.MonthsMeal;
import com.baofeng.carebay.entity.Mook;
import com.baofeng.carebay.entity.Products;
import com.baofeng.carebay.service.IMookService;
import com.baofeng.carebay.util.Constants;
import com.baofeng.commons.dao.ChamberDAO;
import com.baofeng.commons.entity.EntityStatus;
import com.baofeng.utils.PageResult;
import com.baofeng.utils.SearchFilter;

@Service("mookService")
public class MookServiceImpl implements IMookService {

	@Autowired
	private MookDAO mookDAO;
	@Autowired
	private ActivityDAO activityDAO;
	@Autowired
	private ProductsDAO productsDAO;
	@Autowired
	private CustomMadeDAO customMadeDAO;
	@Autowired
	private CurseCaseDAO curseCaseDAO;
	@Autowired
	private MonthsMealDAO monthsMealDAO;
	@Autowired
	private ChamberDAO chamberDAO;
	
	public MookDAO getMookDAO() {
		return mookDAO;
	}

	public void setMookDAO(MookDAO mookDAO) {
		this.mookDAO = mookDAO;
	}

	public void setProductsDAO(ProductsDAO productsDAO) {
		this.productsDAO = productsDAO;
	}

	public void setCustomMadeDAO(CustomMadeDAO customMadeDAO) {
		this.customMadeDAO = customMadeDAO;
	}

	public void setCurseCaseDAO(CurseCaseDAO curseCaseDAO) {
		this.curseCaseDAO = curseCaseDAO;
	}

	public void setMonthsMealDAO(MonthsMealDAO monthsMealDAO) {
		this.monthsMealDAO = monthsMealDAO;
	}

	public void setActivityDAO(ActivityDAO activityDAO) {
		this.activityDAO = activityDAO;
	}

	@Override
	public PageResult readAllPages(Integer pageSize, Integer currentPage, SearchFilter filter) {
		PageResult rows = this.mookDAO.readAllPages(pageSize, currentPage, filter);
		if (rows != null && rows.getRows().size() > 0) {
			List<Mook> list = new ArrayList<Mook>();
			for (Object o : rows.getRows()) {
				Mook details = (Mook) o;
				details.setImage(Constants.DEFAULT_HTTPIMAGES + "/" + Constants.sha1ToPath(details.getImageSha1()).replace(File.separator, "/") + "/" + details.getImage());
				String cid = details.getOperator().getChamber();
				Chamber chamber = this.chamberDAO.readChamber(cid);
				if(chamber != null)
					details.setImageSha1(chamber.getName());
				list.add(details);
			}
			rows.setRows(list);
		}
		return rows;
	}

	@Override
	public boolean addMook(Mook mook) {
		if (mook != null && mook.getId() != null && mook.getId().trim().length() > 0) {
			Mook $mook = this.readMook(mook.getId());
			if ($mook != null) {
				$mook.setName(mook.getName());
				$mook.setIndexs(mook.getIndexs());
				$mook.setType(mook.getType());
				$mook.setParams(mook.getParams());
				if (mook.getImage() != null && mook.getImageSha1() != null && mook.getImage().trim().length() > 0 && mook.getImageSha1().trim().length() > 0) {
					$mook.setImage(mook.getImage());
					$mook.setImageSha1(mook.getImageSha1());
				}
				if ("1".equals(mook.getType())) {
					Products ducts = this.productsDAO.readProducts(mook.getParams());
					$mook.setParamsName(ducts.getName());
				} else if ("2".equals(mook.getType())) {
					CustomMadeDetails details = this.customMadeDAO.readCustomMadeDetails(mook.getParams());
					$mook.setParamsName(details.getName());
				} else if ("3".equals(mook.getType())) {
					CurseCase curse = this.curseCaseDAO.readCurseCase(mook.getParams());
					String paIds = curse.getCustom().getTos().getId();
					String params = String.format("%s,%s", paIds,mook.getParams());
					$mook.setParams(params);
					$mook.setParamsName(curse.getName());
				} else if ("4".equals(mook.getType())) {
					MonthsMeal meal = this.monthsMealDAO.readMonthsMeal(mook.getParams());
					$mook.setParamsName(meal.getName());
				} else if ("5".equals(mook.getType())) {
					Activity act = this.activityDAO.readActivity(mook.getParams());
					$mook.setParamsName(act.getName());
				}
				mook = $mook;
			}
		} else {
			if ("1".equals(mook.getType())) {
				Products ducts = this.productsDAO.readProducts(mook.getParams());
				mook.setParamsName(ducts.getName());
			} else if ("2".equals(mook.getType())) {
				CustomMadeDetails details = this.customMadeDAO.readCustomMadeDetails(mook.getParams());
				mook.setParamsName(details.getName());
			} else if ("3".equals(mook.getType())) {
				CurseCase curse = this.curseCaseDAO.readCurseCase(mook.getParams());
				String paIds = curse.getCustom().getTos().getId();
				String params = String.format("%s,%s", paIds,mook.getParams());
				mook.setParams(params);
				mook.setParamsName(curse.getName());
			} else if ("4".equals(mook.getType())) {
				MonthsMeal meal = this.monthsMealDAO.readMonthsMeal(mook.getParams());
				mook.setParamsName(meal.getName());
			} else if ("5".equals(mook.getType())) {
				Activity act = this.activityDAO.readActivity(mook.getParams());
				mook.setParamsName(act.getName());
			}
			mook.setOnline(Integer.valueOf(0));
		}
		return this.mookDAO.saveBabyJournal(mook);
	}

	@Override
	public Mook readMook(String id) {
		return this.mookDAO.readMook(id);
	}

	@Override
	public boolean deleteBabyJournal(String id) {
		Mook journal = this.readMook(id);
		if (journal != null) {
			journal.setStatus(EntityStatus.DELETED);
			this.mookDAO.saveBabyJournal(journal);
			return true;
		}
		return false;
	}

	@Override
	public boolean onLineBabyJournal(String id) {
		Mook journal = this.readMook(id);
		if (journal != null) {

			if (journal.getOnline().intValue() == 0) {
				this.mookDAO.saveBabyJournalHql("update Mook set online = 1 where id = '" + id + "'");
			} else {
				this.mookDAO.saveBabyJournalHql("update Mook set online = 0 where id = '" + id + "'");
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean updateMook(String id1, String indexs1) {
		Mook mook = this.readMook(id1);
		if (mook != null) {
			mook.setIndexs(Integer.valueOf(indexs1));
			this.mookDAO.saveBabyJournal(mook);
			return true;
		}
		return false;
	}

	public ChamberDAO getChamberDAO() {
		return chamberDAO;
	}

	public void setChamberDAO(ChamberDAO chamberDAO) {
		this.chamberDAO = chamberDAO;
	}
}
