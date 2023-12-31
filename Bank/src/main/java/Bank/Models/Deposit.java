package Bank.Models;

import Bank.Enums.CurrencyType;
import Bank.Enums.DepositStatus;
import Bank.Enums.DepositType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "DEPOSIT")
public class Deposit {
    @Id
    @UuidGenerator
    @Column(name = "depositId", nullable = false)
    private String depositId;

    @Column(name = "depositType", nullable = false)
    @Enumerated(EnumType.STRING)
    private DepositType depositType;

    @Column(name = "creationDate", nullable = false)
    private LocalDate creationDate;

    @Column(name = "finishDate", nullable = false)
    private LocalDate finishDate;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "depositStatus", nullable = false)
    @Enumerated(EnumType.STRING)
    private DepositStatus depositStatus;

    @Column(name = "currencyType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "accountId")
    private BankAccount bankAccountDeposit;

    @Override
    public String toString() {
        return depositId + "," +
                depositType.toString() + "," +
                creationDate.toString() + "," +
                finishDate.toString() + "," +
                amount.toString() + "," +
                currencyType.toString() + "," +
                depositStatus.toString() + "," +
                bankAccountDeposit.getAccountNr() +",,";
    }
}
