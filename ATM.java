import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

public class ATM {

    private ArrayList<Customer> customers;
    private HashMap<String, Account> accounts;
    private TreeMap<String, Account> sortedAccounts;

    // Constructor
    public ATM() {
        customers = new ArrayList<>();
        accounts = new HashMap<>();
        sortedAccounts = new TreeMap<>();
    }

    // =========================
    // CREATE
    // =========================

    public void addCustomer(Customer customer) {

        customers.add(customer);

        Account account = customer.getAccount();

        accounts.put(
                account.getAccountNumber(),
                account
        );

        sortedAccounts.put(
                account.getAccountNumber(),
                account
        );
    }

    // =========================
    // SEARCH ACCOUNT
    // =========================

    public Account findAccount(String accountNumber) {

        return accounts.get(accountNumber);
    }

    public void searchAccount(String accountNumber) {

        Account account = accounts.get(accountNumber);

        if (account == null) {

            System.out.println("Account not found.");

            return;
        }

        System.out.println("\n===== ACCOUNT FOUND =====");

        System.out.println(
                "Account Number: "
                        + account.getAccountNumber()
        );

        System.out.println(
                "Current Balance: ₹"
                        + account.getBalance()
        );
    }

    // =========================
    // DISPLAY CUSTOMERS
    // =========================

    public void displayCustomers() {

        System.out.println("\n===== CUSTOMER RECORDS =====");

        if (customers.isEmpty()) {

            System.out.println("No customers available.");

            return;
        }

        for (Customer customer : customers) {

            System.out.println(customer);
        }
    }

    // =========================
    // DISPLAY ACCOUNTS
    // =========================

    public void displayAccounts() {

        System.out.println("\n===== ACCOUNT RECORDS =====");

        if (accounts.isEmpty()) {

            System.out.println("No accounts available.");

            return;
        }

        for (Account account : accounts.values()) {

            System.out.println(
                    "Account Number: "
                            + account.getAccountNumber()
                            + " | Balance: ₹"
                            + account.getBalance()
            );
        }
    }

    // =========================
    // SORT ACCOUNTS
    // =========================

    public void displaySortedAccounts() {

        System.out.println("\n===== SORTED ACCOUNTS =====");

        if (sortedAccounts.isEmpty()) {

            System.out.println("No accounts available.");

            return;
        }

        for (Account account : sortedAccounts.values()) {

            System.out.println(
                    "Account Number: "
                            + account.getAccountNumber()
                            + " | Balance: ₹"
                            + account.getBalance()
            );
        }
    }

    // =========================
    // DELETE ACCOUNT
    // =========================

    public void deleteAccount(String accountNumber) {

        Account account = accounts.remove(accountNumber);

        if (account == null) {

            System.out.println("Account not found.");

            return;
        }

        sortedAccounts.remove(accountNumber);

        customers.removeIf(
                customer ->
                        customer.getAccount()
                                .getAccountNumber()
                                .equals(accountNumber)
        );

        System.out.println(
                "Account deleted successfully."
        );
    }

    // =========================
    // LOGIN
    // =========================

    public Account login(
            String accountNumber,
            int pin) {

        Account account =
                accounts.get(accountNumber);

        if (account == null) {

            System.out.println(
                    "Account not found."
            );

            return null;
        }

        if (!account.validatePin(pin)) {

            System.out.println(
                    "Invalid PIN."
            );

            return null;
        }

        System.out.println(
                "\nLogin successful!"
        );

        return account;
    }

    // =========================
    // ATM MENU
    // =========================

    public void showMenu(Account account) {

        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println(
                    "\n=========================="
            );

            System.out.println(
                    "       ATM MENU"
            );

            System.out.println(
                    "=========================="
            );

            System.out.println(
                    "1. Balance Enquiry"
            );

            System.out.println(
                    "2. Deposit"
            );

            System.out.println(
                    "3. Withdrawal"
            );

            System.out.println(
                    "4. Mini Statement"
            );

            System.out.println(
                    "5. Logout"
            );

            System.out.print(
                    "Enter your choice: "
            );

            int choice;

            try {

                choice = scanner.nextInt();

            } catch (Exception e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );

                scanner.nextLine();

                continue;
            }

            switch (choice) {

                // -------------------------
                // BALANCE
                // -------------------------

                case 1:

                    System.out.println(
                            "\n===== BALANCE ENQUIRY ====="
                    );

                    System.out.println(
                            "Account Number: "
                                    + account.getAccountNumber()
                    );

                    System.out.println(
                            "Available Balance: ₹"
                                    + account.getBalance()
                    );

                    break;

                // -------------------------
                // DEPOSIT
                // -------------------------

                case 2:

                    System.out.print(
                            "\nEnter deposit amount: ₹"
                    );

                    try {

                        double depositAmount =
                                scanner.nextDouble();

                        account.deposit(
                                depositAmount
                        );

                        System.out.println(
                                "Amount deposited successfully."
                        );

                        System.out.println(
                                "New Balance: ₹"
                                        + account.getBalance()
                        );

                    } catch (IllegalArgumentException e) {

                        System.out.println(
                                e.getMessage()
                        );

                    } catch (Exception e) {

                        System.out.println(
                                "Invalid amount."
                        );

                        scanner.nextLine();
                    }

                    break;

                // -------------------------
                // WITHDRAW
                // -------------------------

                case 3:

                    System.out.print(
                            "\nEnter withdrawal amount: ₹"
                    );

                    try {

                        double withdrawalAmount =
                                scanner.nextDouble();

                        account.withdraw(
                                withdrawalAmount
                        );

                        System.out.println(
                                "Please collect your cash."
                        );

                        System.out.println(
                                "Remaining Balance: ₹"
                                        + account.getBalance()
                        );

                    } catch (IllegalArgumentException e) {

                        System.out.println(
                                e.getMessage()
                        );

                    } catch (Exception e) {

                        System.out.println(
                                "Invalid amount."
                        );

                        scanner.nextLine();
                    }

                    break;

                // -------------------------
                // MINI STATEMENT
                // -------------------------

                case 4:

                    account.displayTransactions();

                    break;

                // -------------------------
                // LOGOUT
                // -------------------------

                case 5:

                    System.out.println(
                            "\nLogged out successfully."
                    );

                    return;

                // -------------------------
                // INVALID CHOICE
                // -------------------------

                default:

                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }
    }

    // =========================
    // ACCOUNT REPORT
    // =========================

    public void generateAccountReport() {

        System.out.println(
                "\n===== ACCOUNT REPORT ====="
        );

        System.out.println(
                "Total Customers: "
                        + customers.size()
        );

        System.out.println(
                "Total Accounts: "
                        + accounts.size()
        );

        double totalBalance = 0;

        for (Account account : accounts.values()) {

            totalBalance +=
                    account.getBalance();
        }

        System.out.println(
                "Total Bank Balance: ₹"
                        + totalBalance
        );
    }

    // =========================
    // TRANSACTION REPORT
    // =========================

    public void generateTransactionReport() {

        System.out.println(
                "\n===== TRANSACTION REPORT ====="
        );

        int totalTransactions = 0;

        for (Account account : accounts.values()) {

            totalTransactions +=
                    account.getTransactions().size();
        }

        System.out.println(
                "Total Transactions: "
                        + totalTransactions
        );
    }

    // =========================
    // GETTERS
    // =========================

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public HashMap<String, Account> getAccounts() {
        return accounts;
    }

    public TreeMap<String, Account> getSortedAccounts() {
        return sortedAccounts;
    }
}