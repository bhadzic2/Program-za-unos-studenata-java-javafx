package ba.unsa.etf.rpr;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
@ExtendWith(ApplicationExtension.class)
class NoviControllerTest {
    private static Stage theStage;

    @Start
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("/fxml/novi.fxml"));
        stage.setScene(new Scene(mainNode, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
        theStage = stage;
    }

    @AfterAll
    public static void zatvoriProzor() {
        Platform.runLater(() -> theStage.close());
    }

    @Test
    public void testProgress(FxRobot robot) {
        robot.clickOn("#fldIme");
        robot.write("abc");

        ProgressBar progress = robot.lookup("#progressBar").queryAs(ProgressBar.class);
        assertNotNull(progress);
        assertEquals(0.3, progress.getProgress());

        robot.clickOn("#fldIme");
        robot.press(KeyCode.END).release(KeyCode.END);
        robot.write("def");

        assertEquals(0.6, progress.getProgress());

        robot.clickOn("#fldIme");
        robot.press(KeyCode.END).release(KeyCode.END);
        robot.write("ghij");

        assertEquals(1, progress.getProgress());

        robot.clickOn("#fldIme");
        robot.press(KeyCode.END).release(KeyCode.END);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        assertEquals(0.9, progress.getProgress());
    }

    // Pomoćna funkcija za still progresa
    private boolean imaLiStil(Node kontrola, String stil) {
        for(String s : kontrola.getStyleClass())
            if (s.equals(stil))
                return true;
        return false;
    }

    @Test
    public void testBojaProgresa(FxRobot robot) {
        robot.clickOn("#fldIme");
        robot.write("abc");

        ProgressBar progress = robot.lookup("#progressBar").queryAs(ProgressBar.class);
        assertNotNull(progress);

        assertTrue(imaLiStil(progress, "crveniProgress"));
        assertFalse(imaLiStil(progress, "zeleniProgress"));

        robot.clickOn("#fldIme");
        robot.press(KeyCode.END).release(KeyCode.END);
        robot.write("def");

        assertTrue(imaLiStil(progress, "crveniProgress"));
        assertFalse(imaLiStil(progress, "zeleniProgress"));

        robot.clickOn("#fldIme");
        robot.press(KeyCode.END).release(KeyCode.END);
        robot.write("ghij");

        assertFalse(imaLiStil(progress, "crveniProgress"));
        assertTrue(imaLiStil(progress, "zeleniProgress"));

        robot.clickOn("#fldIme");
        robot.press(KeyCode.END).release(KeyCode.END);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);

        assertTrue(imaLiStil(progress, "crveniProgress"));
        assertFalse(imaLiStil(progress, "zeleniProgress"));
    }


    @Test
    public void testDijalog(FxRobot robot) {
        // Ako se unese prekratko ime, Ok treba da prikaže dijalog
        robot.clickOn("#fldIme");
        robot.press(KeyCode.END).release(KeyCode.END);
        robot.write("Mia Miic");

        robot.clickOn("Ok");

        // Čekamo da dijalog postane vidljiv
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Dijalozi imaju specijalnu CSS klasu
        robot.lookup(".dialog-pane").tryQuery().isPresent();

        DialogPane dp = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertEquals("Neispravno ime studenta", dp.getHeaderText());

        Button okButton = (Button) dp.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        assertTrue(theStage.isShowing());

        robot.clickOn("Cancel");
        // Čekamo da se prozor zatvori
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(theStage.isShowing());
    }
}