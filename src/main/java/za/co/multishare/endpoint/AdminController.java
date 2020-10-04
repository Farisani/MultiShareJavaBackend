package za.co.multishare.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import za.co.multishare.domain.dto.AdminUserDetailsDto;
import za.co.multishare.service.AdminService;

import java.time.LocalDate;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(final AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("search")
    public ResponseEntity<List<AdminUserDetailsDto>> searchUsers(@RequestParam final String searchCriteria,
                                                                 @RequestParam final String searchQuery,
                                                                 @RequestParam final Integer pageNumber,
                                                                 @RequestParam final Integer pageSize) {
        return new ResponseEntity<>(adminService.searchForUsers(searchCriteria,
                searchQuery,
                pageNumber,
                pageSize),
                HttpStatus.OK);
    }

    @GetMapping("searchByRegistrationDate")
    public ResponseEntity<List<AdminUserDetailsDto>> searchByRegistrationDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate startDate,
                                                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final LocalDate endDate,
                                                                              @RequestParam final Integer pageNumber,
                                                                              @RequestParam final Integer pageSize) {
        return new ResponseEntity<>(adminService.searchByRegistrationDates(startDate.atStartOfDay(),
                endDate.plusDays(1).atTime(LocalTime.MIDNIGHT), pageNumber, pageSize), HttpStatus.OK);
    }
}
