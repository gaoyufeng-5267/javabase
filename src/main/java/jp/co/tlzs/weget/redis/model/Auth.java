package jp.co.tlzs.weget.redis.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
public class Auth {

    String id;
    String accountId;
    String agent;
    boolean status;
    public Auth(String id,
            String accountId,
            String agent,
            boolean status){
        this.id= id;
        this.accountId = accountId;
        this.agent = agent;
        this.status = status;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


}
