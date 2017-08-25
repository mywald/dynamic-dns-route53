package de.mywald.dyndns.usecases.impl;

import de.mywald.dyndns.usecases.UpdateIpUseCase;
import de.mywald.dyndns.domain.IPAdress;
import de.mywald.dyndns.domain.Secret;
import de.mywald.dyndns.domain.Subdomain;
import org.springframework.stereotype.Service;

@Service
class UpdateIpService implements UpdateIpUseCase {

    @Override
    public void updateIp(Secret secret, Subdomain subdomain, IPAdress ip) {
        //Download current Settings from S3
        //Apply Configuration
        //Build Terraform Template with Velocity
        //Init Terraform with S3 Backend
        //Apply Terraform (init and apply)

    }
}
