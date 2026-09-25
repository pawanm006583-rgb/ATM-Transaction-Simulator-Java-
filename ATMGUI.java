import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.*;

public class ATMGUI extends JFrame {

    private ATM atm;
    private Account loggedInAccount;

    private JTextField accountField;
    private JPasswordField pinField;

    public ATMGUI(ATM atm) {

        this.atm = atm;

        setTitle("ATM Transaction Simulator");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        showLoginScreen();
    }

    // =========================================================
    // LOGIN SCREEN
    // =========================================================

    private void showLoginScreen() {

        getContentPane().removeAll();

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                BorderFactory.createEmptyBorder(
                        40, 50, 40, 50
                )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        // TITLE
        JLabel title = new JLabel(
                "ATM TRANSACTION SIMULATOR",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        panel.add(title, gbc);


        // SUBTITLE
        JLabel subtitle = new JLabel(
                "Secure Banking Login",
                SwingConstants.CENTER
        );

        subtitle.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );

        gbc.gridy = 1;

        panel.add(subtitle, gbc);


        // ACCOUNT NUMBER LABEL
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;

        panel.add(
                new JLabel("Account Number:"),
                gbc
        );


        // ACCOUNT NUMBER FIELD
        accountField = new JTextField();

        gbc.gridx = 1;

        panel.add(
                accountField,
                gbc
        );


        // PIN LABEL
        gbc.gridx = 0;
        gbc.gridy = 3;

        panel.add(
                new JLabel("PIN:"),
                gbc
        );


        // PIN FIELD
        pinField = new JPasswordField();

        gbc.gridx = 1;

        panel.add(
                pinField,
                gbc
        );


        // LOGIN BUTTON
        JButton loginButton =
                new JButton("LOGIN");

        loginButton.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        14
                )
        );

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;

        panel.add(
                loginButton,
                gbc
        );


        loginButton.addActionListener(
                e -> login()
        );


        // TEST ACCOUNT INFORMATION
        JLabel testAccounts = new JLabel(
                "<html><center>"
                        + "Test Accounts:<br>"
                        + "10001 / 1234 &nbsp;&nbsp; "
                        + "10002 / 5678 &nbsp;&nbsp; "
                        + "10003 / 4321"
                        + "</center></html>",
                SwingConstants.CENTER
        );

        testAccounts.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        12
                )
        );

        gbc.gridy = 5;

        panel.add(
                testAccounts,
                gbc
        );


        add(panel);

        revalidate();
        repaint();
    }


    // =========================================================
    // LOGIN
    // =========================================================

    private void login() {

        String accountNumber =
                accountField.getText().trim();

        String pinText =
                new String(
                        pinField.getPassword()
                ).trim();


        // VALIDATION
        if (accountNumber.isEmpty()
                || pinText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter account number and PIN.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        try {

            int pin =
                    Integer.parseInt(pinText);


            // FIND ACCOUNT
            Account account =
                    atm.findAccount(
                            accountNumber
                    );


            if (account == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Account not found.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }


            // VALIDATE PIN
            if (!account.validatePin(pin)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Invalid PIN.",
                        "Login Failed",
                        JOptionPane.ERROR_MESSAGE
                );

                pinField.setText("");

                return;
            }


            // LOGIN SUCCESS
            loggedInAccount = account;

            JOptionPane.showMessageDialog(
                    this,
                    "Login successful!",
                    "Welcome",
                    JOptionPane.INFORMATION_MESSAGE
            );

            showDashboard();


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "PIN must contain numbers only.",
                    "Invalid PIN",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // DASHBOARD
    // =========================================================

    private void showDashboard() {

        getContentPane().removeAll();


        JPanel mainPanel =
                new JPanel(
                        new BorderLayout(
                                20,
                                20
                        )
                );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );


        // =====================================================
        // HEADER
        // =====================================================

        JPanel headerPanel =
                new JPanel(
                        new GridLayout(
                                2,
                                1
                        )
                );


        JLabel title =
                new JLabel(
                        "ATM DASHBOARD",
                        SwingConstants.CENTER
                );

        title.setFont(
                new Font(
                        "Arial",
                        Font.BOLD,
                        26
                )
        );


        JLabel accountLabel =
                new JLabel(
                        "Account: "
                                + loggedInAccount
                                .getAccountNumber(),
                        SwingConstants.CENTER
                );

        accountLabel.setFont(
                new Font(
                        "Arial",
                        Font.PLAIN,
                        15
                )
        );


        headerPanel.add(title);
        headerPanel.add(accountLabel);


        mainPanel.add(
                headerPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // BUTTON PANEL
        // =====================================================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                4,
                                2,
                                15,
                                15
                        )
                );


        JButton balanceButton =
                new JButton(
                        "Balance Enquiry"
                );


        JButton depositButton =
                new JButton(
                        "Deposit"
                );


        JButton withdrawButton =
                new JButton(
                        "Withdrawal"
                );


        JButton statementButton =
                new JButton(
                        "Mini Statement"
                );


        JButton amountSortButton =
                new JButton(
                        "Sort by Amount"
                );


        JButton dateSortButton =
                new JButton(
                        "Sort by Date"
                );


        JButton logoutButton =
                new JButton(
                        "Logout"
                );


        // ADD BUTTONS
        buttonPanel.add(
                balanceButton
        );

        buttonPanel.add(
                depositButton
        );

        buttonPanel.add(
                withdrawButton
        );

        buttonPanel.add(
                statementButton
        );

        buttonPanel.add(
                amountSortButton
        );

        buttonPanel.add(
                dateSortButton
        );

        buttonPanel.add(
                logoutButton
        );


        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );


        // =====================================================
        // BUTTON ACTIONS
        // =====================================================

        balanceButton.addActionListener(
                e -> showBalance()
        );


        depositButton.addActionListener(
                e -> deposit()
        );


        withdrawButton.addActionListener(
                e -> withdraw()
        );


        statementButton.addActionListener(
                e -> showStatement()
        );


        amountSortButton.addActionListener(
                e -> showTransactionsSortedByAmount()
        );


        dateSortButton.addActionListener(
                e -> showTransactionsSortedByDate()
        );


        logoutButton.addActionListener(
                e -> logout()
        );


        add(mainPanel);

        revalidate();
        repaint();
    }


    // =========================================================
    // BALANCE ENQUIRY
    // =========================================================

    private void showBalance() {

        JOptionPane.showMessageDialog(
                this,

                "Account Number: "
                        + loggedInAccount
                        .getAccountNumber()

                        + "\n\nCurrent Balance: ₹"
                        + loggedInAccount
                        .getBalance(),

                "Balance Enquiry",

                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // DEPOSIT
    // =========================================================

    private void deposit() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter deposit amount:"
                );


        if (input == null) {

            return;
        }


        try {

            double amount =
                    Double.parseDouble(
                            input
                    );


            loggedInAccount.deposit(
                    amount
            );


            JOptionPane.showMessageDialog(
                    this,

                    "Deposit successful!\n\n"
                            + "Deposited Amount: ₹"
                            + amount

                            + "\nNew Balance: ₹"
                            + loggedInAccount
                            .getBalance(),

                    "Deposit",

                    JOptionPane.INFORMATION_MESSAGE
            );


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,

                    "Please enter a valid amount.",

                    "Invalid Amount",

                    JOptionPane.ERROR_MESSAGE
            );


        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,

                    e.getMessage(),

                    "Deposit Error",

                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // WITHDRAWAL
    // =========================================================

    private void withdraw() {

        String input =
                JOptionPane.showInputDialog(
                        this,
                        "Enter withdrawal amount:"
                );


        if (input == null) {

            return;
        }


        try {

            double amount =
                    Double.parseDouble(
                            input
                    );


            loggedInAccount.withdraw(
                    amount
            );


            JOptionPane.showMessageDialog(
                    this,

                    "Withdrawal successful!\n\n"
                            + "Withdrawn Amount: ₹"
                            + amount

                            + "\nRemaining Balance: ₹"
                            + loggedInAccount
                            .getBalance(),

                    "Withdrawal",

                    JOptionPane.INFORMATION_MESSAGE
            );


        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,

                    "Please enter a valid amount.",

                    "Invalid Amount",

                    JOptionPane.ERROR_MESSAGE
            );


        } catch (IllegalArgumentException e) {

            JOptionPane.showMessageDialog(
                    this,

                    e.getMessage(),

                    "Withdrawal Error",

                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // MINI STATEMENT
    // =========================================================

    private void showStatement() {

        StringBuilder statement =
                new StringBuilder();


        statement.append(
                "ACCOUNT NUMBER: "
        );

        statement.append(
                loggedInAccount
                        .getAccountNumber()
        );

        statement.append(
                "\n\n"
        );


        if (
                loggedInAccount
                        .getTransactions()
                        .isEmpty()
        ) {

            statement.append(
                    "No transactions available."
            );

        } else {

            for (
                    Transaction transaction :
                    loggedInAccount
                            .getTransactions()
            ) {

                statement.append(
                        transaction
                );

                statement.append(
                        "\n"
                );
            }
        }


        JTextArea textArea =
                new JTextArea(
                        statement.toString()
                );


        textArea.setEditable(
                false
        );

        textArea.setRows(12);
        textArea.setColumns(50);


        JScrollPane scrollPane =
                new JScrollPane(
                        textArea
                );


        JOptionPane.showMessageDialog(
                this,

                scrollPane,

                "Mini Statement",

                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // SORT TRANSACTIONS BY AMOUNT
    // =========================================================

    private void showTransactionsSortedByAmount() {

        StringBuilder result =
                new StringBuilder();


        result.append(
                "TRANSACTIONS SORTED BY AMOUNT\n"
        );

        result.append(
                "================================\n\n"
        );


        ArrayList<Transaction> sorted =
                new ArrayList<>(
                        loggedInAccount
                                .getTransactions()
                );


        sorted.sort(
                Comparator.comparingDouble(
                        Transaction::getAmount
                )
        );


        if (sorted.isEmpty()) {

            result.append(
                    "No transactions available."
            );

        } else {

            for (
                    Transaction transaction :
                    sorted
            ) {

                result.append(
                        transaction
                );

                result.append(
                        "\n"
                );
            }
        }


        JTextArea textArea =
                new JTextArea(
                        result.toString()
                );


        textArea.setEditable(
                false
        );

        textArea.setRows(12);
        textArea.setColumns(50);


        JScrollPane scrollPane =
                new JScrollPane(
                        textArea
                );


        JOptionPane.showMessageDialog(
                this,

                scrollPane,

                "Sort by Amount",

                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // SORT TRANSACTIONS BY DATE
    // =========================================================

    private void showTransactionsSortedByDate() {

        StringBuilder result =
                new StringBuilder();


        result.append(
                "TRANSACTIONS SORTED BY DATE\n"
        );

        result.append(
                "================================\n\n"
        );


        ArrayList<Transaction> sorted =
                new ArrayList<>(
                        loggedInAccount
                                .getTransactions()
                );


        sorted.sort(
                Comparator.comparing(
                        Transaction::getDateTime
                )
        );


        if (sorted.isEmpty()) {

            result.append(
                    "No transactions available."
            );

        } else {

            for (
                    Transaction transaction :
                    sorted
            ) {

                result.append(
                        transaction
                );

                result.append(
                        "\n"
                );
            }
        }


        JTextArea textArea =
                new JTextArea(
                        result.toString()
                );


        textArea.setEditable(
                false
        );

        textArea.setRows(12);
        textArea.setColumns(50);


        JScrollPane scrollPane =
                new JScrollPane(
                        textArea
                );


        JOptionPane.showMessageDialog(
                this,

                scrollPane,

                "Sort by Date",

                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // LOGOUT
    // =========================================================

    private void logout() {

        int choice =
                JOptionPane.showConfirmDialog(
                        this,

                        "Are you sure you want to logout?",

                        "Logout",

                        JOptionPane.YES_NO_OPTION
                );


        if (
                choice ==
                        JOptionPane.YES_OPTION
        ) {

            loggedInAccount = null;

            accountField = null;
            pinField = null;

            showLoginScreen();
        }
    }
}