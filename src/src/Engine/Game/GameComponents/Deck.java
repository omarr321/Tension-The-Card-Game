package Engine.Game.GameComponents;

import java.util.ArrayList;
import java.util.UUID;

import Helper.Utility;

public class Deck {
    private final UUID uuid = UUID.randomUUID();
    private UUID parentUUID;

    private ArrayList<Card> deck = new ArrayList<>();

    public Deck(UUID parentUUID) {this.parentUUID = parentUUID;}
    public Deck(UUID parentUUID, ArrayList<Card> deck) {this.deck = deck; this.parentUUID = parentUUID;}

    public void addCardToTop(Card card) {
        this.deck.addFirst(card);
    }
    public void addCardToBottom(Card card) {
        this.deck.addLast(card);
    }
    public void addCardRandom(Card card) {this.deck.add(Utility.getRandInt(0, this.deck.size()-1), card);}

    public Card drawTop() {
        return this.deck.removeFirst();
    };

    public Card drawRandom() {
        int max = this.deck.size()-1;
        return this.deck.remove(Utility.getRandInt(0, max));
    }

    public void shuffle() {
        shuffle(150);
    }
    public void shuffle(int count) {
        int max = this.deck.size()-1;
        for (int i = 0; i < count; i++) {
           swap(Utility.getRandInt(0, max), Utility.getRandInt(0, max));
        }
    }

    private void swap(int pos1, int pos2) {
        Card temp = this.deck.get(pos1);
        this.deck.set(pos1, this.deck.get(pos2));
        this.deck.set(pos2, temp);
    }

    public UUID getUuid() {return this.uuid;}
    public UUID getParentUUID() {return this.parentUUID;}

    public void setParentUUID(UUID uuid) {this.parentUUID = uuid;}
}
