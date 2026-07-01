package com.example.splitwise.repositories;

import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupAdminRepository extends JpaRepository<GroupAdmin, Long> {

    Optional<GroupAdmin> findByGroupAndAdmin(Group group, User admin);

    List<GroupAdmin> findByGroup(Group group);
}