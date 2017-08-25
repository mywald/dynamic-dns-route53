package de.mywald.dyndns.usecases.impl;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.route53.AmazonRoute53Async;
import com.amazonaws.services.route53.AmazonRoute53AsyncClientBuilder;
import com.amazonaws.services.route53.model.*;
import de.mywald.dyndns.domain.IPAddress;
import de.mywald.dyndns.domain.Secret;
import de.mywald.dyndns.domain.Subdomain;
import de.mywald.dyndns.usecases.UpdateIpUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

@Service
class UpdateIpService implements UpdateIpUseCase {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateIpService.class);

    private final String accessKey;
    private final String secretKey;
    private String zoneId;
    private String domain;
    private Long ttl;
    private String region;

    public UpdateIpService(@Value("${dyndns.aws.access-key}") String accessKey,
                           @Value("${dyndns.aws.secret-key}") String secretKey,
                           @Value("${dyndns.aws.region}") String region,
                           @Value("${dyndns.route53.zone-id}") String zoneId,
                           @Value("${dyndns.route53.domain}") String domain,
                           @Value("${dyndns.route53.ttl:60}") Long ttl) {

        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.zoneId = zoneId;
        this.domain = domain;
        this.ttl = ttl;
        this.region = region;
    }

    @Override
    public void updateIp(Secret secret, Subdomain subdomain, IPAddress ip) {

        LOG.info("Updating " + subdomain + " to " + ip);

        AmazonRoute53Async client = buildClient();
        ResourceRecordSet recordSet = createRecordSet(subdomain, ip);
        ChangeResourceRecordSetsResult result = sendUpsertRequest(client, recordSet);

        LOG.info(result.toString());
    }

    private AmazonRoute53Async buildClient() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonRoute53AsyncClientBuilder.standard()
                .withRegion(region)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    private ChangeResourceRecordSetsResult sendUpsertRequest(AmazonRoute53Async client, ResourceRecordSet recordSet) {
        Change change = new Change("UPSERT", recordSet);
        ChangeBatch changeBatch = new ChangeBatch(Collections.singletonList(change));
        ChangeResourceRecordSetsRequest request = new ChangeResourceRecordSetsRequest(zoneId, changeBatch);
        return client.changeResourceRecordSets(request);
    }

    private ResourceRecordSet createRecordSet(Subdomain subdomain, IPAddress ip) {
        ResourceRecordSet recordSet = new ResourceRecordSet(subdomain + "." + domain, "A");
        recordSet.setTTL(ttl);
        recordSet.setResourceRecords(Arrays.asList(new ResourceRecord(ip.toString())));
        return recordSet;
    }
}
