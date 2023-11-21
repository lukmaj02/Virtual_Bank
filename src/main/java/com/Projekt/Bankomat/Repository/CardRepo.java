package com.Projekt.Bankomat.Repository;

import com.Projekt.Bankomat.Models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card, String> {
    Optional<Card> findByCardNr(String nrKarty);
//    @Query(value = "SELECT k FROM KARTA_PLATNICZA k JOIN bankAccount kb " +
//            "WHERE k.cardNr = ?1 and k.cvc = ?2 and kb.userId = " +
//            "(SELECT u.userId FROM UZYTKOWNIK u WHERE u.firstName = ?3 and u.lastName = ?4)")
//    Optional<Card> findUserCard(String cardNr,
//                                                    String cvc,
//                                                    String firstName,
//                                                    String lastName);

    @Query(value = "SELECT k FROM KARTA_PLATNICZA k JOIN bankAccount kb JOIN user u " +
            "WHERE k.cardNr = ?1 and k.cvc = ?2 and u.firstName = ?3 and u.lastName = ?4")
    Optional<Card> findUserCard(String nrKarty,
                                String cvc,
                                String imie,
                                String nazwisko);

}
