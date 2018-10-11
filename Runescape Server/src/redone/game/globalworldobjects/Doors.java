package redone.game.globalworldobjects;

import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import redone.Server;
import redone.game.database.MySQLDatabase;
import redone.game.objects.Objects;



public class Doors {

	private static Doors mInstance = null;

	private List<Doors> doors = new ArrayList<Doors>();

	private File doorFile;
	
	public static Doors getInstance() {
		if (mInstance == null) {
			mInstance = new Doors();
		}
		return mInstance;
	}

	private Doors()
	{

	}
	
	private Doors(int door, int x, int y, int z, int face, int type, int open) {
		this.doorId = door;
		this.originalId = door;
		this.doorX = x;
		this.doorY = y;
		this.originalX = x;
		this.originalY = y;
		this.doorZ = z;
		this.originalFace = face;
		this.currentFace = face;
		this.type = type;
		this.open = open;
	}
	
	private Doors getDoor(int id, int x, int y, int z) {
		for (Doors d : doors) {
			if (d.doorId == id) {
				if (d.doorX == x && d.doorY == y && d.doorZ == z) {
					return d;
				}
			}
		}
		return null;
	}
	
	public boolean handleDoor(int id, int x, int y, int z) {

		Doors d = getDoor(id, x, y, z);
		
		if (d == null) {
			if (DoubleDoors.getInstance().handleDoor(id, x, y, z)) {
				return true;
			}
			return false;
		}
		int xAdjustment = 0, yAdjustment = 0;
		if (d.type == 0) {
			if (d.open == 0) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					xAdjustment = -1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					yAdjustment = 1;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					xAdjustment = 1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					yAdjustment = -1;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					yAdjustment = 1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					xAdjustment = 1;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					yAdjustment = -1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					xAdjustment = -1;
				}
			}
		} else if (d.type == 9) {
			if (d.open == 0) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					xAdjustment = 1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					xAdjustment = 1;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					xAdjustment = -1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					xAdjustment = -1;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					xAdjustment = 1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					xAdjustment = 1;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					xAdjustment = -1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					xAdjustment = -1;
				}
			}
		}
		if (xAdjustment != 0 || yAdjustment != 0) { 
			Objects o = new Objects(-1, d.doorX, d.doorY, d.doorZ, 0, d.type, 0);
			Server.objectHandler.placeObject(o);
		}
		if (d.doorX == d.originalX && d.doorY == d.originalY) {
			d.doorX += xAdjustment;
			d.doorY += yAdjustment;
		} else { 
			Objects o = new Objects(-1, d.doorX, d.doorY, d.doorZ, 0, d.type, 0);
			Server.objectHandler.placeObject(o);
			d.doorX = d.originalX;
			d.doorY = d.originalY;
		}
		if (d.doorId == d.originalId) {
			if (d.open == 0) {
				d.doorId += 1;
			} else if (d.open == 1) {
				d.doorId -= 1;
			}
		} else if (d.doorId != d.originalId) {
			if (d.open == 0) {
				d.doorId -= 1;
			} else if (d.open == 1) {
				d.doorId += 1;
			}
		}
		Server.objectHandler.placeObject(new Objects(d.doorId, d.doorX, d.doorY, d.doorZ, getNextFace(d), d.type, 0));
		return true;
	}
	
	private int getNextFace(Doors d) {
		int f = d.originalFace;
		if (d.type == 0) {
			if (d.open == 0) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					f = 1;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					f = 2;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					f = 3;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					f = 0;
				} else if (d.originalFace != d.currentFace){
					f = d.originalFace;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					f = 3;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					f = 0;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					f = 1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					f = 2;
				} else if (d.originalFace != d.currentFace){
					f = d.originalFace;
				}
			}
		} else if (d.type == 9) {
			if (d.open == 0) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					f = 3;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					f = 2;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					f = 1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					f = 0;
				} else if (d.originalFace != d.currentFace){
					f = d.originalFace;
				}
			} else if (d.open == 1) {
				if (d.originalFace == 0 && d.currentFace == 0) {
					f = 3;
				} else if (d.originalFace == 1 && d.currentFace == 1) {
					f = 0;
				} else if (d.originalFace == 2 && d.currentFace == 2) {
					f = 1;
				} else if (d.originalFace == 3 && d.currentFace == 3) {
					f = 2;
				} else if (d.originalFace != d.currentFace){
					f = d.originalFace;
				}
			}
		}
		d.currentFace = f;
		return f;
	}
	
	public void load() {
		doors.clear();
		long start = System.currentTimeMillis();
		MySQLDatabase database = MySQLDatabase.getInstance();
		ResultSet rs = database.runQuery("SELECT * FROM `doors`;");
		
		try {
			while(rs.next())
			{
				int id = rs.getInt(1);
				int x = rs.getInt(2);
				int y = rs.getInt(3);
				int f = rs.getInt(4);
				int z = rs.getInt(5);
				int t = rs.getInt(6);
				
				doors.add(new Doors(id, x, y, z, f, t, alreadyOpen(id)?1:0));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Loaded "+ doors.size() +" doors in "+ (System.currentTimeMillis() - start) +"ms.");
	}
	
	private boolean alreadyOpen(int id) {
		for (int i = 0; i < openDoors.length; i++) {
			if (openDoors[i] == id) {
				return true;
			}
		}
		return false;
	}

	private int doorId;
	private int originalId;
	private int doorX;
	private int doorY;
	private int originalX;
	private int originalY;
	private int doorZ;
	private int originalFace;
	private int currentFace;
	private int type;
	private int open;
	
	private static int[] openDoors = {
		1504, 1514, 1517, 1520, 1531, 
		1534, 2033, 2035, 2037, 2998, 
		3271, 4468, 4697, 6101,6103, 
		6105, 6107, 6109, 6111, 6113, 
		6115, 6976, 6978, 8696, 8819,
		10261, 10263,10265,11708,11710,
		11712,11715,11994,12445, 13002,	
	};
	
}