package io.github.kavahub.learnjava.countryserver;

import jakarta.jws.WebService;

/**
 * 
 * 服务实现
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@WebService(endpointInterface = "io.github.kavahub.learnjava.countryserver.CountryService")
public class CountryServiceImpl implements CountryService {

    private CountryRepository countryRepository = new CountryRepository();

    @Override
    public Country findByName(String name) {
        return countryRepository.findCountry(name);
    }

}
