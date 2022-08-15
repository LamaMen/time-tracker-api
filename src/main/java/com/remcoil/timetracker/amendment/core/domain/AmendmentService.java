package com.remcoil.timetracker.amendment.core.domain;

import com.remcoil.timetracker.amendment.core.data.AmendmentEntity;
import com.remcoil.timetracker.amendment.core.data.AmendmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AmendmentService {
    private final Logger logger = LoggerFactory.getLogger("Amendments");
    private final AmendmentRepository amendmentRepository;

    public AmendmentService(AmendmentRepository amendmentRepository) {
        this.amendmentRepository = amendmentRepository;
    }

    public void add(@NonNull Amendment amendment) {
        AmendmentEntity amendmentEntity = new AmendmentEntity(amendment);
        amendmentRepository.save(amendmentEntity);
        logger.info("Amendment with id: {} saved", amendment.getId());
    }

    public List<Amendment> getByPeriod(@Nullable UUID userId, @NonNull LocalDate start, @NonNull LocalDate end) {
        List<AmendmentEntity> amendmentEntities = userId != null ? amendmentRepository.getByPeriodAndUser(userId, start, end) : amendmentRepository.getByPeriod(start, end);
        return amendmentEntities.stream().map(AmendmentEntity::toAmendment).collect(Collectors.toList());
    }
}
