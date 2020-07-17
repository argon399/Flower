package org.flower.workflow;

import org.flower.workflow.issue.Issue;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "backlog")
public class Backlog {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "backlog_issue",
            joinColumns = { @JoinColumn(name = "id_backlog") },
            inverseJoinColumns = { @JoinColumn(name = "id_issue") }
    )
    //@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Issue> issues;
}
