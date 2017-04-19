
public class Main {

    public static void main(String[] args) {
        if (XMLInteract.prepareXML("mon_carnet.xml")) {
            Builder.createMenu();
        }
        else {
            System.out.println("Arrêt du programme, impossible de charger le XML.");
        }
    }

}
