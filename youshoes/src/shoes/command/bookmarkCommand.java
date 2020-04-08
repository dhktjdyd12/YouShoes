package shoes.command;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shoes.common.Command;
import shoes.dao.bookmarkDAO;
import shoes.dto.bookmarkDTO;
import shoes.dto.pmDTO;

public class bookmarkCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		bookmarkDAO bookmarkDAO= new bookmarkDAO();

		int pmNO = ((pmDTO)request.getSession().getAttribute("pmDTO")).getPm_no();
		
		List<bookmarkDTO> bookmark = bookmarkDAO.bookmarkGet(pmNO);
		request.setAttribute("bookmark", bookmark);
		
		return "view/pMem/bookmark.jsp";
	}

}
