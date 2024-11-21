import java.util.HashMap;

public class BankService {
    private HashMap<String, Double> accounts;

    public BankService() {
        accounts = new HashMap<>();
        // Sample accounts
        accounts.put("ACC123", 1000.0);
        accounts.put("ACC456", 500.0);
        accounts.put("ACC789", 1200.0);
    }

    public boolean processTransaction(String accountNumber, double amount) {
        if (accounts.containsKey(accountNumber)) {
            double balance = accounts.get(accountNumber);
            if (balance >= amount) {
                accounts.put(accountNumber, balance - amount);
                return true;
            }
        }
        return false;
    }
}
