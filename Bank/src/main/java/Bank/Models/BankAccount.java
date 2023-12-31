package Bank.Models;

import Bank.Enums.AccountType;
import Bank.Enums.CurrencyType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "BANK_ACCOUNT")
public class BankAccount {
    @Id
    @UuidGenerator
    private String accountId;

    @Column(name = "accountNr", nullable = false, length = 26, unique = true)
    private String accountNr;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @Column(name = "currencyType", nullable = false)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;

    @Column(name = "accountType", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(
            mappedBy = "bankAccount",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Card> cards;

    @OneToMany(
            mappedBy = "bankAccountDeposit",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Deposit> deposits;

    @OneToMany(
            mappedBy = "bankAccountCredit",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Credit> credits;

    @OneToMany(
            mappedBy = "fromAccountNr",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Transaction> transactions;

    @Override
    public String toString() {
        return accountId + "," +
                accountNr + "," +
                balance + "," +
                currencyType.toString() + "," +
                accountType.toString() + ",,";
    }
}
