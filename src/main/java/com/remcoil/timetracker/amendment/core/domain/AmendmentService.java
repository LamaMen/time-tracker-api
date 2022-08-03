package com.remcoil.timetracker.amendment.core.domain;

import com.remcoil.timetracker.amendment.core.data.AmendmentEntity;
import com.remcoil.timetracker.amendment.core.data.AmendmentRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AmendmentService {
    private final AmendmentRepository amendmentRepository;

    public AmendmentService(AmendmentRepository amendmentRepository) {
        this.amendmentRepository = amendmentRepository;
    }

    public void add(Amendment amendment) {
        AmendmentEntity amendmentEntity = new AmendmentEntity(amendment);
        amendmentRepository.save(amendmentEntity);
    }

    public List<Amendment> getByPeriod(@Nullable UUID userId, LocalDate start, LocalDate end) {
        List<AmendmentEntity> amendmentEntities = userId != null ? amendmentRepository.getByPeriodAndUser(userId, start, end) : amendmentRepository.getByPeriod(start, end);
        return amendmentEntities.stream().map(AmendmentEntity::toAmendment).collect(Collectors.toList());
    }
}
