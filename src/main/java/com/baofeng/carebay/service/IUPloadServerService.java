package com.baofeng.carebay.service;

import com.baofeng.carebay.entity.UPloadServer;
import com.baofeng.utils.IBaseService;

public interface IUPloadServerService extends IBaseService {

	boolean addUPloadServer(UPloadServer upserver);

	UPloadServer readUPloadServer(String id);

	UPloadServer editUPloadServer(String id);

	boolean delUPloadServer(String id);

}
