package com.remcoil.timetracker.projects.core.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Project {
    private int id;
    private String name;
    private boolean isArchive;

    public void archive() {
        isArchive = true;
    }
}
