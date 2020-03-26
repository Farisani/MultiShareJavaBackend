package za.co.multishare.repository.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.multishare.domain.entity.ContactInfo;

@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {
}
