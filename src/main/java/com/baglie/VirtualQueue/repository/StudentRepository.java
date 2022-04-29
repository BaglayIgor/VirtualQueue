package com.baglie.VirtualQueue.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<DTOStudent, Long> {

    DTOStudent findByName(String name);

}