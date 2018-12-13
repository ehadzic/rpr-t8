package ba.unsa.etf.rpr.t8;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.io.File;
import java.net.URL;

import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField tekst;
    public Button dugme;
    public Button dugmeZaKraj;
    public ListView lista;
    public File root = new File("D:\\");
    public ObservableList<String> spisak= FXCollections.observableArrayList();


    public void akcija(ActionEvent actionEvent) {
        dugme.setDisable(true);
        dugmeZaKraj.setDisable(false);
        spisak.remove(0,spisak.size());
        Pretraga p=new Pretraga();
        Thread t=new Thread(p);
        t.start();
    }

    public void prekid(ActionEvent actionEvent) {
        dugmeZaKraj.setDisable(true);
        dugme.setDisable(false);
    }

    public class Pretraga implements Runnable{

        @Override
        public void run() {
            trazi(tekst.getText(),root.getAbsolutePath());
        }

    }

    public void trazi(String dio_imena, String parent){
        File f=new File(parent);
        if(dugmeZaKraj.isDisabled()){
            Thread.currentThread().stop();
        }
        File[] djeca=f.listFiles();
        if(djeca!=null){
            for (File d:djeca) {
                if(d.isDirectory()){
                    trazi(dio_imena,d.getAbsolutePath());
                }
                else{
                    if(d.getName().contains(dio_imena)) {
                        Platform.runLater(
                                ()->{spisak.add(d.getAbsolutePath());
                                }
                        );
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            if(f.getAbsolutePath().equals(root.getAbsolutePath())){
                dugme.setDisable(false);
                dugmeZaKraj.setDisable(true);
            }
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lista.setItems(spisak);
    }
}