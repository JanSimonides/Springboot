package Softip.Spring.service;


import Softip.Spring.mapper.TypeMapper;
import Softip.Spring.model.dto.TypeDTO;
import Softip.Spring.model.entity.Property;
import Softip.Spring.model.entity.State;
import Softip.Spring.model.entity.Type;
import Softip.Spring.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TypeService {
    @Autowired
    TypeRepository typeRepository;

    @Autowired
    TypeMapper typeMapper;


    public List<Type> findAll() {
        return typeRepository.findAll();
    }

    public Type findType(int x) {
        return typeRepository.findByIntType(x);
    }

    public void initType() {
        Type type0 = new Type(0, "IMA");
        Type type1 = new Type(1, "DIM");
        try {
            typeRepository.save(type0);
            typeRepository.save(type1);
        } catch (Exception ignored) {
        }
    }

    public void add(String number, String description) {
        int x = Integer.parseInt(number);
        Type type = new Type(x, description);

        if (typeRepository.existsByIntType(x)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Type already exists"
            );
        }
        else {
            typeRepository.save(type);
        }

    }
}
