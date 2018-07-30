package com.ranok.network.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest extends BaseRequest {
    @SerializedName("userLogin")
    public String login;
    @SerializedName("userPassword")
    public String password;

    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
