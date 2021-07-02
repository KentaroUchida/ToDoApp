package jp.kobespiral.KentaroUchida.todo.controller;

import java.util.Date;
import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobespiral.KentaroUchida.todo.dto.MemberForm;
import jp.kobespiral.KentaroUchida.todo.dto.ToDoForm;
import jp.kobespiral.KentaroUchida.todo.entity.Member;
import jp.kobespiral.KentaroUchida.todo.entity.ToDo;
import jp.kobespiral.KentaroUchida.todo.repository.ToDoRepository;
import jp.kobespiral.KentaroUchida.todo.service.MemberService;
import jp.kobespiral.KentaroUchida.todo.service.ToDoService;

/**
 * ToDoのコントローラ
 */
@Controller
public class ToDoController {
    @Autowired
    ToDoService ts;
    @Autowired
    MemberService ms;
    ToDoRepository tRepo;

    @GetMapping("/")
    public String showLogin(Model model) {
        return "login";
    }

    /**
     * ログイン画面
     * 
     * @param model
     * @return ログイン画面ページを表示
     */
    @PostMapping("/login")
    public String Login(String mid,Model model) {
        Member m = ms.getMember(mid);
        return "redirect:/" + mid;
    }

    /**
     * ユーザ用のToDoリスト一覧を表示
     * 
     * @param mid   ユーザID
     * @param model
     * @return ToDoリストのページを表示
     */
    @GetMapping("/{mid}")
    public String showToDoListOfMember(@PathVariable String mid, Model model) {
        // そのユーザのToDoリスト
        List<ToDo> todos = ts.getToDoList(mid);
        // そのユーザのDoneリスト
        List<ToDo> dones = ts.getDoneList(mid);

        // Doneはdone日時が新しいもの順にソート
        dones.sort((a, b) -> a.getDoneAt().before(b.getDoneAt()) ? 1 : -1);

        Member member = ms.getMember(mid);
        ToDoForm blankForm = new ToDoForm();

        // テンプレートにオブジェクトをセットする
        model.addAttribute("todos", todos);
        model.addAttribute("dones", dones);
        model.addAttribute("member", member);
        model.addAttribute("ToDoForm", blankForm);

        return "mytodos";
    }

    /**
     * 全ユーザのToDoリスト一覧を表示
     * 
     * @param mid   ユーザID
     * @param model
     * @return 全ユーザのToDoリストのページを表示
     */
    @GetMapping("/all")
    public String showToDoListOfAll(Model model) {
        //System.out.println("mid=" +mid +"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        // すべてのユーザのToDoリスト
        List<ToDo> todos = ts.getToDoList();
        // すべてのユーザのDoneリスト
        List<ToDo> dones = ts.getDoneList();

        // Doneはdone日時が新しいもの順にソート
        //dones.sort((a, b) -> a.getDoneAt().before(b.getDoneAt()) ? 1 : -1);

        //Member member = ms.getMember(mid);
        //ToDoForm blankForm = new ToDoForm();

        // テンプレートにオブジェクトをセットする
        model.addAttribute("todos", todos);
        model.addAttribute("dones", dones);
        //model.addAttribute("member", member);
        //model.addAttribute("ToDoForm", blankForm);
       // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

        return "ourtodos";
    }

    /**
     * あるユーザのToDoを新規作成する
     * 
     * @param mid           ユーザID
     * @param form          テンプレートから渡されるフォーム
     * @param model
     * @return ToDoリストのページを表示
     */
    @PostMapping("/{mid}/register")
    public String createToDo(@PathVariable String mid,@ModelAttribute(name = "ToDoForm") ToDoForm form,  Model model) {
        ToDo t = ts.createToDo(mid,form);
        model.addAttribute("ToDoForm", t);
        return showToDoListOfMember(mid, model);
    }

    /**
     * あるユーザのToDoをdoneする
     * 
     * @param mid           ユーザID
     * @param form          テンプレートから渡されるフォーム
     * @param model
     * @return ToDoリストのページを表示
     */
    @PostMapping("/{mid}/{seq}/done")
    public String doneToDo(@PathVariable String mid,@PathVariable Long seq,Model model) {
        ToDo t = ts.getToDo(seq);
        t.setDone(true);
        tRepo.save(t);
        return showToDoListOfMember(mid, model);
    }

}
/* 
@Controller
public class ToDoController {
   @Autowired
   ToDoService tService;

   /**
    * ログインページ HTTP-GET /member/ooo/login
    * @param model
    * @return
    *
   @GetMapping("/login")
   String Login()
   /**
    * 管理者用・ユーザ登録ページ HTTP-GET /member/ooo/register
    * @param model
    * @return
    *
   @GetMapping("/mytodos")
   String showUserToDos(Model model) {
       List<ToDo> ToDos = tService.getToDoList();
       model.addAttribute("todos", ToDos);
       ToDoForm form = new ToDoForm();
       model.addAttribute("ToDoForm", form);
       
       return "mytodos";
   }
   /**
    * 管理者用・ユーザ登録ページ HTTP-GET /member/ooo/register
    * @param model
    * @return
    *
    @GetMapping("/mytodos")
    String showUserForm(Model model) {
        List<ToDo> ToDos = tService.getToDoList();
        model.addAttribute("members", members);
        MemberForm form = new MemberForm();
        model.addAttribute("MemberForm", form);
        
        return "mytodos";
    }
   /**
    * 管理者用・ユーザ登録確認ページを表示 HTTP-POST /admin/check
    * @param form
    * @param model
    * @return
    *
   @PostMapping("/check") 
   String checkUserForm(@ModelAttribute(name = "MemberForm") MemberForm form,  Model model) {
       model.addAttribute("MemberForm", form);
       return "check";
   }
   /**
    * 管理者用・ユーザ登録処理 -> 完了ページ HTTP-POST /admin/register
    * @param form
    * @param model
    * @return
    *
   @PostMapping("/register")
   String createUser(@ModelAttribute(name = "MemberForm") MemberForm form,  Model model) {
       Member m =  mService.createMember(form);
       model.addAttribute("MemberForm", m);
       return "registered";
   }
   /**
    * 管理者用・ユーザ削除処理　HTTP-GET /admin/delete/{mid}
    * @param mid
    * @param model
    * @return
    *
   @GetMapping("/delete/{mid}")
   String deleteUser(@PathVariable String mid, Model model) {
       mService.deleteMember(mid);
       return showUserForm(model);
   }
}
*/