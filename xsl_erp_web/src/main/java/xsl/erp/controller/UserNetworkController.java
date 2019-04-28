package xsl.erp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import vo.UserNetworkLinkRes;
import vo.UserNetworkNodeRes;
import xsl.erp.service.UserNetworkService;

import java.util.List;

@Controller
@RequestMapping("/xsl/user/network")
public class UserNetworkController {
	@Autowired
	private UserNetworkService userNetworkService;

	@RequestMapping("/getNode")
	@ResponseBody
	public List<UserNetworkNodeRes> getNode(){
		return userNetworkService.getNode();
	}

	@RequestMapping("/getLink")
	@ResponseBody
	public List<UserNetworkLinkRes> getLink(){
		return userNetworkService.getLink();
	}

}
