package io.github.kavahub.learnjava.simpleserver;

import jakarta.jws.WebService;

@WebService(endpointInterface = "io.github.kavahub.learnjava.simpleserver.CountryService")
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository = new CountryRepository();

    @Override
    public Country findByName(String name) {
        return countryRepository.findCountry(name);
    }

}
