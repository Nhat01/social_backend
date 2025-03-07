package com.nhat.social.request;

import com.nhat.social.model.User;

public class CreateChatRequest {
    private User reqUser;
    private User user2;

    public CreateChatRequest() {
    }

    public CreateChatRequest(User reqUser, User user2) {
        this.reqUser = reqUser;
        this.user2 = user2;
    }

    public User getReqUser() {
        return reqUser;
    }

    public void setReqUser(User reqUser) {
        this.reqUser = reqUser;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
