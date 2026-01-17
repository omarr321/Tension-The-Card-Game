package Engine.Game;

import java.util.UUID;

public class Board {
    private UUID uuid = UUID.randomUUID();

    private Player playerOne;
    private Player playerTwo;
    private int ropeValue;
    private int ropeDist;

    public Board(String playerOneName, String playerTwoName) { this(playerOneName, playerTwoName, 0, 15);}
    public Board(String playerOneName, String playerTwoName, int ropeValue, int ropeDist) {
        this.playerOne = new Player(uuid, playerOneName);
        this.playerTwo = new Player(uuid, playerTwoName);

        this.ropeValue = ropeValue;
        this.ropeDist = ropeDist;
    }

    //getters

    public UUID getUuid() {return uuid;}
    public Player getPlayer(String name) {
        if (this.playerOne.getName().equals(name)) {
            return this.playerOne;
        } else if (this.playerTwo.getName().equals(name)) {
            return this.playerTwo;
        } else {
            return null;
        }
    }
    public Player getPlayer(UUID uuid) {
        if (this.playerOne.getUuid().equals(uuid)) {
            return this.playerOne;
        } else if (this.playerTwo.getUuid().equals(uuid)) {
            return this.playerTwo;
        } else {
            return null;
        }
    }
    public int getRopeValue() {return ropeValue;}
    public int getRopeDist() {return ropeDist;}
}
