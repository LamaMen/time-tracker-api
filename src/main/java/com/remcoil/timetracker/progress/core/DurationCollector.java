package com.remcoil.timetracker.progress.core;

import com.remcoil.timetracker.progress.core.models.Continuous;
import com.remcoil.timetracker.progress.core.models.ProjectDuration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class DurationCollector implements Collector<Continuous, List<Duration>, ProjectDuration> {
    @Override
    public Supplier<List<Duration>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Duration>, Continuous> accumulator() {
        return (d, s) -> {
            Duration duration = s.calculateDuration();
            d.add(duration);
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
