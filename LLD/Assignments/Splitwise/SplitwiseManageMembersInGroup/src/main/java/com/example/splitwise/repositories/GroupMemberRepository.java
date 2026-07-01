package com.example.splitwise.repositories;

import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupMember;
import com.example.splitwise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    Optional<GroupMember> findByGroupAndUser(Group group, User user);

    List<GroupMember> findByGroup(Group group);
}