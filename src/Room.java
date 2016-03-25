import java.util.ArrayList;
import java.util.Random;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class Room {

	private int roomID;
	private String roomName;
	private boolean discovered;
	private int visited;
	private ArrayList<String> items = new ArrayList<String>();

	//constructor
	public Room()
	{
		roomID = 0;
		roomName = "The Void";
		discovered = true;
		visited = 0;
	}

	public Room(int _roomID)
	{
		roomID = _roomID;
		Connection con = DBConnect.connect();
		try {
			PreparedStatement detailRoom = con.prepareStatement("select roomname, discovered, visited from rooms where roomid = ?");
			detailRoom.setInt(1, roomID);
			ResultSet rs = detailRoom.executeQuery();
			while(rs.next()) {
				roomName = rs.getString("roomname");
				if(rs.getInt("discovered") == 0) {
					discovered = false;
				} else {
					discovered = true;
				}
				visited = rs.getInt("visited");
			}
			populateItems(con);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
	}

	//getter
	public int getRoomID()
	{
		return roomID;
	}
	public String getRoomName()
	{
		return roomName;
	}
	public boolean getDiscovered()
	{
		return discovered;
	}
	public int getVisited()
	{
		return visited;
	}
	public ArrayList<String> getItems()
	{
		return items;
	}
	

	//setter
	public void setRoomName(String _roomName)
	{
		roomName = _roomName;
		Connection con = DBConnect.connect();
		try {
			PreparedStatement setrn = con.prepareStatement("update rooms set roomname = ? where roomid = ?");
			setrn.setString(1, roomName);
			setrn.setInt(2, roomID);
			setrn.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
	}
	public void setDiscovered(boolean _discovered)
	{
		discovered = _discovered;
		Connection con = DBConnect.connect();
		int discovery = 0;
		try {
			PreparedStatement setd = con.prepareStatement("update rooms set discovered = ? where roomid = ?");
			if(discovered) {
				discovery = 1;
			}
			setd.setInt(1, discovery);
			setd.setInt(2, roomID);
			setd.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
	}
	public void setVisited(int _visited)
	{
		visited = _visited;
		Connection con = DBConnect.connect();
		try {
			PreparedStatement setv = con.prepareStatement("update rooms set visited = ? where roomid = ?");
			setv.setInt(1, visited);
			setv.setInt(2, roomID);
			setv.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
	}

	//other
	public void visit()
	{
		visited++;
		setVisited(visited);
	}
	
	public static void visit(int _roomID)
	{
		Connection con = DBConnect.connect();
		try {
			PreparedStatement getv = con.prepareStatement("select visited from rooms where roomid = ?");
			getv.setInt(1, _roomID);
			ResultSet rs = getv.executeQuery();
			int _visited = 0;
			while(rs.next()) {
				_visited = rs.getInt("visited");
			}
			PreparedStatement setv = con.prepareStatement("update rooms set visited = ? where roomid = ?");
			setv.setInt(1, _visited);
			setv.setInt(2, _roomID);
			setv.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		DBConnect.disconnect(con);
	}

	private void populateItems(Connection con)
	{
		try {
			PreparedStatement populate = con.prepareStatement("select itemname from items where roomid = ?");
			populate.setInt(1, roomID);
			ResultSet rs = populate.executeQuery();
			while(rs.next()) {
				items.add(rs.getString("itemname"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void discoverSecretRoom()
	{
		Random rnd = new Random();
		int find = rnd.nextInt(4);
		if(find == 0)
		{
			Connection con = DBConnect.connect();
			try {
				PreparedStatement setd = con.prepareStatement("update rooms set discovered = 1 where roomid = 8");
				setd.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			DBConnect.disconnect(con);
		}
	}

	/* sql used to make tables

create table player (
currentRoom int,
steps int,
visited int,
);

create table rooms (
roomid int not null primary key,
roomname varchar(50),
discovered int,
visited int
);

create table items (
itemname varchar(50),
roomid int
);
alter table items add foreign key(roomid) references rooms(roomid);

create table recentitems (
itemname varchar(50)
);

create table connectedrooms (
roomid1 int,
roomid2 int
);
alter table connectedrooms add foreign key(roomid1) references rooms(roomid);
alter table connectedrooms add foreign key(roomid2) references rooms(roomid);

insert into rooms(roomid, roomname, discovered, visited) values(1, 'Foyer', 1, 0);
insert into rooms(roomid, roomname, discovered, visited) values(2, 'Front Room', 1, 0);
insert into rooms(roomid, roomname, discovered, visited) values(3, 'Library', 1, 0);
insert into rooms(roomid, roomname, discovered, visited) values(4, 'Kitchen', 1, 0);
insert into rooms(roomid, roomname, discovered, visited) values(5, 'Dining Room', 1, 0);
insert into rooms(roomid, roomname, discovered, visited) values(6, 'Vault', 1, 0);
insert into rooms(roomid, roomname, discovered, visited) values(7, 'Parlor', 1, 0);
insert into rooms(roomid, roomname, discovered, visited) values(8, 'Secret Room', 0, 0);

insert into connectedrooms(roomid1, roomid2) values(1,2);
insert into connectedrooms(roomid1, roomid2) values(2,1);

insert into connectedrooms(roomid1, roomid2) values(2,3);
insert into connectedrooms(roomid1, roomid2) values(3,2);

insert into connectedrooms(roomid1, roomid2) values(2,4);
insert into connectedrooms(roomid1, roomid2) values(4,2);

insert into connectedrooms(roomid1, roomid2) values(3,5);
insert into connectedrooms(roomid1, roomid2) values(5,3);

insert into connectedrooms(roomid1, roomid2) values(4,7);
insert into connectedrooms(roomid1, roomid2) values(7,4);

insert into connectedrooms(roomid1, roomid2) values(7,6);
insert into connectedrooms(roomid1, roomid2) values(6,7);

insert into connectedrooms(roomid1, roomid2) values(6,8);
insert into connectedrooms(roomid1, roomid2) values(8,6);

insert into items(itemname, roomid) values('dead scorpion',1);
insert into items(itemname, roomid) values('piano',2);
insert into items(itemname, roomid) values('spiders',3);
insert into items(itemname, roomid) values('bats',4);
insert into items(itemname, roomid) values('dust',5);
insert into items(itemname, roomid) values('empty box',5);
insert into items(itemname, roomid) values('3 walking skeletons',6);
insert into items(itemname, roomid) values('treasure chest',7);
insert into items(itemname, roomid) values('piles of gold',8);

	 */

}
