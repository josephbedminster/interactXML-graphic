import javax.swing.*;

import org.jdom2.Document;
import org.jdom2.Element;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class MyTree {
    public static void build(Element dom) {
        try {
        JFrame frame = new JFrame("Afficher le contenu du carnet");
        frame.setTitle("Afficher le contenu du carnet");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        DefaultMutableTreeNode parent = fillTree(dom);
        JTree tree = new JTree(parent);
        JPanel panel1 = Builder.createPanel();
        panel1.setPreferredSize(new Dimension(400, 375));
        panel1.add(tree);

        //Bouton retour
        JPanel panel2 = Builder.createPanel();
        panel2.setPreferredSize(new Dimension(400, 25));
        JButton buttonRetour = new JButton("Retour");
        buttonRetour.setPreferredSize(new Dimension(400, 25));
        buttonRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Builder.createMenu();
            }
        });

        panel2.add(buttonRetour);
        panel1.add(panel2);
        frame.add(panel1);
        } catch (Exception e) {
            System.out.println("Erreur TREE : "+e);
        }
    }

    public static DefaultMutableTreeNode fillTree(Element dom) {
        DefaultMutableTreeNode parent = new DefaultMutableTreeNode("Carnet", true);
        java.util.List xmlList = dom.getChildren("personne");
        Iterator it = xmlList.iterator();
        //Pour chaque user :
        while(it.hasNext())
        {
            Element temp = (Element)it.next();
            DefaultMutableTreeNode personne = new DefaultMutableTreeNode("Personne");
            DefaultMutableTreeNode nom = new DefaultMutableTreeNode(temp.getChild("nom").getText());
            DefaultMutableTreeNode prenom = new DefaultMutableTreeNode(temp.getChild("prenom").getText());
            DefaultMutableTreeNode items = new DefaultMutableTreeNode("Items");


            java.util.List itemsList = temp.getChild("items").getChildren("item");
            Iterator itItems = itemsList.iterator();
            while(itItems.hasNext()) {
                Element tempItem = (Element) itItems.next();
                DefaultMutableTreeNode item = new DefaultMutableTreeNode("Item");
                DefaultMutableTreeNode itemNom = new DefaultMutableTreeNode(tempItem.getChild("nom").getText());
                DefaultMutableTreeNode itemDate = new DefaultMutableTreeNode(tempItem.getChild("date").getText());
                DefaultMutableTreeNode itemType = new DefaultMutableTreeNode(tempItem.getChild("type").getText());
                item.add(itemType);
                item.add(itemDate);
                item.add(itemNom);
                items.add(item);
            }
            personne.add(nom);
            personne.add(prenom);
            personne.add(items);
            parent.add(personne);

        }
        return (parent);
    }
}