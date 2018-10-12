package redone.net.packets;

import redone.game.players.Client;

public interface PacketType
{
	public static final int FIRST_CLICK = 132, SECOND_CLICK = 252, THIRD_CLICK = 70, FOURTH_CLICK = 234;
	public static final int ATTACK_PLAYER = 73, MAGE_PLAYER = 249;
	public static final int PART1 = 135;
	public static final int PART2 = 208;
	public static final int CHALLENGE = 128;
	
	public void processPacket(Client c, int packetType, int packetSize);
}
