package com.example.splitwise.repositories;

import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupAdminRepository extends JpaRepository<GroupAdmin, Long> {

    boolean existsByGroupAndAdmin(Group group, User admin);

    void deleteAllByGroup(Group group);
}