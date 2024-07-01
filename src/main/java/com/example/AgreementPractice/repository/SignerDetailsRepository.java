package com.example.AgreementPractice.repository;

import com.example.AgreementPractice.entity.SignerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignerDetailsRepository extends JpaRepository<SignerDetails,Long> {
}
