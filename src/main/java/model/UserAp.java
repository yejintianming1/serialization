package model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UserAp implements Serializable {

    private String name;
    private String email;
    private List<UserAp> userList;
    private Map<String, UserAp> userMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<UserAp> getUserList() {
        return userList;
    }

    public void setUserList(List<UserAp> userList) {
        this.userList = userList;
    }

    public Map<String, UserAp> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<String, UserAp> userMap) {
        this.userMap = userMap;
    }
}
