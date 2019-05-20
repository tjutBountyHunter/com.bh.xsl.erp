package xsl.erp.pojo;

import java.math.BigDecimal;

public class XslAccount {
    String accountId;
    private int totalUser;
    private int newUser;
    private int totalTask;
    private int newTask;
    private int totalDoneTask;
    private int newDoneTask;
    private BigDecimal totalMoney;
    private BigDecimal newMoney;
    private String date;

    public String getTime() {
        return date;
    }

    public void setTime(String time) {
        date = time;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(int totalUser) {
        this.totalUser = totalUser;
    }

    public int getNewUser() {
        return newUser;
    }

    public void setNewUser(int newUser) {
        this.newUser = newUser;
    }

    public int getTotalTask() {
        return totalTask;
    }

    public void setTotalTask(int totalTask) {
        this.totalTask = totalTask;
    }

    public int getNewTask() {
        return newTask;
    }

    public void setNewTask(int newTask) {
        this.newTask = newTask;
    }

    public int getTotalDoneTask() {
        return totalDoneTask;
    }

    public void setTotalDoneTask(int totalDoneTask) {
        this.totalDoneTask = totalDoneTask;
    }

    public int getNewDoneTask() {
        return newDoneTask;
    }

    public void setNewDoneTask(int newDoneTask) {
        this.newDoneTask = newDoneTask;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(BigDecimal newMoney) {
        this.newMoney = newMoney;
    }
}
