package study.querydsl.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MemberDto {

    private String username;
    private int age;

    public MemberDto() { }

    @QueryProjection // 추가한 다음에 compileQuerydsl을 하면 새로 생성
    // 단점 : MemberDto가 Querydsl에 의존성을 가지게 됨
    public MemberDto(String username, int age) {
        this.username = username;
        this.age = age;
    }
}
