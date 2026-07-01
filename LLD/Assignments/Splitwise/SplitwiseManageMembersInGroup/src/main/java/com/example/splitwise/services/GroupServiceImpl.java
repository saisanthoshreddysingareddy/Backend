package com.example.splitwise.services;

import com.example.splitwise.exceptions.InvalidGroupException;
import com.example.splitwise.exceptions.InvalidUserException;
import com.example.splitwise.exceptions.UnAuthorizedAccessException;
import com.example.splitwise.models.Group;
import com.example.splitwise.models.GroupAdmin;
import com.example.splitwise.models.GroupMember;
import com.example.splitwise.models.User;
import com.example.splitwise.repositories.GroupAdminRepository;
import com.example.splitwise.repositories.GroupMemberRepository;
import com.example.splitwise.repositories.GroupRepository;
import com.example.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public GroupMember addMember(long groupId,
                                 long adminId,
                                 long userId)
            throws InvalidGroupException,
            InvalidUserException,
            UnAuthorizedAccessException {

    
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidGroupException("Group not found"));

        
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new InvalidUserException("Admin not found"));

        
        User member = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User not found"));

        
        GroupAdmin groupAdmin = groupAdminRepository
                .findByGroupAndAdmin(group, admin)
                .orElseThrow(() ->
                        new UnAuthorizedAccessException("Only admins can add members"));
        
        if (groupMemberRepository.findByGroupAndUser(group, member).isPresent()) {
            throw new InvalidUserException("Member already present in the group");
        }
        
        GroupMember groupMember = new GroupMember();

        groupMember.setGroup(group);
        groupMember.setUser(member);
        groupMember.setAddedBy(groupAdmin.getAdmin());
        groupMember.setAddedAt(new Date());

        
        return groupMemberRepository.save(groupMember);
    }

    @Override
    public void removeMember(long groupId,
                             long adminId,
                             long userId)
            throws InvalidGroupException,
            InvalidUserException,
            UnAuthorizedAccessException {

        
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidGroupException("Group not found"));

        
        User admin = userRepository.findById(adminId)
                .orElseThrow(() -> new InvalidUserException("Admin not found"));

       
        User member = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("Member not found"));

       
        groupAdminRepository.findByGroupAndAdmin(group, admin)
                .orElseThrow(() ->
                        new UnAuthorizedAccessException("Only admins can remove members"));

        
        GroupMember groupMember = groupMemberRepository
                .findByGroupAndUser(group, member)
                .orElseThrow(() ->
                        new InvalidUserException("Member not present in group"));

        
        groupMemberRepository.delete(groupMember);
    }

    @Override
    public List<User> fetchAllMembers(long groupId,
                                      long userId)
            throws InvalidGroupException,
            InvalidUserException,
            UnAuthorizedAccessException {

        
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new InvalidGroupException("Group not found"));

        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User not found"));

        
        boolean isAdmin =
                groupAdminRepository.findByGroupAndAdmin(group, user).isPresent();

        
        boolean isMember =
                groupMemberRepository.findByGroupAndUser(group, user).isPresent();

        
        if (!isAdmin && !isMember) {
            throw new UnAuthorizedAccessException(
                    "User is not part of this group");
        }

        List<User> users = new ArrayList<>();

        
        List<GroupMember> members =
                groupMemberRepository.findByGroup(group);

        for (GroupMember member : members) {
            users.add(member.getUser());
        }

        
        List<GroupAdmin> admins =
                groupAdminRepository.findByGroup(group);

        for (GroupAdmin admin : admins) {
            users.add(admin.getAdmin());
        }

        return users;
    }
}