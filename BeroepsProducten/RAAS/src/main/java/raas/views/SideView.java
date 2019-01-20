package raas.views;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import raas.models.Ongeval;
import raas.controllers.OngevalController;
import raas.models.Partij;
import raas.controllers.PartijController;

import java.io.IOException;
import java.net.UnknownHostException;

public class SideView extends VBox {
    public SearchView searchByHM, searchByVKL;
    public OngevallenBijHmView ongevallenBijHmView;
    private CenterView cp;
    private InformationView ip;
    private Label appTitle;

    public SideView(CenterView cp, InformationView ip) {
        this.ip = ip;
        this.cp = cp;
        appTitle = new Label("RAAS");
        appTitle.setId("app-title");
        searchByHM = new SearchView("HECTOMETERPAAL", "-", "Verander HmPaal", cp, this, "paal.png");
        searchByVKL = new SearchView("ONGEVALNUMMER", "-", "Verander nummer", cp, this, "number.png");
        ongevallenBijHmView = new OngevallenBijHmView(this);
        this.getChildren().addAll(appTitle, searchByHM, searchByVKL, ongevallenBijHmView);
    }

    public void loadOngevalVKL(OngevalController ongC, String nummer) {
        Ongeval ong = ongC.findOngevalByVKL(nummer);
        Partij partij = new PartijController().findPartijByVKL(nummer);
        this.searchByVKL.query.setText(ong.getVklNummer());
        this.loadOngeval(ongC,
                ong.getWegNummer(),
                ong.getHectoMeter(),
                ong.getHectoLetter(),
                ong.getRichting());
        ip.showOngevalInformatie(ong, partij);
    }


    public void loadOngeval(OngevalController ong, String weg, String hm, String letter, String richting) {
            ip.tutorial();
            try {
                String ongevallen[] = ong.findOngevalByHm(weg, hm, letter, richting);
                this.ongevallenBijHmView.showAllOngevallen(ongevallen);
                richting = showRichting(richting);
                hm = showHm(hm);
                this.searchByHM.query.setText(String.format("%s %s%s %s", weg, hm, letter, richting));
            } catch (UnknownHostException e) {
                this.ongevallenBijHmView.noOngevallen();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                cp.removeSearchPopUp();
            }
    }

    private String showHm(String hm) {
        StringBuilder stringBuilder = new StringBuilder(hm);
        stringBuilder.insert(hm.length()-1, ",");
        return stringBuilder.toString();
    }

    private String showRichting(String richting) {
        if (!richting.equals("")) {
            richting = richting.equals("R") ? richting +"e" : richting+"i";
        }
        return richting;
    }

}
