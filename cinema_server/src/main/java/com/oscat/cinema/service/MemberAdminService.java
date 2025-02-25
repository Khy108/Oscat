package com.oscat.cinema.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oscat.cinema.dao.MemberAdminRepository;
import com.oscat.cinema.entity.Member;

@Service
public class MemberAdminService {

    private final MemberAdminRepository memberRepository;

    @Autowired
    public MemberAdminService(MemberAdminRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 新增會員
    public Member addMember(Member member) {
        return memberRepository.save(member);
    }

    // 根據email查找會員
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    // 獲取所有會員列表
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // 根據email刪除會員
    public String deleteMemberByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if (optionalMember.isPresent()) {
            memberRepository.deleteByEmail(email);
            return "刪除成功";
        } else {
            return "找不到該會員";
        }
    }

    // 更新會員資料
    public Optional<Member> updateMember(Member newMember) {
        Optional<Member> existingMember = memberRepository.findByEmail(newMember.getEmail());
        if (existingMember.isPresent()) {
            Member memberToUpdate = existingMember.get();
            memberToUpdate.setMemberName(newMember.getMemberName());
            memberToUpdate.setPassword(newMember.getPassword());
            memberToUpdate.setPhone(newMember.getPhone());
            memberToUpdate.setGender(newMember.getGender());
            memberToUpdate.setBirthDate(newMember.getBirthDate());

            return Optional.of(memberRepository.save(memberToUpdate));
        } else {
            return Optional.empty();
        }
    }
}
