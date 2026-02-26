package com.userauthentication;

import com.userregistration.User;
import java.time.LocalDateTime;

public class Session {

    private User user;
    private LocalDateTime loginTime;

    public Session(User user) {
        this.user = user;
        this.loginTime = LocalDateTime.now();
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }
}