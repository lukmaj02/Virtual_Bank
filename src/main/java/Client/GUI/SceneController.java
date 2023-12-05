package Client.GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SceneController extends Application {
    //first formula page
    protected String name = "";
    protected String lastName = "";
    protected String dateOfBirth = "";
    protected String gender = "";
    protected String maritalStatus = "";
    protected String address = "";
    protected String city = "";
    protected String state = "";
    protected ArrayList<String> firstPageFormulaDate = new ArrayList<>();

    // variables to control scene changes
    protected Stage _stage;
    protected Scene _scene;
    protected Parent _root;



    protected void openFrontPage(ActionEvent event, ArrayList<String> firstFormulaPageData) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontPage.fxml"));
        _root = loader.load();

        FrontPage frontPage = loader.getController();
        frontPage.firstFormulaPageData = firstFormulaPageData;

        _stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        _scene = new Scene(_root);
        _stage.setScene(_scene);
        _stage.show();
        _stage.centerOnScreen();
    }

    protected void openFormula(ActionEvent event, ArrayList<String > firstFormulaPageData) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstFormulaPage.fxml"));
        _root = loader.load();

        FirstFormulaPage formulaPage = loader.getController();
        formulaPage.initializeFormula(1, firstFormulaPageData); // change randomly|depending on users count

        _stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        _scene = new Scene(_root);
        _stage.setScene(_scene);
        _stage.show();
        _stage.centerOnScreen();
    }
    protected void openSecondFormulaPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SecondFormulaPage.fxml"));
        _root = loader.load();

        SecondFormulaPage secondFormulaPage = loader.getController();
        secondFormulaPage.initializeFormula(1); // change randomly|depending on users count

        _stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        _scene = new Scene(_root);
        _stage.setScene(_scene);
        _stage.show();
        _stage.centerOnScreen();
    }

    protected void openThirdFormulaPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ThirdFormulaPage.fxml"));
        _root = loader.load();

        ThirdFormulaPage thirdFormulaPage = loader.getController();
        thirdFormulaPage.initializeFormula(1); // change randomly|depending on users count

        _stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        _scene = new Scene(_root);
        _stage.setScene(_scene);
        _stage.show();
        _stage.centerOnScreen();
    }

    protected void _fillFirstFormulaPageData() {
        name = firstPageFormulaDate.get(0);
        lastName = firstPageFormulaDate.get(1);
        dateOfBirth = firstPageFormulaDate.get(2);
        gender = firstPageFormulaDate.get(3);
        maritalStatus = firstPageFormulaDate.get(4);
        address = firstPageFormulaDate.get(5);
        city = firstPageFormulaDate.get(6);
        state = firstPageFormulaDate.get(7);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FrontPage.fxml"));

        _root = loader.load();
        _scene = new Scene(_root);

        stage.getIcons().add(new Image("bank-icon.png"));
        stage.setTitle("Virutal Banking System.");

        stage.setScene(_scene);
        stage.setResizable(false);
        stage.show();
    }
}

