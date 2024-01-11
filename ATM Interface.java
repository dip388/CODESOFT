import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false; // Insufficent Balance 
        }
        balance -= amount;
        return true;
    }
}

class ATM {
    private BankAccount bankAccount;

    public ATM(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void deposit(double amount) {
        bankAccount.deposit(amount);
    }

    public boolean withdraw(double amount) {
        return bankAccount.withdraw(amount);
    }

    public double checkBalance() {
        return bankAccount.getBalance();
    }
}

public class ATMApp extends JFrame {
    private ATM atm;
    private JTextField amountField;
    private JTextArea outputArea;
    

    public ATMApp(ATM atm) {
        this.atm = atm;

        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setBackground(getForeground());
        add(panel);
       

        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        JButton withdrawButton = new JButton("Withdraw");
        JButton depositButton = new JButton("Deposit");
        JButton checkBalanceButton = new JButton("Check Balance");
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);

        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(withdrawButton);
        panel.add(depositButton);
        panel.add(checkBalanceButton);
        panel.add(outputArea);

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleWithdraw();
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeposit();
            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCheckBalance();
            }
        });
    }

    private void handleWithdraw() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            if (atm.withdraw(amount)) {
                displayMessage("Withdrawal successful. Remaining balance: " + atm.checkBalance());
            } else {
            	JOptionPane.showMessageDialog(this, "Insufficient Account Balance!!: ");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Invalid input. Please enter a valid amount.");
        }
    }

    private void handleDeposit() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            atm.deposit(amount);
            displayMessage("Deposit successful. Remaining balance: " + atm.checkBalance());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,"Invalid input. Please enter a valid amount.");
        }
    }

    private void handleCheckBalance() {
        double balance = atm.checkBalance();
        displayMessage("Current balance: " + balance);
    }

    private void displayMessage(String message) {
        outputArea.setText(message);
    }

    public static void main(String[] args) {
        BankAccount bankAccount = new BankAccount(1000.0); // initial balance
        ATM atm = new ATM(bankAccount);

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ATMApp(atm).setVisible(true);
            }
        });
    }
}
