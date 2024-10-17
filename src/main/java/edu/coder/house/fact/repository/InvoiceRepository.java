package edu.coder.house.fact.repository;

import edu.coder.house.fact.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
