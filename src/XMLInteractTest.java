import static org.junit.Assert.*;
import static junit.framework.TestCase.assertTrue;
import java.io.*;

public class XMLInteractTest {
    @org.junit.Test
    public void TestPrepareXML() throws Exception {
        assertFalse(XMLInteract.prepareXML("empty.xml"));
        assertTrue(XMLInteract.prepareXML("mon_carnet.xml"));
    }
    @org.junit.Test
    public void TestAddPersonne() throws Exception {
        assertTrue(createFile());
        assertTrue(XMLInteract.prepareXML("test.xml"));
        assertTrue(XMLInteract.addPersonne("TestNom2", "TestPrenom2", "Objet", "Type"));
        assertFalse(XMLInteract.addPersonne("TestNom2", "TestPrenom2", "Objet", "Type"));
        assertTrue(deleteFile());
    }

    @org.junit.Test
    public void TestRemPersonne() throws Exception {
        assertTrue(createFile());
        assertTrue(XMLInteract.prepareXML("test.xml"));
        assertTrue(XMLInteract.remPersonne("TestNom TestPrenom"));
        assertFalse(XMLInteract.remPersonne("TestNom TestPrenom"));
        assertTrue(deleteFile());
    }
    @org.junit.Test
    public void TestAddItem() throws Exception {
        assertTrue(createFile());
        assertTrue(XMLInteract.prepareXML("test.xml"));
        assertTrue(XMLInteract.addItem("TestNom TestPrenom", "AddedItem","Document"));
        assertTrue(deleteFile());
    }
    @org.junit.Test
    public void TestCountItem() throws Exception {
        assertTrue(createFile());
        assertTrue(XMLInteract.prepareXML("test.xml"));
        assertEquals(1, XMLInteract.countPersonnes());
        assertTrue(deleteFile());
    }

    @org.junit.Test
    public void TestGetPersonnes() throws Exception {
        assertTrue(createFile());
        assertTrue(XMLInteract.prepareXML("test.xml"));
        assertEquals("TestNom TestPrenom", XMLInteract.getPersonnes()[0]);
        assertTrue(deleteFile());
    }

    public boolean createFile() {
        try {
            File f = new File("test.xml");
            f.createNewFile();
            PrintWriter writer = new PrintWriter("test.xml", "UTF-8");
            writer.println(exampleXML());
            writer.close();
            return (true);
        } catch (Exception e) {
            System.out.println("Echec dans la création du fichier text.xml : "+ e);
        }
        return (false);
    }

    public boolean deleteFile() {
        try{
            File f = new File("test.xml");
            if(f.delete()){
                System.out.println("Fichier trouvé et supprimé. OK");
                return (true);
            }else{
                System.out.println("Fichier inexistant. OK");
            }
        } catch (Exception e) {
            System.out.println("Echec dans la suppression du fichier text.xml : "+ e);
        }
        return (false);
    }

    public String exampleXML() {
        return ("<carnet>\n" +
                "    <personne>\n" +
                "        <nom>TestNom</nom>\n" +
                "        <prenom>TestPrenom</prenom>\n" +
                "        <items>\n" +
                "            <item>\n" +
                "                <nom>Objet</nom>\n" +
                "                <date>17/03/2017</date>\n" +
                "                <type>Type</type>\n" +
                "            </item>\n" +
                "        </items>\n" +
                "    </personne>\n" +
                "</carnet>");
    }

}