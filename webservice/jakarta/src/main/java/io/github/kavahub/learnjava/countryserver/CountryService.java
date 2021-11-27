package io.github.kavahub.learnjava.countryserver;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;
import jakarta.jws.soap.SOAPBinding.Style;

/**
 * 
 * 服务接口
 *
 * @author PinWei Wan
 * @since 1.0.0
 */
@WebService
@SOAPBinding(style=Style.RPC)
public interface CountryService {
    
    @WebMethod
    Country findByName(String name);

}
