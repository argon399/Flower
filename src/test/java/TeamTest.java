import org.flower.project.team.Team;
import org.flower.project.team.TeamRole;
import org.flower.project.team.User;
import org.junit.jupiter.api.Test;

public class TeamTest {
    private Team team = new Team();
    private User user1 = new User();
    private User user2 = new User();

    @Test
    public void test_team_leader() {
        assert(team.getLeader() == null);

        team.setLeader(user1);
        assert(team.getLeader().equals(user1));
    }

    @Test
    public void test_team_member() {
        assert(!team.isMember(user2));

        team.addMember(user2, TeamRole.DEVELOPER);
        assert(team.isMember(user2));

        team.deleteMember(user2);
        assert(!team.isMember(user2));
    }

}
