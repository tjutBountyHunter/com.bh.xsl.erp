package xsl.erp.service;

import vo.UserNetworkLinkRes;
import vo.UserNetworkNodeRes;

import java.util.List;

public interface UserNetworkService {
	List<UserNetworkNodeRes> getNode();

	List<UserNetworkLinkRes> getLink();
}
