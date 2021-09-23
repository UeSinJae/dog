package action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import svc.DogCartSearchService;
import vo.ActionForward;
import vo.Cart;

public class DogCartSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		DogCartSearchService dogCartSearchService = new DogCartSearchService();
		
		//String타입을 int로 변경(이유: >= <=와 같은 비교연산자를 사용하기위해서)
		int startMoney = Integer.parseInt(request.getParameter("startMoney"));
		int endMoney = Integer.parseInt(request.getParameter("endMoney"));
		
		//ArrayList<Cart> searchCartList = dogCartSearchService.getCartSearchLsit(startMoney, endMoney, request);
		ArrayList<Cart> cartList = dogCartSearchService.getCartSearchLsit(startMoney, endMoney, request);
		
		/*검색한 항목에 대한 총 금액 계산*/
		int totalMoney = 0;//검색한 항목의 지불할 총 금액
		int money = 0;//검색한 항목 하나에 대한 지불 금액
		
		for(int i=0;i<cartList.size();i++) {
			//불독의 가격 2000 * 1
			money = cartList.get(i).getPrice()*cartList.get(i).getQty();
			totalMoney += money;
		}
		/*---------------dogCartList.jsp 뷰페이지에 총금액을 request영역에 공유하여 디스패치방식으로 포워딩 ------------------*/
		request.setAttribute("totalMoney", totalMoney);
		request.setAttribute("cartList", cartList);//교재에 없는 부분
		request.setAttribute("startMoney", startMoney);//검색에 사용된 시작 금액을 request영역에 속성으로 공유
		request.setAttribute("endMoney", endMoney);//검색에 사용된 마지막 금액을 request영역에 속성으로 공유한 후
		
		ActionForward forward = new ActionForward("dogCartList.jsp", false);
				
		return forward;
	}

}
