package de.mywald.dyndns.domain;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "dyndns")
public class SubdomainPermissions {

    List<SubdomainPermission> subdomains = new ArrayList<>();

    public List<SubdomainPermission> getSubdomains() {
        return subdomains;
    }

    public static class SubdomainPermission {

        private Subdomain subdomain;

        private Secret secret;

        public Secret getSecret() {
            return secret;
        }

        public void setSecret(Secret secret) {
            this.secret = secret;
        }

        public Subdomain getSubdomain() {
            return subdomain;
        }

        public void setSubdomain(Subdomain subdomain) {
            this.subdomain = subdomain;
        }
    }
}
