package jp.kobespiral.KentaroUchida.todo.dto;

import java.util.Date;

import jp.kobespiral.KentaroUchida.todo.entity.ToDo;
// import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.NoArgsConstructor;
@Data
public class ToDoForm {
    String title; //todoタイトル

    public ToDo toEntity(){
        ToDo t = new ToDo();
        t.setTitle(title);
        t.setCreatedAt(new Date());
        return t;
    }
}