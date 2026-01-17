package Engine.Game.GameComponents;

import java.util.UUID;

/**
This class holds the card data for a card. When it gets loaded from disk, it stores it in the db so that it can be pulled with ease.
 @author Omar R.
 @version 1.0.0
 */
public class Card {
    private final UUID uuid = UUID.randomUUID();
    private UUID parentUUID;

    //Card Info
    private String name;
    private String desc;
    private String flavorText;
    //TODO: Add art path as a variable

    //Card Data
    private String id;
    private CARD_TYPE cardType;
    private CARD_SUBTYPE cardSubtype;
    private String set;
    private RARITY rarity;

    //Card Values
    private int gripPower;
    private int pullPower;
    private int rallyCost;

    public Card(UUID parentUUID, String name, String desc, String flavorText, String id, CARD_TYPE type, CARD_SUBTYPE subtype, String set, RARITY rarity, int gripPower, int pullPower, int rallyCost) {
        this.parentUUID = parentUUID;

        this.name = name;
        this.desc = desc;
        this.flavorText = flavorText;

        this.id = id;
        this.cardType = type;
        this.cardSubtype = subtype;
        this.set = set;
        this.rarity = rarity;

        this.gripPower = gripPower;
        this.pullPower = pullPower;
        this.rallyCost = rallyCost;
    }

    //Getters


    public UUID getUuid() {return uuid;}
    public UUID getParentUUID() {return parentUUID;}

    public String getName() {return this.name;}
    public String getDesc() {return this.desc;}
    public String getFlavorText() {return this.flavorText;}

    public String getId() {return this.id;}
    public CARD_TYPE getCardType() {return this.cardType;}
    public CARD_SUBTYPE getCardSubtype() {return this.cardSubtype;}
    public String getSet(){return this.set;}
    public RARITY getRarity() {return this.rarity;}

    public int getGripPower() {return this.gripPower;}
    public int getPullPower() {return this.pullPower;}
    public int getRallyCost() {return this.rallyCost;}

    //Setters
    public void setParentUUID(UUID uuid) {this.parentUUID = uuid;}

    public void setName(String name) {this.name = name;}
    public void setDesc(String desc) {this.desc = desc;}
    public void setFlavorText(String flavorText) {this.flavorText =flavorText;}

    public void setId(String id) {this.id = id;}
    public void setCardType(CARD_TYPE type) {this.cardType = type;}
    public void setCardSubtype(CARD_SUBTYPE subtype) {this.cardSubtype = subtype;}
    public void setSet(String set) {this.set = set;}
    public void setRarity(RARITY rarity) {this.rarity = rarity;}
    public void setGripPower(int gripPower) {this.gripPower = gripPower;}
    public void setPullPower(int pullPower) {this.pullPower = pullPower;}
    public void setRallyCost(int rallyCost) {this.rallyCost = rallyCost;}

    //Enums so Card Types can be compared and consistence
    public enum CARD_TYPE {
        BOARD("Board", "BRD"),
        ACTION("Action", "ACT"),
        TRAP("Trap", "TRP");

        private final String FullString;
        private final String ShortHand;

        CARD_TYPE(String full, String shorthand) {
            this.FullString = full;
            this.ShortHand = shorthand;
        }

        public String getFullName() {
            return this.FullString;
        }
        public String getShortHand() {
            return this.ShortHand;
        }
    }

    public enum CARD_SUBTYPE {
        TRAP("Trap", "TRP"),
        ACTION("Action", "ACT"),
        HAULER("Hauler", "HLR"),
        ANCHOR("Anchor", "ANC"),
        ROPE("Rope", "ROP"),
        HEAVY("Heavy", "HVY"),
        DREDGER("Dredger", "DRG"),
        WINCH("Winch", "WCH");

        private final String FullString;
        private final String ShortHand;

        CARD_SUBTYPE(String full, String shorthand) {
            this.FullString = full;
            this.ShortHand = shorthand;
        }

        public String getFullName() {
            return this.FullString;
        }
        public String getShortHand() {
            return this.ShortHand;
        }
    }

    public enum RARITY {
        FRAYED("Frayed", "FRY"),
        BRAIDED("Braided", "BDD"),
        REINFORCED("Reinforced", "RNC"),
        GALVANIZED("Galvanized", "GLV"),
        UNBREAKABLE("Unbreakable", "UBK");

        private final String FullString;
        private final String ShortHand;

        RARITY(String full, String shorthand) {
            this.FullString = full;
            this.ShortHand = shorthand;
        }

        public String getFullName() {
            return this.FullString;
        }
        public String getShortHand() {
            return this.ShortHand;
        }
    }
}
