package Employee.GUI;

import Employee.SceneController;
import Employee.dto.EmployeeDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class FrontPage extends SceneController {
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField userPasswordPasswordField;
    @FXML
    private Button logInButton;

    public void executeAnAction(ActionEvent actionEvent) throws Exception {
        if (actionEvent.getSource() == logInButton) {
            var msg = sendToServerWithResponse("USER,LOGIN," + emailTextField.getText() + "," + userPasswordPasswordField.getText());
            if (isResponseValid(msg)) {
                var employee = EmployeeDto.mapper(msg);
                if(employee.getRole().equals("EMPLOYEE")) {
                    openEmployeePage(actionEvent, employee);
                }
            }
        }
    }
}
