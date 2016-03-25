

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Explore
 */
@WebServlet("/Explore")
public class Explore extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Explore() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Player p1 = new Player(Integer.parseInt(request.getParameter("roomid")));
		Room.visit(Integer.parseInt(request.getParameter("roomid")));
		if((Integer.parseInt(request.getParameter("roomid"))) == 6) {
			Room.discoverSecretRoom();
		}
		String message = "<h1>You are in the " + p1.getLocationName() + "</h1><br>";
		/*switch(Integer.parseInt(request.getParameter("roomid"))) {
		case 1: message += "<img src=\"\" alt=\"Foyer\">";
		break;
		case 2: message += "<img src=\"\" alt=\"Front Room\">";
		break;
		case 3: message += "<img src=\"\" alt=\"Library\">";
		break;
		case 4: message += "<img src=\"\" alt=\"Kitchen\">";
		break;
		case 5: message += "<img src=\"\" alt=\"Dining Room\">";
		break;
		case 6: message += "<img src=\"\" alt=\"Vault\">";
		break;
		case 7: message += "<img src=\"\" alt=\"Parlor\">";
		break;
		case 8: message += "<img src=\"\" alt=\"Secret Room\">";
		break;
		}*/
		message += p1.getCurrentItems();
		message += p1.seenItems();
		message += p1.movementChoice();
		request.setAttribute("messages", message);

		request.getRequestDispatcher("/Zork.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
