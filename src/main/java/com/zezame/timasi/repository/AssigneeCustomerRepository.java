package com.zezame.timasi.repository;

import com.zezame.timasi.model.company.AssigneeCustomer;
import com.zezame.timasi.model.company.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssigneeCustomerRepository extends JpaRepository<AssigneeCustomer, UUID> {

    List<AssigneeCustomer> findByAssigneeAndCustomerNotIn(User assignee, List<User> customers);

    Optional<AssigneeCustomer> findByCustomer(User customer);
}
