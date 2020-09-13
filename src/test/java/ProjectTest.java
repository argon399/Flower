import org.flower.project.Project;
import org.flower.project.Sprint;
import org.flower.project.issue.Issue;
import org.flower.project.issue.IssuePriority;
import org.flower.project.issue.IssueType;
import org.flower.project.team.Team;
import org.flower.project.team.User;
import org.junit.jupiter.api.Test;

public class ProjectTest {
    private User user1 = new User();
    private Team team = new Team();
    private Project project = new Project();
    private Issue issue1 = new Issue(1, "Issue 1", IssuePriority.MIDDLE, IssueType.EPIC, user1);
    private Sprint sprint = new Sprint();

    @Test
    public void test_workflow_add_issue_backlog() {
        assert(!project.getBacklog().contains(issue1));

        project.addIssue(issue1);
        assert(project.getBacklog().contains(issue1));
    }

    @Test
    public void test_workflow_add_sprint() {
        assert(!project.getSprints().contains(sprint));

        project.addSprint(sprint);
        assert(project.getSprints().contains(sprint));
    }

    @Test
    public void test_workflow_del_sprint() {
        project.addSprint(sprint);
        assert(project.getSprints().contains(sprint));

        project.removeSprint(sprint);
        assert(!project.getSprints().contains(sprint));
    }

    @Test
    public void test_workflow_move_issue_sprint() {
        project.addIssue(issue1);
        assert(project.getBacklog().contains(issue1));

        project.moveIssue(issue1, sprint);
        assert(!project.getBacklog().contains(issue1));
        assert(sprint.getIssues().contains(issue1));
    }

    @Test
    public void test_workflow_del_issue_backlog() {
        project.addIssue(issue1);
        assert(project.getBacklog().contains(issue1));

        project.removeIssue(issue1);
        assert(!project.getBacklog().contains(issue1));
    }
 }
