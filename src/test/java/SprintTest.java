import org.flower.project.Sprint;
import org.flower.project.issue.Issue;
import org.flower.project.issue.IssuePriority;
import org.flower.project.issue.IssueType;
import org.flower.project.team.User;
import org.junit.jupiter.api.Test;

public class SprintTest {
    private Sprint sprint = new Sprint();
    private User user = new User();
    private Issue issue = new Issue(1, "Issue 1", IssuePriority.MIDDLE, IssueType.TASK, user);

    @Test
    public void text_sprint_add_issue() {
        assert(sprint.getIssues().size() == 0);

        sprint.addIssue(issue);
        assert(sprint.getIssues().contains(issue));
    }

    @Test
    public void text_sprint_del_issue() {
        sprint.addIssue(issue);
        assert(sprint.getIssues().contains(issue));

        sprint.deleteIssue(issue);
        assert(!sprint.getIssues().contains(issue));
        assert(sprint.getIssues().size() == 0);
    }
}
