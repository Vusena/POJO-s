package org.example.services;

import org.example.entities.Member;
import org.example.repositories.MemberRepository;

import java.util.List;

public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // --------------------------------------------------
    // 1. Register a new member (with validation)
    // --------------------------------------------------
    public void registerMember(Member member) {
        validateMember(member);

        // Ensure unique email
        if (memberRepository.findByEmail(member.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        memberRepository.save(member);
    }

    // --------------------------------------------------
    // 2. Remove a member
    // --------------------------------------------------
    public void removeMember(int id) {
        memberRepository.delete(id);
    }

    // --------------------------------------------------
    // 3. View member borrowing history
    // --------------------------------------------------
    public List<?> viewBorrowingHistory(int memberId) {
        Member member = memberRepository.findById(memberId);

        if (member == null) {
            throw new IllegalArgumentException("Member not found");
        }

        return member.getBorrowedBooks();
    }

    // --------------------------------------------------
    // 4. Get member by ID
    // --------------------------------------------------
    public Member getMember(int id) {
        return memberRepository.findById(id);
    }

    // --------------------------------------------------
    // 5. List all members
    // --------------------------------------------------
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // --------------------------------------------------
    // VALIDATION
    // --------------------------------------------------
    private void validateMember(Member member) {

        if (member.getName() == null || member.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        if (member.getEmail() == null || !member.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }

        if (member.getMembershipDate() == null) {
            throw new IllegalArgumentException("Membership date cannot be null");
        }
    }
}
