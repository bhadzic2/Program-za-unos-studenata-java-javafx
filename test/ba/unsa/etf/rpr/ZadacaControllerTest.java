package ba.unsa.etf.rpr;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(ApplicationExtension.class)
class ZadacaControllerTest {
    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("/fxml/zadaca2.fxml"));
        stage.setScene(new Scene(mainNode, 500, 350));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testBrojeva(FxRobot robot) {
        // Pronalazimo tekstualno polje
        TextField fld = robot.lookup("#fldText").queryAs(TextField.class);
        assertNotNull(fld);

        robot.clickOn("1");
        robot.clickOn("4");
        robot.clickOn("7");
        assertEquals("147", fld.getText());
        fld.setText("");

        robot.clickOn("2");
        robot.clickOn("6");
        robot.clickOn("8");
        assertEquals("268", fld.getText());
        fld.setText("");

        robot.clickOn("9");
        robot.clickOn("0");
        robot.clickOn("3");
        robot.clickOn("6");
        assertEquals("9036", fld.getText());
        fld.setText("");
    }

    @Test
    public void testPoravnanje(FxRobot robot) {
        // Pronalazimo tekstualno polje
        TextField fld = robot.lookup("#fldText").queryAs(TextField.class);
        assertNotNull(fld);

        robot.clickOn("3");
        robot.clickOn("2");
        robot.clickOn("1");
        robot.clickOn("0");
        assertEquals("3210", fld.getText());
        assertEquals(HPos.RIGHT, fld.getAlignment().getHpos());
        assertEquals(false, fld.isEditable());
    }

    @Test
    public void testSlider(FxRobot robot) {
        // Pronalazimo tekstualno polje
        Slider slider = robot.lookup("#sliderStudents").queryAs(Slider.class);
        assertNotNull(slider);

        assertEquals(5, slider.getMin());
        assertEquals(15, slider.getMax());
        Platform.runLater(() -> slider.setValue(10));

        // Čekamo da se promijeni vrijednost slidera
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ListView lvStudents = robot.lookup("#lvStudents").queryAs(ListView.class);
        assertNotNull(lvStudents);

        assertEquals(10, lvStudents.getItems().size());
        assertEquals("Student4", lvStudents.getItems().get(3));
        assertEquals("Student7", lvStudents.getItems().get(6));
        assertEquals("Student9", lvStudents.getItems().get(8));
        assertEquals("Student10", lvStudents.getItems().get(9));
    }

    @Test
    public void testPosljednjiStudent(FxRobot robot) {
        robot.clickOn("2");
        robot.clickOn("9");
        robot.clickOn("2");
        robot.clickOn("6");

        // Pronalazimo tekstualno polje
        Slider slider = robot.lookup("#sliderStudents").queryAs(Slider.class);
        assertNotNull(slider);
        Platform.runLater(() -> slider.setValue(15));

        // Čekamo da se promijeni vrijednost slidera
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ListView lvStudents = robot.lookup("#lvStudents").queryAs(ListView.class);
        assertNotNull(lvStudents);

        assertEquals(15, lvStudents.getItems().size());
        assertEquals("Student2926", lvStudents.getItems().get(14));
    }

    @Test
    public void testPromjenaBoje(FxRobot robot) {
        // Nalazimo dugmad
        Button[] btn = new Button[10];
        for (int i=0; i<10; i++) {
            String labela = "" + i;
            if (i==5) labela = "4"; // Neće raditi za 5 zbog tick marks slidera
            btn[i] = robot.lookup(labela).queryAs(Button.class);
        }

        // Nalazimo choicebox
        robot.clickOn("#choiceColor");
        robot.clickOn("Crvena");

        Paint red = Paint.valueOf("#ff0000");
        for (int i=0; i<10; i++) {
            boolean pronadjeno = false;
            Background bg = btn[i].getBackground();
            for(BackgroundFill bf : bg.getFills())
                if (bf.getFill().equals(red))
                    pronadjeno = true;
            assertTrue(pronadjeno);
        }

        // Nalazimo choicebox
        robot.clickOn("#choiceColor");
        robot.clickOn("Zelena");

        Paint green = Paint.valueOf("#008000");
        for (int i=0; i<10; i++) {
            boolean pronadjeno = false;
            Background bg = btn[i].getBackground();
            for(BackgroundFill bf : bg.getFills()) {
                if (bf.getFill().equals(green))
                    pronadjeno = true;
            }
            assertTrue(pronadjeno);
        }

        // Nalazimo choicebox
        robot.clickOn("#choiceColor");
        robot.clickOn("Plava");

        Paint blue = Paint.valueOf("#0000ff");
        for (int i=0; i<10; i++) {
            boolean pronadjeno = false;
            Background bg = btn[i].getBackground();
            for(BackgroundFill bf : bg.getFills()) {
                if (bf.getFill().equals(blue))
                    pronadjeno = true;
            }
            assertTrue(pronadjeno);
        }

        // Nije promijenjena boja dugmeta "Unos studenta"
        Button btnUnos = robot.lookup("Unos studenta").queryAs(Button.class);
        Background bg = btnUnos.getBackground();
        for(BackgroundFill bf : bg.getFills()) {
            assertFalse(bf.getFill().equals(blue));
            assertFalse(bf.getFill().equals(red));
            assertFalse(bf.getFill().equals(green));
        }
    }

    @Test
    public void testDefaultBoja(FxRobot robot) {
        // Nalazimo dugmad
        Button[] btn = new Button[10];
        for (int i=0; i<10; i++) {
            String labela = "" + i;
            if (i==5) labela = "4"; // Neće raditi za 5 zbog tick marks slidera
            btn[i] = robot.lookup(labela).queryAs(Button.class);
        }

        // Nalazimo choicebox
        robot.clickOn("#choiceColor");
        robot.clickOn("Crvena");

        Paint red = Paint.valueOf("#ff0000");
        for (int i=0; i<10; i++) {
            boolean pronadjeno = false;
            Background bg = btn[i].getBackground();
            for(BackgroundFill bf : bg.getFills())
                if (bf.getFill().equals(red))
                    pronadjeno = true;
            assertTrue(pronadjeno);
        }

        // Vraćamo na default
        robot.clickOn("#choiceColor");
        robot.clickOn("Default");

        for (int i=0; i<10; i++) {
            boolean pronadjeno = false;
            Background bg = btn[i].getBackground();
            for(BackgroundFill bf : bg.getFills())
                if (bf.getFill().equals(red))
                    pronadjeno = true;
            assertFalse(pronadjeno);
        }
    }

    @Test
    public void testUnosStudenta(FxRobot robot) {
        // Ovaj test provjerava samo da li će student biti unesen
        // Ostali testovi su u drugoj klasi
        robot.clickOn("Unos studenta");
        robot.lookup("#fldIme").tryQuery().isPresent();

        // Uzimamo Stage otvorenog prozora da bismo ga mogli zatvoriti
        TextField fldIme = robot.lookup("#fldIme").queryAs(TextField.class);
        Stage prozor = (Stage)fldIme.getScene().getWindow();

        robot.clickOn("#fldIme");
        robot.write("Meho Mehic");
        robot.clickOn("Ok");

        ListView lvStudents = robot.lookup("#lvStudents").queryAs(ListView.class);
        assertNotNull(lvStudents);

        assertEquals(6, lvStudents.getItems().size());
        assertEquals("Meho Mehic", lvStudents.getItems().get(5));

        // Ok dugme bi trebalo da zatvori prozor, ali ako nije - zatvorićemo
        boolean otvoren = prozor.isShowing();
        if (otvoren) Platform.runLater(() -> prozor.close());
        assertFalse(otvoren);
    }
}