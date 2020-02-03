package Softip.Spring.service;


import Softip.Spring.repository.PropertyRepositery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TypeService {
    @Autowired
    PropertyRepositery typeRepositery;

}
