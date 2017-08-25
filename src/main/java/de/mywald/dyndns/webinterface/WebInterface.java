package de.mywald.dyndns.webinterface;

import de.mywald.dyndns.domain.IPAddress;
import de.mywald.dyndns.domain.Secret;
import de.mywald.dyndns.domain.Subdomain;
import de.mywald.dyndns.usecases.CheckPermissionUseCase;
import de.mywald.dyndns.usecases.UpdateIpUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/app")
public class WebInterface {

    private final CheckPermissionUseCase checkPermissionUseCase;
    private final UpdateIpUseCase updateIpUseCase;

    @Autowired
    public WebInterface(CheckPermissionUseCase checkPermissionUseCase, UpdateIpUseCase updateIpUseCase) {
        this.checkPermissionUseCase = checkPermissionUseCase;
        this.updateIpUseCase = updateIpUseCase;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/update/{subdomain}")
    public ResponseEntity<String> updateIp(@PathVariable("subdomain") String subdomainString,
                                           @RequestParam("secret") String secretString,
                                           @RequestParam("ip") String ipString) {

        Subdomain subdomain = new Subdomain(subdomainString);
        Secret secret = new Secret(secretString);
        IPAddress ip = new IPAddress(ipString);
        if (checkPermissionUseCase.hasPermission(subdomain, secret)) {
            updateIpUseCase.updateIp(secret, subdomain, ip);
            return ResponseEntity.ok("ok");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
