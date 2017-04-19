import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.JTree.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class Builder {
    static JFrame fenetre = new JFrame();

    public static void createMenu() {
        fenetre.setTitle("bedmin_j - Interagir avec le document XML");
        fenetre.setSize(400, 400);
        fenetre.setLocationRelativeTo(null);
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
        fenetre.setLocationRelativeTo(null);
        JPanel panelHome = createPanel();
        //Bouton
        JButton buttonAff = new JButton("Afficher la liste");
        JButton buttonAdd = new JButton("Ajouter une personne");
        JButton buttonAddItem = new JButton("Ajouter un objet à une personne");
        JButton buttonRem = new JButton("Supprimer une personne");
        panelHome.add(buttonAff);
        panelHome.add(buttonAdd);
        panelHome.add(buttonAddItem);
        panelHome.add(buttonRem);
        fenetre.setContentPane(panelHome);
        buttonAff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Affichage...");
                panelHome.setVisible(false);
                createAffichageListe();
            }
        });
        buttonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelHome.setVisible(false);
                createAffichageAdd();
            }
        });
        buttonAddItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelHome.setVisible(false);
                createAffichageAddItem();
            }
        });
        buttonRem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panelHome.setVisible(false);
                createAffichageRem();
            }
        });
    }
    public static void createAffichageListe() {
        try {
            XMLInteract.treeBuild();
        } catch (Exception e) {
            System.out.println("Impossible d'afficher la liste : "+e);
        }
    }

    public static void createAffichageRem() {
        try {
        //Création des éléments
        JPanel panelMainRem = createPanel();
        JLabel labelListe = new JLabel("Supprimer une personne :");
        labelListe.setPreferredSize(new Dimension(400, 25));
        Object[] listPersonnes = XMLInteract.getPersonnes();
        JComboBox comboPersonnes = new JComboBox(listPersonnes);
        comboPersonnes.setPreferredSize(new Dimension(400, 25));

        //Bouton supprimer
        JButton buttonRem = new JButton("Supprimer");
        buttonRem.setPreferredSize(new Dimension(400, 25));
        buttonRem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                XMLInteract.remPersonne(comboPersonnes.getSelectedItem().toString());
                panelMainRem.setVisible(false);
                createSuccessWindow();
            }
            });

        //Bouton retour
        JButton buttonRetour = new JButton("Retour");
        buttonRetour.setPreferredSize(new Dimension(400, 25));
        buttonRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Quitter");
                panelMainRem.setVisible(false);
                createMenu();
            }
        });

        //Insertion des éléments
        panelMainRem.add(labelListe);
        panelMainRem.add(comboPersonnes);
        panelMainRem.add(buttonRem);
        panelMainRem.add(buttonRetour);
        fenetre.setContentPane(panelMainRem);
        } catch (Exception e) {
            System.out.println("Impossible d'afficher la fenêtre <supprimer> : "+e);
        }
    }

    public static void createSuccessWindow() {
        //Création des éléments
        JPanel panelMainSuccess = createPanel();
        JLabel labelListe = new JLabel("L'opération a été effectuée avec succès.");

        //Bouton retour
        JButton buttonRetour = new JButton("Menu");
        buttonRetour.setPreferredSize(new Dimension(400, 25));
        buttonRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Quitter");
                panelMainSuccess.setVisible(false);
                createMenu();
            }
        });

        //Insertion des éléments
        panelMainSuccess.add(labelListe);
        panelMainSuccess.add(buttonRetour);
        fenetre.setContentPane(panelMainSuccess);
    }


    public static void createErrorWindow(String message) {
        //Création des éléments
        JPanel panelMainError = createPanel();
        JLabel labelListe = new JLabel("L'opération a échoué :");
        labelListe.setPreferredSize(new Dimension(400, 25));
        JLabel labelMessage = new JLabel(message);
        labelMessage.setPreferredSize(new Dimension(400, 75));

        //Bouton retour
        JButton buttonRetour = new JButton("Menu");
        buttonRetour.setPreferredSize(new Dimension(400, 25));
        buttonRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Quitter");
                panelMainError.setVisible(false);
                createMenu();
            }
        });

        //Insertion des éléments
        panelMainError.add(labelListe);
        panelMainError.add(labelMessage);
        panelMainError.add(buttonRetour);
        fenetre.setContentPane(panelMainError);
    }


    public static void createAffichageAdd() {
        try {
            //Création des éléments
            JPanel panelMainAdd = createPanel();
            JLabel labelListe = new JLabel("Liste des personnes :");
            labelListe.setPreferredSize(new Dimension(400, 25));
            JLabel labelNom = new JLabel("Nom : ");
            labelNom.setPreferredSize(new Dimension(100, 25));
            JTextField fieldNom = new JTextField(20);
            JLabel labelPrenom = new JLabel("Prénom :");
            labelPrenom.setPreferredSize(new Dimension(100, 25));
            JTextField fieldPrenom = new JTextField(20);
            JLabel labelItemNom = new JLabel("Nom objet :");
            labelItemNom.setPreferredSize(new Dimension(100, 25));
            JTextField fieldItemNom = new JTextField(20);
            JLabel labelItemTypes = new JLabel("Type objet :");
            labelItemTypes.setPreferredSize(new Dimension(100, 25));
            Object[] types = new Object[]{"Vêtement", "Jouet", "Livre", "Document", "Monnaie"};
            JComboBox comboItemTypes = new JComboBox(types);
            comboItemTypes.setPreferredSize(new Dimension(400, 25));
            JLabel labelSpace = new JLabel("");
            labelSpace.setPreferredSize(new Dimension(400, 25));

            //Bouton retour
            JButton buttonRetour = new JButton("Retour");
            buttonRetour.setPreferredSize(new Dimension(400, 25));
            buttonRetour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Quitter");
                    panelMainAdd.setVisible(false);
                    createMenu();
                }
            });

            //Bouton ajouter
            JButton buttonAjouter = new JButton("Ajouter");
            buttonAjouter.setPreferredSize(new Dimension(400, 25));
            buttonAjouter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    panelMainAdd.setVisible(false);
                    if (fieldNom.getText().equals("")
                            || fieldPrenom.getText().equals("")
                            || fieldItemNom.getText().equals("")) {
                        createErrorWindow("Un ou plusieurs champs sont vides ! ");
                    }
                    else {
                        if (XMLInteract.addPersonne(fieldNom.getText(),
                                fieldPrenom.getText(),
                                fieldItemNom.getText(),
                                comboItemTypes.getSelectedItem().toString())) {
                            createSuccessWindow();
                        } else {
                            createErrorWindow("La personne existe déjà. Si vous souhaitez lui ajouter un item, retournez au menu.");
                        }
                    }
                }
            });

            //Insertion des éléments dans le panneau
            panelMainAdd.add(labelListe);
            panelMainAdd.add(labelNom);
            panelMainAdd.add(fieldNom);
            panelMainAdd.add(labelPrenom);
            panelMainAdd.add(fieldPrenom);
            panelMainAdd.add(labelItemNom);
            panelMainAdd.add(fieldItemNom);
            panelMainAdd.add(labelItemTypes);
            panelMainAdd.add(comboItemTypes);
            panelMainAdd.add(labelSpace);
            panelMainAdd.add(buttonAjouter);
            panelMainAdd.add(buttonRetour);
            fenetre.setContentPane(panelMainAdd);
        } catch (Exception e) {
            System.out.println("Impossible d'afficher la fenêtre : "+e);
        }
    }

    public static void createAffichageAddItem() {
        try {
            //Création des éléments
            JPanel panelMainAddItem = createPanel();
            JLabel labelListe = new JLabel("Liste des personnes :");
            labelListe.setPreferredSize(new Dimension(400, 25));
            Object[] listPersonnes = XMLInteract.getPersonnes();
            JComboBox comboPersonnes = new JComboBox(listPersonnes);
            comboPersonnes.setPreferredSize(new Dimension(400, 25));
            JLabel labelItemNom = new JLabel("Nom objet :");
            labelItemNom.setPreferredSize(new Dimension(100, 25));
            JTextField fieldItemNom = new JTextField(20);
            JLabel labelItemTypes = new JLabel("Type objet :");
            labelItemTypes.setPreferredSize(new Dimension(100, 25));
            Object[] types = new Object[]{"Vêtement", "Jouet", "Livre", "Document", "Monnaie"};
            JComboBox comboItemTypes = new JComboBox(types);
            comboItemTypes.setPreferredSize(new Dimension(400, 25));
            JLabel labelSpace = new JLabel("");
            labelSpace.setPreferredSize(new Dimension(400, 25));

            //Bouton ajouter
            JButton buttonAdd = new JButton("Ajouter");
            buttonAdd.setPreferredSize(new Dimension(400, 25));
            buttonAdd.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    XMLInteract.addItem(comboPersonnes.getSelectedItem().toString(),
                                        fieldItemNom.getText(),
                                        comboItemTypes.getSelectedItem().toString());
                                        panelMainAddItem.setVisible(false);
                    createSuccessWindow();
                }
            });


            //Bouton retour
            JButton buttonRetour = new JButton("Retour");
            buttonRetour.setPreferredSize(new Dimension(400, 25));
            buttonRetour.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Quitter");
                    panelMainAddItem.setVisible(false);
                    createMenu();
                }
            });

            //Insertion des éléments dans le panneau
            panelMainAddItem.add(labelListe);
            panelMainAddItem.add(comboPersonnes);
            panelMainAddItem.add(labelItemNom);
            panelMainAddItem.add(fieldItemNom);
            panelMainAddItem.add(labelItemTypes);
            panelMainAddItem.add(comboItemTypes);
            panelMainAddItem.add(buttonAdd);
            panelMainAddItem.add(buttonRetour);
            fenetre.setContentPane(panelMainAddItem);
        } catch (Exception e) {
            System.out.println("Impossible d'afficher la fenêtre : "+e);
        }
    }


    public static JPanel createPanel() {
        JPanel pan = new JPanel();
        pan.setSize(400, 400);
        pan.setBackground(Color.WHITE);
        return (pan);
    }


}
