package FrontEnd;

import Program.Batch;
import Program.Freezer;
import Program.Food;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;

public class FrontEndController {
    @FXML private TextField product;
    @FXML private Button addItem;
    @FXML private VBox ListObx;
    @FXML private AnchorPane rootNode;
    @FXML public Label foodMessage;
    // Overlay items
    @FXML private TextField addWeight;
    @FXML private TextField expiryDate;
    @FXML private TextField consumeWeight;
    @FXML private Label message;


    Food currentProduct;
    Parent overlaidLayer;
    Freezer mc  = new Freezer();;

    @FXML void initialize() {
        restartApp();
    }

    private void restartApp() {
        ListObx.getChildren().clear();
        String button_style = "-fx-background-color: #333;";
        for (Food _b : this.mc.FoodItems) {
            HBox root = new HBox();
            VBox vb = new VBox();

            Label bL = new Label("Total Weight : ");
            Label cL = new Label("Total Consumed : ");
            Label dL = new Label("Total Batches : ");

            Label aD = new Label(_b.name);
            Label bD = new Label(String.format("%.2f", _b.GetTotalWeight()) + " KG");
            Label cD = new Label("NA");
            Label dD = new Label("NA");

            aD.setStyle("-fx-font-weight: bold; -fx-font-size: 20; -fx-font-family: Avenir");
            bL.setStyle("-fx-font-weight: bold; -fx-font-family: Avenir");
            cL.setStyle("-fx-font-weight: bold; -fx-font-family: Avenir");
            dL.setStyle("-fx-font-weight: bold; -fx-font-family: Avenir");

            HBox a = new HBox(new Pane(){{ setPrefWidth(10); }}, aD);
            HBox b = new HBox(new Pane(){{ setPrefWidth(10); }}, bL, bD);
            HBox c = new HBox(new Pane(){{ setPrefWidth(10); }}, cL, cD);
            HBox d = new HBox(new Pane(){{ setPrefWidth(10); }}, dL, dD);

            vb.getChildren().addAll(a,b,c,d);

            VBox buttonList = new VBox();
            Button addBatch = new Button("Add Product Batch") {{setStyle(button_style); setPrefWidth(150); }};
            Button consumeBatch = new Button("Consume Product") {{setStyle(button_style); setPrefWidth(150); }};
            Button deleteItem = new Button("Delete Item") {{setStyle(button_style); setPrefWidth(150); }};

            addBatch.setTextFill(Color.WHITE);
            consumeBatch.setTextFill(Color.WHITE);
            deleteItem.setTextFill(Color.WHITE);

            consumeBatch.setOnMouseClicked(mouseEvent -> {
                try {
                    OverlayPage("SubtractProducts.fxml");
                    currentProduct = _b;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            addBatch.setOnMouseClicked(mouseEvent -> {
                try {
                    OverlayPage("AddProducts.fxml");
                    currentProduct = _b;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error");
                }
            });

            deleteItem.setOnMouseClicked(mouseEvent -> {
                mc.FoodItems.remove(_b);
                restartApp();
            });

            buttonList.getChildren().addAll(addBatch, consumeBatch, deleteItem);
            root.getChildren().add(vb);
            root.getChildren().add(buttonList);

            buttonList.spacingProperty().set(5);
            root.spacingProperty().set(50);
            root.paddingProperty().setValue(new Insets(5));

            buttonList.setOpacity(0);
            root.setOnMouseEntered(mouseEvent -> FadeIn(buttonList, 0.15));
            root.setOnMouseExited(mouseEvent -> FadeOut(buttonList, 0.15));

            ListObx.getChildren().add(root);

        }
    }

    // FX stuff to make it fancy
    public void FadeIn(Parent target, double _d) {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(target.opacityProperty(), 1, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(_d), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void FadeOut(Parent target, double _d) {
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(target.opacityProperty(), 0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.seconds(_d), kv);
        timeline.getKeyFrames().add(kf);
        timeline.play();
    }

    public void OverlayPage(String _source) throws IOException {
        FXMLLoader rootLoader = new FXMLLoader(getClass().getResource(_source));
        rootLoader.setController(this);
        overlaidLayer = rootLoader.load();
        rootNode.getChildren().add(overlaidLayer);
    }

    public void RemoveOverLayPage(Parent target) {
        rootNode.getChildren().remove(target);
    }

    // Getting values to consume
    @FXML void ConsumeBatch() {

            this.currentProduct.Consume(Double.parseDouble(consumeWeight.getText()));
            this.restartApp();
            this.RemoveOverLayPage(overlaidLayer);
    }

    // Getting values to add
    @FXML void AddBatch() {
        if(addWeight.getText().isEmpty()||expiryDate.getText().isEmpty()){
            message.setText("Please add a weight and expiry");
        }else {
            message.setText("");
            Batch nb = new Batch(Double.parseDouble(addWeight.getText()), (Integer.parseInt(expiryDate.getText())));
            this.currentProduct.Batches.add(nb);
            this.restartApp();
            this.RemoveOverLayPage(overlaidLayer);
        }
    }

    @FXML void RemoveOverlayGUI(MouseEvent event) {
        RemoveOverLayPage(overlaidLayer);
    }

    @FXML void AddItemClicked(MouseEvent event) {

        if(product.getText().isEmpty()){
            foodMessage.setText("Please enter food");
        } else{
            foodMessage.setText("");
            Food _x = new Food(product.getText());
            this.mc.FoodItems.add(_x);
            restartApp();
        }
        product.setText("");

    }
}
