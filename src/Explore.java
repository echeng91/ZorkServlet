

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
		switch(Integer.parseInt(request.getParameter("roomid"))) {
		case 1: message += "<img src=\"http://cdn.bulbagarden.net/upload/thumb/4/47/451Skorupi.png/250px-451Skorupi.png\" alt=\"Foyer\">";
		break;
		case 2: message += "<img src=\"http://elitechoice.org/wp-content/uploads/2013/04/steinway_sons_louis_xv_gold-piano.jpg\" alt=\"Front Room\">";
		break;
		case 3: message += "<img src=\"http://cdn.bulbagarden.net/upload/thumb/7/75/167Spinarak.png/250px-167Spinarak.png\" alt=\"Library\">";
		break;
		case 4: message += "<img src=\"http://cdn.bulbagarden.net/upload/thumb/d/da/041Zubat.png/250px-041Zubat.png\" alt=\"Kitchen\">";
		break;
		case 5: message += "<img src=\"http://www.maciejratajski.com/sites/default/files/work/image/ratajski-knowledge-1.jpg\" alt=\"Dining Room\" height=\"200\">";
		break;
		case 6: message += "<img src=\"https://upload.wikimedia.org/wikipedia/commons/8/87/WinonaSavingsBankVault.JPG\" alt=\"Vault\" height=\"200\">";
		break;
		case 7: message += "<img src=\"http://vignette2.wikia.nocookie.net/darksouls/images/a/a8/Mimic.png/revision/latest?cb=20130714135545\" alt=\"Parlor\">";
		break;
		case 8: message += "<img src=\"http://i.imgur.com/2378cuH.jpg\" alt=\"Secret Room\" height=\"200\">";
		break;
		}
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
