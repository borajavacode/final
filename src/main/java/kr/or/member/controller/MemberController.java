package kr.or.member.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import kr.or.member.model.service.MemberService;
import kr.or.table.model.vo.BusinessSellerInfo;
import kr.or.table.model.vo.Member;


@Controller
public class MemberController {
	// 스프링이 서버 시작시 자동으로 생성한 객체 중 아래 변수와 일치하는 데이터타입의 객체를 찾아서 변수에저장
	// 의존성 주입(DI)
	@Autowired  
	private MemberService service;
	@RequestMapping(value="/loginFrm.do")
	public String loginFrm() {
		return "zipcoock/member/loginFrm";
	}
	
	
	@RequestMapping(value="/login.do")
	public String login(Member member, HttpSession session, Model model) {
		//Model -> request영역에 데이터를 등록하기 위한 객체
		//request.setAttribute("key",value) -> model.addAttribute("key",value);
		System.out.println("사용자 입력 비밀번호 : " + member.getMemberPw());
		Member m = service.selectOneMember(member);
		if(m != null) {
			session.setAttribute("m", m);
			model.addAttribute("msg","로그인 성공");
			model.addAttribute("loc", "/");
		}else {
			model.addAttribute("msg","아이디 또는 비밀번호를 확인하세요");
			model.addAttribute("loc", "/");
		}
		return "zipcoock/common/msg";
	}
	@RequestMapping(value="/joinFrm.do")
	public String joinFrm() {
		return "zipcoock/member/joinFrm";
	}
	
	@RequestMapping(value="/bjoinFrm.do")
	public String bjoinFrm() {
		return "zipcoock/member/bjoin";
	}
	@RequestMapping(value="/kjoin.do")
	public String kjoin(String memberId, Model model) {
		model.addAttribute("id",memberId);
		return "zipcoock/member/kjoin";
	}
	@RequestMapping(value="/ajaxLogin.do")
	@ResponseBody
	public int ajaxLogin(String id,Member member, HttpSession session, Model model) {
		member.setMemberId(id);
		member.setMemberPw(id);
		System.out.println(id);
		Member m = service.selectOneMember(member);
		
		if(m != null) {
			session.setAttribute("m", m);
			model.addAttribute("msg","로그인 성공");
			model.addAttribute("loc", "/");
			return 1;
		}else {
			return 0;
		}
		
		}	
	
	@RequestMapping(value="/CheckSnum.do") // 사업자 번호 조회 페이지 이동
	public String checkSnum() {
		return "zipcoock/member/CheckSnum";
	}
	@RequestMapping(value="/ajaxbNoCheck.do") //입력한 사업자 번호 DB와 조회하는 AJAX 
	@ResponseBody
	public int ajaxbNoCheck(BusinessSellerInfo BusinessSellerInfo) {
		BusinessSellerInfo bsi = service.selectOneBusinessNo(BusinessSellerInfo);
		if(bsi != null) {
			return 1;
		}else {
			return 0;
		}
		
		
	}
	@RequestMapping(value="sjoinFrm.do") // 판매자 가입 페이지 이동
	public String sjoinFrm() {
		return "zipcoock/member/sjoin";
	}	
	
	}