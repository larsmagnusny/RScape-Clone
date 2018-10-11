package redone.game.shops;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import redone.game.database.MySQLDatabase;
import redone.game.players.PlayerHandler;
import redone.util.Misc;

/**
 * Shops
 **/

public class ShopHandler {

	public static int MaxShops = 200;
	public static int MaxShopItems = 200;
	public static int MaxShowDelay = 10;
	public static int MaxSpecShowDelay = 60;
	public static int TotalShops = 0;
	public static int[][] ShopItems = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsN = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsDelay = new int[MaxShops][MaxShopItems];
	public static int[][] ShopItemsSN = new int[MaxShops][MaxShopItems];
	public static int[] ShopItemsStandard = new int[MaxShops];
	public static String[] ShopName = new String[MaxShops];
	public static int[] ShopSModifier = new int[MaxShops];
	public static int[] ShopBModifier = new int[MaxShops];
	public static long[][] ShopItemsRestock = new long[MaxShops][MaxShopItems];

	public ShopHandler() {
		for (int i = 0; i < MaxShops; i++) {
			for (int j = 0; j < MaxShopItems; j++) {
				ResetItem(i, j);
				ShopItemsSN[i][j] = 0;
			}
			ShopItemsStandard[i] = 0;
			ShopSModifier[i] = 0;
			ShopBModifier[i] = 0;
			ShopName[i] = "";
		}
		TotalShops = 0;
		loadShops();
	}
	
	public static int restockTimeItem(int itemId) {
		switch(itemId) {
		
			default:
			return 86400000;
		}

	}

	public void process() {
		boolean DidUpdate = false;
		for (int i = 1; i <= TotalShops; i++) {
			for (int j = 0; j < MaxShopItems; j++) {
				if (ShopItems[i][j] > 0) {
					if (ShopItemsDelay[i][j] >= MaxShowDelay) {
						if (j <= ShopItemsStandard[i] && ShopItemsN[i][j] <= ShopItemsSN[i][j]) {
							if (ShopItemsN[i][j] < ShopItemsSN[i][j] && System.currentTimeMillis() - ShopItemsRestock[i][j] > restockTimeItem(ShopItems[i][j])) {
								ShopItemsN[i][j] += 1;
								ShopItemsDelay[i][j] = 1;
								ShopItemsDelay[i][j] = 0;
								DidUpdate = true;
								ShopItemsRestock[i][j] = System.currentTimeMillis();
							}
						} else if (ShopItemsDelay[i][j] >= MaxSpecShowDelay) {
							DiscountItem(i, j);
							ShopItemsDelay[i][j] = 0;
							DidUpdate = true;
						}
					}
					ShopItemsDelay[i][j]++;
				}
			}
			if (DidUpdate) {
				for (int k = 1; k < PlayerHandler.players.length; k++) {
					if (PlayerHandler.players[k] != null) {
						if (PlayerHandler.players[k].isShopping && PlayerHandler.players[k].myShopId == i) {
							PlayerHandler.players[k].updateShop = true;
							PlayerHandler.players[k].updateshop(i);
						}
					}
				}
				DidUpdate = false;
			}
		}
	}

	private void DiscountItem(int ShopID, int ArrayID) {
		ShopItemsN[ShopID][ArrayID] -= 1;
		if (ShopItemsN[ShopID][ArrayID] <= 0) {
			ShopItemsN[ShopID][ArrayID] = 0;
			ResetItem(ShopID, ArrayID);
		}
	}

	private void ResetItem(int ShopID, int ArrayID) {
		ShopItems[ShopID][ArrayID] = 0;
		ShopItemsN[ShopID][ArrayID] = 0;
		ShopItemsDelay[ShopID][ArrayID] = 0;
	}


	public boolean loadShops() {
		MySQLDatabase database = MySQLDatabase.getInstance();
		ResultSet rs = database.runQuery("SELECT * FROM `shops`;");
		
		try {
			while(rs.next())
			{
				int ShopID = rs.getInt(1);
				ShopName[ShopID] = rs.getString(2).replaceAll("_", " ");
				ShopSModifier[ShopID] = rs.getInt(3);
				ShopBModifier[ShopID] = rs.getInt(4);

				for(int i = 0; i < 18; i++)
				{
					ShopItems[ShopID][i] = rs.getInt(5 + 2 * i);
					ShopItemsN[ShopID][i] = rs.getInt(6 + 2 * i);
					ShopItemsSN[ShopID][i] = rs.getInt(6 + 2 * i);
					ShopItemsStandard[ShopID]++;
				}
			}
			
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
