package org.example.secret_santa.mission.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.matching.repository.MatchingRepository;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.mission.dto.MissionComplete;
import org.example.secret_santa.mission.dto.ViewMissionComplete;
import org.example.secret_santa.mission.entity.Mission;
import org.example.secret_santa.mission.repository.MissionRepository;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.exception.TeamNotFoundException;
import org.example.secret_santa.team.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {


    private final MissionRepository missionRepository;
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;
    private final Storage storage;


    public Mission getOwnMission(Long giverMemberId, Long groupId) {

        Member member = memberRepository.findById(giverMemberId)
            .orElseThrow(MemberNotFoundException::new);

        Team team = teamRepository.findById(groupId)
            .orElseThrow(TeamNotFoundException::new);

        Matching match = matchingRepository.findByGiverMemberAndTeam(member, team);
        Mission mission = missionRepository.findByMatching(match);

        return mission;
    }

    @Transactional
    public void putMission(String memId, MissionComplete missionComplete) throws IOException {
        Member member = memberRepository.findByMemId(memId).get();
        Mission mission = missionRepository.findByMemberId(member.getId());

        //gcs code
        String uuid = UUID.randomUUID().toString(); // Google Cloud Storage에 저장될 파일 이름
        String ext = missionComplete.getMissionImage().getContentType(); // 파일의 형식 ex) JPG

        BlobInfo blobInfo = storage.create(
                BlobInfo.newBuilder(bucketName, uuid)
                        .setContentType(ext)
                        .build(),
                missionComplete.getMissionImage().getInputStream()
        );

        mission.setMissionMessage(missionComplete.getComment());
        mission.setMissionImg(uuid);
        mission.setIsSuccess(true);
    }

    public List<ViewMissionComplete> viewAllMission(Long teamId) {
        List<Mission> completeList = missionRepository.findAllByIsSuccessTrue();
        List<ViewMissionComplete> view = new ArrayList<>();

        completeList.stream()
                .filter(mission -> mission.getMatching().getTeam().getId().equals(teamId)) // teamId와 일치하는 것만 필터링
                .forEach(mission -> view.add(ViewMissionComplete.of(mission, bucketName))); // 변환 후 추가
        return view;
    }
}
