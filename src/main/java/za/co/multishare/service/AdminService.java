package za.co.multishare.service;

import za.co.multishare.domain.dto.AdminUserDetailsDto;

import java.util.List;

public interface AdminService {

    List<AdminUserDetailsDto> searchForUsers(String searchCriteria,
                                             String searchQuery,
                                             Integer pageNumber,
                                             Integer pageSize);

    List<AdminUserDetailsDto> getAll(Integer pageNumber,
                                     Integer pageSize);

    Integer getTotalNumberOfUsers();
}
