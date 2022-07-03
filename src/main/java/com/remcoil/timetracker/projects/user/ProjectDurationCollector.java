package com.remcoil.timetracker.projects.user;

import com.remcoil.timetracker.core.DateUtil;
import com.remcoil.timetracker.projects.core.ProjectDuration;
import com.remcoil.timetracker.sessions.core.SessionEntity;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ProjectDurationCollector implements Collector<SessionEntity, List<Duration>, ProjectDuration> {
    @Override
    public Supplier<List<Duration>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Duration>, SessionEntity> accumulator() {
        return (d, s) -> {
            LocalDateTime end = s.getEndTime() != null ? s.getEndTime() : DateUtil.now();
            Duration sessionDuration = Duration.between(s.getStartTime(), end);
            d.add(sessionDuration);
        };
    }

    @Override
    public BinaryOperator<List<Duration>> combiner() {
        return (list1, list2) -> {
            list1.addAll(list2);
            return list1;
        };
    }

    @Override
    public Function<List<Duration>, ProjectDuration> finisher() {
        return (l) -> {
            Duration result = Duration.ZERO;
            for (Duration duration : l) {
                result = result.plus(duration);
            }

            return new ProjectDuration(result);
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        Set<Characteristics> characteristics = new HashSet<>();
        characteristics.add(Characteristics.UNORDERED);
        return characteristics;
    }
}
