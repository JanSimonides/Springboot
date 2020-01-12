package Softip.Spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PropertyService {
    @Autowired
    PropertyRepositery propertyRepositery;

}
