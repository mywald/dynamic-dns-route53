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

        private String subdomain;

        private String secret;

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getSubdomain() {
            return subdomain;
        }

        public void setSubdomain(String subdomain) {
            this.subdomain = subdomain;
        }
    }
}
