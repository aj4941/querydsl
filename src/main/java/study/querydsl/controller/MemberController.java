package study.querydsl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.repository.MemberJpaRepository;
import study.querydsl.repository.MemberRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberJpaRepository memberJpaRepository;
    private final MemberRepository memberRepository;

    // localhost:8080/v1/members?teamName=teamB&ageGoe=31&ageLoe=25 등으로 쿼리 파라미터 전달 가능
    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }

    // localhost:8080/v2/members?page=0&size=5 로 페이징 관련 데이터 넣기 가능
    // 여기서 pageable.getOffset()은 page * size로 작용하고, pageable.getPageSize()는 size로 작용한다
    // 즉 .offset(page * size), .limit(size)로 가져오게 되는 것이다.
    @GetMapping("/v2/members")
    public Page<MemberTeamDto> searchMemberV2(MemberSearchCondition condition, Pageable pageable) {
        return memberRepository.searchPageComplex(condition, pageable);
        // 결과는 먼저 데이터의 개수가 size만큼 나오고 페이징과 관련한 데이터도 출력된다.
    }
}
