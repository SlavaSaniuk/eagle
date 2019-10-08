package by.bsac.feign.clients;

import by.bsac.models.Account;
import by.bsac.models.User;
import feign.Headers;
import feign.RequestLine;


public interface AccountManagementService {

    @Headers({"Content-Type: application/json","Charset: utf-8"})
    @RequestLine("POST /register")
    User registerAccount(Account account);

    @Headers({"Content-Type: application/json", "Charset: utf-8"})
    @RequestLine("POST /login")
    User loginAccount(Account account);

}
