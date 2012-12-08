package simpleExamples;

/**
 * Created with IntelliJ IDEA.
 * User: eatmuchpie
 * Date: 07/12/2012
 * Time: 17:44
 * To change this template use File | Settings | File Templates.
 */
public class BankAccount {
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public synchronized void deposit(int money) {
        balance = balance + money;
    }

    public synchronized int retrieve(int money) {
        int result = 0;
        if (balance > money) {
            result = money;
        } else {
            result = balance;
        }
        balance = balance - result;
        return result;
    }
}
