package Engine;

import Engine.Game.GameComponents.Card;
import Engine.Game.GameComponents.Effect;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class Database {
    private ArrayList<Card> activeCardList = new ArrayList<>();
    private ArrayList<Effect> activeEffectList = new ArrayList<>();

    public Database(String[] catagoryList) {
        for (String e : catagoryList) {
            String currentWorkingDirectory = new File("").getAbsolutePath();
            Path folderPath = Paths.get(currentWorkingDirectory, "src", "database", "cards", e);
            File[] fileList = new File(folderPath.toString()).listFiles();
            for (File f : fileList) {
                String[] arr = f.toString().split("\\.");
                if (f.isFile() && arr[arr.length-1].equals("crd")) {
                    String[] fileSplit = arr[arr.length-2].split("\\\\");
                    activeCardList.add(createCard(e, fileSplit[fileSplit.length-1]));
                }
            }

            //TODO: Check effectsRequired.ttcg to figure out what effects need to be loaded
        }
    }

    public String getCardList() {
        StringBuilder tmp = new StringBuilder("[");
        for (Card c : activeCardList) {
            tmp.append("(").append(c.getId()).append(", ").append(c.getName()).append("); ");
        }
        String stg = tmp.substring(0, tmp.length()-2);
        return stg + "]";
    }

    public Card getCopyOfCard(String cardID, UUID parentUUID) {
        for (Card c : activeCardList) {
            if (c.getId().equals(cardID)) {
                return new Card(parentUUID, c.getName(), c.getDesc(), c.getFlavorText(), c.getId(), c.getCardType(), c.getCardSubtype(), c.getSet(), c.getRarity(), c.getGripPower(), c.getPullPower(), c.getRallyCost());
            }
        }
        return null;
    }

    private Card createCard(String catagory, String cardName) {
        jsonHandler temp = new jsonHandler(catagory, cardName);
        return new Card(null, temp.getStringKey("header.name"), temp.getStringKey("flavor.desc"), temp.getStringKey("flavor.text"), temp.getStringKey("header.id"), Card.getTypeByString(temp.getStringKey("header.type")), Card.getSubTypeByString(temp.getStringKey("header.sub_type")), temp.getStringKey("header.set"), Card.getRarityByString(temp.getStringKey("header.rarity")), temp.getIntKey("stats.grip_power"), temp.getIntKey("stats.pull_power"), temp.getIntKey("stats.rally_cost"));
    }

    //TODO: Work on effect
    private Effect createEffect(String catagory, String effectName) {
        return null;
    }
}
