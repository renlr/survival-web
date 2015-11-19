package com.baofeng.commons.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baofeng.commons.dao.MenuitemDAO;
import com.baofeng.commons.entity.MenuItem;
import com.baofeng.commons.entity.Operator;
import com.baofeng.commons.service.IMenuitemService;
import com.baofeng.commons.service.IOperatorService;
import com.baofeng.utils.PageResult;

@Service("menuitemService")
public class MenuitemServiceImpl implements IMenuitemService {

	@Autowired
	private MenuitemDAO menuitemDAO;

	@Autowired
	private IOperatorService operatorService;
	

	public MenuitemDAO getMenuitemDAO() {
		return menuitemDAO;
	}

	public void setMenuitemDAO(MenuitemDAO menuitemDAO) {
		this.menuitemDAO = menuitemDAO;
	}

	public IOperatorService getOperatorService() {
		return operatorService;
	}

	public void setOperatorService(IOperatorService operatorService) {
		this.operatorService = operatorService;
	}

	@Override
	public PageResult readAllPages(int rows, int page, Operator user) {
		PageResult $page = this.menuitemDAO.readAllPages(rows, page, user);
		if ($page != null && $page.getRows().size() > 0) {
			List<MenuItem> list = new ArrayList<MenuItem>();
			List<MenuItem> userList = this.operatorService.readMenuLeftList(user);
			List<Integer> userItem = new ArrayList<Integer>();
			for (MenuItem em : userList) {
				userItem.add(em.getId());
			}
			for (Object o : $page.getRows()) {
				MenuItem item = (MenuItem) o;
				if (userItem.contains(item.getId())) {
					list.add(item);
				}
			}
			$page.setRows(list);
		}
		return $page;
	}

	@Override
	public boolean init() {
		try {
			Operator operator = this.operatorService.validation("admins", "123.com");
			if (operator == null) {
				operator = new Operator();
				operator.setAccounts("admins");
				operator.setEmail("renlr@outlook.com");
				operator.setName("超级管理员");
				operator.setPassword("123.com");
				operator.setPhone("13601655231");
				operator.setDelFlag(Integer.valueOf(0));
				operator.setStatus(Integer.valueOf(0));
				operator.setType(Integer.valueOf(0));
				this.operatorService.addUser(operator);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void createMenuItemGbox() {
		MenuItem items = new MenuItem();
		items.setName("系统设置");
		items.setUrl("/main.do");
		items.setCreateDT(new Date());
		items.setDivid("system");
		items.setIndexs(1);
		createMenuItem(items);
		createMenuItem(new MenuItem("系统用户", "user/show.do", items, new Date(), null, 101));
		createMenuItem(new MenuItem("充值记录", "user/show.do", items, new Date(), null, 115));
		createMenuItem(new MenuItem("消费记录", "user/show.do", items, new Date(), null, 120));
		createMenuItem(new MenuItem("更新版本", "version/show.do", items, new Date(), null, 125));

		MenuItem item1 = new MenuItem();
		item1.setName("首页");
		item1.setUrl("/main.do");
		item1.setCreateDT(new Date());
		item1.setDivid("home");
		item1.setIndexs(7);
		createMenuItem(item1);
		createMenuItem(new MenuItem("游戏用户", "guser/show.do", item1, new Date(), null, 209));
		createMenuItem(new MenuItem("游戏海报", "poster/show.do", item1, new Date(), null, 210));
		createMenuItem(new MenuItem("英雄资料", "hero/show.do", item1, new Date(), null, 215));
		createMenuItem(new MenuItem("装备资料", "equip/show.do", item1, new Date(), null, 220));
		createMenuItem(new MenuItem("宣传视频", "video/show.do", item1, new Date(), null, 225));
		createMenuItem(new MenuItem("魔盒奖品", "prize/show.do", item1, new Date(), null, 240));
		createMenuItem(new MenuItem("抽奖记录", "records/show.do", item1, new Date(), null, 245));
		createMenuItem(new MenuItem("意见反馈", "inbox/show.do", item1, new Date(), null, 250));
	}
	public void createMenuItemSite() {
		MenuItem items = new MenuItem();
		items.setName("系统设置");
		items.setUrl("/main.do");
		items.setCreateDT(new Date());
		items.setDivid("system");
		items.setIndexs(1);
		createMenuItem(items);
		createMenuItem(new MenuItem("系统用户", "user/show.do", items, new Date(), null, 101));
		createMenuItem(new MenuItem("充值记录", "user/show.do", items, new Date(), null, 115));
		createMenuItem(new MenuItem("消费记录", "user/show.do", items, new Date(), null, 120));
		createMenuItem(new MenuItem("更新版本", "version/show.do", items, new Date(), null, 125));

		MenuItem item1 = new MenuItem();
		item1.setName("首页");
		item1.setUrl("/main.do");
		item1.setCreateDT(new Date());
		item1.setDivid("home");
		item1.setIndexs(7);
		createMenuItem(item1);
		createMenuItem(new MenuItem("首页轮播", "viwepager/show.do", item1, new Date(), null, 209));
		createMenuItem(new MenuItem("游戏产品", "product/show.do", item1, new Date(), null, 210));
		createMenuItem(new MenuItem("招聘信息", "recruit/show.do", item1, new Date(), null, 215));
		createMenuItem(new MenuItem("友情链接", "link/show.do", item1, new Date(), null, 220));
		createMenuItem(new MenuItem("资讯动态", "dynamic/show.do", item1, new Date(), null, 225));
		createMenuItem(new MenuItem("公司地址", "address/show.do", item1, new Date(), null, 225));
	}
	/**
	 * 功能：馨月汇pad版
	 * */
	public void createMenuItemIpad() {
		MenuItem items = new MenuItem();
		items.setName("系统设置");
		items.setUrl("");
		items.setCreateDT(new Date());
		items.setDivid("system");
		items.setIndexs(1);
		createMenuItem(items);
		createMenuItem(new MenuItem("系统用户", "user/show.do", items, new Date(), null, 101));
		createMenuItem(new MenuItem("会所管理", "chamber/show.do", items, new Date(), null, 102));
		createMenuItem(new MenuItem("菜单导航", "naviga/show.do", items, new Date(), null, 103));
		createMenuItem(new MenuItem("饮食调查", "psq/show.do?inds=1", items, new Date(), null, 104));
		createMenuItem(new MenuItem("服务调查", "psq/show.do?inds=2", items, new Date(), null, 105));
		createMenuItem(new MenuItem("管家服务", "call/show.do", items, new Date(), null, 108));
		createMenuItem(new MenuItem("操作日志", "mlog/show.do", items, new Date(), null, 109));

		MenuItem item1 = new MenuItem();
		item1.setName("首页");
		item1.setUrl("");
		item1.setCreateDT(new Date());
		item1.setDivid("home");
		item1.setIndexs(7);
		createMenuItem(item1);
		createMenuItem(new MenuItem("馨月用户", "care/user.do", item1, new Date(), null, 209));
		createMenuItem(new MenuItem("内容精选", "mook/show.do", item1, new Date(), null, 212));
		createMenuItem(new MenuItem("母婴乐购", "productsCategory/show.do", item1, new Date(), null, 213));
		createMenuItem(new MenuItem("高级定制", "custom/show.do", item1, new Date(), null, 214));
		createMenuItem(new MenuItem("服务精选", "tos/show.do", item1, new Date(), null, 215));
		createMenuItem(new MenuItem("月子套餐", "meal/show.do", item1, new Date(), null, 216));
		createMenuItem(new MenuItem("套餐轮播", "meal/carousel.do", item1, new Date(), null, 217));
		createMenuItem(new MenuItem("最新活动", "activity/show.do", item1, new Date(), null, 218));
		createMenuItem(new MenuItem("留言消息", "msgInbox/show.do", item1, new Date(), null, 219));
		createMenuItem(new MenuItem("已点菜品", "meal/orders.do", item1, new Date(), null, 220));
		createMenuItem(new MenuItem("已售商品", "products/orders.do", item1, new Date(), null, 221));
	}

	/**
	 * 功能：馨月汇
	 * */
	public void createMenuItemCareBay() {
		MenuItem item1 = new MenuItem();
		item1.setName("首页");
		item1.setUrl("week/show.do");
		item1.setCreateDT(new Date());
		item1.setDivid("home");
		item1.setIndexs(7);
		createMenuItem(item1);
		createMenuItem(new MenuItem("周期功能", "week/show.do", item1, new Date(), null, 201));
		createMenuItem(new MenuItem("导航模块", "fmod/showTuiJian.do", item1, new Date(), null, 202));
		createMenuItem(new MenuItem("首页精选", "recduct/show.do", item1, new Date(), null, 203));
		createMenuItem(new MenuItem("护理套餐", "curse/show.do", item1, new Date(), null, 204));
		createMenuItem(new MenuItem("营养套餐", "slim/show.do", item1, new Date(), null, 205));
		createMenuItem(new MenuItem("母婴杂志", "mook/show.do", item1, new Date(), null, 206));
		createMenuItem(new MenuItem("成长指标", "growup/show.do", item1, new Date(), null, 207));

		createMenuItem(new MenuItem("添加用户", "chamber/show.do", item1, new Date(), null, 209));
		createMenuItem(new MenuItem("问卷试题", "psq/show.do", item1, new Date(), null, 210));
		createMenuItem(new MenuItem("调查结果", "fds/show.do", item1, new Date(), null, 211));
		createMenuItem(new MenuItem("服务类型", "growup/show.do", item1, new Date(), null, 212));

		MenuItem item2 = new MenuItem();
		item2.setName("个人中心");
		item2.setUrl("carebay/show.do");
		item2.setCreateDT(new Date());
		item2.setDivid("center");
		item2.setIndexs(6);
		createMenuItem(item2);
		createMenuItem(new MenuItem("亲属关系", "family/show.do", item2, new Date(), null, 301));
		createMenuItem(new MenuItem("馨月宝宝", "carebay/show.do", item2, new Date(), null, 302));
		createMenuItem(new MenuItem("馨月用户", "care/show.do", item2, new Date(), null, 303));
		createMenuItem(new MenuItem("馨月会员", "care/show.do", item2, new Date(), null, 304));
		createMenuItem(new MenuItem("用户预约", "reserv/show.do", item2, new Date(), null, 305));
		createMenuItem(new MenuItem("会员俱乐部", "carebay/show.do", item2, new Date(), null, 306));
		createMenuItem(new MenuItem("VIP俱乐部", "product/show.do", item2, new Date(), null, 307));
		createMenuItem(new MenuItem("用户数据", "user/show.do", item2, new Date(), null, 308));
		createMenuItem(new MenuItem("杂志收藏", "user/show.do", item2, new Date(), null, 309));

		MenuItem item3 = new MenuItem();
		item3.setName("云相册");
		item3.setUrl("photos/show.do");
		item3.setCreateDT(new Date());
		item3.setDivid("photoAlbum");
		item3.setIndexs(5);
		createMenuItem(item3);
		createMenuItem(new MenuItem("用户图片", "cover/show.do", item3, new Date(), null, 401));
		createMenuItem(new MenuItem("照片详细", "details/show.do", item3, new Date(), null, 402));
		createMenuItem(new MenuItem("发布记录", "photos/show.do", item3, new Date(), null, 403));
		createMenuItem(new MenuItem("上传服务", "upserver/show.do", item3, new Date(), null, 404));

		MenuItem item4 = new MenuItem();
		item4.setName("馨月商品");
		item4.setUrl("products/show.do");
		item4.setCreateDT(new Date());
		item4.setDivid("products");
		item4.setIndexs(4);
		createMenuItem(item4);
		createMenuItem(new MenuItem("推荐列表", "ductcy/show.do", item4, new Date(), null, 501));
		createMenuItem(new MenuItem("商品分类", "productsCategory/show.do", item4, new Date(), null, 502));
		createMenuItem(new MenuItem("商品管理", "products/show.do", item4, new Date(), null, 504));
		createMenuItem(new MenuItem("已售商品", "products/show.do", item4, new Date(), null, 505));
		createMenuItem(new MenuItem("评价管理", "products/show.do", item4, new Date(), null, 506));
		createMenuItem(new MenuItem("购物车", "products/show.do", item4, new Date(), null, 507));
		createMenuItem(new MenuItem("用户收藏", "products/show.do", item4, new Date(), null, 508));
		createMenuItem(new MenuItem("收货地址", "products/show.do", item4, new Date(), null, 509));
		createMenuItem(new MenuItem("配送方式", "products/show.do", item4, new Date(), null, 510));

		MenuItem item5 = new MenuItem();
		item5.setName("馨月服务");
		item5.setUrl("products/show.do");
		item5.setCreateDT(new Date());
		item5.setDivid("service");
		item5.setIndexs(3);
		createMenuItem(item5);
		createMenuItem(new MenuItem("服务医师", "doctor/show.do", item5, new Date(), null, 601));
		createMenuItem(new MenuItem("馨月汇馆", "product/show.do", item5, new Date(), null, 602));
		createMenuItem(new MenuItem("饮食指导", "product/show.do", item5, new Date(), null, 603));
		createMenuItem(new MenuItem("最近浏览", "product/show.do", item5, new Date(), null, 604));

		MenuItem item6 = new MenuItem();
		item6.setName("妈妈论坛");
		item6.setUrl("cfg/show.do");
		item6.setCreateDT(new Date());
		item6.setDivid("luntan");
		item6.setIndexs(2);
		createMenuItem(item6);
		createMenuItem(new MenuItem("妈妈论坛", "growup/show.do", item6, new Date(), null, 701));
	}

	/**
	 * 功能：找妹
	 * */
	public void createMenuItemFs() {
		MenuItem item1 = new MenuItem();
		item1.setId(2);
		item1.setName("找妹应用");
		item1.setUrl("user/show.do");
		item1.setCreateDT(new Date());
		item1.setDivid("home");
		item1.setIndexs(2);
		createMenuItem(item1);
		createMenuItem(new MenuItem("上传服务", "upserver/show.do", item1, new Date(), null, 201));
		createMenuItem(new MenuItem("图片审核", "details/show.do", item1, new Date(), null, 202));
		createMenuItem(new MenuItem("用户管理", "details/show.do", item1, new Date(), null, 203));
		createMenuItem(new MenuItem("收入明细", "details/show.do", item1, new Date(), null, 203));
	}

	@Override
	public void createMenuItem(MenuItem menuItem) {
		this.menuitemDAO.saveMenuItem(menuItem);
	}
}
