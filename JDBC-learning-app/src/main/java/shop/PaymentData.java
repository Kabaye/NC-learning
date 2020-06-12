package shop;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(staticName = "of")
public class PaymentData {
    private long customerId;
    private long bankAccount;
    private String accountCurrency;

    public PaymentData(long bankAccount, String accountCurrency) {
        customerId = 0l;

        this.bankAccount = bankAccount;
        this.accountCurrency = accountCurrency;
    }

    public static PaymentData getBasicInstance() {
        return new PaymentData(0, "BYN");
    }

    @Override
    public String toString() {
        return "PaymentData{" +
                "bankAccount = " + bankAccount +
                ", accountCurrency = '" + accountCurrency + '\'' +
                '}';
    }
}
