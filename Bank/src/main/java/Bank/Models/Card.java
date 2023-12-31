package Bank.Models;

import Bank.Enums.CardType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "PAYMENT_CARD")
public class Card {
    @Id
    @UuidGenerator
    @Column(name = "cardId", nullable = false)
    private String cardId;

    @Column(name = "cardNr",nullable = false, length = 16)
    private String cardNr;

    @Column(name = "expirationDate", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "cvc", nullable = false, length = 3)
    private String cvc;

    @Column(name = "cardType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name ="isDiscard", nullable = false)
    private boolean isDiscard = true;

    @Column(name = "pin", nullable = false, length = 4)
    private String pin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private BankAccount bankAccount;

    @Override
    public String toString() {
        return
                cardId + "," +
                cardNr + "," +
                expirationDate + "," +
                cvc + "," +
                cardType.toString() + "," +
                isDiscard + "," +
                pin + ",,";
    }
}
