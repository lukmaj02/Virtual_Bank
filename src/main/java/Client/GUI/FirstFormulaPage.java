package Client.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class FirstFormulaPage extends SceneController {
    public TextField lastNameTextField;
    @FXML
    private ComboBox<String> maritalStatusComboBox;
    @FXML
    private Button nextPageButton;
    @FXML
    private Button previousPageButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private DatePicker dateOfBirthDatePicker;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField stateTextField;
    @FXML
    private Label formulaLabel;

    public ArrayList<String > secondPageData = new ArrayList<>();
    public ArrayList<String > firstPageData = new ArrayList<>();
    public ArrayList<String > thirdPageData = new ArrayList<>();

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FirstFormulaPage.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void initializeFormula(Integer number, ArrayList<String> firstPageFormulaDate, ArrayList<String > secondPageFormulaDate, ArrayList<String> thirdFormulaPageData) {
        formulaLabel.setText("Formula Page no. " + Integer.toString(number));
        secondPageData = secondPageFormulaDate;
        thirdPageData = thirdFormulaPageData;
        initalizeComboBox();
        initalizeData(firstPageFormulaDate);
    }

    private void initalizeData(ArrayList<String> firstPageFormulaDate) {
        if (firstPageFormulaDate.isEmpty())
            return;
        nameTextField.setText(firstPageFormulaDate.get(0));
        lastNameTextField.setText(firstPageFormulaDate.get(1));

        if (!Objects.equals(firstPageFormulaDate.get(2), "")) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            LocalDate date = LocalDate.parse(firstPageFormulaDate.get(2), dateTimeFormatter);
            dateOfBirthDatePicker.setValue(date);
        }

        if (Objects.equals(firstPageFormulaDate.get(3), "Male"))
            maleRadioButton.setSelected(true);
        else if (Objects.equals(firstPageFormulaDate.get(3), "Female"))
            femaleRadioButton.setSelected(true);

        // TODO check
        if (! Objects.equals(firstPageFormulaDate.get(4), ""))
            maritalStatusComboBox.setValue(firstPageFormulaDate.get(4));

        addressTextField.setText(firstPageFormulaDate.get(5));
        cityTextField.setText(firstPageFormulaDate.get(6));
        stateTextField.setText(firstPageFormulaDate.get(7));
    }

    private void initalizeComboBox() {
        maritalStatusComboBox.getItems().removeAll(maritalStatusComboBox.getItems());
        maritalStatusComboBox.getItems().addAll("Single", "Married", "Divorced", "Widowed");
    }

    private void fillFirstPageVariables() {
        firstPageData.clear();

        String dateAsString = "";
        if (dateOfBirthDatePicker.getValue() != null) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            dateAsString = dateOfBirthDatePicker.getValue().format(dateTimeFormatter);
        }

        String genderAsString = "";
        if (maleRadioButton.isSelected() || femaleRadioButton.isSelected())
            genderAsString = maleRadioButton.isSelected() ? "Male": "Female";

        String maritalStatusAsString = "";
        if (!maritalStatusComboBox.getSelectionModel().isEmpty())
            maritalStatusAsString = maritalStatusComboBox.getValue();

        firstPageData.addAll(Arrays.asList
                (
                    nameTextField.getText(), lastNameTextField.getText(), dateAsString, genderAsString,
                    maritalStatusAsString, addressTextField.getText(), cityTextField.getText(),
                    stateTextField.getText()
                )
        );
    }

    public void executeAnAction(ActionEvent actionEvent) throws Exception {
        if (actionEvent.getSource() == maleRadioButton) {
            if (femaleRadioButton.isSelected())
                femaleRadioButton.setSelected(false);
        } else if (actionEvent.getSource() == femaleRadioButton) {
            if (maleRadioButton.isSelected())
                maleRadioButton.setSelected(false);
        } else if (actionEvent.getSource() == nextPageButton) {
            //TODO przypisać tutaj wartość z formula page albo new arrayList w zależności co będzie
            fillFirstPageVariables();

            SecondFormulaPage secondFormulaPage = new SecondFormulaPage();
            secondFormulaPage.firstPageData = firstPageData;
            secondFormulaPage.secondPageData = secondPageData;
            secondFormulaPage.thirdPageData = thirdPageData;

            openSecondFormulaPage(actionEvent, firstPageData, secondPageData, thirdPageData);

        } else if (actionEvent.getSource() == previousPageButton) {
            fillFirstPageVariables();

            // fill ArrayList from frontpage with proper values
            FrontPage frontPage = new FrontPage();
            frontPage.firstFormulaPageData = firstPageFormulaDate;
            frontPage.secondPageFormulaDate = secondPageData;

            openFrontPage(actionEvent, frontPage.firstFormulaPageData);
        }
    }

}