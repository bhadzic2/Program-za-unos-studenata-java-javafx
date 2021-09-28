package ba.unsa.etf.rpr;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import static ba.unsa.etf.rpr.StudentiModel.dodajStudentaSaImenom;

public class ControllerUnos {
    private String student;//
    @FXML
    public TextField fldIme;
    @FXML
    public ProgressBar progressBar;

    private boolean imePrezimeValidno;
    @FXML
    public Button cancelB;

    private boolean validnoImePrezime(String n) {
        return n.trim().length()>=10;
    }

    @FXML
    public void initialize() {
        imePrezimeValidno = false;

        fldIme.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                progressBar.setProgress(n.length()/10.);
                if ((n.length()/10.)<1) {
                    progressBar.getStyleClass().removeAll("zeleniProgress");
                    progressBar.getStyleClass().add("crveniProgress");
                } else {
                    progressBar.getStyleClass().removeAll("crveniProgress");
                    progressBar.getStyleClass().add("zeleniProgress");
                }
            }
        });

    }
    public void okKliknuto(ActionEvent actionEvent){
            if (!validnoImePrezime(fldIme.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Neispravno ime");
                alert.setHeaderText("Neispravno ime studenta");
                alert.setContentText("Ime studenta treba biti najmanje 10 karaktera dugačko");
                alert.show();
            }else{
                student=fldIme.getText();
                dodajStudentaSaImenom(student);//dodavanje u listu
                Stage stage = (Stage) cancelB.getScene().getWindow();
                stage.close();
            }
    }
    public void cancelKliknut(ActionEvent actionEvent){
        Stage stage = (Stage) cancelB.getScene().getWindow();
        stage.close();
    }

}
