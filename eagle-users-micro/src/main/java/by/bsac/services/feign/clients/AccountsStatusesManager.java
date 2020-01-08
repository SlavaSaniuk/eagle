package by.bsac.services.feign.clients;

import by.bsac.webmvc.dto.AccountWithStatusDto;
import feign.Headers;
import feign.RequestLine;

public interface AccountsStatusesManager {

    @Headers({"Content-Type: application/json","Charset: utf-8"})
    @RequestLine("POST /confirm_account")
    AccountWithStatusDto confirmAccount(AccountWithStatusDto dto);

}
