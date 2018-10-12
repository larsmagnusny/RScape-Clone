package redone.game.players;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import redone.game.database.MySQLDatabase;
import redone.util.Misc;

public class PlayerSave {

	/**
	 * Loading
	 **/
	public static int loadGame(Client player, String playerName, String playerPass)
	{
		MySQLDatabase database = MySQLDatabase.getInstance();
		ResultSet accountId = database.runQuery("SELECT `UserID` FROM runescape.`account` WHERE `Username` = '"+playerName+"' AND `Password` = PASSWORD('"+playerPass+"');");
		
		try {
			if(accountId.next())
			{
				int userID = accountId.getInt(1);
				
				ResultSet characterPage = database.runQuery("SELECT * FROM `character` WHERE `UserID` = '"+userID+"';");
				ResultSet character_equipmentPage = database.runQuery("SELECT * FROM `character_equipment` WHERE `UserID` = '"+userID+"';");
				ResultSet character_equipmentNPage = database.runQuery("SELECT * FROM `character_equipmentn` WHERE `UserID` = '"+userID+"';");
				ResultSet character_friendsList = database.runQuery("SELECT * FROM `character_friendslist` WHERE `UserID` = '"+userID+"';");
				ResultSet character_ignoreList = database.runQuery("SELECT * FROM `character_ignorelist` WHERE `UserID` = '"+userID+"';");
				ResultSet character_inventoryPage = database.runQuery("SELECT * FROM `character_inventory` WHERE `UserID` = '"+userID+"';");
				ResultSet character_inventoryNPage = database.runQuery("SELECT * FROM `character_inventoryn` WHERE `UserID` = '"+userID+"';");
				ResultSet character_lookPage = database.runQuery("SELECT * FROM `character_look` WHERE `UserID` = '"+userID+"';");
				ResultSet character_skillsLevelPage = database.runQuery("SELECT * FROM `character_skillslevel` WHERE `UserID` = '"+userID+"';");
				ResultSet character_skillsXPPage = database.runQuery("SELECT * FROM `character_skillsxp` WHERE `UserID` = '"+userID+"';");
				ResultSet character_bankItems = database.runQuery("SELECT * FROM `character_bank` WHERE `UserID` = '"+userID+"';");
				
				if(characterPage.next())
				{
					// Load character stuff
					player.heightLevel = characterPage.getInt("characterHeight");
					player.teleportToX = characterPage.getInt("characterPosX") <= 0 ? characterPage.getInt("lastX") : characterPage.getInt("characterPosX");
					player.teleportToY = characterPage.getInt("characterPosY") <= 0 ? characterPage.getInt("lastY") : characterPage.getInt("characterPosY");
					player.playerRights = characterPage.getInt("characterRights");
					player.blackMarks = characterPage.getInt("blackMarks");
					player.lostCannon = characterPage.getBoolean("lostCannon");
					player.getCannon().myBalls = characterPage.getInt("myBalls");
					player.cannonX = characterPage.getInt("cannonX");
					player.cannonY = characterPage.getInt("cannonY");
					player.removedTasks[0] = characterPage.getInt("removedTask0");
					player.removedTasks[1] = characterPage.getInt("removedTask1");
					player.removedTasks[2] = characterPage.getInt("removedTask2");
					player.removedTasks[3] = characterPage.getInt("removedTask3");
					player.SlayerMaster = characterPage.getInt("slayerMaster");
					player.slayerTask = characterPage.getInt("slayerTask");
					player.slayerPoints = characterPage.getInt("slayerPoints");
					player.taskAmount = characterPage.getInt("taskAmount");
					player.cwGames = characterPage.getInt("cwGames");
					player.crystalBowArrowCount = characterPage.getInt("crystalBowShots");
					player.randomActions = characterPage.getInt("randomActions");
					player.skullTimer = characterPage.getInt("skullTimer");
					player.recoilHits = characterPage.getInt("recoilHits");
					player.brightness = characterPage.getInt("brightness");
					player.spiritTree = characterPage.getBoolean("spiritTree");
					player.npcCanAttack = characterPage.getBoolean("npcCanAttack");
					player.rope = characterPage.getBoolean("rope");
					player.rope2 = characterPage.getBoolean("rope2");
					player.recievedMask = characterPage.getBoolean("recievedMask");
					player.recievedReward = characterPage.getBoolean("recievedReward");
					player.splitChat = characterPage.getBoolean("splitChat");
					player.hasPaid = characterPage.getBoolean("hasPaid");
					player.poison = characterPage.getBoolean("poison");
					player.closeTutorialInterface = characterPage.getBoolean("closeTutorialInterface");
					player.canWalkTutorial = characterPage.getBoolean("canWalkTutorial");
					player.needsNewTask = characterPage.getBoolean("needsNewTask");
					player.isBotting = characterPage.getBoolean("isBotting");
					player.musicOn = characterPage.getBoolean("musicOn");
					player.barrowsNpcs[0][1] = characterPage.getInt("barrowsNpcs_0");
					player.barrowsNpcs[1][1] = characterPage.getInt("barrowsNpcs_1");
					player.barrowsNpcs[2][1] = characterPage.getInt("barrowsNpcs_2");
					player.barrowsNpcs[3][1] = characterPage.getInt("barrowsNpcs_3");
					player.barrowsNpcs[4][1] = characterPage.getInt("barrowsNpcs_4");
					player.barrowsNpcs[5][1] = characterPage.getInt("barrowsNpcs_5");
					player.summonId = characterPage.getInt("summonId");
					player.hasNpc = characterPage.getBoolean("hasNpc");
					player.barrowsKillCount = characterPage.getInt("barrowsKillCount");
					player.luthas = characterPage.getBoolean("luthas");
					player.village = characterPage.getBoolean("village");
					player.lastThieve = characterPage.getLong("lastThieve");
					player.homeTele = characterPage.getLong("homeTele");
					player.tutorialProgress = characterPage.getInt("tutorialProgress");
					player.strongHold = characterPage.getBoolean("strongHold");
					//player.filter = characterPage.getBoolean("filter");
					player.ratdied2 = characterPage.getBoolean("ratdied2");
					player.questStages = characterPage.getInt("questStages");
					player.cookAss = characterPage.getInt("cookAss");
					player.bananas = characterPage.getInt("bananas");
					player.sheepShear = characterPage.getInt("sheepShear");
					player.runeMist = characterPage.getInt("runeMist");
					player.dragonSlayerQuestStage = characterPage.getInt("dragonSlayerQuestStage");
					player.doricQuest = characterPage.getInt("doricQuest");
					player.pirateTreasure = characterPage.getInt("pirateTreasure");
					player.romeojuliet = characterPage.getInt("romeoJuliet");
					player.vampSlayer = characterPage.getInt("vampSlayer");
					player.gertCat = characterPage.getInt("gertCat");
					player.witchspot = characterPage.getInt("witchspot");
					player.restGhost = characterPage.getInt("restGhost");
					player.impsC = characterPage.getInt("impsC");
					player.knightS = characterPage.getInt("knightS");
					player.lastX = characterPage.getInt("lastX");
					player.lastY = characterPage.getInt("lastY");
					player.lastH = characterPage.getInt("lastH");
					player.hasStarter = characterPage.getBoolean("hasStarter");
					player.thankedForDonation = characterPage.getInt("thankedForDonation");
					player.membership = characterPage.getBoolean("membership");
					player.canSpeak = characterPage.getBoolean("canSpeak");
					player.questPoints = characterPage.getInt("questPoints");
					player.playerMagicBook = characterPage.getInt("magicBook");
					player.specAmount = characterPage.getDouble("specialAmount");
					player.randomCoffin = characterPage.getInt("selectedCoffin");
					player.isRunning2 = characterPage.getBoolean("isRunning");
					player.playerEnergy = characterPage.getInt("characterEnergy");
					player.teleBlockDelay = System.currentTimeMillis();
					player.teleBlockLength = characterPage.getInt("teleblockLength");
					player.lastYell = characterPage.getLong("lastYell");
					player.pcPoints = characterPage.getInt("pcPoints");
					player.magePoints = characterPage.getInt("magePoints");
					player.autoRet = characterPage.getInt("autoRet");
					player.accountFlagged = characterPage.getBoolean("flagged");
					player.lastLoginDate = characterPage.getInt("lastLoginDate");
					player.hasBankpin = characterPage.getBoolean("hasBankpin");
					player.setPin = characterPage.getBoolean("setPin");
					player.pinDeleteDateRequested = characterPage.getInt("pinRegisteredDeleteDay");
					player.requestPinDelete = characterPage.getBoolean("requestPinDelete");
					player.bankPin1 = characterPage.getInt("bankPin1");
					player.bankPin2 = characterPage.getInt("bankPin2");
					player.bankPin3 = characterPage.getInt("bankPin3");
					player.bankPin4 = characterPage.getInt("bankPin4");
					player.waveId = characterPage.getInt("wave");
					player.ptjob = characterPage.getInt("ptjob");
					player.creationAddress = "creationAddress";
					player.getPlayList().unlocked[0] = characterPage.getBoolean("music_0");
					player.getPlayList().unlocked[1] = characterPage.getBoolean("music_1");
					player.getPlayList().unlocked[2] = characterPage.getBoolean("music_2");
					player.getPlayList().unlocked[3] = characterPage.getBoolean("music_3");
					player.getPlayList().unlocked[4] = characterPage.getBoolean("music_4");
					player.getPlayList().unlocked[5] = characterPage.getBoolean("music_5");
					player.getPlayList().unlocked[6] = characterPage.getBoolean("music_6");
					player.getPlayList().unlocked[7] = characterPage.getBoolean("music_7");
					player.getPlayList().unlocked[8] = characterPage.getBoolean("music_8");
					player.getPlayList().unlocked[9] = characterPage.getBoolean("music_9");
					player.getPlayList().unlocked[10] = characterPage.getBoolean("music_10");
					player.getPlayList().unlocked[11] = characterPage.getBoolean("music_11");
					player.getPlayList().unlocked[12] = characterPage.getBoolean("music_12");
					player.getPlayList().unlocked[13] = characterPage.getBoolean("music_13");
					player.getPlayList().unlocked[14] = characterPage.getBoolean("music_14");
					player.getPlayList().unlocked[15] = characterPage.getBoolean("music_15");
					player.getPlayList().unlocked[16] = characterPage.getBoolean("music_16");
					player.getPlayList().unlocked[17] = characterPage.getBoolean("music_17");
					player.getPlayList().unlocked[18] = characterPage.getBoolean("music_18");
					player.getPlayList().unlocked[19] = characterPage.getBoolean("music_19");
					player.getPlayList().unlocked[20] = characterPage.getBoolean("music_20");
					player.getPlayList().unlocked[21] = characterPage.getBoolean("music_21");
					player.getPlayList().unlocked[22] = characterPage.getBoolean("music_22");
					player.getPlayList().unlocked[23] = characterPage.getBoolean("music_23");
					player.getPlayList().unlocked[24] = characterPage.getBoolean("music_24");
					player.getPlayList().unlocked[25] = characterPage.getBoolean("music_25");
					player.getPlayList().unlocked[26] = characterPage.getBoolean("music_26");
					player.getPlayList().unlocked[27] = characterPage.getBoolean("music_27");
					player.getPlayList().unlocked[28] = characterPage.getBoolean("music_28");
					player.getPlayList().unlocked[29] = characterPage.getBoolean("music_29");
					player.getPlayList().unlocked[30] = characterPage.getBoolean("music_30");
					player.getPlayList().unlocked[31] = characterPage.getBoolean("music_31");
					player.getPlayList().unlocked[32] = characterPage.getBoolean("music_32");
					player.getPlayList().unlocked[33] = characterPage.getBoolean("music_33");
					player.getPlayList().unlocked[34] = characterPage.getBoolean("music_34");
					player.getPlayList().unlocked[35] = characterPage.getBoolean("music_35");
					player.getPlayList().unlocked[36] = characterPage.getBoolean("music_36");
					player.getPlayList().unlocked[37] = characterPage.getBoolean("music_37");
					player.getPlayList().unlocked[38] = characterPage.getBoolean("music_38");
					player.getPlayList().unlocked[39] = characterPage.getBoolean("music_39");
					player.getPlayList().unlocked[40] = characterPage.getBoolean("music_40");
					player.getPlayList().unlocked[41] = characterPage.getBoolean("music_41");
					player.getPlayList().unlocked[42] = characterPage.getBoolean("music_42");
					player.getPlayList().unlocked[43] = characterPage.getBoolean("music_43");
					player.getPlayList().unlocked[44] = characterPage.getBoolean("music_44");
					player.getPlayList().unlocked[45] = characterPage.getBoolean("music_45");
					player.getPlayList().unlocked[46] = characterPage.getBoolean("music_46");
					player.getPlayList().unlocked[47] = characterPage.getBoolean("music_47");
					player.getPlayList().unlocked[48] = characterPage.getBoolean("music_48");
					player.getPlayList().unlocked[49] = characterPage.getBoolean("music_49");
					player.voidStatus[0] = characterPage.getInt("void_0");
					player.voidStatus[1] = characterPage.getInt("void_1");
					player.voidStatus[2] = characterPage.getInt("void_2");
					player.voidStatus[3] = characterPage.getInt("void_3");
					player.voidStatus[4] = characterPage.getInt("void_4");
					player.killCount = characterPage.getInt("gwkc");
					player.fightMode = characterPage.getInt("fightMode");
				}
				else
				{
					return 13;
				}
				
				if(character_equipmentPage.next() && character_equipmentNPage.next())
				{
					// Character equipment
					player.playerEquipment[0] = character_equipmentPage.getInt("slot_0");
					player.playerEquipment[1] = character_equipmentPage.getInt("slot_1");
					player.playerEquipment[2] = character_equipmentPage.getInt("slot_2");
					player.playerEquipment[3] = character_equipmentPage.getInt("slot_3");
					player.playerEquipment[4] = character_equipmentPage.getInt("slot_4");
					player.playerEquipment[5] = character_equipmentPage.getInt("slot_5");
					player.playerEquipment[6] = character_equipmentPage.getInt("slot_6");
					player.playerEquipment[7] = character_equipmentPage.getInt("slot_7");
					player.playerEquipment[8] = character_equipmentPage.getInt("slot_8");
					player.playerEquipment[9] = character_equipmentPage.getInt("slot_9");
					player.playerEquipment[10] = character_equipmentPage.getInt("slot_10");
					player.playerEquipment[11] = character_equipmentPage.getInt("slot_11");
					player.playerEquipment[12] = character_equipmentPage.getInt("slot_12");
					player.playerEquipment[13] = character_equipmentPage.getInt("slot_13");
					player.playerEquipmentN[0] = character_equipmentNPage.getInt("slot_0");
					player.playerEquipmentN[1] = character_equipmentNPage.getInt("slot_1");
					player.playerEquipmentN[2] = character_equipmentNPage.getInt("slot_2");
					player.playerEquipmentN[3] = character_equipmentNPage.getInt("slot_3");
					player.playerEquipmentN[4] = character_equipmentNPage.getInt("slot_4");
					player.playerEquipmentN[5] = character_equipmentNPage.getInt("slot_5");
					player.playerEquipmentN[6] = character_equipmentNPage.getInt("slot_6");
					player.playerEquipmentN[7] = character_equipmentNPage.getInt("slot_7");
					player.playerEquipmentN[8] = character_equipmentNPage.getInt("slot_8");
					player.playerEquipmentN[9] = character_equipmentNPage.getInt("slot_9");
					player.playerEquipmentN[10] = character_equipmentNPage.getInt("slot_10");
					player.playerEquipmentN[11] = character_equipmentNPage.getInt("slot_11");
					player.playerEquipmentN[12] = character_equipmentNPage.getInt("slot_12");
					player.playerEquipmentN[13] = character_equipmentNPage.getInt("slot_13");
				}
				else
				{
					return 13;
				}
				
				if(character_lookPage.next())
				{
					// Load character look
					player.playerAppearance[0] = character_lookPage.getInt("look_0");
					player.playerAppearance[1] = character_lookPage.getInt("look_1");
					player.playerAppearance[2] = character_lookPage.getInt("look_2");
					player.playerAppearance[3] = character_lookPage.getInt("look_3");
					player.playerAppearance[4] = character_lookPage.getInt("look_4");
					player.playerAppearance[5] = character_lookPage.getInt("look_5");
					player.playerAppearance[6] = character_lookPage.getInt("look_6");
					player.playerAppearance[7] = character_lookPage.getInt("look_7");
					player.playerAppearance[8] = character_lookPage.getInt("look_8");
					player.playerAppearance[9] = character_lookPage.getInt("look_9");
					player.playerAppearance[10] = character_lookPage.getInt("look_10");
					player.playerAppearance[11] = character_lookPage.getInt("look_11");
					player.playerAppearance[12] = character_lookPage.getInt("look_12");
				}
				else
				{
					return 13;
				}
				if(character_skillsLevelPage.next() && character_skillsXPPage.next())
				{
					player.playerLevel[0] = character_skillsLevelPage.getInt("slot_0");
					player.playerXP[0] = character_skillsXPPage.getInt("slot_0");
					player.playerLevel[1] = character_skillsLevelPage.getInt("slot_1");
					player.playerXP[1] = character_skillsXPPage.getInt("slot_1");
					player.playerLevel[2] = character_skillsLevelPage.getInt("slot_2");
					player.playerXP[2] = character_skillsXPPage.getInt("slot_2");
					player.playerLevel[3] = character_skillsLevelPage.getInt("slot_3");
					player.playerXP[3] = character_skillsXPPage.getInt("slot_3");
					player.playerLevel[4] = character_skillsLevelPage.getInt("slot_4");
					player.playerXP[4] = character_skillsXPPage.getInt("slot_4");
					player.playerLevel[5] = character_skillsLevelPage.getInt("slot_5");
					player.playerXP[5] = character_skillsXPPage.getInt("slot_5");
					player.playerLevel[6] = character_skillsLevelPage.getInt("slot_6");
					player.playerXP[6] = character_skillsXPPage.getInt("slot_6");
					player.playerLevel[7] = character_skillsLevelPage.getInt("slot_7");
					player.playerXP[7] = character_skillsXPPage.getInt("slot_7");
					player.playerLevel[8] = character_skillsLevelPage.getInt("slot_8");
					player.playerXP[8] = character_skillsXPPage.getInt("slot_8");
					player.playerLevel[9] = character_skillsLevelPage.getInt("slot_9");
					player.playerXP[9] = character_skillsXPPage.getInt("slot_9");
					player.playerLevel[10] = character_skillsLevelPage.getInt("slot_10");
					player.playerXP[10] = character_skillsXPPage.getInt("slot_10");
					player.playerLevel[11] = character_skillsLevelPage.getInt("slot_11");
					player.playerXP[11] = character_skillsXPPage.getInt("slot_11");
					player.playerLevel[12] = character_skillsLevelPage.getInt("slot_12");
					player.playerXP[12] = character_skillsXPPage.getInt("slot_12");
					player.playerLevel[13] = character_skillsLevelPage.getInt("slot_13");
					player.playerXP[13] = character_skillsXPPage.getInt("slot_13");
					player.playerLevel[14] = character_skillsLevelPage.getInt("slot_14");
					player.playerXP[14] = character_skillsXPPage.getInt("slot_14");
					player.playerLevel[15] = character_skillsLevelPage.getInt("slot_15");
					player.playerXP[15] = character_skillsXPPage.getInt("slot_15");
					player.playerLevel[16] = character_skillsLevelPage.getInt("slot_16");
					player.playerXP[16] = character_skillsXPPage.getInt("slot_16");
					player.playerLevel[17] = character_skillsLevelPage.getInt("slot_17");
					player.playerXP[17] = character_skillsXPPage.getInt("slot_17");
					player.playerLevel[18] = character_skillsLevelPage.getInt("slot_18");
					player.playerXP[18] = character_skillsXPPage.getInt("slot_18");
					player.playerLevel[19] = character_skillsLevelPage.getInt("slot_19");
					player.playerXP[19] = character_skillsXPPage.getInt("slot_19");
					player.playerLevel[20] = character_skillsLevelPage.getInt("slot_20");
					player.playerXP[20] = character_skillsXPPage.getInt("slot_20");
					player.playerLevel[21] = character_skillsLevelPage.getInt("slot_21");
					player.playerXP[21] = character_skillsXPPage.getInt("slot_21");
					player.playerLevel[22] = character_skillsLevelPage.getInt("slot_22");
					player.playerXP[22] = character_skillsXPPage.getInt("slot_22");
					player.playerLevel[23] = character_skillsLevelPage.getInt("slot_23");
					player.playerXP[23] = character_skillsXPPage.getInt("slot_23");
					player.playerLevel[24] = character_skillsLevelPage.getInt("slot_24");
					player.playerXP[24] = character_skillsXPPage.getInt("slot_24");
				}
				else
				{
					return 13;
				}
				if(character_inventoryPage.next() && character_inventoryNPage.next())
				{
					player.playerItems[0] = character_inventoryPage.getInt("slot_0");
					player.playerItems[1] = character_inventoryPage.getInt("slot_1");
					player.playerItems[2] = character_inventoryPage.getInt("slot_2");
					player.playerItems[3] = character_inventoryPage.getInt("slot_3");
					player.playerItems[4] = character_inventoryPage.getInt("slot_4");
					player.playerItems[5] = character_inventoryPage.getInt("slot_5");
					player.playerItems[6] = character_inventoryPage.getInt("slot_6");
					player.playerItems[7] = character_inventoryPage.getInt("slot_7");
					player.playerItems[8] = character_inventoryPage.getInt("slot_8");
					player.playerItems[9] = character_inventoryPage.getInt("slot_9");
					player.playerItems[10] = character_inventoryPage.getInt("slot_10");
					player.playerItems[11] = character_inventoryPage.getInt("slot_11");
					player.playerItems[12] = character_inventoryPage.getInt("slot_12");
					player.playerItems[13] = character_inventoryPage.getInt("slot_13");
					player.playerItems[14] = character_inventoryPage.getInt("slot_14");
					player.playerItems[15] = character_inventoryPage.getInt("slot_15");
					player.playerItems[16] = character_inventoryPage.getInt("slot_16");
					player.playerItems[17] = character_inventoryPage.getInt("slot_17");
					player.playerItems[18] = character_inventoryPage.getInt("slot_18");
					player.playerItems[19] = character_inventoryPage.getInt("slot_19");
					player.playerItems[20] = character_inventoryPage.getInt("slot_20");
					player.playerItems[21] = character_inventoryPage.getInt("slot_21");
					player.playerItems[22] = character_inventoryPage.getInt("slot_22");
					player.playerItems[23] = character_inventoryPage.getInt("slot_23");
					player.playerItems[24] = character_inventoryPage.getInt("slot_24");
					player.playerItems[25] = character_inventoryPage.getInt("slot_25");
					player.playerItems[26] = character_inventoryPage.getInt("slot_26");
					player.playerItems[27] = character_inventoryPage.getInt("slot_27");
					player.playerItemsN[0] = character_inventoryNPage.getInt("slot_0");
					player.playerItemsN[1] = character_inventoryNPage.getInt("slot_1");
					player.playerItemsN[2] = character_inventoryNPage.getInt("slot_2");
					player.playerItemsN[3] = character_inventoryNPage.getInt("slot_3");
					player.playerItemsN[4] = character_inventoryNPage.getInt("slot_4");
					player.playerItemsN[5] = character_inventoryNPage.getInt("slot_5");
					player.playerItemsN[6] = character_inventoryNPage.getInt("slot_6");
					player.playerItemsN[7] = character_inventoryNPage.getInt("slot_7");
					player.playerItemsN[8] = character_inventoryNPage.getInt("slot_8");
					player.playerItemsN[9] = character_inventoryNPage.getInt("slot_9");
					player.playerItemsN[10] = character_inventoryNPage.getInt("slot_10");
					player.playerItemsN[11] = character_inventoryNPage.getInt("slot_11");
					player.playerItemsN[12] = character_inventoryNPage.getInt("slot_12");
					player.playerItemsN[13] = character_inventoryNPage.getInt("slot_13");
					player.playerItemsN[14] = character_inventoryNPage.getInt("slot_14");
					player.playerItemsN[15] = character_inventoryNPage.getInt("slot_15");
					player.playerItemsN[16] = character_inventoryNPage.getInt("slot_16");
					player.playerItemsN[17] = character_inventoryNPage.getInt("slot_17");
					player.playerItemsN[18] = character_inventoryNPage.getInt("slot_18");
					player.playerItemsN[19] = character_inventoryNPage.getInt("slot_19");
					player.playerItemsN[20] = character_inventoryNPage.getInt("slot_20");
					player.playerItemsN[21] = character_inventoryNPage.getInt("slot_21");
					player.playerItemsN[22] = character_inventoryNPage.getInt("slot_22");
					player.playerItemsN[23] = character_inventoryNPage.getInt("slot_23");
					player.playerItemsN[24] = character_inventoryNPage.getInt("slot_24");
					player.playerItemsN[25] = character_inventoryNPage.getInt("slot_25");
					player.playerItemsN[26] = character_inventoryNPage.getInt("slot_26");
					player.playerItemsN[27] = character_inventoryNPage.getInt("slot_27");
				}
				else
				{
					return 13;
				}

				while(character_bankItems.next())
				{
					player.bankItems[character_bankItems.getInt("slotNum")] = character_bankItems.getInt("ItemID");
					player.bankItemsN[character_bankItems.getInt("slotNum")] = character_bankItems.getInt("ItemAmount");
				}
				
				int i = 0;
				while(character_friendsList.next())
				{
					player.friends[i++] = character_friendsList.getLong("FriendID");
				}
				
				i = 0;
				while(character_ignoreList.next())
				{
					player.friends[i++] = character_ignoreList.getLong("IgnoredPlayerID");
				}
			} 
			else
			{
				return 13;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return 1;
	}

	/**
	 * Saving
	 **/
	public static boolean saveGame(Client player)
	{
		if (!player.saveFile || player.newPlayer || !player.saveCharacter) {
			// System.out.println("first");
			return false;
		}
		if (player.playerName == null
				|| PlayerHandler.players[player.playerId] == null) {
			// System.out.println("second");
			return false;
		}
		
		MySQLDatabase database = MySQLDatabase.getInstance();
		ResultSet accountId = database.runQuery("SELECT `UserID` FROM runescape.`account` WHERE `Username` = '"+player.playerName+"' AND `Password` = PASSWORD('"+player.playerPass+"');");
		
		try {
			if(accountId.next())
			{
				int userID = accountId.getInt(1);
				
				ResultSet character_friendsList = database.runQuery("SELECT * FROM `character_friendslist` WHERE `UserID` = '"+userID+"';");
				ResultSet character_ignoreList = database.runQuery("SELECT * FROM `character_ignorelist` WHERE `UserID` = '"+userID+"';");
				ResultSet character_bankItems = database.runQuery("SELECT * FROM `character_bank` WHERE `UserID` = '"+userID+"';");
				
				player.playerName = player.playerName2;
				
				int tbTime = (int) (player.teleBlockDelay - System.currentTimeMillis() + player.teleBlockLength);
				if (tbTime > 300000 || tbTime < 0) {
					tbTime = 0;
				}
				
				String sqlstr1 = "UPDATE `character` SET "
								+"characterHeight = '"+player.heightLevel+"',"
								+"characterPosX = '"+player.absX+"',"
								+"characterPosY = '"+player.absY+"',"
								+"characterRights = '"+player.playerRights+"',"
								+"hasStarter = '"+(player.hasStarter ? 1 : 0) +"',"
								+"bankPin1 = '"+player.bankPin1+"',"
								+"bankPin2 = '"+player.bankPin2+"',"
								+"bankPin3 = '"+player.bankPin3+"',"
								+"bankPin4 = '"+player.bankPin4+"',"
								+"hasBankpin = '"+(player.hasBankpin ? 1 : 0) +"',"
								+"pinRegisteredDeleteDay = '"+player.pinDeleteDateRequested+"',"
								+"requestPinDelete = '"+(player.requestPinDelete ? 1 : 0) +"',"
								+"lastLoginDate = '"+player.lastLoginDate+"',"
								+"setPin = '"+(player.setPin ? 1 : 0) +"',"
								+"hasPaid = '"+(player.hasPaid ? 1 : 0) +"',"
								+"lostCannon = '"+(player.lostCannon ? 1 : 0) +"',"
								+"cannonX = '"+player.cannonX+"',"
								+"cannonY = '"+player.cannonY+"',"
								+"myBalls = '"+player.getCannon().myBalls+"',"
								+"poison = '"+(player.poison ? 1 : 0) +"',"
								+"spiritTree = '"+(player.spiritTree ? 1 : 0) +"',"
								+"npcCanAttack = '"+(player.npcCanAttack ? 1 : 0) +"',"
								+"rope = '"+(player.rope ? 1 : 0) +"',"
								+"rope2 = '"+(player.rope2 ? 1 : 0) +"',"
								+"recievedMask = '"+(player.recievedMask ? 1 : 0) +"',"
								+"recievedReward = '"+(player.recievedReward ? 1 : 0) +"',"
								+"isBotting = '"+(player.isBotting ? 1 : 0) +"',"
								+"brightness = '"+player.brightness+"',"
								+"closeTutorialInterface = '"+(player.closeTutorialInterface ? 1 : 0) +"',"
								+"canWalkTutorial = '"+(player.canWalkTutorial ? 1 : 0) +"',"
								+"village = '"+(player.village ? 1 : 0) +"',"
								+"lastThieve = '"+player.lastThieve+"',"
								+"homeTele = '"+player.homeTele+"',"
								+"strongHold = '"+(player.strongHold ? 1 : 0) +"',"
								+"characterEnergy = '"+player.playerEnergy+"',"
								+"crystalBowShots = '"+player.crystalBowArrowCount+"',"
								+"splitChat = '"+(player.splitChat ? 1 : 0) +"',"
								+"canSpeak = '"+(player.canSpeak ? 1 : 0) +"',"
								+"barrowsNpcs_0 = '"+player.barrowsNpcs[0][1]+"',"
								+"barrowsNpcs_1 = '"+player.barrowsNpcs[1][1]+"',"
								+"barrowsNpcs_2 = '"+player.barrowsNpcs[2][1]+"',"
								+"barrowsNpcs_3 = '"+player.barrowsNpcs[3][1]+"',"
								+"barrowsNpcs_4 = '"+player.barrowsNpcs[4][1]+"',"
								+"barrowsNpcs_5 = '"+player.barrowsNpcs[5][1]+"',"
								+"questStages = '"+player.questStages+"',"
								+"SlayerMaster = '"+player.SlayerMaster+"',"
								+"music_0 = '"+(player.getPlayList().unlocked[0] ? 1 : 0) +"',"
								+"music_1 = '"+(player.getPlayList().unlocked[1] ? 1 : 0) +"',"
								+"music_2 = '"+(player.getPlayList().unlocked[2] ? 1 : 0) +"',"
								+"music_3 = '"+(player.getPlayList().unlocked[3] ? 1 : 0) +"',"
								+"music_4 = '"+(player.getPlayList().unlocked[4] ? 1 : 0) +"',"
								+"music_5 = '"+(player.getPlayList().unlocked[5] ? 1 : 0) +"',"
								+"music_6 = '"+(player.getPlayList().unlocked[6] ? 1 : 0) +"',"
								+"music_7 = '"+(player.getPlayList().unlocked[7] ? 1 : 0) +"',"
								+"music_8 = '"+(player.getPlayList().unlocked[8] ? 1 : 0) +"',"
								+"music_9 = '"+(player.getPlayList().unlocked[9] ? 1 : 0) +"',"
								+"music_10 = '"+(player.getPlayList().unlocked[10] ? 1 : 0) +"',"
								+"music_11 = '"+(player.getPlayList().unlocked[11] ? 1 : 0) +"',"
								+"music_12 = '"+(player.getPlayList().unlocked[12] ? 1 : 0) +"',"
								+"music_13 = '"+(player.getPlayList().unlocked[13] ? 1 : 0) +"',"
								+"music_14 = '"+(player.getPlayList().unlocked[14] ? 1 : 0) +"',"
								+"music_15 = '"+(player.getPlayList().unlocked[15] ? 1 : 0) +"',"
								+"music_16 = '"+(player.getPlayList().unlocked[16] ? 1 : 0) +"',"
								+"music_17 = '"+(player.getPlayList().unlocked[17] ? 1 : 0) +"',"
								+"music_18 = '"+(player.getPlayList().unlocked[18] ? 1 : 0) +"',"
								+"music_19 = '"+(player.getPlayList().unlocked[19] ? 1 : 0) +"',"
								+"music_20 = '"+(player.getPlayList().unlocked[20] ? 1 : 0) +"',"
								+"music_21 = '"+(player.getPlayList().unlocked[21] ? 1 : 0) +"',"
								+"music_22 = '"+(player.getPlayList().unlocked[22] ? 1 : 0) +"',"
								+"music_23 = '"+(player.getPlayList().unlocked[23] ? 1 : 0) +"',"
								+"music_24 = '"+(player.getPlayList().unlocked[24] ? 1 : 0) +"',"
								+"music_25 = '"+(player.getPlayList().unlocked[25] ? 1 : 0) +"',"
								+"music_26 = '"+(player.getPlayList().unlocked[26] ? 1 : 0) +"',"
								+"music_27 = '"+(player.getPlayList().unlocked[27] ? 1 : 0) +"',"
								+"music_28 = '"+(player.getPlayList().unlocked[28] ? 1 : 0) +"',"
								+"music_29 = '"+(player.getPlayList().unlocked[29] ? 1 : 0) +"',"
								+"music_30 = '"+(player.getPlayList().unlocked[30] ? 1 : 0) +"',"
								+"music_31 = '"+(player.getPlayList().unlocked[31] ? 1 : 0) +"',"
								+"music_32 = '"+(player.getPlayList().unlocked[32] ? 1 : 0) +"',"
								+"music_33 = '"+(player.getPlayList().unlocked[33] ? 1 : 0) +"',"
								+"music_34 = '"+(player.getPlayList().unlocked[34] ? 1 : 0) +"',"
								+"music_35 = '"+(player.getPlayList().unlocked[35] ? 1 : 0) +"',"
								+"music_36 = '"+(player.getPlayList().unlocked[36] ? 1 : 0) +"',"
								+"music_37 = '"+(player.getPlayList().unlocked[37] ? 1 : 0) +"',"
								+"music_38 = '"+(player.getPlayList().unlocked[38] ? 1 : 0) +"',"
								+"music_39 = '"+(player.getPlayList().unlocked[39] ? 1 : 0) +"',"
								+"music_40 = '"+(player.getPlayList().unlocked[40] ? 1 : 0) +"',"
								+"music_41 = '"+(player.getPlayList().unlocked[41] ? 1 : 0) +"',"
								+"music_42 = '"+(player.getPlayList().unlocked[42] ? 1 : 0) +"',"
								+"music_43 = '"+(player.getPlayList().unlocked[43] ? 1 : 0) +"',"
								+"music_44 = '"+(player.getPlayList().unlocked[44] ? 1 : 0) +"',"
								+"music_45 = '"+(player.getPlayList().unlocked[45] ? 1 : 0) +"',"
								+"music_46 = '"+(player.getPlayList().unlocked[46] ? 1 : 0) +"',"
								+"music_47 = '"+(player.getPlayList().unlocked[47] ? 1 : 0) +"',"
								+"music_48 = '"+(player.getPlayList().unlocked[48] ? 1 : 0) +"',"
								+"music_49 = '"+(player.getPlayList().unlocked[49] ? 1 : 0) +"',"
								+"randomActions = '"+player.randomActions+"',"
								+"blackMarks = '"+player.blackMarks+"',"
								+"tutorialProgress = '"+player.tutorialProgress+"',"
								+"skullTimer = '"+player.skullTimer+"',"
								+"recoilHits = '"+player.recoilHits+"',"
								+"lastX = '"+player.lastX+"',"
								+"lastY = '"+player.lastY+"',"
								+"lastH = '"+player.lastH+"',"
								+"removedTask0 = '"+player.removedTasks[0]+"',"
								+"removedTask1 = '"+player.removedTasks[1]+"',"
								+"removedTask2 = '"+player.removedTasks[2]+"',"
								+"removedTask3 = '"+player.removedTasks[3]+"',"
								+"creationAddress = 'Somethingfun',"
								+"hasNpc = '"+(player.hasNpc ? 1 : 0)  +"',"
								+"summonId = '"+player.summonId+"',"
								+"thankedForDonation = '"+player.thankedForDonation+"',"
								+"membership = '"+(player.membership ? 1 : 0) +"',"
								+"questPoints = '"+player.questPoints+"',"
								+"bananas = '"+player.bananas+"',"
								+"magicBook = '"+player.playerMagicBook+"',"
								+"specialAmount = '"+player.specAmount+"',"
								+"musicOn = '"+(player.musicOn ? 1 : 0) +"',"
								+"needsNewTask = '"+(player.needsNewTask ? 1 : 0) +"',"
								+"luthas = '"+(player.luthas ? 1 : 0) +"',"
								+"selectedCoffin = '"+player.randomCoffin+"',"
								+"runeMist = '"+player.runeMist+"',"
								+"cookAss = '"+player.cookAss+"',"
								+"pirateTreasure = '"+player.pirateTreasure+"',"
								+"ptjob = '"+player.ptjob+"',"
								+"doricQuest = '"+player.doricQuest+"',"
								+"dragonSlayerQuestStage = '"+player.dragonSlayerQuestStage+"',"
								+"impsC = '"+player.impsC+"',"
								+"knightS = '"+player.knightS+"',"
								+"sheepShear = '"+player.sheepShear+"',"
								+"romeoJuliet = '"+player.romeojuliet+"',"
								+"gertCat = '"+player.gertCat+"',"
								+"cwGames = '"+player.cwGames+"',"
								+"witchspot = '"+player.witchspot+"',"
								+"restGhost = '"+player.restGhost+"',"
								+"vampSlayer = '"+player.vampSlayer+"',"
								+"RatDied2 = '"+(player.ratdied2 ? 1 : 0) +"',"
								+"teleblockLength = '"+player.teleBlockLength+"',"
								+"pcPoints = '"+player.pcPoints+"',"
								+"lastYell = '"+player.lastYell+"',"
								+"slayerTask = '"+player.slayerTask+"',"
								+"taskAmount = '"+player.taskAmount+"',"
								+"magePoints = '"+player.magePoints+"',"
								+"autoRet = '"+player.autoRet+"',"
								+"barrowsKillCount = '"+player.barrowsKillCount+"',"
								+"slayerPoints = '"+player.slayerPoints+"',"
								+"flagged = '"+(player.accountFlagged ? 1 : 0) +"',"
								+"wave = '"+player.waveId+"',"
								+"gwkc = '"+player.killCount+"',"
								+"isRunning = '"+(player.isRunning ? 1 : 0) +"',"
								+"fightMode = '"+player.fightMode+"',"
								+"void_0 = '"+player.voidStatus[0]+"',"
								+"void_1 = '"+player.voidStatus[1]+"',"
								+"void_2 = '"+player.voidStatus[2]+"',"
								+"void_3 = '"+player.voidStatus[3]+"',"
								+"void_4 = '"+player.voidStatus[4]+"' "
								+"WHERE `UserID` = "+userID+";";
				
				String sqlstr2 = "UPDATE `character_equipment` SET "
								+"slot_0 = '"+player.playerEquipment[0]+"',"
								+"slot_1 = '"+player.playerEquipment[1]+"',"
								+"slot_2 = '"+player.playerEquipment[2]+"',"
								+"slot_3 = '"+player.playerEquipment[3]+"',"
								+"slot_4 = '"+player.playerEquipment[4]+"',"
								+"slot_5 = '"+player.playerEquipment[5]+"',"
								+"slot_6 = '"+player.playerEquipment[6]+"',"
								+"slot_7 = '"+player.playerEquipment[7]+"',"
								+"slot_8 = '"+player.playerEquipment[8]+"',"
								+"slot_9 = '"+player.playerEquipment[9]+"',"
								+"slot_10 = '"+player.playerEquipment[10]+"',"
								+"slot_11 = '"+player.playerEquipment[11]+"',"
								+"slot_12 = '"+player.playerEquipment[12]+"',"
								+"slot_13 = '"+player.playerEquipment[13]+"' "
								+"WHERE `UserID` = "+userID+";";
				
				String sqlstr3 = "UPDATE `character_equipmentn` SET "
								+"slot_0 = '"+player.playerEquipmentN[0]+"',"
								+"slot_1 = '"+player.playerEquipmentN[1]+"',"
								+"slot_2 = '"+player.playerEquipmentN[2]+"',"
								+"slot_3 = '"+player.playerEquipmentN[3]+"',"
								+"slot_4 = '"+player.playerEquipmentN[4]+"',"
								+"slot_5 = '"+player.playerEquipmentN[5]+"',"
								+"slot_6 = '"+player.playerEquipmentN[6]+"',"
								+"slot_7 = '"+player.playerEquipmentN[7]+"',"
								+"slot_8 = '"+player.playerEquipmentN[8]+"',"
								+"slot_9 = '"+player.playerEquipmentN[9]+"',"
								+"slot_10 = '"+player.playerEquipmentN[10]+"',"
								+"slot_11 = '"+player.playerEquipmentN[11]+"',"
								+"slot_12 = '"+player.playerEquipmentN[12]+"',"
								+"slot_13 = '"+player.playerEquipmentN[13]+"' "
								+"WHERE `UserID` = "+userID+";";
				
				String sqlstr4 = "UPDATE `character_look` SET "
								+"look_0 = '"+player.playerAppearance[0]+"',"
								+"look_1 = '"+player.playerAppearance[1]+"',"
								+"look_2 = '"+player.playerAppearance[2]+"',"
								+"look_3 = '"+player.playerAppearance[3]+"',"
								+"look_4 = '"+player.playerAppearance[4]+"',"
								+"look_5 = '"+player.playerAppearance[5]+"',"
								+"look_6 = '"+player.playerAppearance[6]+"',"
								+"look_7 = '"+player.playerAppearance[7]+"',"
								+"look_8 = '"+player.playerAppearance[8]+"',"
								+"look_9 = '"+player.playerAppearance[9]+"',"
								+"look_10 = '"+player.playerAppearance[10]+"',"
								+"look_11 = '"+player.playerAppearance[11]+"',"
								+"look_12 = '"+player.playerAppearance[12]+"' "
								+"WHERE `UserID` = "+userID+";";
				
				String sqlstr5 = "UPDATE `character_skillslevel` SET "
								+"slot_0 = '"+player.playerLevel[0]+"',"
								+"slot_1 = '"+player.playerLevel[1]+"',"
								+"slot_2 = '"+player.playerLevel[2]+"',"
								+"slot_3 = '"+player.playerLevel[3]+"',"
								+"slot_4 = '"+player.playerLevel[4]+"',"
								+"slot_5 = '"+player.playerLevel[5]+"',"
								+"slot_6 = '"+player.playerLevel[6]+"',"
								+"slot_7 = '"+player.playerLevel[7]+"',"
								+"slot_8 = '"+player.playerLevel[8]+"',"
								+"slot_9 = '"+player.playerLevel[9]+"',"
								+"slot_10 = '"+player.playerLevel[10]+"',"
								+"slot_11 = '"+player.playerLevel[11]+"',"
								+"slot_12 = '"+player.playerLevel[12]+"',"
								+"slot_13 = '"+player.playerLevel[13]+"',"
								+"slot_14 = '"+player.playerLevel[14]+"',"
								+"slot_15 = '"+player.playerLevel[15]+"',"
								+"slot_16 = '"+player.playerLevel[16]+"',"
								+"slot_17 = '"+player.playerLevel[17]+"',"
								+"slot_18 = '"+player.playerLevel[18]+"',"
								+"slot_19 = '"+player.playerLevel[19]+"',"
								+"slot_20 = '"+player.playerLevel[20]+"',"
								+"slot_21 = '"+player.playerLevel[21]+"',"
								+"slot_22 = '"+player.playerLevel[22]+"',"
								+"slot_23 = '"+player.playerLevel[22]+"',"
								+"slot_24 = '"+player.playerLevel[22]+"' "
								+"WHERE `UserID` = "+userID+";";
				
				String sqlstr6 = "UPDATE `character_skillsxp` SET "
								+"slot_0 = '"+player.playerXP[0]+"',"
								+"slot_1 = '"+player.playerXP[1]+"',"
								+"slot_2 = '"+player.playerXP[2]+"',"
								+"slot_3 = '"+player.playerXP[3]+"',"
								+"slot_4 = '"+player.playerXP[4]+"',"
								+"slot_5 = '"+player.playerXP[5]+"',"
								+"slot_6 = '"+player.playerXP[6]+"',"
								+"slot_7 = '"+player.playerXP[7]+"',"
								+"slot_8 = '"+player.playerXP[8]+"',"
								+"slot_9 = '"+player.playerXP[9]+"',"
								+"slot_10 = '"+player.playerXP[10]+"',"
								+"slot_11 = '"+player.playerXP[11]+"',"
								+"slot_12 = '"+player.playerXP[12]+"',"
								+"slot_13 = '"+player.playerXP[13]+"',"
								+"slot_14 = '"+player.playerXP[14]+"',"
								+"slot_15 = '"+player.playerXP[15]+"',"
								+"slot_16 = '"+player.playerXP[16]+"',"
								+"slot_17 = '"+player.playerXP[17]+"',"
								+"slot_18 = '"+player.playerXP[18]+"',"
								+"slot_19 = '"+player.playerXP[19]+"',"
								+"slot_20 = '"+player.playerXP[20]+"',"
								+"slot_21 = '"+player.playerXP[21]+"',"
								+"slot_22 = '"+player.playerXP[22]+"',"
								+"slot_23 = '"+player.playerXP[22]+"',"
								+"slot_24 = '"+player.playerXP[22]+"' "
								+"WHERE `UserID` = "+userID+";";
				String sqlstr7 = "UPDATE `character_inventory` SET "
								+"slot_0 = '"+player.playerItems[0]+"',"
								+"slot_1 = '"+player.playerItems[1]+"',"
								+"slot_2 = '"+player.playerItems[2]+"',"
								+"slot_3 = '"+player.playerItems[3]+"',"
								+"slot_4 = '"+player.playerItems[4]+"',"
								+"slot_5 = '"+player.playerItems[5]+"',"
								+"slot_6 = '"+player.playerItems[6]+"',"
								+"slot_7 = '"+player.playerItems[7]+"',"
								+"slot_8 = '"+player.playerItems[8]+"',"
								+"slot_9 = '"+player.playerItems[9]+"',"
								+"slot_10 = '"+player.playerItems[10]+"',"
								+"slot_11 = '"+player.playerItems[11]+"',"
								+"slot_12 = '"+player.playerItems[12]+"',"
								+"slot_13 = '"+player.playerItems[13]+"',"
								+"slot_14 = '"+player.playerItems[14]+"',"
								+"slot_15 = '"+player.playerItems[15]+"',"
								+"slot_16 = '"+player.playerItems[16]+"',"
								+"slot_17 = '"+player.playerItems[17]+"',"
								+"slot_18 = '"+player.playerItems[18]+"',"
								+"slot_19 = '"+player.playerItems[19]+"',"
								+"slot_20 = '"+player.playerItems[20]+"',"
								+"slot_21 = '"+player.playerItems[21]+"',"
								+"slot_22 = '"+player.playerItems[22]+"',"
								+"slot_23 = '"+player.playerItems[23]+"',"
								+"slot_24 = '"+player.playerItems[24]+"',"
								+"slot_25 = '"+player.playerItems[25]+"',"
								+"slot_26 = '"+player.playerItems[26]+"',"
								+"slot_27 = '"+player.playerItems[27]+"' "
								+"WHERE `UserID` = "+userID+";";
				String sqlstr8 = "UPDATE `character_inventoryn` SET "
								+"slot_0 = '"+player.playerItemsN[0]+"',"
								+"slot_1 = '"+player.playerItemsN[1]+"',"
								+"slot_2 = '"+player.playerItemsN[2]+"',"
								+"slot_3 = '"+player.playerItemsN[3]+"',"
								+"slot_4 = '"+player.playerItemsN[4]+"',"
								+"slot_5 = '"+player.playerItemsN[5]+"',"
								+"slot_6 = '"+player.playerItemsN[6]+"',"
								+"slot_7 = '"+player.playerItemsN[7]+"',"
								+"slot_8 = '"+player.playerItemsN[8]+"',"
								+"slot_9 = '"+player.playerItemsN[9]+"',"
								+"slot_10 = '"+player.playerItemsN[10]+"',"
								+"slot_11 = '"+player.playerItemsN[11]+"',"
								+"slot_12 = '"+player.playerItemsN[12]+"',"
								+"slot_13 = '"+player.playerItemsN[13]+"',"
								+"slot_14 = '"+player.playerItemsN[14]+"',"
								+"slot_15 = '"+player.playerItemsN[15]+"',"
								+"slot_16 = '"+player.playerItemsN[16]+"',"
								+"slot_17 = '"+player.playerItemsN[17]+"',"
								+"slot_18 = '"+player.playerItemsN[18]+"',"
								+"slot_19 = '"+player.playerItemsN[19]+"',"
								+"slot_20 = '"+player.playerItemsN[20]+"',"
								+"slot_21 = '"+player.playerItemsN[21]+"',"
								+"slot_22 = '"+player.playerItemsN[22]+"',"
								+"slot_23 = '"+player.playerItemsN[23]+"',"
								+"slot_24 = '"+player.playerItemsN[24]+"',"
								+"slot_25 = '"+player.playerItemsN[25]+"',"
								+"slot_26 = '"+player.playerItemsN[26]+"',"
								+"slot_27 = '"+player.playerItemsN[27]+"' "
								+"WHERE `UserID` = "+userID+";";
				
				Statement s = MySQLDatabase.con.createStatement();
				
				int r1 = s.executeUpdate(sqlstr1); 
				if(r1 == 0)
				{
					String sql = "INSERT INTO `character` ("
							+ "`UserID`, `characterHeight`, `characterPosx`, `characterPosy`, `characterRights`, "
							+ "`hasStarter`, `bankPin1`, `bankPin2`, `bankPin3`, `bankPin4`, `hasBankpin`, `pinRegisteredDeleteDay`, "
							+ "`requestPinDelete`, `lastLoginDate`, `setPin`, `hasPaid`, `lostCannon`, `cannonX`, `cannonY`, `myBalls`, "
							+ "`poison`, `spiritTree`, `npcCanAttack`, `rope`, `rope2`, `recievedMask`, `recievedReward`, `isBotting`, "
							+ "`brightness`, `closeTutorialInterface`, `canWalkTutorial`, `village`, `lastThieve`, `homeTele`, `strongHold`, "
							+ "`characterEnergy`, `crystalBowShots`, `splitChat`, `canSpeak`, `barrowsNpcs_0`, `barrowsNpcs_1`, `barrowsNpcs_2`, "
							+ "`barrowsNpcs_3`, `barrowsNpcs_4`, `barrowsNpcs_5`, `questStages`, `SlayerMaster`, `music_0`, `music_1`, `music_2`, "
							+ "`music_3`, `music_4`, `music_5`, `music_6`, `music_7`, `music_8`, `music_9`, `music_10`, `music_11`, `music_12`, "
							+ "`music_13`, `music_14`, `music_15`, `music_16`, `music_17`, `music_18`, `music_19`, `music_20`, `music_21`, "
							+ "`music_22`, `music_23`, `music_24`, `music_25`, `music_26`, `music_27`, `music_28`, `music_29`, `music_30`, "
							+ "`music_31`, `music_32`, `music_33`, `music_34`, `music_35`, `music_36`, `music_37`, `music_38`, `music_39`, "
							+ "`music_40`, `music_41`, `music_42`, `music_43`, `music_44`, `music_45`, `music_46`, `music_47`, `music_48`, "
							+ "`music_49`, `randomActions`, `blackMarks`, `tutorialProgress`, `skullTimer`, `recoilHits`, `lastX`, `lastY`, "
							+ "`lastH`, `removedTask0`, `removedTask1`, `removedTask2`, `removedTask3`, `creationAddress`, `hasNpc`, "
							+ "`summonId`, `thankedForDonation`, `membership`, `questPoints`, `bananas`, `magicBook`, `specialAmount`, "
							+ "`musicOn`, `needsNewTask`, `luthas`, `selectedCoffin`, `runeMist`, `cookAss`, `pirateTreasure`, `ptjob`, "
							+ "`doricQuest`, `dragonSlayerQuestStage`, `impsC`, `knightS`, `sheepShear`, `romeoJuliet`, `gertCat`, `cwGames`, "
							+ "`witchspot`, `restGhost`, `vampSlayer`, `RatDied2`, `teleblockLength`, `pcPoints`, `lastYell`, `slayerTask`, "
							+ "`taskAmount`, `magePoints`, `autoRet`, `barrowsKillCount`, `slayerPoints`, `flagged`, `wave`, `gwkc`, `isRunning`, "
							+ "`fightMode`, `void_0`, `void_1`, `void_2`, `void_3`, `void_4`) "
							+ "VALUES ("
							+ "'"+userID+"', "
							+ "'"+player.heightLevel+"', "
							+ "'"+player.absX+"', "
							+ "'"+player.absY+"', "
							+ "'"+player.playerRights+"', "
							+ (player.hasStarter ? 1 : 0) +", "
							+ "'"+player.bankPin1+"', "
							+ "'"+player.bankPin2+"', "
							+ "'"+player.bankPin3+"', "
							+ "'"+player.bankPin4+"', "
							+ (player.hasBankpin ? 1 : 0) +", "
							+ "'"+player.deletePinDate+"', "
							+ (player.requestPinDelete ? 1 : 0) +", "
							+ "'"+player.lastLoginDate+"', "
							+ (player.setPin ? 1 : 0) +", "
							+ (player.hasPaid ? 1 : 0) +", "
							+ (player.lostCannon ? 1 : 0) +", "
							+ "'"+player.cannonX+"', "
							+ "'"+player.cannonY+"', "
							+ "'"+player.getCannon().myBalls+"', "
							+ (player.poison ? 1 : 0) +", "
							+ (player.spiritTree ? 1 : 0) +", "
							+ (player.npcCanAttack ? 1 : 0) +", "
							+ (player.rope ? 1 : 0) +", "
							+ (player.rope2 ? 1 : 0) +", "
							+ (player.recievedMask ? 1 : 0) +", "
							+ (player.recievedReward ? 1 : 0) +", "
							+ (player.isBotting ? 1 : 0) +", "
							+ "'"+player.brightness+"', "
							+ (player.closeTutorialInterface ? 1 : 0) +", "
							+ (player.canWalkTutorial ? 1 : 0) +", "
							+ (player.village ? 1 : 0) +", "
							+ "'"+player.lastThieve+"', "
							+ "'"+player.homeTele+"', "
							+ (player.strongHold ? 1 : 0) +", "
							+ "'"+player.playerEnergy+"', "
							+ "'"+player.crystalBowArrowCount+"', "
							+ (player.splitChat ? 1 : 0) +", "
							+ (player.canSpeak ? 1 : 0) +", "
							+ "'"+player.barrowsNpcs[0][1]+"', "
							+ "'"+player.barrowsNpcs[1][1]+"', "
							+ "'"+player.barrowsNpcs[2][1]+"', "
							+ "'"+player.barrowsNpcs[3][1]+"', "
							+ "'"+player.barrowsNpcs[4][1]+"', "
							+ "'"+player.barrowsNpcs[5][1]+"', "
							+ "'"+player.questStages+"', "
							+ "'"+player.SlayerMaster+"', "
							+ player.getPlayList().unlocked[0]+", "
							+ player.getPlayList().unlocked[1]+", "
							+ player.getPlayList().unlocked[2]+", "
							+ player.getPlayList().unlocked[3]+", "
							+ player.getPlayList().unlocked[4]+", "
							+ player.getPlayList().unlocked[5]+", "
							+ player.getPlayList().unlocked[6]+", "
							+ player.getPlayList().unlocked[7]+", "
							+ player.getPlayList().unlocked[8]+", "
							+ player.getPlayList().unlocked[9]+", "
							+ player.getPlayList().unlocked[10]+", "
							+ player.getPlayList().unlocked[11]+", "
							+ player.getPlayList().unlocked[12]+", "
							+ player.getPlayList().unlocked[13]+", "
							+ player.getPlayList().unlocked[14]+", "
							+ player.getPlayList().unlocked[15]+", "
							+ player.getPlayList().unlocked[16]+", "
							+ player.getPlayList().unlocked[17]+", "
							+ player.getPlayList().unlocked[18]+", "
							+ player.getPlayList().unlocked[19]+", "
							+ player.getPlayList().unlocked[20]+", "
							+ player.getPlayList().unlocked[21]+", "
							+ player.getPlayList().unlocked[22]+", "
							+ player.getPlayList().unlocked[23]+", "
							+ player.getPlayList().unlocked[24]+", "
							+ player.getPlayList().unlocked[25]+", "
							+ player.getPlayList().unlocked[26]+", "
							+ player.getPlayList().unlocked[27]+", "
							+ player.getPlayList().unlocked[28]+", "
							+ player.getPlayList().unlocked[29]+", "
							+ player.getPlayList().unlocked[30]+", "
							+ player.getPlayList().unlocked[31]+", "
							+ player.getPlayList().unlocked[32]+", "
							+ player.getPlayList().unlocked[33]+", "
							+ player.getPlayList().unlocked[34]+", "
							+ player.getPlayList().unlocked[35]+", "
							+ player.getPlayList().unlocked[36]+", "
							+ player.getPlayList().unlocked[37]+", "
							+ player.getPlayList().unlocked[38]+", "
							+ player.getPlayList().unlocked[39]+", "
							+ player.getPlayList().unlocked[40]+", "
							+ player.getPlayList().unlocked[41]+", "
							+ player.getPlayList().unlocked[42]+", "
							+ player.getPlayList().unlocked[43]+", "
							+ player.getPlayList().unlocked[44]+", "
							+ player.getPlayList().unlocked[45]+", "
							+ player.getPlayList().unlocked[46]+", "
							+ player.getPlayList().unlocked[47]+", "
							+ player.getPlayList().unlocked[48]+", "
							+ player.getPlayList().unlocked[49]+", "
							+ "'"+player.randomActions+"', "
							+ "'"+player.blackMarks+"', "
							+ "'"+player.tutorialProgress+"', "
							+ "'"+player.skullTimer+"', "
							+ "'"+player.recoilHits+"', "
							+ "'"+player.lastX+"', "
							+ "'"+player.lastY+"', "
							+ "'"+player.lastH+"', "
							+ "'"+player.removedTasks[0]+"', "
							+ "'"+player.removedTasks[1]+"', "
							+ "'"+player.removedTasks[2]+"', "
							+ "'"+player.removedTasks[3]+"', "
							+ "'creationAddress', "
							+ (player.hasNpc ? 1 : 0) +", "
							+ "'"+player.summonId+"', "
							+ "'"+player.thankedForDonation+"', "
							+ (player.membership ? 1 : 0) +", "
							+ "'"+player.questPoints+"', "
							+ "'"+player.bananas+"', "
							+ "'"+player.playerMagicBook+"', "
							+ "'"+player.specAmount+"', "
							+ (player.musicOn ? 1 : 0) +", "
							+ (player.needsNewTask ? 1 : 0) +", "
							+ (player.luthas ? 1 : 0) +", "
							+ "'"+player.randomCoffin+"', "
							+ "'"+player.runeMist+"', "
							+ "'"+player.cookAss+"', "
							+ "'"+player.pirateTreasure+"', "
							+ "'"+player.ptjob+"', "
							+ "'"+player.doricQuest+"', "
							+ "'"+player.dragonSlayerQuestStage+"', "
							+ "'"+player.impsC+"', "
							+ "'"+player.knightS+"', "
							+ "'"+player.sheepShear+"', "
							+ "'"+player.romeojuliet+"', "
							+ "'"+player.gertCat+"', "
							+ "'"+player.cwGames+"', "
							+ "'"+player.witchspot+"', "
							+ "'"+player.restGhost+"', "
							+ "'"+player.vampSlayer+"', "
							+ (player.ratdied2 ? 1 : 0) +", "
							+ "'"+player.teleBlockLength+"', "
							+ "'"+player.pcPoints+"', "
							+ "'"+player.lastYell+"', "
							+ "'"+player.slayerTask+"', "
							+ "'"+player.taskAmount+"', "
							+ "'"+player.magePoints+"', "
							+ "'"+player.autoRet+"', "
							+ "'"+player.barrowsKillCount+"', "
							+ "'"+player.slayerPoints+"', "
							+ (player.accountFlagged ? 1 : 0) +", "
							+ "'"+player.waveId+"', "
							+ "'"+player.killCount+"', "
							+ (player.isRunning ? 1 : 0) +", "
							+ "'"+player.fightMode+"', "
							+ "'"+player.voidStatus[0]+"', "
							+ "'"+player.voidStatus[1]+"', "
							+ "'"+player.voidStatus[2]+"', "
							+ "'"+player.voidStatus[3]+"', "
							+ "'"+player.voidStatus[4]+"');";
					
					boolean r = s.execute(sql); 
					
					if(!r)
						System.out.println("FAILED!");
				}
				
				int r2 = s.executeUpdate(sqlstr2); 
				if(r2 == 0)
				{
					String sql = "INSERT INTO `character_equipment` ("
							+ "`UserID`, `slot_0`, `slot_1`, `slot_2`, `slot_3`, "
							+ "`slot_4`, `slot_5`, `slot_6`, `slot_7`, `slot_8`, "
							+ "`slot_9`, `slot_10`, `slot_11`, `slot_12`, `slot_13`)"
							+ " VALUES ("
							+ "'"+userID+"', "
							+ player.playerEquipment[0] +", "
							+ player.playerEquipment[1] +", "
							+ player.playerEquipment[2] +", "
							+ player.playerEquipment[3] +", "
							+ player.playerEquipment[4] +", "
							+ player.playerEquipment[5] +", "
							+ player.playerEquipment[6] +", "
							+ player.playerEquipment[7] +", "
							+ player.playerEquipment[8] +", "
							+ player.playerEquipment[9] +", "
							+ player.playerEquipment[0] +", "
							+ player.playerEquipment[10] +", "
							+ player.playerEquipment[11] +", "
							+ player.playerEquipment[12] +");";
				
					
					boolean r = s.execute(sql); 
					if(!r)
						System.out.println("FAILED!");
				}
				
				int r3 = s.executeUpdate(sqlstr3); 
				if(r3 == 0)
				{
					String sql = "INSERT INTO `character_equipmentn` ("
							+ "`UserID`, `slot_0`, `slot_1`, `slot_2`, `slot_3`, "
							+ "`slot_4`, `slot_5`, `slot_6`, `slot_7`, `slot_8`, "
							+ "`slot_9`, `slot_10`, `slot_11`, `slot_12`, `slot_13`)"
							+ " VALUES ("
							+ "'"+userID+"', "
							+ player.playerEquipmentN[0] +", "
							+ player.playerEquipmentN[1] +", "
							+ player.playerEquipmentN[2] +", "
							+ player.playerEquipmentN[3] +", "
							+ player.playerEquipmentN[4] +", "
							+ player.playerEquipmentN[5] +", "
							+ player.playerEquipmentN[6] +", "
							+ player.playerEquipmentN[7] +", "
							+ player.playerEquipmentN[8] +", "
							+ player.playerEquipmentN[9] +", "
							+ player.playerEquipmentN[10] +", "
							+ player.playerEquipmentN[11] +", "
							+ player.playerEquipmentN[12] +", "
							+ player.playerEquipmentN[13] +");";
				
					
					boolean r = s.execute(sql); 
					if(!r)
						System.out.println("FAILED!");
				}
				
				int r4 = s.executeUpdate(sqlstr4); 
				if(r4 == 0)
				{
					String sql = "INSERT INTO `character_look` ("
							+ "`UserID`, `look_0`, `look_1`, `look_2`, "
							+ "`look_3`, `look_4`, `look_5`, `look_6`, "
							+ "`look_7`, `look_8`, `look_9`, `look_10`, "
							+ "`look_11`, `look_12`"
							+ ") VALUES ("
							+ "'"+userID+"', "
							+ player.playerAppearance[0] +","
							+ player.playerAppearance[1] +","
							+ player.playerAppearance[2] +","
							+ player.playerAppearance[3] +","
							+ player.playerAppearance[4] +", "
							+ player.playerAppearance[5] +", "
							+ player.playerAppearance[6] +", "
							+ player.playerAppearance[7] +", "
							+ player.playerAppearance[8] +", "
							+ player.playerAppearance[9] +", "
							+ player.playerAppearance[10] +", "
							+ player.playerAppearance[11] +", "
							+ player.playerAppearance[12] +");";
					
					boolean r = s.execute(sql); 
					if(!r)
						System.out.println("FAILED!");
				}
				
				int r5 = s.executeUpdate(sqlstr5);
				if(r5 == 0)
				{
					// skills level
					String sql = "INSERT INTO `character_skillslevel` ("
							+ "`UserID`, `slot_0`, `slot_1`, `slot_2`, `slot_3`, "
							+ "`slot_4`, `slot_5`, `slot_6`, `slot_7`, `slot_8`, "
							+ "`slot_9`, `slot_10`, `slot_11`, `slot_12`, `slot_13`, "
							+ "`slot_14`, `slot_15`, `slot_16`, `slot_17`, `slot_18`, "
							+ "`slot_19`, `slot_20`, `slot_21`, `slot_22`, `slot_23`, `slot_24`) "
							+ "VALUES ("
							+ "'"+userID+"', "
							+ "'"+player.playerLevel[0]+"', "
							+ "'"+player.playerLevel[1]+"', "
							+ "'"+player.playerLevel[2]+"', "
							+ "'"+player.playerLevel[3]+"', "
							+ "'"+player.playerLevel[4]+"', "
							+ "'"+player.playerLevel[5]+"', "
							+ "'"+player.playerLevel[6]+"', "
							+ "'"+player.playerLevel[7]+"', "
							+ "'"+player.playerLevel[8]+"', "
							+ "'"+player.playerLevel[9]+"', "
							+ "'"+player.playerLevel[10]+"', "
							+ "'"+player.playerLevel[11]+"', "
							+ "'"+player.playerLevel[12]+"', "
							+ "'"+player.playerLevel[13]+"', "
							+ "'"+player.playerLevel[14]+"', "
							+ "'"+player.playerLevel[15]+"', "
							+ "'"+player.playerLevel[16]+"', "
							+ "'"+player.playerLevel[17]+"', "
							+ "'"+player.playerLevel[18]+"', "
							+ "'"+player.playerLevel[19]+"', "
							+ "'"+player.playerLevel[20]+"', "
							+ "'"+player.playerLevel[21]+"', "
							+ "'"+player.playerLevel[22]+"', "
							+ "'"+player.playerLevel[23]+"', "
							+ "'"+player.playerLevel[24]+"')";
					
					boolean r = s.execute(sql); 
					if(!r)
						System.out.println("FAILED!");
				}
				
				int r6 = s.executeUpdate(sqlstr6); 
				if(r6 == 0)
				{
					// skills xp
					String sql = "INSERT INTO `character_skillsxp` ("
							+ "`UserID`, `slot_0`, `slot_1`, `slot_2`, `slot_3`, "
							+ "`slot_4`, `slot_5`, `slot_6`, `slot_7`, `slot_8`, "
							+ "`slot_9`, `slot_10`, `slot_11`, `slot_12`, `slot_13`, "
							+ "`slot_14`, `slot_15`, `slot_16`, `slot_17`, `slot_18`, "
							+ "`slot_19`, `slot_20`, `slot_21`, `slot_22`, `slot_23`, `slot_24`) "
							+ "VALUES ("
							+ "'"+userID+"', "
							+ "'"+player.playerXP[0]+"', "
							+ "'"+player.playerXP[1]+"', "
							+ "'"+player.playerXP[2]+"', "
							+ "'"+player.playerXP[3]+"', "
							+ "'"+player.playerXP[4]+"', "
							+ "'"+player.playerXP[5]+"', "
							+ "'"+player.playerXP[6]+"', "
							+ "'"+player.playerXP[7]+"', "
							+ "'"+player.playerXP[8]+"', "
							+ "'"+player.playerXP[9]+"', "
							+ "'"+player.playerXP[10]+"', "
							+ "'"+player.playerXP[11]+"', "
							+ "'"+player.playerXP[12]+"', "
							+ "'"+player.playerXP[13]+"', "
							+ "'"+player.playerXP[14]+"', "
							+ "'"+player.playerXP[15]+"', "
							+ "'"+player.playerXP[16]+"', "
							+ "'"+player.playerXP[17]+"', "
							+ "'"+player.playerXP[18]+"', "
							+ "'"+player.playerXP[19]+"', "
							+ "'"+player.playerXP[20]+"', "
							+ "'"+player.playerXP[21]+"', "
							+ "'"+player.playerXP[22]+"', "
							+ "'"+player.playerXP[23]+"', "
							+ "'"+player.playerXP[24]+"')";
					
					boolean r = s.execute(sql); 
					if(!r)
						System.out.println("FAILED!");
				}
				
				int r7 = s.executeUpdate(sqlstr7); 
				if(r7 == 0)
				{
					// inventory
					String sql = "INSERT INTO `character_inventory` ("
							+ "`UserID`, `slot_0`, `slot_1`, `slot_2`, `slot_3`, `slot_4`, "
							+ "`slot_5`, `slot_6`, `slot_7`, `slot_8`, `slot_9`, `slot_10`, "
							+ "`slot_11`, `slot_12`, `slot_13`, `slot_14`, `slot_15`, `slot_16`, "
							+ "`slot_17`, `slot_18`, `slot_19`, `slot_20`, `slot_21`, `slot_22`, "
							+ "`slot_23`, `slot_24`, `slot_25`, `slot_26`, `slot_27`) "
							+ "VALUES ("
							+ "'"+userID+"', "
							+ "'"+player.playerItems[0]+"', "
							+ "'"+player.playerItems[1]+"', "
							+ "'"+player.playerItems[2]+"', "
							+ "'"+player.playerItems[3]+"', "
							+ "'"+player.playerItems[4]+"', "
							+ "'"+player.playerItems[5]+"', "
							+ "'"+player.playerItems[6]+"',"
							+ "'"+player.playerItems[7]+"', "
							+ "'"+player.playerItems[8]+"', "
							+ "'"+player.playerItems[9]+"', "
							+ "'"+player.playerItems[10]+"', "
							+ "'"+player.playerItems[11]+"', "
							+ "'"+player.playerItems[12]+"', "
							+ "'"+player.playerItems[13]+"', "
							+ "'"+player.playerItems[14]+"', "
							+ "'"+player.playerItems[15]+"', "
							+ "'"+player.playerItems[16]+"', "
							+ "'"+player.playerItems[17]+"', "
							+ "'"+player.playerItems[18]+"', "
							+ "'"+player.playerItems[19]+"', "
							+ "'"+player.playerItems[20]+"', "
							+ "'"+player.playerItems[21]+"', "
							+ "'"+player.playerItems[22]+"', "
							+ "'"+player.playerItems[23]+"', "
							+ "'"+player.playerItems[24]+"', "
							+ "'"+player.playerItems[25]+"', "
							+ "'"+player.playerItems[26]+"', "
							+ "'"+player.playerItems[27]+"')";
					boolean r = s.execute(sql); 
					if(!r)
						System.out.println("FAILED!");
				}
				
				int r8 = s.executeUpdate(sqlstr8); 
				if(r8 == 0)
				{
					// inventory amount
					// skills inventory
					String sql = "INSERT INTO `character_inventoryn` ("
							+ "`UserID`, `slot_0`, `slot_1`, `slot_2`, `slot_3`, `slot_4`, "
							+ "`slot_5`, `slot_6`, `slot_7`, `slot_8`, `slot_9`, `slot_10`, "
							+ "`slot_11`, `slot_12`, `slot_13`, `slot_14`, `slot_15`, `slot_16`, "
							+ "`slot_17`, `slot_18`, `slot_19`, `slot_20`, `slot_21`, `slot_22`, "
							+ "`slot_23`, `slot_24`, `slot_25`, `slot_26`, `slot_27`) "
							+ "VALUES ("
							+ "'"+userID+"', "
							+ "'"+player.playerItemsN[0]+"', "
							+ "'"+player.playerItemsN[1]+"', "
							+ "'"+player.playerItemsN[2]+"', "
							+ "'"+player.playerItemsN[3]+"', "
							+ "'"+player.playerItemsN[4]+"', "
							+ "'"+player.playerItemsN[5]+"', "
							+ "'"+player.playerItemsN[6]+"', "
							+ "'"+player.playerItemsN[7]+"', "
							+ "'"+player.playerItemsN[8]+"', "
							+ "'"+player.playerItemsN[9]+"', "
							+ "'"+player.playerItemsN[10]+"', "
							+ "'"+player.playerItemsN[11]+"', "
							+ "'"+player.playerItemsN[12]+"', "
							+ "'"+player.playerItemsN[13]+"', "
							+ "'"+player.playerItemsN[14]+"', "
							+ "'"+player.playerItemsN[15]+"', "
							+ "'"+player.playerItemsN[16]+"', "
							+ "'"+player.playerItemsN[17]+"', "
							+ "'"+player.playerItemsN[18]+"', "
							+ "'"+player.playerItemsN[19]+"', "
							+ "'"+player.playerItemsN[20]+"', "
							+ "'"+player.playerItemsN[21]+"', "
							+ "'"+player.playerItemsN[22]+"', "
							+ "'"+player.playerItemsN[23]+"', "
							+ "'"+player.playerItemsN[24]+"', "
							+ "'"+player.playerItemsN[25]+"', "
							+ "'"+player.playerItemsN[26]+"', "
							+ "'"+player.playerItemsN[27]+"');";
					boolean r = s.execute(sql); 
					if(!r)
						System.out.println("FAILED!");
				}
				
				
				boolean[] slotUpdated = new boolean[player.bankItems.length];
				
				for(int i = 0; i < player.bankItems.length; i++)
					slotUpdated[i] = false;
				
				// Update slots if they are already listed in the database
				while(character_bankItems.next())
				{
					int slotNum = character_bankItems.getInt(2);
					int itemID = player.bankItems[slotNum];
					
					if(itemID != player.bankItems[slotNum] && itemID > 0)
					{
						String sql = "UPDATE `character_bank` SET "
								   	+"`slotNum` = '"+slotNum+"',"
								   	+"`ItemID` = '"+(itemID == 0 ? "NULL" : itemID) +"',"
								   	+"`ItemAmount` = '"+(itemID == 0 ? 0 : player.bankItemsN[slotNum])+"' "
								   	+"WHERE UserID = '"+userID+"';";
						
						int r9 = s.executeUpdate(sql);
						
						if(r9 > 0)
						{
							slotUpdated[slotNum] = true;
							System.out.println("Updated bank slot: "+slotNum+" id: "+itemID);
						}
					}
				}
				for (int i = 0; i < player.bankItems.length; i++)
				{
					if (player.bankItems[i] > 0 && !slotUpdated[i]) 
					{
						int slotNum = i;
						int itemID = player.bankItems[slotNum];
						int itemAmount = player.bankItemsN[slotNum];
						
						String sql = "INSERT INTO `character_bank` (`UserID`, `slotNum`, `ItemID`, `ItemAmount`) "
									+"VALUES ("
									+"'"+userID+"', "
									+"'"+slotNum+"', "
									+"'"+itemID+"', "
									+"'"+itemAmount+"');";
					
						boolean rs9 = s.execute(sql);
					
						if(!rs9)
						{
							slotUpdated[slotNum] = true;
							System.out.println("Added bank slot: "+slotNum+" id: "+itemID);
						}
					}
				}
				
				boolean[] friendSlotUpdated = new boolean[player.friends.length];
				
				for(int i = 0; i < player.friends.length; i++)
					friendSlotUpdated[i] = false;
				
				int i = 0;
				while(character_friendsList.next())
				{
					long friendID = character_friendsList.getLong(2);
					
					if(friendID != player.friends[i] && player.friends[i] > 0)
					{
						String sql = "UPDATE `character_friendslist` SET "
							   	+"`FriendID` = '"+friendID+"' "
							   	+"WHERE UserID = '"+userID+"';";
					
						int rs9 = s.executeUpdate(sql);
					
						if(rs9 > 0)
						{
							friendSlotUpdated[i] = true;
							System.out.println("Updated friend: "+friendID);
						}
					}
					else
					{
						String sql = "DELETE FROM `character_friendslist` "
									+"WHERE `FriendID` = '"+friendID+"';";
					
						ResultSet rs9 = database.runQuery(sql);
					
						if(rs9.next())
						{
							friendSlotUpdated[i] = true;
							System.out.println("Deleted friend: "+friendID);
						}
					}
					i++;
				}
				
				for (i = 0; i < player.friends.length; i++)
				{
					if (player.friends[i] > 0 && !friendSlotUpdated[i]) 
					{
						long friendID = player.friends[i];
						
						String sql = "INSERT INTO `character_friendslist` (`UserID`, `FriendID`) "
									+"VALUES ("
									+"'"+userID+"', "
									+"'"+friendID+"');";
					
						boolean rs9 = s.execute(sql);
					
						if(rs9)
						{
							friendSlotUpdated[i] = true;
							System.out.println("Added friend: "+friendID);
						}
					}
				}
				
				boolean[] ignoreSlotUpdated = new boolean[player.ignores.length];
				
				for(i = 0; i < player.ignores.length; i++)
					ignoreSlotUpdated[i] = false;
				
				while(character_ignoreList.next())
				{
					long ignoreID = character_ignoreList.getLong(2);
					
					if(ignoreID != player.friends[i] && player.friends[i] > 0)
					{
						String sql = "UPDATE `character_ignorelist` SET "
							   	+"`IgnoredPlayerID` = '"+ignoreID+"' "
							   	+"WHERE UserID = '"+userID+"';";
					
						int rs9 = s.executeUpdate(sql);
					
						if(rs9 > 0)
						{
							ignoreSlotUpdated[i] = true;
							System.out.println("Updated enemy: "+ignoreID);
						}
					}
					else
					{
						String sql = "DELETE FROM `character_ignorelist` "
									+"WHERE `IgnoredPlayerID` = '"+ignoreID+"';";
					
						ResultSet rs9 = database.runQuery(sql);
					
						if(rs9.next())
						{
							ignoreSlotUpdated[i] = true;
							System.out.println("Deleted enemy: "+ignoreID);
						}
					}
					i++;
				}
				
				for (i = 0; i < player.ignores.length; i++)
				{
					if (player.ignores[i] > 0 && !ignoreSlotUpdated[i]) 
					{
						long ignoreID = player.ignores[i];
						
						String sql = "INSERT INTO `character_ignorelist` (`UserID`, `IgnoredPlayerID`) "
									+"VALUES ("
									+"'"+userID+"', "
									+"'"+ignoreID+"');";
					
						boolean rs9 = s.execute(sql);
					
						if(rs9)
						{
							ignoreSlotUpdated[i] = true;
							System.out.println("Added enemy: "+ignoreID);
						}
					}
				}
				
				if(r1 > 0 && r2 > 0 && r3 > 0 && r4 > 0 && r5 > 0 && r6 > 0 && r7 > 0 && r8 > 0)
				{
					return true;
				}
			}
			else
			{
				return false;
			}
		}
		catch(SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
