//package org.example.secret_santa.mission.service;
//
//import org.example.secret_santa.mission.entity.Mission;
//import org.example.secret_santa.mission.repository.MissionRepository;
//import org.example.secret_santa.matching.entity.Matching;
//import org.example.secret_santa.matching.repository.MatchingRepository;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Random;
//
//@Service
//public class MissionSchedulerService {
//
//    private final MissionRepository missionRepository;
//    private final MatchingRepository matchingRepository;
//
//    public MissionSchedulerService(MissionRepository missionRepository, MatchingRepository matchingRepository) {
//        this.missionRepository = missionRepository;
//        this.matchingRepository = matchingRepository;
//    }
//
//    // 하루마다 새로운 미션을 생성하는 스케줄러
//    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
//    public void createDailyMission() {
//        // 랜덤으로 선택할 더미 데이터 리스트
//
//
//
//
//        // 랜덤으로 더미 데이터 선택
//        Random random = new Random();
//        String randomContent = contentsList.get(random.nextInt(contentsList.size()));
//        String randomMessage = messagesList.get(random.nextInt(messagesList.size()));
//
//        // 임의의 매칭을 선택 (매칭 데이터가 있어야 합니다)
//        Matching matching = matchingRepository.findAll().get(random.nextInt((int) matchingRepository.count()));
//
//        // 미션 생성
//        Mission newMission = Mission.builder()
//                .contents(randomContent)
//                .message(randomMessage)
//                .isSuccess(false)  // 미션의 초기 상태는 성공하지 않은 상태로 설정
//                .matching(matching)
//                .build();
//
//        missionRepository.save(newMission);  // 새로운 미션을 데이터베이스에 저장
//        System.out.println("하루 미션이 생성되었습니다.");
//    }
//}
