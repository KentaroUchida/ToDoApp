package jp.kobespiral.KentaroUchida.todo.repository;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.kobespiral.KentaroUchida.todo.entity.ToDo;
@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long>{
    List<ToDo> findAll();
    
    List<ToDo> findByDone(boolean done);
    List<ToDo> findByMid(String mid);
    List<ToDo> findByMidAndDone(String mid, boolean done);

}