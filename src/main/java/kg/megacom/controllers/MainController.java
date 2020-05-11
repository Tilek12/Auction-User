package kg.megacom.controllers;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import kg.megacom.http.impl.HttpHelperImpl;
import kg.megacom.models.Lot;
import lombok.SneakyThrows;

public class MainController {

    private Lot currentLot;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuItem menuItemAddLot;

    @FXML
    private MenuItem menuItemEditLot;

    @FXML
    private ListView<String> listOfLots;

    @FXML
    private Label txtLotName;

    @FXML
    private Label txtMinPrice;

    @FXML
    private Label txtMaxPrice;

    @FXML
    private Label txtStep;

    @FXML
    private Label txtStartDate;

    @FXML
    private Label txtEndDate;

    @FXML
    private Label txtStatus;

    @FXML
    private Button btnLot;

    @FXML
    private GridPane infoTable;

    @FXML
    void onMenuItemClicked(ActionEvent event) {
        if (event.getSource().equals(menuItemAddLot)){
            addNewLot();
        }else if(event.getSource().equals(menuItemEditLot)){
            editCurrentLot();
        }
    }

    private void editCurrentLot() {

        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/addLotForm.fxml"));
            loader.load();

            AddLotController addLotController = loader.getController();
            Lot lot = currentLot;
            addLotController.init(stage, lot);

            stage.setScene(new Scene(loader.getRoot()));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @SneakyThrows
                @Override
                public void handle(WindowEvent event) {
                    refresh();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Редактирование лота");
        stage.show();
    }

    private void addNewLot() {

        Stage stage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/layouts/addLotForm.fxml"));
            loader.load();

            AddLotController addLotController = loader.getController();
            addLotController.init(stage, new Lot());

            stage.setScene(new Scene(loader.getRoot()));
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @SneakyThrows
                @Override
                public void handle(WindowEvent event) {
                    refresh();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Создание лота");
        stage.show();
    }

    @FXML
    void initialize() throws IOException {

        refresh();
    }

    private void refresh() throws IOException {

        List<Lot> lotList = new ArrayList<>(HttpHelperImpl.INSTANCE.getAllLots());

        ObservableList<Lot> lots = FXCollections.observableArrayList(lotList);
        ObservableList<String> lotsName = FXCollections.observableArrayList();

            for (Lot i: lots){
               lotsName.add(i.getName());
            }

        listOfLots.setItems(lotsName);

        // получаем модель выбора элементов
        MultipleSelectionModel<String> lotsSelectionModel = listOfLots.getSelectionModel();

        // устанавливаем слушатель для отслеживания изменений
        lotsSelectionModel.selectedItemProperty().addListener(new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> changed, String oldValue, String newValue){

                for (Lot i: lots) {
                    if (newValue == null) {
                        infoTable.setVisible(false);

                        txtLotName.setText(null);
                        txtMinPrice.setText(null);
                        txtMaxPrice.setText(null);
                        txtStep.setText(null);
                        txtStartDate.setText(null);
                        txtEndDate.setText(null);
                        txtStatus.setText(null);

                    }else if(i.getName().equals(newValue)){
                        infoTable.setVisible(true);

                        Lot lot = new Lot();

                        lot.setId(i.getId());
                        lot.setName(i.getName());
                        lot.setMinPrice(i.getMinPrice());
                        lot.setMaxPrice(i.getMaxPrice());
                        lot.setStep(i.getStep());
                        //lot.setStartDate(i.getStartDate());
                        //lot.setEndDate(i.getEndDate());

                        txtLotName.setText(lot.getName());
                        txtMinPrice.setText(String.valueOf(lot.getMinPrice()));
                        txtMaxPrice.setText(String.valueOf(lot.getMaxPrice()));
                        txtStep.setText(String.valueOf(lot.getStep()));
                        //txtStartDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(lot.getStartDate()));
                        //txtEndDate.setText(DateFormat.getDateInstance(DateFormat.MEDIUM).format(lot.getEndDate()));

                        currentLot = lot;

                    }
                }
            }
        });
    }
}