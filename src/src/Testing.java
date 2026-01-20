import Engine.Database;
import Engine.jsonHandler;

public class Testing {
    public static void main(String[] args) {
        jsonHandler temp = new jsonHandler("classic", "testCard");

        System.out.println(temp.getStringKey("header.id"));
        System.out.println(temp.getStringKey("header.name"));
        System.out.println(temp.getStringKey("header.type"));
        System.out.println(temp.getStringKey("header.sub_type"));
        System.out.println(temp.getStringKey("header.set"));
        System.out.println(temp.getStringKey("header.rarity"));

        System.out.println(temp.getIntKey("stats.grip_power"));
        System.out.println(temp.getIntKey("stats.pull_power"));
        System.out.println(temp.getIntKey("stats.rally_cost"));

        System.out.println(temp.getStringKey("flavor.desc"));
        System.out.println(temp.getStringKey("flavor.text"));

        Database tmp = new Database(new String[] {"classic"});
        System.out.println(tmp.getCardList());
        System.out.println(tmp.getCopyOfCard("TTCG_FRY_0001", null).getName());
        System.out.println(tmp.getCopyOfCard("TTCG_BDD_0002", null).getName());
        System.out.println(tmp.getCopyOfCard("TTCG_RNC_0003", null).getName());
    }
}
