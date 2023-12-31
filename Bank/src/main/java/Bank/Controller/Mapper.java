package Bank.Controller;

import Bank.DtoModels.UserDto;
import Bank.Enums.Gender;
import Bank.Enums.MaritalStatus;
import Bank.Enums.Role;

import java.time.LocalDate;
import java.util.List;

public class Mapper {
    private Mapper(){}

    public static String[] splitedData(String data){
        return data.split(",");
    }
    public static String[] toLogin(String data) {
        return data.split(",",2);
    }
    public static String[] toCredit(String data) {return data.split(",",4);}
    public static String[] toDeposit(String data) {return data.split(",",3);}

    public static UserDto toUserDto(String data){
        var splitedData = data.split(",");
        return UserDto.builder()
                .firstName(splitedData[0])
                .lastName(splitedData[1])
                .email(splitedData[2])
                .password(splitedData[3])
                .phoneNumber(splitedData[4])
                .dateOfBirth(LocalDate.parse(splitedData[5]))
                .address(splitedData[6])
                .city(splitedData[7])
                .gender(Gender.valueOf(splitedData[8]))
                .maritalStatus(MaritalStatus.valueOf(splitedData[9]))
                .role(Role.valueOf(splitedData[10]))
                .build();
    }

    public static UserDto toEditUserInformations(String data){
        var splitedData = data.split(",");
        return UserDto.builder()
                .email(splitedData[0])
                .address(splitedData[1])
                .city((splitedData[2]))
                .phoneNumber(splitedData[3])
                .maritalStatus(MaritalStatus.valueOf(splitedData[4]))
                .build();
    }

    public static String[] toTransaction(String data){
        return data.split(",",6);
    }

    public static <T> String listToString(List<T> objects){
        StringBuilder response = new StringBuilder();
        for (T object : objects){
            response.append(object.toString());
        }
        return response.toString();
    }
}
