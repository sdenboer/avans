package pechpal.views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import pechpal.models.Ongeval;
import pechpal.models.Partij;

import java.util.Arrays;
import java.util.List;

/**
 * De informationView is een Pane aan de rechterkant van de MainView borderpane en kan
 * ongevalinformatie laten zien of een handleiding.
 * @see MainView
 * @author sven
 * @version 1.0.0
 */
public class InformationView extends StackPane {
    private ScrollPane scrPane;
    private Button btnKenmerken, btnPartij;
    private GridPane p;
    private int rowIndexInc;

    /**
     * initialiseert de InformationView en maakt het een scrollpane ivm de hoeveelheid informatie
     * die weergeven kan worden.
     */
    public InformationView() {
        scrPane = new ScrollPane();
        this.setId("info-pane");
        scrPane.setId("info-scroll");
        this.getChildren().add(scrPane);
    }

    /**
     * Nadat een specifiek ongeval(nummer) is geselecteerd in de sideView kan de ongevalinformatie worden weergegeven.
     * @param ongeval is het gekozen ongeval en de kenmerken/informatie die bij het ongeval horen.
     * @param partij is de partijinformatie die gekoppeld is aan het ongeval. Bij enkele ongevallen is deze niet
     *               beschikbaar en kan dus een NULL-value zijn.
     * @see SideView
     */
    public void showOngevalInformatie(Ongeval ongeval, Partij partij) {
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        btnKenmerken = new Button("KENMERKEN");
        btnPartij = new Button("PARTIJ");
        hBox.getChildren().addAll(btnKenmerken);
        if (partij != null) {
            hBox.getChildren().addAll(btnPartij);
            btnPartij.setOnAction(event -> {
                btnKenmerken.setId("info-button");
                btnPartij.setId("info-button-pressed");
                this.rowIndexInc = 1;
                p.getChildren().clear();
                showPartijInfo(partij);
            });
        }
        btnKenmerken.setId("info-button");
        btnPartij.setId("info-button");
        this.p = new GridPane();
        btnKenmerken.setOnAction(event -> {
            btnKenmerken.setId("info-button-pressed");
            btnPartij.setId("info-button");
            this.rowIndexInc = 1;
            p.getChildren().clear();
            showOngevalKenmerken(ongeval);
        });
        btnKenmerken.fire();
        vBox.getChildren().addAll(hBox, p);
        scrPane.setContent(vBox);
    }

    /**
     * In deze method worden de kenmerken die beschikbaar zijn bij het gekozen ongeval gekoppeld aan de value.
     * Het is een lange method ivm inconsistente informatie uit het BRON bestand.
     * @param ongeval is het specifieke ongeval
     */
    private void showOngevalKenmerken(Ongeval ongeval) {
        ongeval.getOngevalKenmerken().forEach((key, value) -> {
            switch (key.toLowerCase()) {
                case "regnummer":
                    setRowContent("registratienummer", value, p);
                    break;
                case "pvopgem":
                    setRowContent("processverbaal opgemaakt", "ja", p);
                    break;
                case "datum_vkl":
                    setRowContent("datum", value, p);
                    break;
                case "dag_code":
                    setRowContent("dag code", value, p);
                    break;
                case "mnd_nummer":
                    setRowContent("maand", value, p);
                    break;
                case "jaar_vkl":
                    setRowContent("jaar", value, p);
                    break;
                case "tijdstip":
                    setRowContent("tijdstip", value, p);
                    break;
                case "dagtype":
                    setRowContent("dag", value, p);
                    break;
                case "uur":
                    setRowContent("uur", value+".00", p);
                    break;
                case "ddl_id":
                    setRowContent("dagdeel", value, p);
                    break;
                case "ap3_code":
                case "ap4_code":
                case "ap5_code":
                    setRowContent("afloop", value, p);
                    break;
                case "antl_sla":
                    setRowContent("aantal slachtoffers", value, p);
                    break;
                case "antl_dod":
                    setRowContent("aantal doden", value, p);
                    break;
                case "antl_gzh":
                    setRowContent("aantal gewonden (zkh)", value, p);
                    break;
                case "antl_seh":
                    setRowContent("aantal slachtoffers (seh)", value, p);
                    break;
                case "antl_gov":
                    setRowContent("aantal gewonden (overig)", value, p);
                    break;
                case "antl_ptj":
                    setRowContent("aantal partijen", value, p);
                    break;
                case "antl_tdt":
                    setRowContent("aantal toedrachten", value, p);
                    break;
                case "mne_code":
                    setRowContent("manouvre", value, p);
                    break;
                case "aol_id":
                    setRowContent("aard ongeval", value, p);
                    break;
                case "niveaukop":
                    switch (value.toLowerCase()) {
                        case "e" :
                            value = "gekoppeld aan bn";
                            break;
                        case "k" :
                            value = "gekoppeld op kruispuntniveau";
                            break;
                        case "s" :
                            value = "gekoppeld op straatniveau";
                            break;
                        case "g" :
                            value = "gekoppeld op gemeenteniveau";
                            break;
                    }
                    setRowContent("niveaukop", value, p);
                    break;
                case "wse_id":
                case "wse_an":
                    setRowContent("wegsituatie", value, p);
                    break;
                case "bebkom":
                    if (value.equals("bi")) {
                        setRowContent("bebouwde kom", "binnen", p);
                    } else {
                        setRowContent("bebouwde kom", "buiten", p);
                    }
                    break;
                case "maxsnelhd":
                    setRowContent("maximale snelheid", value + " km/u", p);
                    break;
                case "wvl_id":
                    setRowContent("wegverlichting", value, p);
                    break;
                case "wvg_id":
                case "wvg_an":
                    setRowContent("wegverharding", value, p);
                    break;
                case "wdk_id":
                    setRowContent("wegdek", value, p);
                    break;
                case "lgd_id":
                    setRowContent("lichtgesteldheid", value, p);
                    break;
                case "zad_id":
                    setRowContent("zichtafstand", value, p);
                    break;
                case "wgd_code_1":
                case "wgd_code_2":
                    switch (value.toLowerCase()) {
                        case "d" :
                            value = "droog";
                            break;
                        case "r" :
                            value = "regen";
                            break;
                        case "m" :
                            value = "mist";
                            break;
                        case "s" :
                            value = "sneeuw/hagel";
                            break;
                        case "h" :
                            value = "harde windstoten";
                            break;
                        case "o" :
                            value = "onbekend";
                            break;
                    }
                    setRowContent("weersgesteldheid", value, p);
                    break;
                case "bzd_id_vm1":
                case "bzd_id_vm2":
                case "bzd_id_vm3":
                case "bzd_vm_an":
                    setRowContent("bijzonderheden verkeersmaatregel", value, p);
                    break;
                case "bzd_id_if1":
                case "bzd_id_if2":
                case "bzd_id_if3":
                case "bzd_if_an":
                    setRowContent("bijzonderheden infrastructuur", value, p);
                    break;
                case "bzd_id_ta1":
                case "bzd_id_ta2":
                case "bzd_id_ta3":
                case "bzd_ta_an":
                    setRowContent("bijzonderheden aard", value, p);
                    break;
                case "gme_naam":
                    setRowContent("gemeente", value, p);
                    break;
                case "pve_naam":
                    setRowContent("provincie", value, p);
                    break;
                case "plt_naam":
                    setRowContent("politiedistrict", value, p);
                    break;
                case "dienstnaam":
                    setRowContent("dienst", value, p);
                    break;
                //these are to be ignored:
                case "dienstcode" :
                case "fk_veld5" :
                case "distrnaam" :
                case "kdd_naam" :
                case "distrcode" :
                case "pve_code" :
                case "vkl_nummer" :
                case "wvk_id" :
                case "gme_id" :
                    break;
                default:
                    //mocht het bestand vernieuwen
                    setRowContent(key, value, p);
                    break;
            }
        });
    }

    /**
     * Deze methode is vrijwel identiek aan de showOngevalKenmerken methode alleen zijn de values uit het partijenbestand
     * en niet het ongevallenbestand.
     * @param partij is de partijinformatie die bij het ongeval hoort.
     */
    private void showPartijInfo(Partij partij) {
        partij.getPartijKenmerken().forEach((key, value) -> {
            switch (key.toLowerCase()) {
                case "ptj_id":
                    setRowContent("partij id", value, p);
                    break;
                case "nummer":
                    setRowContent("volgnummer", value, p);
                    break;
                case "doorrijder":
                    setRowContent("doorrijder", "ja", p);
                    break;
                case "ote_id":
                case "ote_an":
                    setRowContent("objecttype", value, p);
                    break;
                case "ntt_code_v":
                    setRowContent("voertuig nationaliteit", value, p);
                    break;
                case "vtgverz":
                    if (value.equals("j")) {
                        setRowContent("voertuig verzekerd", "ja", p);
                    } else {
                        setRowContent("voertuig verzekerd", "nee", p);
                    }
                    break;
                case "schade":
                    if (value.equals("j")) {
                        setRowContent("schade", "ja", p);
                    } else {
                        setRowContent("schade", "nee", p);
                    }
                    break;
                case "getraanh":
                    setRowContent("aanhangwagen", "ja", p);
                    break;
                case "gevstof":
                    setRowContent("gevaarlijke stoffen", "ja", p);
                    break;
                case "vtgverl":
                    switch (value.toLowerCase()) {
                        case "nb":
                            value = "niet brandend";
                            break;
                        case "br":
                            value = "brandend";
                            break;
                        case "na":
                            value = "niet aanwezig/nvt";
                            break;
                        case "xx":
                            value = "onbekend";
                            break;
                    }
                    setRowContent("verlichting", value, p);
                    break;
                case "antl_pas":
                    setRowContent("aantal passagiers", value, p);
                    break;
                case "gebdat":
                    setRowContent("geboortedatum partij", value, p);
                    break;
                case "gebjaar":
                    setRowContent("geboortejaar partij", value, p);
                    break;
                case "leeftijd":
                    setRowContent("leeftijd partij", value, p);
                    break;
                case "lke_id":
                    setRowContent("leeftijdsklasse partij", value, p);
                    break;
                case "ntt_code_b":
                    setRowContent("nationaliteit partij", value, p);
                    break;
                case "geslacht":
                    if (value.equals("v")) {
                        setRowContent("geslacht partij", "vrouw", p);
                    } else {
                        setRowContent("geslacht partij", "man", p);
                    }
                    break;
                case "blaastest":
                    if (value.equals("j")) {
                        setRowContent("blaastest", "ja, afgenomen", p);
                    } else {
                        setRowContent("blaastest", "nee, niet afgenomen", p);
                    }
                    break;
                case "art_8":
                    switch (value.toLowerCase()) {
                        case "j" :
                            value = "ja, geconstateerd";
                            break;
                        case "w" :
                            value = "Artikel 8 niet geconstateerd, wel alcohol";
                            break;
                        case "g" :
                            value = "Geen alcohol";
                            break;
                    }
                    setRowContent("artikel 8 geconstateerd", value, p);
                    break;
                case "medicgebr":
                    setRowContent("drugs- en/of medicijngebruik", "ja", p);
                    break;
                case "rijbewgel":
                    setRowContent("geldig rijbewijs", "ja", p);
                    break;
                case "rijbewcat":
                    setRowContent("rijbewijs categorie", value, p);
                    break;
                case "rijbewbeg":
                    if (value.equals("J")) {
                        setRowContent("beginnersrijbewijs", "ja", p);
                    } else {
                        setRowContent("beginnersrijbewijs", "nee", p);
                    }
                    break;
                case "bromfcert":
                    if (value.equals("J")) {
                        setRowContent("brommercertificaat", "ja", p);
                    } else {
                        setRowContent("brommercertificaat", "nee", p);
                    }
                    break;
                case "uitgpos1":
                case "uitgpos2" :
                case "uitgpos_an" :
                    switch (value.toLowerCase()) {
                        case "1" :
                            value = "Rijbaan";
                            break;
                        case "2" :
                            value = "Fietspad/fietsstrook";
                            break;
                        case "3" :
                            value = "Trottoir/berm";
                            break;
                        case "4" :
                            value = "Vluchtheuvel/middenberm";
                            break;
                        case "5" :
                            value = "Inrit/uitrit";
                            break;
                        case "6" :
                            value = "Vluchtstrook";
                            break;
                        case "7" :
                            value = "Parkeervoorziening";
                            break;
                        case "8" :
                            value = "Tram-/busbaan";
                            break;
                    }
                    setRowContent("vastgelegde plaats", value, p);
                    break;
                case "voorgbew" :
                    switch (value.toLowerCase()) {
                        case "1" :
                            value = "Oversteken";
                            break;
                        case "2" :
                            value = "Vooruit";
                            break;
                        case "3" :
                            value = "Links rijstrook wisselen";
                            break;
                        case "4" :
                            value = "Stilstand";
                            break;
                        case "5" :
                            value = "Rechts rijstrook wisselen";
                            break;
                        case "6" :
                            value = "Linksaf";
                            break;
                        case "7" :
                            value = "Links omkeren";
                            break;
                        case "8" :
                            value = "Achteruit";
                            break;
                        case "9" :
                            value = "Rechts omkeren";
                            break;
                        case "10" :
                            value = "Rechtsaf";
                            break;
                        case "11" :
                            value = "Parkeerstand";
                            break;
                    }
                    setRowContent("Voorgenomen beweging bestuurder", value, p);
                    break;
                case "agt_type":
                    if (value.equals("V")) {
                        setRowContent("Aangrijppunt betrekking op", "voertuig", p);
                    } else {
                        setRowContent("Aangrijppunt betrekking op", "aanhangwagen", p);
                    }
                    break;
                case "agt_id_1":
                case "agt_id_2" :
                    setRowContent("Aangrijppunt", value, p);
                    break;
                case "bwg_id_1":
                case "bwg_id_2" :
                case "bwg_an" :
                    setRowContent("Beweging", value, p);
                    break;
                case "tdt_id_1":
                case "tdt_id_2" :
                case "tdt_id_3" :
                case "tdt_an" :
                    setRowContent("toedracht", value, p);
                    break;
                    //these are to be ignored:
                case "vkl_nummer" :
                    break;
                default:
                    //mocht het bestand vernieuwen
                    setRowContent(key, value, p);
                    break;
            }
        });
    }

    /**
     * Deze methode plaatst de informatie op de informationView.
     * @param strLabel is de Key van de kenmerken die een label is
     * @param value is de value die bij de key van strLabel hoort.
     * @param p is de gridpane waar de informatie geplaatst wordt
     */
    private void setRowContent(String strLabel, String value, GridPane p) {
        Label label = new Label(strLabel.toUpperCase());
        Label val = new Label(value.toUpperCase());
        label.setId("info-label");
        val.setId("info-value");
        p.add(new Label(""), 0, rowIndexInc+1);
        p.add(label, 0, rowIndexInc+2);
        p.add(val, 0, rowIndexInc+3);
        rowIndexInc = rowIndexInc+3;
    }

    public void tutorial() {
        Label title = new Label("HANDLEIDING");
        title.setId("side-title");
        p = new GridPane();
        Label explSearchByHM = new Label("Zoeken op Hectometerpaaltje");
        explSearchByHM.setId("info-label");
        Label weg = new Label("WEG: ");
        weg.setId("info-label");
        Label explWeg = new Label("Bijvoorbeel A1 of N198.");
        explWeg.setId("info-value");
        Label hm = new Label("HECTOMETER:");
        hm.setId("info-label");
        Label explHm = new Label("Bijvoorbeeld 4,0 of 55,6.");
        explHm.setId("info-value");
        Label ltr = new Label("LETTER:");
        ltr.setId("info-label");
        Label explLtr = new Label("Bijvoorbeeld 'm' of 's'.");
        explLtr.setWrapText(true);
        explLtr.setId("info-value");
        Label richting = new Label("RICHTING:");
        richting.setId("info-label");
        Label explRichting = new Label("Bijvoorbeeld 'Rechts' of 'Links'.");
        explRichting.setId("info-value");
        Label explSearchByVKL = new Label("Zoeken op Ongevalnummer (VKL)");
        explSearchByVKL.setId("info-label");
        Label nr = new Label("NUMMER:");
        nr.setId("info-label");
        Label explNr = new Label("Bijvoorbeeld '320070576809'");
        explNr.setId("info-value");
        List<Label> labels = Arrays.asList(title,
                explSearchByHM,
                new Label(""),
                weg,
                explWeg,
                new Label(""),
                hm,
                explHm,
                new Label(""),
                ltr,
                explLtr,
                new Label(""),
                richting,
                explRichting,
                new Label(""),
                new Label(""),
                explSearchByVKL,
                new Label(""),
                nr,
                explNr);
        for (int j = 0; j < labels.size(); j++) {
            p.add(labels.get(j), 0, j);
        }
        this.scrPane.setContent(p);
    }

}
