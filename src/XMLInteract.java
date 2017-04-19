import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;

public class XMLInteract {
    public static org.jdom2.Document doc;
    public static Element root = new Element("carnet");
    public static String path_file = new String();

    public static boolean prepareXML(String file) {
        try {
            path_file = file;
            File f = new File(path_file);
            //f.createNewFile();
            if (f.exists()) {
                return (parseXML());
            }
            else {
                System.out.println("Le XML n'existe pas.");
                return (false);
            }
        } catch (Exception e) {
            System.out.println("Echec dans la création du fichier mon_carnet.xml : " + e);
        }
        return (false);
    }

    public static boolean parseXML() {
        SAXBuilder sxb = new SAXBuilder();
        try
        {
            doc = sxb.build(new File(path_file));
            root = doc.getRootElement();
            return (true);
        }
        catch(Exception e){
            System.out.println("Problème lors du parsing du XML: " + e.getMessage());
        }
        return (false);
    }


    public static boolean addPersonne(String sNom, String sPrenom, String sItemNom, String sItemType) {
        try {
            if (findPersonne(sNom + " " + sPrenom) == false) {
                Element personnes = new Element("personne");
                Element nom = new Element("nom");
                nom.setText(sNom);
                personnes.addContent(nom);
                Element prenom = new Element("prenom");
                prenom.setText(sPrenom);
                personnes.addContent(prenom);
                Element items = new Element("items");
                Element item = new Element("item");
                Element itemNom = new Element("nom");
                itemNom.setText(sItemNom);
                item.addContent(itemNom);
                Element itemDate = new Element("date");
                itemDate.setText(getDate());
                item.addContent(itemDate);
                Element itemType = new Element("type");
                itemType.setText(sItemType);
                item.addContent(itemType);
                items.addContent(item);
                personnes.addContent(items);
                root.addContent(personnes);
                writeXML();
                return (true);
            }
            else { return (false); }
        } catch (Exception exception) {
            System.out.println("Problème lors de la création d'une nouvelle personne: " + exception.getMessage());
        }
        return (false);
    }

    public static boolean remPersonne(String identite) {
        try {
            List xmlList = root.getChildren("personne");
            Iterator it = xmlList.iterator();
            while(it.hasNext())
            {
                Element temp = (Element)it.next();
                String tempName = temp.getChild("nom").getText() + " " + temp.getChild("prenom").getText();
                if (tempName.equals(identite))
                {
                    System.out.println("Supression de : " +temp.getChild("nom").getText() + " " + temp.getChild("prenom").getText());
                    temp.detach();
                    writeXML();
                    return (true);
                }
            }
        } catch (Exception exception) {
            System.out.println("Impossible de supprimer l'élément <" + identite + ">. " + exception);
        }
        return (false);
    }

    public static boolean addItem(String identite, String sItemNom, String sItemType) {
        try {
            List xmlList = root.getChildren("personne");
            Iterator it = xmlList.iterator();
            while(it.hasNext())
            {
                Element temp = (Element)it.next();
                String tempName = temp.getChild("nom").getText() + " " + temp.getChild("prenom").getText();
                if (tempName.equals(identite))
                {
                    Element items = temp.getChild("items");
                    Element item = new Element("item");
                    Element itemNom = new Element("nom");
                    itemNom.setText(sItemNom);
                    item.addContent(itemNom);
                    Element itemDate = new Element("date");
                    itemDate.setText(getDate());
                    item.addContent(itemDate);
                    Element itemType = new Element("type");
                    itemType.setText(sItemType);
                    item.addContent(itemType);
                    items.addContent(item);
                    System.out.println("Insertion de l'objet dans l'utilisateur.");
                    writeXML();
                    return (true);
                }
            }
        } catch (Exception exception) {
            System.out.println("Impossible de supprimer l'élément <" + identite + ">. " + exception);
        }
        return (false);
    }

    public static boolean findPersonne(String identite) {
        boolean exists = false;
        try {
            List xmlList = root.getChildren("personne");
            Iterator it = xmlList.iterator();
            while(it.hasNext())
            {
                Element temp = (Element)it.next();
                String tempName = temp.getChild("nom").getText() + " " + temp.getChild("prenom").getText();
                if (tempName.equals(identite))
                {
                    exists = true;
                }
            }
        } catch (Exception exception) {
            System.out.println("Impossible de supprimer l'élément <" + identite + ">. " + exception);
        }
        return (exists);
    }

    public static String printXML() {
        return (root.toString());
    }

    public static int countPersonnes() {
        int i = 0;
        try {
            java.util.List xmlList = root.getChildren("personne");
            Iterator it = xmlList.iterator();
            while(it.hasNext()) {
                Element temp = (Element) it.next();
                i++;
            }
        } catch (Exception exception) {
            System.out.println("Impossible de counter le nombre de personnes : " + exception);
        }
        return (i);
    }

    public static Object[] getPersonnes() {
        Object[] personnes = new Object[countPersonnes()];
        try {
            java.util.List xmlList = root.getChildren("personne");
            Iterator it = xmlList.iterator();
            int i = 0;
            //Pour chaque user :
            while (it.hasNext()) {
                Element temp = (Element) it.next();
                String s = temp.getChild("nom").getText() + " " + temp.getChild("prenom").getText();
                personnes[i] = s;
                i++;
            }
        } catch (Exception exception) {
            System.out.println("Impossible de récupérer la liste des personnes : " + exception);
        }
        return (personnes);
    }

    public static String getDate() {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            Date date = Calendar.getInstance().getTime();
            String sDate = df.format(date);
            return (sDate);
        } catch (Exception exception) {
            System.out.println("Impossible de récupérer la date du jour : " + exception);
        }
        return (null);
    }

    public static void writeXML() {
            try {
                System.out.println(path_file);
                System.out.println("XML généré, écriture dans le fichier.");
                XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
                xmlOutput.output(root, new FileWriter(path_file));
                System.out.println("Ecriture terminée.");
            } catch (IOException exception) {
                System.out.println("Problème lors de la création du XML: " + exception.getMessage());
            }
    }
    public static void treeBuild() {
       MyTree.build(root);
    }

}
