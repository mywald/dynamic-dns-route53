package de.mywald.dyndns.usecases;

import de.mywald.dyndns.domain.Secret;
import de.mywald.dyndns.domain.Subdomain;

public interface CheckPermissionUseCase {

    boolean hasPermission(Subdomain subdomain, Secret secret);
}
