package by.bsac.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "AccountManagement", url = "http://10.8.8.2:80/eagle-authentication") //
public interface AccountManagementService {
}
