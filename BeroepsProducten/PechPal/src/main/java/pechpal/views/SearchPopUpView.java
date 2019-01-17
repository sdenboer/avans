package pechpal.views;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import pechpal.controllers.OngevalController;
import java.util.NoSuchElementException;

public class SearchPopUpView extends GridPane {
    private Button button;
    private TextField tfdWeg, tfdHecometer, tfdLetter, tfdNummer;
    private ComboBox<String> cbxRichting;
    private Label nummer, weg, hectometer, richting, letter;

    public SearchPopUpView(){
        button = new Button("VERANDER");
    }

    public void search(String s, SideView sv, CenterView cv) {
        if (s.equals("HECTOMETERPAAL")) {
            this.searchByHectometer(sv, cv);
        } else if (s.equals("ONGEVALNUMMER"))  {
            this.searchByVkl(sv, cv);
        }
    }

    private void setMap(OngevalController ongC, CenterView cv) {
        double coord[] = ongC.loadMap();
        cv.webEngine.executeScript(String.format("newMarker(%s,%s);", coord[0], coord[1]));
    }

    private void ongPane() {
        button.setId("popup-btn-ong");
        nummer = new Label("VKL Nummer   ");
        nummer.setId("popup-txt");
        tfdNummer = new TextField();
        this.add(nummer, 0, 0);
        this.add(tfdNummer, 1, 0);
        this.add(button, 1, 2);
    }

    private void hmPane() {
        button.setId("popup-btn-hm");
        weg = new Label("Weg");
        weg.setId("popup-txt");
        hectometer = new Label("Hectometer");
        hectometer.setId("popup-txt");
        letter = new Label("Letter (optioneel)");
        letter.setId("popup-txt");
        richting = new Label("Richting (optioneel)");
        richting.setId("popup-txt");
        tfdWeg = new TextField();
        tfdHecometer = new TextField();
        tfdLetter = new TextField();
        cbxRichting = new ComboBox<>();
        cbxRichting.getItems().addAll("", "Re", "Li");
        cbxRichting.getSelectionModel().selectFirst();
        this.add(weg,0, 0);
        this.add(hectometer, 0, 1);
        this.add(letter, 0, 2);
        this.add(richting, 0, 3);
        this.add(tfdWeg, 1,0);
        this.add(tfdHecometer, 1, 1);
        this.add(tfdLetter, 1, 2);
        this.add(cbxRichting, 1, 3);
        this.add(button, 1, 4);
    }

    private void searchByHectometer(SideView sv, CenterView cv) {
        this.hmPane();
        this.setId("popup-hm");
        button.setOnAction(event -> {
            OngevalController ongC = new OngevalController();
            String richting = cbxRichting.getValue();
            String hm = tfdHecometer.getText().replace(",", "");
            if (!richting.equals("")) {
                richting = richting.replaceAll("([ei])", "");
            }
            try {
                sv.loadOngeval(ongC,
                        tfdWeg.getText(),
                        hm,
                        tfdLetter.getText(),
                        richting);
                sv.searchByVKL.query.setText("-");
                setMap(ongC, cv);
            } catch (IndexOutOfBoundsException ioe) {
                Alert noInput = new Alert(Alert.AlertType.ERROR);
                noInput.setTitle("Geen input");
                noInput.setContentText("Voer gegevens in");
                noInput.show();
            } catch (NullPointerException | NoSuchElementException ne) {
                Alert notFound = new Alert(Alert.AlertType.ERROR);
                notFound.setTitle("Verkeerde input");
                notFound.setContentText("Deze hectometerpaal bestaat niet");
                notFound.show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void searchByVkl(SideView sv, CenterView cv) {
        this.ongPane();
        this.setId("popup-ong");
        button.setOnAction(event -> {
            OngevalController ongC = new OngevalController();
            try {
                sv.loadOngevalVKL(ongC, tfdNummer.getText());
                this.setMap(ongC, cv);
            } catch (NullPointerException npe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("GEEN VERBINDING");
                alert.setContentText("Kaart niet beschikbaar");
                alert.show();
                npe.printStackTrace();
            } catch (IndexOutOfBoundsException | NoSuchElementException ioe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Foutieve invoer!");
                alert.setContentText("Dit ongevalnummer bestaat niet of heeft niet plaatsgevonden bij een hectometerpaaltje");
                alert.show();
            }
        });
    }
}

