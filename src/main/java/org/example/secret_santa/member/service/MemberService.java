package org.example.secret_santa.member.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.example.secret_santa.member.dto.RegisterInfo;
import org.example.secret_santa.member.dto.UpdateInfo;
import org.example.secret_santa.member.dto.ViewMyInfo;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberDuplicationException;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.example.secret_santa.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Storage storage;

    public Long registerMember(RegisterInfo registerInfo) throws IOException {
        Optional<Member> byMemId = memberRepository.findByMemId(registerInfo.getMemId());
        if(byMemId.isPresent())
            throw new MemberDuplicationException();

        //gcs code
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = registerInfo.getProfileImage().getContentType(); // 파일의 형식 ex) JPG

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                registerInfo.getProfileImage().getInputStream()
        );
        Member member = RegisterInfo.toEntity(registerInfo, uuid);
        member.hashPassword(bCryptPasswordEncoder);

        return memberRepository.save(member).getId();
    }
    public ViewMyInfo viewMyInfo(String memId) {
        Optional<Member> member = memberRepository.findByMemId(memId);
        if(member.isEmpty())
            throw new MemberNotFoundException();

        return ViewMyInfo.of(member.get(), bucketName);
    }
    public void deleteMember(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        memberRepository.deleteById(memberId);
    }

    @Transactional
    public Long updateMember(Long memberId, UpdateInfo updateInfo) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        member.updateInfo(updateInfo);
        return memberId;
    }

    public String getNickname(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        return member.getNickName();
    }

}
//
