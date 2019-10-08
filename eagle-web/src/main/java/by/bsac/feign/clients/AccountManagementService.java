package by.bsac.feign.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "AccountManagement", url = "http://10.8.8.2:80/eagle-authentication") //Disabled, because feign clients scanning is not configured
public interface AccountManagementService {
}
