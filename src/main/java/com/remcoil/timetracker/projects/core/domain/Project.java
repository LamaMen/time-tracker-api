package com.remcoil.timetracker.projects.core.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Project {
    private final int id;
    private final String name;
}
