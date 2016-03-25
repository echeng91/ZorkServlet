import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Player {

	private int currentRoomID;
	private Room currentRoom;
	private ArrayList<String> recentItems = new ArrayList<String>();
	
	public Player(int roomID)
	{
		currentRoom = new Room(roomID);
		Connection con = DBConnect.connect();
		try {
			PreparedStatement play = con.prepareStatement("update player currentroom=?, steps=?, visited=?, recentitems=? where currentroom=?");
			play.setInt(1, roomID);
			play.setInt(2, getStepCount());
			play.setInt(3, getRoomsVisited());
			play.setString(4, "");
			play.setInt(5, currentRoomID);
			currentRoomID = roomID;
			play.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
	}
	
	public int getCurrentRoomID()
	{
		return currentRoomID;
	}
	
	public int getRoomsVisited()
	{
		int visitCount = 0;
		Connection con = DBConnect.connect();
		try {
			PreparedStatement count = con.prepareStatement("select visited from rooms where visited > 0");
			ResultSet rs = count.executeQuery();
			while(rs.next()) {
				visitCount++;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
		return visitCount;
	}

	public int getStepCount()
	{
		int stepCount = 0;
		Connection con = DBConnect.connect();
		try {
			PreparedStatement count = con.prepareStatement("select visited from rooms");
			ResultSet rs = count.executeQuery();
			while(rs.next()) {
				stepCount += rs.getInt("visited");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
		return stepCount;
	}
	
	public String movementChoice()
	{
		String movement = "<h3>Where will you go?<h3><br><table>";
		Connection con = DBConnect.connect();
		try{
			PreparedStatement mvmt = con.prepareStatement("select roomid, roomname from rooms left join connectedrooms on rooms.roomid=connectedrooms.roomid2 where connectedrooms.roomid1=? and rooms.discovered=1");
			mvmt.setInt(1, currentRoomID);
			ResultSet rs = mvmt.executeQuery();
			while(rs.next()){
				movement += "<tr><td><form action=\"Explore\" method=\"get\"><input type=\"hidden\" name=\"roomid\" value=\"" 
						+ rs.getInt("roomid") + "\">" + "<input type=\"submit\" value=\"" + rs.getString("roomname") 
						+ "\"></form></td></tr>";
			}
			rs.close();
			con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
		movement += "</table>";
		return movement;
	}
	
	public String seenItems()
	{
		String itemsSeen = "<h3>You remember seeing:</h3><ul>";
		Connection con = DBConnect.connect();
		try {
			ResultSet rs = null;
			PreparedStatement saw = con.prepareStatement("select itemname from items where roomid=?");
			saw.setInt(1, currentRoomID);
			rs = saw.executeQuery();
			while(rs.next())
			{
				PreparedStatement note = con.prepareStatement("insert into recentitems (itemname) values(?)");
				note.setString(1, rs.getString(1));
				note.executeUpdate();
			}
			PreparedStatement seen = con.prepareStatement("select itemname from recentitems");
			rs = seen.executeQuery();
			while(rs.next())
			{
				recentItems.add(rs.getString(1));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		if(recentItems.size() < 5) {
			for(String item: recentItems) {
				itemsSeen += "<li>" + item + "</li>";
			}
		} else {
			for(int i = 1; i < 6; i++) {
				itemsSeen += "<li>" + recentItems.get(recentItems.size() - i) + "</li>";
			}
		}
		itemsSeen += "</ul>";
		DBConnect.disconnect(con);
		return itemsSeen;
	}
	
	public String getLocationName()
	{
		return currentRoom.getRoomName();
	}
	public String getCurrentItems()
	{
		String itemsSeen = "<h3>You see:</h3><ul>";
		ArrayList<String> current = currentRoom.getItems();
		for(String item: current) {
			itemsSeen += "<li>" + item + "</li>";
		}
		itemsSeen += "</ul>";
		return itemsSeen;
	}
	/*insert into player (currentroom, steps, visited, recentitems) values (1, 0, 0, 'none');
	 * 
	 */
}
