package shoes.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shoes.common.Command;
import shoes.dao.ProductDAO;
import shoes.dto.pdtDTO;
import shoes.dto.pmDTO;

public class ProductDetailCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		HttpSession httpsession = request.getSession(true);
		pmDTO pmdto = new pmDTO();
		pmdto=(pmDTO) httpsession.getAttribute("pmDTO");
		int pmno = pmdto.getPm_no();
		
		int no = Integer.parseInt(request.getParameter("pdt_no"));
		ProductDAO pDAO = new ProductDAO();
		pdtDTO pdto = new pdtDTO();
		pdto = pDAO.productDetail(no, pmno);
		
		
		request.setAttribute("pdto", pdto);
		
		
		return "/view/pMem/productDetail.jsp";
	}

}
