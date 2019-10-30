package jestesmy.glodni.cateringi.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Accounts")
public class Accounts {

    @Id
    @Column(name = "AccountID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int accountID;

    private String accountName;

    private String email;

    private String password;

    @ManyToOne
    @JoinColumn(name = "AccountTypeID")
    private AccountType accountType;

    @OneToMany(mappedBy = "accounts")
    private List<Address> address;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private CompanyData companyData;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private UserData userData;

    public Accounts() { }

    public Accounts(String accountName, String email, String password) {
        this.accountName = accountName;
        this.email = email;
        this.password = password;
    }

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public CompanyData getCompanyData() {
        return companyData;
    }

    public void setCompanyData(CompanyData companyData) {
        this.companyData = companyData;
    }
}
