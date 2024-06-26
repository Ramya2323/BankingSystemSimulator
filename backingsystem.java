import java.util.ArrayList;
import java.util.Scanner;

abstract class BankName {
    void showAccount() {
        System.out.println("No details entered");
    };
}

class User {
    private String userName;
    private String password;
    private long phoneNo;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }
}

class BankAccount extends BankName {
    String accountNo;
    private String fname;
    private String lname;
    private long balance;

    public void openAccount(Scanner sc) {
        try {
            sc.nextLine();
            System.out.println("Enter First Name:");
            fname = sc.nextLine();
            System.out.println("Enter Last Name:");
            lname = sc.nextLine();
            System.out.print("Enter Account No: ");
            accountNo = sc.next();
            System.out.print("Enter Balance: ");
            balance = sc.nextLong();
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }


    @Override
    public void showAccount() {
        System.out.println("Name of account holder: " + fname+" "+lname);
        System.out.println("Account no.: " + accountNo);
        System.out.println("Balance: " + balance);
    }

    public void deposit(Scanner sc) {
        try {
            System.out.println("Enter the amount you want to deposit: ");
            long amt = sc.nextLong();
            balance += amt;
            showAccount();
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public void withdraw(Scanner sc) {
        try {
            showAccount();
            System.out.println("Enter the amount you want to withdraw: ");
            long amt = sc.nextLong();
            if (balance >= amt) {
                balance -= amt;
                System.out.println("Balance after withdrawal: " + balance);
            } else {
                System.out.println("Your balance is less than " + amt + " and cannot be withdrawn.");
            }

        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }
}

public class BankApplication implements Runnable {
    private Scanner sc;
    private User user;
    private ArrayList<BankAccount> accounts;

    public BankApplication(User user) {
        this.user = user;
        this.sc = new Scanner(System.in);
        this.accounts = new ArrayList<>();
    }

    public void run() {
        if (login()) {
            System.out.println("Login Successful!");
            System.out.println("\nWelcome to the Bank\n");
            menu();
        }
    }

    private boolean login() {
        System.out.println("Enter Username:");
        String usernameInput = sc.nextLine();
        System.out.println("Enter Password:");
        String passwordInput = sc.nextLine();
        return usernameInput.equals(user.getUserName()) && passwordInput.equals(user.getPassword());
    }

    private void menu() {
        int choice;
        do {
            System.out.println("1. Open an account\n2. Deposit amount\n3. Withdraw amount\n4. Check balance\n5. Exit");
            System.out.println("Enter your choice: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    BankAccount newAccount = new BankAccount();
                    newAccount.openAccount(sc);
                    accounts.add(newAccount);
                    break;
                case 2:
                    if (!accounts.isEmpty()) {
                        System.out.println("Enter account number to deposit:");
                        String accNo = sc.next();
                        for (BankAccount acc : accounts) {
                            if (accNo.equals(acc.accountNo)) {
                                acc.deposit(sc);
                                break;
                            }
                        }
                    } else {
                        System.out.println("No accounts available.");
                    }
                    break;
                case 3:
                    if (!accounts.isEmpty()) {
                        System.out.println("Enter account number to withdraw:");
                        String accNo = sc.next();
                        for (BankAccount acc : accounts) {
                            if (accNo.equals(acc.accountNo)) {
                                acc.withdraw(sc);
                                break;
                            }
                        }
                    } else {
                        System.out.println("No accounts available.");
                    }
                    break;
                case 4:
                    if (!accounts.isEmpty()) {
                        System.out.println("Enter account number to check balance:");
                        String accNo = sc.next();
                        for (BankAccount acc : accounts) {
                            if (accNo.equals(acc.accountNo)) {
                                acc.showAccount();
                                break;
                            }
                        }
                    } else {
                        System.out.println("No accounts available.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter again.");
                    break;
            }
        } while (choice != 5);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        User user = new User();

        System.out.println("Enter Username:");
        user.setUserName(sc.nextLine());

        System.out.println("Enter Password:");
        user.setPassword(sc.nextLine());

        System.out.println("Enter Phone Number:");
        user.setPhoneNo(Long.parseLong(sc.nextLine()));

        BankApplication app = new BankApplication(user);
        Thread appThread = new Thread(app);
        appThread.start();
    }
}
