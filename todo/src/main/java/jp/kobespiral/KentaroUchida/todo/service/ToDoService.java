package jp.kobespiral.KentaroUchida.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 

import jp.kobespiral.KentaroUchida.todo.dto.ToDoForm;
import jp.kobespiral.KentaroUchida.todo.entity.ToDo;
import jp.kobespiral.KentaroUchida.todo.exception.ToDoAppException;
import jp.kobespiral.KentaroUchida.todo.repository.ToDoRepository;

@Service
public class ToDoService {
   @Autowired
   ToDoRepository tRepo;
   /**
    * ToDoを作成する (C)
    * @param form
    * @return
    */
   public ToDo createToDo(String mid, ToDoForm form) {
       ToDo t = form.toEntity();
       t.setMid(mid);
       t.setDone(false);
       return tRepo.save(t);
   }
   /**
    * ToDoを取得する (R)
    * @param mid
    * @return
    */
   public ToDo getToDo(Long seq) {
       ToDo t = tRepo.findById(seq).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_TODO_EXISTS, seq + ": No such todo exists"));
       return t;
   }
   /**
    * midのToDoリストを取得する(R)
    * @param mid
    * @return
    */
   public List<ToDo> getToDoList(String mid) {
       return tRepo.findByMidAndDone(mid,false);
   }
   /**
    * midのDoneリストを取得する(R)
    * @param mid
    * @return
    */
    public List<ToDo> getDoneList(String mid) {
        return tRepo.findByMidAndDone(mid,true);
    }
   /**
    * 全員のToDoを取得する (R)
    * @return
    */
   public List<ToDo> getToDoList() {
       return tRepo.findByDone(false);
   }
   /**
    * 全員のDoneを取得する (R)
    * @return
    */
    public List<ToDo> getDoneList() {
        return tRepo.findByDone(true);
    }
    /**
    * ToDoをDoneする (R)
    */
    public void done(Long seq) {
        ToDo t = getToDo(seq);
        t.setDone(true);
        t.setDoneAt(new Date());
        tRepo.save(t);
    }

}