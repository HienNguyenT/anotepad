package objects;

import org.json.simple.JSONObject;

public class Account {
    String email;
    boolean rememberMe = true;
    String password;

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
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

    public Account jsonFormat(JSONObject json) {
        Account account = new Account();
        account.setEmail(String.valueOf(json.get("email")));
        account.setPassword(String.valueOf(json.get("password")));
        return account;
    }

}
