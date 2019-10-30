package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "AccountType")
public class AccountType {

    @Id
    @Column(name = "AccountTypeID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int accountTypeID;

    private String Name;

    @OneToMany(mappedBy = "accountType")
    private List<Accounts> accounts;

    public AccountType() { }

    public AccountType(String name) {
        Name = name;
    }

    public int getAccountTypeID() {
        return accountTypeID;
    }

    public void setAccountTypeID(int accountTypeID) {
        this.accountTypeID = accountTypeID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Accounts> accounts) {
        this.accounts = accounts;
    }
}
