package jp.kobespiral.KentaroUchida.todo.dto;

import jp.kobespiral.KentaroUchida.todo.entity.Member;
// import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.NoArgsConstructor;
@Data
public class MemberForm {
    String mid; //メンバーID．
    String name; //名前

    public Member toEntity() {
        Member m = new Member(mid, name);
        return m;
    }
}