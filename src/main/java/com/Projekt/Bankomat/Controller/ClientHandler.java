package com.Projekt.Bankomat.Controller;

import com.Projekt.Bankomat.Controller.Commands.*;
import com.Projekt.Bankomat.DecryptionManager;
import com.Projekt.Bankomat.Enums.AccountType;
import com.Projekt.Bankomat.Enums.CardType;
import com.Projekt.Bankomat.Enums.CurrencyType;
import com.Projekt.Bankomat.Enums.TransactionType;
import com.Projekt.Bankomat.Exceptions.*;
import com.Projekt.Bankomat.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static com.Projekt.Bankomat.Controller.Mapper.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.Callable;

@Component
public class ClientHandler implements Callable<String> {

    private Socket clientSocket;
    private final IUserService userService;
    private final ITransactionService transactionService;
    private final IBankAccountService bankAccountService;
    private final ICardService cardService;
    private final IAdminService adminService;
    private final DecryptionManager decryptionManager;

    @Autowired
    public ClientHandler(IUserService userService,
                         ITransactionService transactionService,
                         IBankAccountService bankAccountService,
                         ICardService cardService,
                         IAdminService adminService, DecryptionManager decryptionManager) {
        this.userService = userService;
        this.transactionService = transactionService;
        this.bankAccountService = bankAccountService;
        this.cardService = cardService;
        this.adminService = adminService;
        this.decryptionManager = decryptionManager;
    }

    public void initSocket(Socket clientSocket){
        this.clientSocket = clientSocket;
    }

    @Override
    public String call() {
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter sender = new PrintWriter(clientSocket.getOutputStream(), true);

            while(true){
                String message = decryptionManager.decrypt(reader.readLine());
                String[] part = message.split(",", 3);
                MainCommand controller = MainCommand.valueOf(part[0].toUpperCase());
                String command = part[1].toUpperCase();
                String data = part[2];


                try{
                    String response = "OK,";
                    switch (controller) {
                        case USER ->
                            response = userController(UserCommand.valueOf(command), data);
                        case TRANSACTION ->
                            response = transactionController(TransactionCommand.valueOf(command), data);
                        case BANK_ACCOUNT ->
                            response = bankAccountController(BankAccountCommand.valueOf(command), data);
                        case CARD ->
                            response = cardController(CardCommand.valueOf(command), data);
                        default -> throw new InvalidCommandException();
                    }
                    sender.println(response);
                }
                catch(RuntimeException e){
                    sender.println("ERROR," + e.getMessage());
                }
            }
        }
        catch(SocketException e){
            System.out.println("Client Disconnected");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return "Task completed";
    }

    private String userController(UserCommand command, String data) throws RuntimeException{
        String systemResponse = "";
        switch (command) {
            case DELETE -> userService.deleteUser(data);
            case REGISTER -> userService.registerUser(toUserDto(data));
            case EDIT -> userService.editUserInformations(toUserDto(data));
            case GET_USER -> systemResponse += (adminService.getUser(data));
            case LOGIN -> {
                var loginInf = toLogin(data);
                systemResponse += (userService.login(loginInf[0], loginInf[1]).toString());
            }
            case GET_ALL -> listToString(adminService.getAllUsers());
            default -> throw new InvalidCommandException();
        }
        return systemResponse;
    }

    private String transactionController(TransactionCommand command, String data){
        String systemResponse = "";
        switch (command) {
            case CREATE -> {
                var info = toTransaction(data);
                transactionService.createTransaction(
                        info[0],
                        info[1],
                        new BigDecimal(info[2]),
                        info[3],
                        CurrencyType.valueOf(info[4]),
                        TransactionType.valueOf(info[5]));
            }
            case SUC_FROM_ACC ->
                    systemResponse += listToString(transactionService.getAllSuccessfullySentTransactionsFromBankAccount(data));
            case NOT_SUC_FROM_ACC ->
                    systemResponse += listToString(transactionService.getAllNotSuccessfullySentTransactionsFromBankAccount(data));
            case SUC_TO_ACC ->
                    systemResponse += listToString(transactionService.getAllSuccessfullySentTransactionsToBankAccount(data));
            case ALL_FROM_ACC ->
                    systemResponse += listToString(transactionService.getAllTransactionFromAccount(data));
            case ALL_TO_USER ->
                    systemResponse += listToString(transactionService.getAllTransactionsSentToUser(data));
            case ALL_FROM_USER ->
                    systemResponse += listToString(transactionService.getAllTransactionsSentByUser(data));
            case ALL_USER ->
                    systemResponse += listToString(transactionService.getAllUserTransactions(data));
            default ->
                    throw new InvalidCommandException();
        }
        return systemResponse;
    }

    private String bankAccountController(BankAccountCommand command, String data){
        String systemResponse = "";
        switch (command) {
            case DELETE -> bankAccountService.deleteAccount(data);
            case CREATE -> {
                var splitedData = splitedData(data);
                bankAccountService.createAccount(
                        splitedData[0],
                        AccountType.valueOf(splitedData[1]),
                        CurrencyType.valueOf(splitedData[2]));
            }
            case USER_ACCOUNTS ->
                    systemResponse += listToString(bankAccountService.getUserBankAccounts(data));
            default -> throw new InvalidCommandException();
        }
        return systemResponse;
    }

    private String cardController(CardCommand command, String data){
        String systemResponse = "";
        switch (command){
            case DELETE ->
                cardService.deleteCard(data);
            case DISCARD ->
                cardService.discardCard(data);
            case EXTEND ->
                cardService.extendExpirationDate(data);
            case CREATE -> {
                var splitedData = splitedData(data);
                cardService.createCard(
                splitedData[0],
                CardType.valueOf(splitedData[1]));
            }
            case PAY -> {
                var splitedData = splitedData(data);
                cardService.paymentByCard(
                        splitedData[0],
                        splitedData[1],
                        splitedData[2],
                        splitedData[3],
                        splitedData[4],
                        new BigDecimal(splitedData[5]));
            }
        }
        return systemResponse;
    }
}