package de.mywald.dyndns.webinterface;

import de.mywald.dyndns.domain.IPAdress;
import de.mywald.dyndns.domain.Secret;
import de.mywald.dyndns.domain.Subdomain;
import de.mywald.dyndns.usecases.UpdateIpUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/app")
public class WebInterface {

    private final UpdateIpUseCase updateIpUseCase;

    @Autowired
    public WebInterface(UpdateIpUseCase updateIpUseCase) {
        this.updateIpUseCase = updateIpUseCase;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/update/{subdomain}")
    public String updateIp(@PathVariable("subdomain") String subdomain,
                         @RequestAttribute("secret") String secret,
                         @RequestAttribute("ip") String ip) {

        updateIpUseCase.updateIp(new Secret(secret), new Subdomain(subdomain), new IPAdress(ip));
        return "OK";
    }
}
