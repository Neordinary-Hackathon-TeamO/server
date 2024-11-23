package org.example.secret_santa.mission.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.secret_santa.common.BaseEntity;
import org.example.secret_santa.matching.entity.Matching;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Mission")
public class Mission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String contents;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(nullable = false)
    @ColumnDefault("false")
    private boolean isSuccess;

    @Column(columnDefinition = "TEXT")
    private String missionImg;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matching_id", nullable = false)
    private Matching matching;


    public void setMissionMessage(String missionMessage) {
        this.message = missionMessage;
    }
    public void setMissionImg(String missionImg) { this.missionImg = missionImg; }
    public void setIsSuccess(boolean isSuccess) { this.isSuccess = isSuccess; }

}
