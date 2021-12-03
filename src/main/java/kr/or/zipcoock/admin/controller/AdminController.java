package kr.or.zipcoock.admin.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.table.model.vo.AdminDeal;
import kr.or.table.model.vo.AdminQna;
import kr.or.table.model.vo.AdminRefund;
import kr.or.table.model.vo.HomecookDeal;
import kr.or.table.model.vo.Member;
import kr.or.table.model.vo.Qna;
import kr.or.zipcoock.admin.model.service.AdminService;



@Controller
public class AdminController 
{
	@Autowired
	private AdminService service;
	

	@RequestMapping(value = "/getAllMemberList.do")
	@ResponseBody
	public ArrayList<Member> get_AllMemberList()
	{		
		return service.selectAllMember();
	}
	
	@RequestMapping(value = "/getAllQnaList.do")
	@ResponseBody
	public ArrayList<AdminQna> get_AllQnaList()
	{		
		return service.selectAllQna();
	}
	
	@RequestMapping(value = "/getAllHomecookDeal.do")
	@ResponseBody
	public ArrayList<AdminDeal> get_AllHomecookDeal()
	{		
		return service.selectAllHomecookDeal();
	}
	
	@RequestMapping(value = "/getAllRefund.do")
	@ResponseBody
	public ArrayList<AdminRefund> get_AllRefund()
	{		
		return service.selectAllRefund();
	}
	
	
	//getAllRefund
}
