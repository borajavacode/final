package kr.or.seller.model.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import kr.or.member.model.dao.MemberDao;
import kr.or.seller.model.dao.SellerDao;
import kr.or.seller.model.vo.SellerProductPageData;
import kr.or.table.model.vo.BusinessSellerInfo;
import kr.or.table.model.vo.Member;
import kr.or.table.model.vo.Product;
import kr.or.table.model.vo.ProductImg;
import kr.or.table.model.vo.PwChangeVO;
import kr.or.table.model.vo.ReturnPolicy;
import kr.or.table.model.vo.ShippingInfo;

@Service
public class SellerService {
	@Autowired
	private SellerDao dao;
	@Autowired
	private MemberDao mdao;
	
	public BusinessSellerInfo selectOneSmember(int memberNo) {
		BusinessSellerInfo bsi = dao.selectOneSmember(memberNo);
		return bsi;
	}
	public int updateSellerMember(BusinessSellerInfo businessSellerInfo) {
		int result = dao.updateSellerMember(businessSellerInfo);
		
		return result;
	}
	
	public int sellerChangePw(PwChangeVO pc) {
		Member m = new Member(); // 멤버객체를 매개변수로하기때문에 생성
		m.setMemberId(pc.getMemberId());
		m.setMemberPw(pc.getOldPassword());
		Member member = mdao.selectOneMember(m);
		if(member == null) {
			return -1;
		}else {
			m.setMemberPw(pc.getNewPassword());
			return mdao.updatePW(m);
		}
	}

	public SellerProductPageData selectProductList(Member m, int reqPage) {
		//한페이지당 게시물 수 : 10개
				int numPerPage = 10;
				//시작 번호, 끝번호
				int end = reqPage*numPerPage;
				System.out.println(end+"end");
				int start = end - numPerPage + 1;
				System.out.println(start);
				Map<Object, Object> pagedata = new HashMap<Object, Object>();
				pagedata.put("start", start);
				pagedata.put("end", end);
				pagedata.put("memberNo", m.getMemberNo());
				//값 가져오기
				System.out.println(pagedata);
				ArrayList<Product> pList = dao.selectProductList(pagedata);
				System.out.println(pList);
				//전체 게시물의 수
				int totalCount = dao.selectTotalCount(m);
				System.out.println(totalCount);
				int totalPage = 0;
				if(totalCount%numPerPage == 0) {
					totalPage = totalCount/numPerPage;
				}else {
					totalPage = totalCount/numPerPage+1;
				}
				int pageNaviSize = 5;
				int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
				String pageNavi = "<ul class='pagination pagination-sm' style='display: inline-flex;'>";
				//이전버튼
				if(pageNo != 1) {
					pageNavi += "<li class='page-item'>";
					pageNavi += "<a class='page-link' style='color:#9ac6e8; text-align: center' href='/productList.do?reqPage="+(pageNo-1)+"'>";
					pageNavi += "&lt;</a></li>";		//&lit;는 < (html에서 태그 열때 쓰이는거라 문자로 쓸땐 이렇게 씀)
				}
				//페이지숫자
				for(int i=0;i<pageNaviSize;i++) {
					if(pageNo==reqPage) {
						pageNavi += "<li class='page-item active'>";
						pageNavi += "<a class='page-link' style='Background-color:#9ac6e8; text-align: center' href='/productList.do?reqPage="+pageNo+"'>";
						pageNavi += pageNo+"</a></li>";
					}else {
						pageNavi += "<li class='page-item'>";
						pageNavi += "<a class='page-link' style='color:#9ac6e8'  href='/productList.do?reqPage="+pageNo+"'>";
						pageNavi += pageNo+"</a></li>";
					}
					pageNo++;
					if(pageNo>totalPage) {
						break;
					}
				}
				//다음버튼
				if(pageNo <= totalPage) {
					pageNavi += "<li class='page-item'>";
					pageNavi += "<a class='page-link' style='color:#9ac6e8'  href='/productList.do?reqPage="+pageNo+"'>";
					pageNavi += "&gt;</a></li>";
				}
				pageNavi +="</ul>";
				
				SellerProductPageData sppd = new SellerProductPageData(pList, pageNavi, start);
				System.out.println(sppd);
				
			
				return sppd;
			}
	@Transactional
	public int productInsert(Product product, ShippingInfo shippingInfo, ArrayList<ProductImg> list, ReturnPolicy returnPolicy) {
	
		  int result1 = dao.insertProduct(product); 
		  int result = 0; //FILE_TBL 테이블에 인서트 성공여부를 위한 INT값
			//insertproduct 성공했을경우
			if(result1 > 0) {
				ProductImg pi = new ProductImg();
				pi = list.get(0);
				pi.setProductNo(product.getProductNo());
				System.out.println(list.get(0)+"dao로 넘어온 list값");
				System.out.println(pi+"dao로 넘어와서 저장한 pi값");
				result = dao.insertProductMainImg(pi);
				// 가장 최근에 넣은 product값 MAX로 구하기for(int i=0; i<list.length; i++)//for(ProductImg fv : list )
				for(int i=1; i<list.size(); i++) { // ArrayList에 들어간 여러개의 파일 불러오기
					ProductImg fv = new ProductImg();
					fv = list.get(i);
					fv.setProductNo(product.getProductNo()); //db에 insert하기위해 필요한 boardNo
					result += dao.insertProductImg(fv);//db에 file_tbl에 insert한 횟수만큼 추가하여 result에 대입
				}
				shippingInfo.setProductNo(product.getProductNo());
				System.out.println(product.getProductNo()+"셀렉트키확인");
				System.out.println(shippingInfo+"프로덕트 no 넣은 값");
				
				int result2 = dao.insertShippingInfo(shippingInfo); 
				
				if( result2 > 0) {
					returnPolicy.setProductNo(product.getProductNo());
					int result3 = dao.insertReturnPolicy(returnPolicy);	
					if (result3 > 0) {
						return result;
						
					}else return -3;//환불정책 insert 실패
					
				}else return -2; //배송정보 insert 실패 
			
			}else {
				return -1; //파일 없는경우
			}
			 
		 
		  
		 
		
	}
	public HashMap<Object, Object> selectOneProductInfo(Product product, ProductImg productImg) {
		HashMap<Object, Object> p = dao.selectOneproductInfo(product);
		productImg.setProductNo(product.getProductNo());
		ArrayList<ProductImg> list = dao.selectProductImg(productImg);
		p.put("oldpath", list);
		return p;
	}
	
	public int productUpdate(Product product, ShippingInfo shippingInfo, ArrayList<ProductImg> mainList,
			ArrayList<ProductImg> detailList, ReturnPolicy returnPolicy, MultipartFile[] productImg, String[] oldPath,
			int[] oldPathNo) {
		int result1 = dao.updateProduct(product);
		System.out.println(product + "업데이트할 프로덕트");
		int result = 0; // FILE_TBL 테이블에 인서트 성공여부를 위한 INT값
		// insertproduct 성공했을경우
		if (result1 > 0) {
			if (mainList.isEmpty()) {
			} else {
				ProductImg pi = new ProductImg();
				pi = mainList.get(0);
				pi.setProductNo(product.getProductNo());

				result = dao.updateProductMainImg(pi);
				// 가장 최근에 넣은 product값 MAX로 구하기for(int i=0; i<list.length; i++)//for(ProductImg
				// fv : list )

				for (ProductImg fv : detailList) { // ArrayList에 들어간 여러개의 파일 불러오기
					fv.setProductNo(product.getProductNo()); // db에 insert하기위해 필요한 boardNo
					System.out.println(fv);
					result += dao.updateProductMainImg(fv);// db에 file_tbl에 insert한 횟수만큼 추가하여 result에 대입
				}
			}
		}
		if (result1 > 0) {
			if (detailList.isEmpty()) {

			} else {
				ProductImg pi = new ProductImg();
				pi = detailList.get(0);
				pi.setProductNo(product.getProductNo());

				result = dao.updateProductMainImg(pi);
				// 가장 최근에 넣은 product값 MAX로 구하기for(int i=0; i<list.length; i++)//for(ProductImg
				// fv : list )

				for (ProductImg fv : detailList) { // ArrayList에 들어간 여러개의 파일 불러오기
					fv.setProductNo(product.getProductNo()); // db에 insert하기위해 필요한 boardNo
					System.out.println(fv);
					result += dao.insertProductImg(fv);// db에 file_tbl에 insert한 횟수만큼 추가하여 result에 대입

				}
			}
		}
		shippingInfo.setProductNo(product.getProductNo());
		System.out.println(product.getProductNo() + "셀렉트키확인");
		System.out.println(shippingInfo + "프로덕트 no 넣은 값");

		int result2 = dao.updateShippingInfo(shippingInfo);

		if (result2 > 0) {
			returnPolicy.setProductNo(product.getProductNo());
			System.out.println("shippinginfo update 성공여부" + result2);
			int result3 = dao.updateReturnPolicy(returnPolicy);
			if (result3 > 0) {
				System.out.println("returnPolicy update 성공여부" + result3);

				ProductImg pimg = new ProductImg();
				int result4 = 0;
				if (oldPathNo != null) {

					for (int i = 0; i < oldPathNo.length; i++) {
						pimg.setProductImgNo(oldPathNo[i]);
						result4 += dao.deleteProductImg(pimg);
					}

					if (result4 == oldPathNo.length) {
						System.out.println("파일삭제 성공");
						return result;

					} else
						return -5; // productImg delete실패

				} else
					return result; // 삭제할파일 없음

			} else
				return -3;// 환불정책 Update 실패}

		} else
			return -2; // 배송정보 Update 실패

	}
	public boolean deleteChoiceProduct(String productNo, Member m) {
		Product product = new Product();
		product.setMemberNo(m.getMemberNo());
		boolean result = true;
		StringTokenizer sT1 = new StringTokenizer(productNo,"/");
		while(sT1.hasMoreTokens()) {
			product.setProductNo(Integer.parseInt(sT1.nextToken())); 
			System.out.println(product);
			int result1 = dao.deleteChoiceProduct(product); 
			if(result1 == 0) {
				result = false;
				break;
			}
		
	}
		return result;
	}
}

