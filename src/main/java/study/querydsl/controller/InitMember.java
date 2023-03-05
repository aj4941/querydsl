package study.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Profile("local") // Profile : test인 테스트 코드를 아무리 돌려봐도 이건 실행되지 않음
@Component
@RequiredArgsConstructor
public class InitMember {

    private final InitMemberService initMemberService;

    @PostConstruct // @PostConstruct와 @Transactional은 분리시켜서 사용해야 한다.
    public void init() {
        initMemberService.init();
    }

    @Component // 위에 initMemberService가 주입받기 위함
    static class InitMemberService {

        @Autowired
        private EntityManager em;

        @Transactional
        public void init() {
            Team teamA = new Team("teamA");
            Team teamB = new Team("teamB");

            em.persist(teamA); em.persist(teamB);

            for (int i = 0; i < 100; i++) {
                Team selectedTeam = (i % 2 == 0) ? teamA : teamB;
                em.persist(new Member("member" + i, i, selectedTeam));
            }
        }
    }
}
