package Engine.Game;

import Engine.Game.GameComponents.Deck;

import java.util.UUID;

public class Player {
    private final UUID uuid = UUID.randomUUID();
    private UUID parentUUID;

    private String name;

    private Deck deck;
    private Deck graveyard;
    private Deck hand;
    private Deck exile;
    private Deck board;
    private Deck trap;

    public Player(UUID parentUUID, String name) {this(parentUUID, name, false);}
    public Player(UUID parentUUID, String name, boolean noEmptyDecks) {
        this.parentUUID = parentUUID;
        this.name = name;

        if(!noEmptyDecks) {
            this.createEmptyDeck(DECK_TYPE.GRAVEYARD);
            this.createEmptyDeck(DECK_TYPE.HAND);
            this.createEmptyDeck(DECK_TYPE.EXILE);
            this.createEmptyDeck(DECK_TYPE.BOARD);
            this.createEmptyDeck(DECK_TYPE.TRAP);
        }
    }

    public void createEmptyDeck(DECK_TYPE type) {
        Deck temp = new Deck(this.uuid);
        switch (type) {
            case DECK:
                this.deck = temp;
            case GRAVEYARD:
                this.graveyard = temp;
            case HAND:
                this.hand = temp;
            case EXILE:
                this.exile = temp;
            case BOARD:
                this.board = temp;
            case TRAP:
                this.trap = temp;
        };
    }

    //Getters
    public UUID getUuid() {return this.uuid;}
    public UUID getParentUUID() {return this.parentUUID;}

    public String getName() {return this.name;}
    public Deck getDeck(DECK_TYPE type) {
        return switch (type) {
            case DECK -> this.deck;
            case GRAVEYARD -> this.graveyard;
            case HAND -> this.hand;
            case EXILE -> this.exile;
            case BOARD -> this.board;
            case TRAP -> this.trap;
            default -> null;
        };
    }
    public Deck getDeck(UUID uuid) {
        if (this.deck.getUuid().equals(uuid)) {
            return this.deck;
        } else if (this.graveyard.getUuid().equals(uuid)) {
            return this.graveyard;
        } else if (this.hand.getUuid().equals(uuid)) {
            return this.hand;
        } else if (this.exile.getUuid().equals(uuid)) {
            return this.exile;
        } else if (this.board.getUuid().equals(uuid)) {
            return this.board;
        } else if (this.trap.getUuid().equals(uuid)) {
            return this.trap;
        } else {
            return null;
        }
    }

    //Setters
    public void setParentUUID(UUID parentUUID) {this.parentUUID = parentUUID;}

    public void setName(String name) {this.name = name;}
    public void setDeck(Deck deck, DECK_TYPE type) {
        switch (type) {
            case DECK:
                this.deck = deck;
                break;
            case GRAVEYARD:
                this.graveyard = deck;
                break;
            case HAND:
                this.hand = deck;
                break;
            case EXILE:
                this.exile = deck;
                break;
            case BOARD:
                this.board = deck;
                break;
            case TRAP:
                this.trap = deck;
        };
    }

    public enum DECK_TYPE {
        DECK,
        GRAVEYARD,
        HAND,
        EXILE,
        BOARD,
        TRAP
    }
}
