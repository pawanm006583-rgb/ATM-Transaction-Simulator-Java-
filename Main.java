import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        ATM atm = new ATM();

        // =========================
        // CREATE ACCOUNTS
        // =========================

        Account account1 =
                new Account(
                        "10001",
                        1234,
                        10000
                );

        Account account2 =
                new Account(
                        "10002",
                        5678,
                        15000
                );

        Account account3 =
                new Account(
                        "10003",
                        4321,
                        8000
                );


        // =========================
        // CREATE CUSTOMERS
        // =========================

        Customer customer1 =
                new Customer(
                        1,
                        "Rahul",
                        "9876543210",
                        account1
                );

        Customer customer2 =
                new Customer(
                        2,
                        "Aman",
                        "9876501234",
                        account2
                );

        Customer customer3 =
                new Customer(
                        3,
                        "Rohit",
                        "9876512345",
                        account3
                );


        // =========================
        // ADD CUSTOMERS
        // =========================

        atm.addCustomer(customer1);
        atm.addCustomer(customer2);
        atm.addCustomer(customer3);


        // =========================
        // SAMPLE TRANSACTIONS
        // =========================

        account1.deposit(2000);
        account1.withdraw(500);

        account2.deposit(3000);
        account2.withdraw(1000);


        // =========================
        // START GUI
        // =========================

        SwingUtilities.invokeLater(
                () -> {

                    ATMGUI gui =
                            new ATMGUI(atm);

                    gui.setVisible(true);
                }
        );
    }
}