package com.zezame.timasi.service.impl;

import com.zezame.timasi.constant.Message;
import com.zezame.timasi.constant.RoleCode;
import com.zezame.timasi.dto.CommonResponseDTO;
import com.zezame.timasi.dto.customer.AssigneeCustomerRequestDTO;
import com.zezame.timasi.exceptiohandler.exception.NotAllowedException;
import com.zezame.timasi.exceptiohandler.exception.NotFoundException;
import com.zezame.timasi.model.BaseModel;
import com.zezame.timasi.model.company.AssigneeCustomer;
import com.zezame.timasi.model.company.User;
import com.zezame.timasi.repository.AssigneeCustomerRepository;
import com.zezame.timasi.repository.UserRepository;
import com.zezame.timasi.service.AssigneeCustomerService;
import com.zezame.timasi.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AssigneeCustomerServiceImpl extends BaseService implements AssigneeCustomerService {
    private final UserRepository userRepository;
    private final AssigneeCustomerRepository assigneeCustomerRepository;

    public AssigneeCustomerServiceImpl(UserRepository userRepository, AssigneeCustomerRepository assigneeCustomerRepository) {
        this.userRepository = userRepository;
        this.assigneeCustomerRepository = assigneeCustomerRepository;
    }

    @Override
    public CommonResponseDTO setAssignee(AssigneeCustomerRequestDTO request) {
        User assignee = findAssigneeById(request.getAssigneeId());

        List<User> users = request.getCustomerIds().stream()
                .map(this::findCustomerById)
                .toList();

        for (User customer : users) {
            var assigneeCustomer = new AssigneeCustomer();
            assigneeCustomer.setAssignee(assignee);
            assigneeCustomer.setCustomer(customer);
            assigneeCustomerRepository.save(prepareCreate(assigneeCustomer));
        }
        return new CommonResponseDTO(Message.CREATED.getName());
    }

    @Override
    public CommonResponseDTO updateAssignee(AssigneeCustomerRequestDTO request) {
        var assignee = findAssigneeById(request.getAssigneeId());

        List<User> customers = request.getCustomerIds().stream()
                .map(this::findCustomerById)
                .toList();

        List<AssigneeCustomer> toBeDeleted = assigneeCustomerRepository.findByAssigneeAndCustomerNotIn(assignee, customers);
        assigneeCustomerRepository.deleteAll(toBeDeleted);

        for (var customer : customers) {
            var assigneeCustomer = assigneeCustomerRepository.findByCustomer(customer)
                    .orElse(new AssigneeCustomer());
            assigneeCustomer.setAssignee(assignee);
            assigneeCustomer.setCustomer(customer);
            assigneeCustomerRepository.save(assigneeCustomer.getId() != null ? prepareUpdate(assigneeCustomer)
                    : prepareCreate(assigneeCustomer));
        }
        return new CommonResponseDTO(Message.UPDATED.getName());
    }

    private User findCustomerById(String id) {
        UUID customerId = convertToUUID(id);
        User user = userRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("User Not Found"));
        if (!user.getRole().getCode().equals(RoleCode.CUST.name())) {
            throw new NotAllowedException("Invalid Customer");
        }
        return user;
    }

    private User findAssigneeById(String id) {
        UUID assigneeId = convertToUUID(id);
        User assignee = userRepository.findById(assigneeId)
                .orElseThrow(() -> new NotFoundException("Assignee Not Found"));
        if (!assignee.getRole().getCode().equals(RoleCode.PIC.name())) {
            throw new NotAllowedException("Invalid Assignee");
        }
        return assignee;
    }
}
