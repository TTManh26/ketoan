package org.hello.repository;

import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import org.hello.entity.Employee;

@EzyRepository
public interface EmployeeRepository extends EzyDatabaseRepository<Integer, Employee> {
    void deleteById(Integer id);
}
