package raas.views;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.apache.commons.io.FileUtils;
import raas.CsvToJson;
import raas.controllers.ReferentieController;
import raas.UnZip;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class StartUpView extends StackPane {
    public static Path pthTempDir;
    private HBox trBackGround;
    private VBox dragNdrop;
    private Label dndText;
    private ImageView imFolder;


    public StartUpView() {
        this.getChildren().addAll(new MainView());
        this.getChildren().addAll(trBackGround());
        this.getChildren().addAll(dragNDrop());
    }

    private HBox trBackGround() {
        trBackGround = new HBox();
        trBackGround.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");
        return trBackGround;
    }

    private VBox dragNDrop() {
        dragNdrop = new VBox();
        dndText = new Label("Drop hier het BRON (Bestand geRegistreerde Ongevallen Nederland) als ZIP");
        dndText.setId("drag-drop-lbl");
        dragNdrop.setStyle("-fx-background-color: #363636;");
        dragNdrop.setId("drag-drop");
        dragNdrop.setSpacing(20);
        dragNdrop.setOnDragEntered(this::dragEntered);
        dragNdrop.setOnDragExited(this::dragExit);
        dragNdrop.setOnDragOver(this::dragOver);
        dragNdrop.setOnDragDropped(this::dragDropped);
        dragNdrop.getChildren().addAll(dndText);
        imFolder = new ImageView(new Image("folders.png"));
        dragNdrop.getChildren().add(imFolder);
        return dragNdrop;
    }

    private boolean dragNDropBool(DragEvent event) {
        return event.getGestureSource() != dragNdrop
                && event.getDragboard().hasFiles();
    }

    private void dragEntered(DragEvent event) {
        if (dragNDropBool(event)
                && !event.getDragboard().getFiles().toString().matches(".*01-01-20\\d{2}_31-12-20\\d{2}.zip.")) {
            imFolder.setImage(new Image("foldersNo.png"));
        }
    }

    private void dragExit(DragEvent event) {
        if (dragNDropBool(event)) {
            dragNdrop.setStyle("-fx-background-color: #363636");
            imFolder.setImage(new Image("folders.png"));

        }
        event.consume();
    }

    private void dragOver(DragEvent event) {
        if (dragNDropBool(event) && event.getDragboard().getFiles().toString().toLowerCase().matches(".*01-01-20\\d{2}_31-12-20\\d{2}.zip.")) {
            event.acceptTransferModes(TransferMode.COPY);
            dragNdrop.setStyle("-fx-background-color: #262626");
            imFolder.setImage(new Image("foldersOk.png"));
        }
        event.consume();
    }

    private void dragDropped(DragEvent event) {
        imFolder.setImage(new Image("foldersLoad.png"));
        dndText.setText("Een moment geduld alstublieft");
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasFiles()) {
            String zipFile = db.getFiles().toString().replace("[", "").replace("]", "");
            try {
                pthTempDir = tempDir();
                new UnZip(pthTempDir, zipFile);
                new CsvToJson(pthTempDir);
                new ReferentieController();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.getChildren().removeAll(dragNdrop, trBackGround);
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();
    }

    private Path tempDir() throws IOException {
        Path pthTempDir = Files.createTempDirectory("pechpaltmp");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                FileUtils.deleteDirectory(pthTempDir.toFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
        return pthTempDir;
    }



}
