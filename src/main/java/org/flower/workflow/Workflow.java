package org.flower.workflow;

import org.flower.workflow.team.Team;

import javax.persistence.*;

@Entity
@Table(name = "workflow")
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Team team;
}
