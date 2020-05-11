package kg.megacom.controllers;

import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kg.megacom.http.impl.HttpHelperImpl;
import kg.megacom.models.Lot;
import kg.megacom.models.Status;

import static java.lang.Double.parseDouble;

public class AddLotController {

    private Stage stage;

    private Lot lot;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<?> listOfLots;

    @FXML
    private TextField txtFieldLotName;

    @FXML
    private TextField txtFieldMinPrice;

    @FXML
    private TextField txtFieldMaxPrice;

    @FXML
    private TextField txtFieldStep;

    @FXML
    private ComboBox<String> cmbBoxStatus;

    @FXML
    private DatePicker datePickStartDate;

    @FXML
    private DatePicker datePickEndDate;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnCancel;

    @FXML
    void onButtonClick(ActionEvent event) throws Exception {

        if (event.getSource().equals(btnSave)){
            saveButtonClick();
            close();
        }else if(event.getSource().equals(btnCancel)){
            close();

        }
    }

    private void close() {
        if(stage != null){
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    private void saveButtonClick() throws Exception {

        //Status statusToSave = new Status();

        lot.setName(txtFieldLotName.getText());
        lot.setMinPrice(parseDouble(txtFieldMinPrice.getText()));
        lot.setMaxPrice(parseDouble(txtFieldMaxPrice.getText()));
        lot.setStep(parseDouble(txtFieldStep.getText()));
        //lot.setStartDate(Date.from(datePickStartDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        //lot.setEndDate(Date.from(datePickEndDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));

        //statusToSave.setName(cmbBoxStatus.getSelectionModel().getSelectedItem());
        //lot.setStatus(statusToSave);



        if(lot.getId() == null){
            HttpHelperImpl.INSTANCE.saveLot(lot);
        }else {
            HttpHelperImpl.INSTANCE.updateLot(lot);
        }

        clearFields();
    }

    private void clearFields() {

        txtFieldLotName.clear();
        txtFieldMinPrice.clear();
        txtFieldMaxPrice.clear();
        cmbBoxStatus.setPromptText("Выберите статус");
        datePickStartDate.setValue(null);
        datePickEndDate.setValue(null);
        txtFieldStep.clear();

    }


    @FXML
    void initialize() {


    }

    public void init(Stage stage, Lot lot){

        //ObservableList<String> statuses = FXCollections.observableArrayList("Открыт", "Завершен", "Не начат");
        //cmbBoxStatus.setItems(statuses);

        this.stage = stage;

        if (lot != null){
            this.lot = lot;

            txtFieldLotName.setText(lot.getName());
            txtFieldMinPrice.setText(String.valueOf(lot.getMinPrice()));
            txtFieldMaxPrice.setText(String.valueOf(lot.getMaxPrice()));
            txtFieldStep.setText(String.valueOf(lot.getStep()));
            //datePickStartDate.setValue(lot.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            //datePickEndDate.setValue(lot.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            //cmbBoxStatus.setPromptText(String.valueOf(lot.getStatus()));

        }else{
            this.lot = new Lot();
        }
    }
}
