package xsl.erp.service.impl;

import com.xsl.erp.mapper.XslNetworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vo.LineStyle;
import vo.Normal;
import vo.UserNetworkLinkRes;
import vo.UserNetworkNodeRes;
import xsl.erp.pojo.XslNetwork;
import xsl.erp.service.UserNetworkService;

import java.util.*;

@Service
public class UserNetworkServiceImpl implements UserNetworkService {
	@Autowired
	private XslNetworkMapper xslNetworkMapper;

	@Override
	public List<UserNetworkNodeRes> getNode() {
		List<XslNetwork> xslNetworks = xslNetworkMapper.selectLimit();
		List<UserNetworkNodeRes> userNetworkNodeRess = new ArrayList<>();
		Set<String> tags = new HashSet<>();

		for(XslNetwork xslNetwork : xslNetworks){
			tags.add(xslNetwork.getAphone());
			tags.add(xslNetwork.getBphone());
		}

		Integer x = -400;
		Integer y = -400;
		int size = tags.size();
		int hid = 1000/size;

		for(String tag : tags){
			UserNetworkNodeRes userNetworkNodeRes = new UserNetworkNodeRes();
			userNetworkNodeRes.setName(tag);
			userNetworkNodeRes.setX(new Random().nextInt(500) - 500);
			userNetworkNodeRes.setY(new Random().nextInt(180) - 180);

			userNetworkNodeRess.add(userNetworkNodeRes);
		}

		return userNetworkNodeRess;
	}

	@Override
	public List<UserNetworkLinkRes> getLink() {
		List<XslNetwork> xslNetworks = xslNetworkMapper.selectLimit();

		List<UserNetworkLinkRes> userNetworkLinkResList = new ArrayList<>();

		for(XslNetwork xslNetwork : xslNetworks){
			UserNetworkLinkRes userNetworkLinkRes = new UserNetworkLinkRes();
			LineStyle lineStyle = new LineStyle();

			userNetworkLinkRes.setSource(xslNetwork.getAphone());
			userNetworkLinkRes.setTarget(xslNetwork.getBphone());
			Normal normal = new Normal();
			normal.setWidth(xslNetwork.getNum());
			lineStyle.setNormal(normal);
			userNetworkLinkRes.setLineStyle(lineStyle);

			userNetworkLinkResList.add(userNetworkLinkRes);
		}

		return userNetworkLinkResList;
	}
}
