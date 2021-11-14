package io.github.kavahub.learnjava;

import java.security.Provider;
import java.security.Security;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AvailableCiphersManualTest {
    @Test
    public void whenGetServices_thenGetAllCipherAlgorithms() {
        for (Provider provider : Security.getProviders()) {
            for (Provider.Service service : provider.getServices()) {
                log.info(service.getAlgorithm());
            }
        }
    }

    @Test
    public void whenGetServicesWithFilter_thenGetAllCompatibleCipherAlgorithms() {
        List<String> algorithms = Arrays.stream(Security.getProviders())
          .flatMap(provider -> provider.getServices().stream())
          .filter(service -> "Cipher".equals(service.getType()))
          .map(Provider.Service::getAlgorithm)
          .collect(Collectors.toList());

        algorithms.forEach(log::info);
    }  
}
