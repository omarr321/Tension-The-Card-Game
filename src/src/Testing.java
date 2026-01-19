import Engine.jsonHandler;

public class Testing {
    public static void main(String[] args) {
        jsonHandler temp = new jsonHandler("classic", "testCard.crd");
        System.out.println(temp.getStringKey(new String[]{"header", "id"}));
    }
}
