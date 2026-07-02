package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UnAuthorizedAccessException;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.GroupAdminRepository;
import com.example.splitwise.repositories.GroupMemberRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupAdminRepository groupAdminRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Override
    public Group createGroup(String groupName,
                             String description,
                             long userId) throws InvalidUserException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User not found"));

        Group group = new Group();
        group.setName(groupName);
        group.setDescription(description);

        group = groupRepository.save(group);

        GroupAdmin admin = new GroupAdmin();
        admin.setGroup(group);
        admin.setAdmin(user);
        admin.setAddedBy(user);

        groupAdminRepository.save(admin);

        return group;
    }

    @Override
    @Transactional
    public void deleteGroup(long groupId,
                            long userId)
            throws InvalidGroupException,
            UnAuthorizedAccessException,
            InvalidUserException {

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidGroupException("Group not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User not found"));

        boolean isAdmin =
                groupAdminRepository.existsByGroupAndAdmin(group, user);

        if (!isAdmin) {
            throw new UnAuthorizedAccessException("Only admins can delete group");
        }

        groupMemberRepository.deleteAllByGroup(group);

        groupAdminRepository.deleteAllByGroup(group);

        groupRepository.delete(group);
    }
}