package com.vistaram.data.relational.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.vistaram.data.relational.domain.GuestDetail;

@Transactional(readOnly=false)
public interface GuestDetailRepository extends JpaRepository<GuestDetail, Long>{

}
