package org.example.repositories;

import org.example.entities.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemberRepository {

    private Map<Integer, Member> members = new HashMap<>();

    // Save or update a member
    public void save(Member member) {
        members.put(member.getId(), member);
    }

    // Find member by ID
    public Member findById(int id) {
        return members.get(id);
    }

    // Remove a member by ID
    public void delete(int id) {
        members.remove(id);
    }

    // Return all members
    public List<Member> findAll() {
        return new ArrayList<>(members.values());
    }

    // Find member by email
    public Member findByEmail(String email) {
        for (Member member : members.values()) {
            if (member.getEmail().equalsIgnoreCase(email)) {
                return member;
            }
        }
        return null;
    }


}
