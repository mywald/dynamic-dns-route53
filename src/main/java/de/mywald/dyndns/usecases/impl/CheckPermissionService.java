package de.mywald.dyndns.usecases.impl;

import de.mywald.dyndns.domain.Secret;
import de.mywald.dyndns.domain.Subdomain;
import de.mywald.dyndns.domain.SubdomainPermissions;
import de.mywald.dyndns.usecases.CheckPermissionUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CheckPermissionService implements CheckPermissionUseCase {

    @Autowired
    private SubdomainPermissions subdomainPermissions;

    @Override
    public boolean hasPermission(Subdomain subdomain, Secret secret) {
        return subdomainPermissions.getSubdomains().stream().anyMatch(
                s -> s.getSecret().equals(secret.toString()) &&
                        s.getSubdomain().equals(subdomain.toString()));
    }
}
