package shoes.command;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shoes.common.Command;
import shoes.dao.pmDAO;
import shoes.dto.pmDTO;

public class sidebar1Command implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		pmDAO pdao = new pmDAO();
		
		HttpSession httpsession = request.getSession(true);
		String pmid= (String) httpsession.getAttribute("id");
		
		pmDTO pmdto = new pmDTO();
		pmdto = (pmDTO) httpsession.getAttribute("pmDTO");
		int no = pmdto.getPm_no();
		
		int ppoint = pdao.selectPoint(no);
		request.setAttribute("point", ppoint);

		return "/view/pMem/sidebar1.jsp";
	}
}
