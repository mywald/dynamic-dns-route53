package de.mywald.dyndns.usecases;

import de.mywald.dyndns.domain.IPAddress;
import de.mywald.dyndns.domain.Secret;
import de.mywald.dyndns.domain.Subdomain;

public interface UpdateIpUseCase {

    void updateIp(Secret secret, Subdomain subdomain, IPAddress ip);
}
