package game.util.server;

import game.enemy.EnemyPlayer;
import game.player.Player;
import java.util.ArrayList;
import org.newdawn.slick.SlickException;

public class DataPacket {

    private byte[] data;
    private volatile boolean update = false;
    
    private static ArrayList<DataPacket> packets = new ArrayList<DataPacket>();
    
    public static final int MAX_SIZE = 12;
    public static ArrayList<EnemyPlayer> enemies = new ArrayList<EnemyPlayer>();
    public static Player player;
    
    public DataPacket() {
        data = new byte[MAX_SIZE];
        update = true;
        packets.add(this);
    }
    
    public DataPacket(byte[] data) {
        this.data = data;
    }
    
    public static void update(ArrayList<EnemyPlayer> newEnemies, Player newPlayer) {
        ArrayList<DataPacket> temp = (ArrayList<DataPacket>) packets.clone();
        for (DataPacket packet : temp) {
            packet.update();
        }
        
        newEnemies.clear();
        newEnemies.addAll(enemies);
        player = newPlayer;
    }
    
    public void update() {
        if (!update)
            return;
        update = false;
        
        for (EnemyPlayer e : enemies) {
            if (e.getId() == get(8)) {
                e.setX(get(0));
                e.setY(get(4));
                System.out.println("Updated an enemy.");
                return;
            }
        }
        try {
            enemies.add(new EnemyPlayer(get(0),get(4),get(8)));
            System.out.println("Created new enemy.");
        } catch (SlickException e) {
            System.out.println("Unable to create enemy player: " + e);
        }
        packets.remove(this);
    }
    
    public void add(int i, int pos) {
        data[pos] = (byte) (i >> 24);
        data[pos+1] = (byte) (i >> 16);
        data[pos+2] = (byte) (i >> 8);
        data[pos+3] = (byte) (i);
    }
    
    public int get(int pos) {
        int ret = 0;
        for (int i=0; i<4; i++) {
            ret <<= 8;
            ret |= (int)data[i+pos] & 0xFF;
        }
        return ret;
    }
    
    public byte[] getBytes() {
        return data;
    }

    public void updateEnemy() {
        update = true;
    }
}
