package com.Projekt.Bankomat.Service;

import com.Projekt.Bankomat.DtoModels.RegistrarionRequest;
import com.Projekt.Bankomat.DtoModels.UserDto;
import com.Projekt.Bankomat.Exceptions.*;
import com.Projekt.Bankomat.Models.BankAccount;
import com.Projekt.Bankomat.Models.User;
import com.Projekt.Bankomat.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class UserService implements IUserService, IAdminService {
    private final UserRepo userRepo;
    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User getUser(String email) throws UserEmailNotFoundException{
        return userRepo.findByEmail(email).orElseThrow(UserEmailNotFoundException::new);
    }

    public List<User> getAllUsers(){
        return userRepo.findAll();
    }


    public void editUserInformations(String email, UserDto userDto) throws UserEmailNotFoundException{
        var uzytkownik = userRepo.findByEmail(email).orElseThrow(UserEmailNotFoundException::new);
        //todo
    }
    public Set<BankAccount> getUserBankAccounts(String email) throws UserEmailNotFoundException{
        var uzytkownik = userRepo.findByEmail(email).orElseThrow(UserEmailNotFoundException::new);
        return uzytkownik.getBankAccount();
    }

    public void registerUser(RegistrarionRequest registraionRequest) throws UserExistsException{
        if(userRepo.existsByEmail(registraionRequest.getEmail()) || userRepo.existsByPhoneNumber(registraionRequest.getPhoneNumber())){
            throw new UserExistsException(registraionRequest.getEmail(), registraionRequest.getPhoneNumber());
        }
        var uzytkownik = User.builder()
                .userId(UUID.randomUUID().toString())
                .address(registraionRequest.getAddress())
                .dateOfBirth(registraionRequest.getDateOfBirth())
                .firstName(registraionRequest.getFirstName())
                .lastName(registraionRequest.getLastName())
                .password(registraionRequest.getPassword())
                .email(registraionRequest.getEmail())
                .phoneNumber(registraionRequest.getPhoneNumber())
                .build();
        userRepo.save(uzytkownik);
    }
    public void deleteUser(String email) throws UserEmailNotFoundException, BankAccountExistsException {
        var uzytkownik = userRepo.findByEmail(email).orElseThrow(UserEmailNotFoundException::new);
        if(!uzytkownik.getBankAccount().isEmpty()){
            throw new BankAccountExistsException();
        }
        userRepo.delete(uzytkownik);
    }

    @Override
    public User login(String email, String password) throws BadCredentialsException{
        return userRepo.findByEmailAndPassword(email, password)
                .orElseThrow(BadCredentialsException::new);
    }
}
