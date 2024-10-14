package edu.coder.house.fact.repository;

import edu.coder.house.fact.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {

    List<Invoice> findByStatus(String status);
    List<Invoice> findByClient_Id(UUID clientId);

}