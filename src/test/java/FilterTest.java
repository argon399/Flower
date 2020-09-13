import org.flower.project.Project;
import org.flower.project.Sprint;
import org.flower.project.filter.*;
import org.flower.project.issue.Issue;
import org.flower.project.issue.IssuePriority;
import org.flower.project.issue.IssueType;
import org.flower.project.team.Team;
import org.flower.project.team.TeamRole;
import org.flower.project.team.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FilterTest {
    private User user1;
    private User user2;
    private User user3;
    private Team team;
    private Issue issue1, issue2, issue3, issue4, issue5, issue6;
    private Sprint sprint;
    private Project project;

    @BeforeEach
    public void setUp() {

        user1 = new User();
        user1.setId(1);
        user1.setUsername("One");
        user1.setFio("fio");
        user2 = new User();
        user2.setId(2);
        user2.setUsername("Two");
        user3 = new User();
        user3.setId(3);
        user3.setUsername("Three");

        team = new Team();
        team.setLeader(user1);
        team.setId(1);
        team.addMember(user2, TeamRole.DEVELOPER);
        team.addMember(user3, TeamRole.DEVELOPER);

        issue1 = new Issue(1, "Epic", IssuePriority.LOW, IssueType.EPIC, user1);
        issue2 = new Issue(2, "Story 1", IssuePriority.MIDDLE, IssueType.STORY, user1);
        issue3 = new Issue(3, "Task 1", IssuePriority.MIDDLE, IssueType.TASK, user2);
        issue4 = new Issue(4, "Story 2", IssuePriority.MIDDLE, IssueType.STORY, user1);
        issue5 = new Issue(5, "Task 2", IssuePriority.HIGH, IssueType.TASK, user2);
        issue6 = new Issue(6, "Task 3", IssuePriority.LOW, IssueType.TASK, user2);

        sprint = new Sprint();

        project = new Project();
        project.setId(1);
        project.setName("Workflow");
        project.setOwner(user1);
        project.setTeam(team);
        project.addSprint(sprint);
        project.addIssue(issue1);
        project.addIssue(issue2);
        project.addIssue(issue3);
        project.addIssue(issue4);
        project.addIssue(issue5);
        project.addIssue(issue6);
        project.moveIssue(issue3, sprint);
        project.moveIssue(issue6, sprint);

        issue3.setExecutor(user3);
        issue6.setExecutor(user3);
    }

    @Test
    public void test_filter_priority_in_backlog() {
        FilterPriority filter = new FilterPriority(IssuePriority.MIDDLE);
        FilterHeap heap = new FilterHeap();
        heap.addFilter(filter);
        List<Issue> issues = heap.filter(project.getBacklog());

        assertThat(issues, containsInAnyOrder(issue2, issue4));
    }

    @Test
    public void test_filter_label_in_backlog() {
        FilterLabel filter = new FilterLabel("Epic");
        FilterHeap heap = new FilterHeap();
        heap.addFilter(filter);
        List<Issue> issues = heap.filter(project.getBacklog());

        assertThat(issues, contains(issue1));
    }

    @Test
    public void test_filter_executor_in_backlog() {
        FilterExecutor filter = new FilterExecutor(user3);
        FilterHeap heap = new FilterHeap();
        heap.addFilter(filter);
        List<Issue> issues = heap.filter(project.getBacklog());

        assertThat(issues.size(), equalTo(0));
    }

    @Test
    public void test_filter_reporter_in_backlog() {
        FilterReporter filter = new FilterReporter(user1);
        FilterHeap heap = new FilterHeap();
        heap.addFilter(filter);
        List<Issue> issues = heap.filter(project.getBacklog());

        assertThat(issues, containsInAnyOrder(issue1, issue2, issue4));
    }

    @Test
    public void test_filter_date_create_in_backlog() {
        FilterDateCreate filter = new FilterDateCreate(new Date());
        FilterHeap heap = new FilterHeap();
        heap.addFilter(filter);
        List<Issue> issues = heap.filter(project.getBacklog());

        assertThat(issues, containsInAnyOrder(issue1, issue2, issue4, issue5));
    }
}