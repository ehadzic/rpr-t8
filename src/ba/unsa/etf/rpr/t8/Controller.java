package ba.unsa.etf.rpr.t8;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.io.File;

public class Controller {

    public Label labela;
    public ListView lista;
    public Button dugmeTrazi;
    public TextField tekst;
    private File korijenskiDirektorij;
    private ObservableList<String> observabilnaLista;
    public Button dugmePrekini;

    public Controller() {
        korijenskiDirektorij = new File(System.getProperty("user.home"));
        observabilnaLista = FXCollections.observableArrayList();
    }

    @FXML
    public void initialize() {
        lista.setItems(observabilnaLista);
        dugmePrekini.setDisable(true);
        dugmeTrazi.setDisable(false);
    }

    private void pretraga(String put, String uzorak) {
        if (dugmePrekini.isDisabled()) Thread.currentThread().stop();
        File file = new File(put);
        if (file.isFile()) {
            if (file.getName().contains(uzorak)) {
                try {
                    Platform.runLater(() -> observabilnaLista.add(file.getAbsolutePath()));
                    Thread.sleep(180);
                } catch (Exception exception) {
                }
            }
        }
        else if (file.isDirectory()) {
            try {
                for (File file1 : file.listFiles()) {
                    pretraga(file1.getAbsolutePath(), uzorak);
                }
            } catch (Exception exception) {
            }
        }
        if (file.getAbsolutePath().equals(korijenskiDirektorij.getAbsolutePath())) {
            dugmePrekini.setDisable(true);
            dugmeTrazi.setDisable(false);
        }
    }

    public void pretragaF(ActionEvent actionEvent) {
        observabilnaLista.clear();
        dugmePrekini.setDisable(false);
        dugmeTrazi.setDisable(true);
        new Thread(() -> pretraga(korijenskiDirektorij.getAbsolutePath(), tekst.getText())).start();
    }

    public void prekiniF(ActionEvent actionEvent) {
        dugmePrekini.setDisable(true);
        dugmeTrazi.setDisable(false);
    }
}