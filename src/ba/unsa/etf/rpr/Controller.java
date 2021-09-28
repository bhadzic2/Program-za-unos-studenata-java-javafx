package ba.unsa.etf.rpr;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class Controller {

    @FXML
    public ChoiceBox choiceColor;
    @FXML
    private TextField fldText;
    @FXML
    private GridPane numeric;
    @FXML
    private ListView<String> lvStudents;
    @FXML
    private Slider sliderStudents;

    private String boja="Default";

    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        Button button = (Button)event.getSource();
        fldText.appendText(button.getText());
    }
    private StudentiModel model=new StudentiModel();
    @FXML
    public void initialize() {
        lvStudents.setItems(model.prosiriDo(((int)sliderStudents.getValue()),fldText.getText()));
        choiceColor.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String o, String n) {
                     if (n.equals("Crvena")) {
                    for(Node but:numeric.getChildren()) {
                        but.getStyleClass().removeAll("plava");
                        but.getStyleClass().removeAll("zelena");
                        but.getStyleClass().add("crvena");
                    }
                } else if(n.equals("Zelena")){
                    for(Node but:numeric.getChildren()) {
                        but.getStyleClass().removeAll("plava");
                        but.getStyleClass().removeAll("crvena");
                        but.getStyleClass().add("zelena");
                    }

                } else if(n.equals("Plava")){
                    for(Node but:numeric.getChildren()) {
                        but.getStyleClass().removeAll("crvena");
                        but.getStyleClass().removeAll("zelena");
                        but.getStyleClass().add("plava");
                    }
                }else{
                    for(Node but:numeric.getChildren()) {
                        but.getStyleClass().removeAll("plava");
                        but.getStyleClass().removeAll("zelena");
                        but.getStyleClass().removeAll("crvena");
                    }
                }
            }
        });

        sliderStudents.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(
                    ObservableValue<? extends Number> observableValue,
                    Number  wasChanging,
                    Number changing) {
                     lvStudents.setItems(model.prosiriDo((int) sliderStudents.getValue(),fldText.getText())); //tad je released

            }

        });
        //da bi radilo i na klik(osim drag-a)
        sliderStudents.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> sliderStudents.setValueChanging(true));
        sliderStudents.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> sliderStudents.setValueChanging(false));


    }
    private ControllerUnos unosControler;

    public void unosKliknut(ActionEvent actionEvent) {
        Parent root = null;
        try {
            Stage myStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/novi.fxml"));
            loader.load();
            unosControler = loader.getController();

            myStage.setTitle("Unos studenta");
            myStage.setScene(new Scene(loader.getRoot(), USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
            myStage.setResizable(false);
            myStage.show();

            lvStudents.setItems(model.getStudenti());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
