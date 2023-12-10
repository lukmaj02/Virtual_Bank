package Client.GUI.User;

import Client.Client;
import Client.dto.BankAccountDto;
import Client.dto.CardDto;
import Client.dto.UserDto;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.io.IOException;
import java.util.Set;

public class UserAccountsPage extends Client {
    @FXML
    public TableView<BankAccountDto> userAccounts;
    @FXML
    public TableColumn<BankAccountDto, String> accountNr;
    @FXML
    public TableColumn<BankAccountDto, String> balance;
    @FXML
    public TableColumn<BankAccountDto, String> currencyType;
    @FXML
    public TableColumn<BankAccountDto, String> accountType;
    @FXML
    public Button backButton;
    @FXML
    public Button createTransaction;
    @FXML
    public Button createDeposit;
    @FXML
    public Button requestCredit;
    @FXML
    public Button deleteAccount;
    @FXML
    public Button createDebitCard;
    @FXML
    public Button createCreditCard;
    @FXML
    public Button viewCards;
    private String currentAccountNr;
    private UserDto user;
    public TableView.TableViewSelectionModel<BankAccountDto> selectedAccount;
    public void initialize(Set<BankAccountDto> bankAccounts, UserDto user){
        this.user = user;
        accountNr.setCellValueFactory(new PropertyValueFactory<>("accountNr"));
        balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        currencyType.setCellValueFactory(new PropertyValueFactory<>("currencyType"));
        accountType.setCellValueFactory(new PropertyValueFactory<>("accountType"));

        ObservableList<BankAccountDto> list = FXCollections.observableList(bankAccounts.stream().toList());
        selectedAccount = userAccounts.getSelectionModel();
        selectedAccount.setSelectionMode(SelectionMode.SINGLE);
        userAccounts.setItems(list);
        userAccounts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null && !newSelection.equals(oldSelection)) {
                currentAccountNr = newSelection.getAccountNr();
            }
        });
    }

    public void executeAnAction(ActionEvent actionEvent) throws IOException {
        if(actionEvent.getSource() == backButton){
            openUserPage(actionEvent,user);
        }
        else if(actionEvent.getSource()==createTransaction){
            //todo openCreationTransactionPage(actionEvent, user);
        }
        else if(actionEvent.getSource() == createDeposit){
            //todo openCreationDepositPage(actionEvent,user);
        }
        else if(actionEvent.getSource() == requestCredit && currentAccountNr != null){
            openRequestingCreditPage(actionEvent, user, currentAccountNr);
        }
        else if (actionEvent.getSource()==deleteAccount && currentAccountNr != null) {
            var msg = sendToServerWithResponse("BANK_ACCOUNT,DELETE,"+ currentAccountNr);
            if(isResponseValid(msg)) {
                openUserPage(actionEvent,user);
                showInfo("DELETION", "Bank account deleted successfully!");
            }
        }
        else if (actionEvent.getSource() == createCreditCard && currentAccountNr != null) {
            var msg = sendToServerWithResponse("CARD,CREATE,"+currentAccountNr +",CREDIT");
            if(isResponseValid(msg)) showInfo("CREATED", "Credit card for account " +currentAccountNr + " created successfully");
        }
        else if (actionEvent.getSource() == createDebitCard && currentAccountNr != null) {
            var msg = sendToServerWithResponse("CARD,CREATE,"+currentAccountNr +",DEBIT");
            if(isResponseValid(msg)) showInfo("CREATED", "Debit card for account " +currentAccountNr + " created successfully");
        }
        else if (actionEvent.getSource()==viewCards) {
            var msg = sendToServerWithResponse("CARD,ACCOUNT_CARDS,"+currentAccountNr);
            if(isResponseValid(msg)){
                openAccountCardsPage(actionEvent, CardDto.mapper(msg),user);
            }
        }
    }
}
