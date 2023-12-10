package Client.GUI.User;

import Client.Client;
import Client.dto.BankAccountDto;
import Client.dto.UserDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Optional;

public class UserPage extends Client {

    @FXML
    public Button deleteUser;
    @FXML
    public Button viewDeposits;
    @FXML
    public Button viewTransactions;
    @FXML
    public Button viewBankAccounts;
    @FXML
    public Button accountCreation;
    @FXML
    public Button userInformations;
    @FXML
    public Button viewCredits;

    private UserDto user;

    public void initialize(UserDto user) {
        this.user = user;
    }

    public void executeAnAction(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource() == userInformations){
            openUserInfo(actionEvent,user);
        }
        else if(actionEvent.getSource() == deleteUser){
            var msg = sendToServerWithResponse("USER,DELETE," + user.getEmail());
            if(isResponseValid(msg)) openFrontPage(actionEvent);
        }
        else if(actionEvent.getSource() == accountCreation){
            openAccountCreationPage(actionEvent, user);
        }
        else if(actionEvent.getSource() == viewBankAccounts){
            var msg = sendToServerWithResponse("BANK_ACCOUNT,USER_ACCOUNTS," + user.getEmail());
            if(isResponseValid(msg)) openUserAccounts(actionEvent, BankAccountDto.mapper(msg), user);
        }
        else if(actionEvent.getSource() == viewTransactions){
            var msg = sendToServerWithResponse("TRANSACTION,ALL_USER," + user.getEmail());
            if(isResponseValid(msg)) {
                //todo
            }
        } else if (actionEvent.getSource() == viewCredits) {
            //todo var msg = sendToServerWithResponse("CREDIT,");
        }
    }
}