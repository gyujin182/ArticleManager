package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MemberController extends Controller {

    private Scanner sc;
    private List<Member> members;
    private String cmd;

    private int lastMemberId = 3;

    public MemberController(Scanner sc) {
        this.sc = sc;
        members = new ArrayList<>();
    }

    public void doAction(String cmd, String actionMethodName) {
        this.cmd = cmd;

        switch (actionMethodName) {
            case "join":
                doJoin();
                break;
            case "Login":
                doLogin();
                break;
            default:
                System.out.println("Invalid action method");
                break;
        }
    }
    public void doLogin() {
                System.out.print("로그인 아이디 : ");
                String loginId = sc.nextLine().trim();
                boolean loginid = false;
                for (Member member : members) {
                    if (member.getLoginId().equals(loginId)) {
                        loginid = true;
                        break;
                    }
                }
                if (!loginid) {
                    System.out.println("존재하지않는 loginId");
                } else {
                    String loginpassword = null;
                    System.out.print("비밀번호 : ");
                    loginpassword = sc.nextLine().trim();
                    for (Member member : members) {
                        if (member.getPassword().equals(loginpassword)) {
                            System.out.println(member.getName() + "님 로그인 되었습니다.");
                            break;
                        } else {
                            System.out.println("password가 틀렸습니다.");
                        }
                    }
                }
    }

    private void doJoin() {
        System.out.println("==회원 가입==");
        int id = lastMemberId + 1;
        String loginId = null;
        while (true) {
            System.out.print("로그인 아이디 : ");
            loginId = sc.nextLine().trim();
            if (isJoinableLoginId(loginId) == false) {
                System.out.println("이미 사용중인 loginId");
                continue;
            }
            break;
        }
        String password = null;
        while (true) {
            System.out.print("비밀번호 : ");
            password = sc.nextLine().trim();
            System.out.print("비밀번호 확인: ");
            String passwordConfirm = sc.nextLine().trim();
            if (password.equals(passwordConfirm) == false) {
                System.out.println("비번 확인해");
                continue;
            }
            break;
        }
        System.out.print("이름 : ");
        String name = sc.nextLine().trim();
        String regDate = Util.getNowStr();
        String updateDate = Util.getNowStr();

        Member member = new Member(id, regDate, updateDate, loginId, password, name);
        members.add(member);

        System.out.println(id + "번 회원이 가입 되었습니다.");
        lastMemberId++;
    }

    private boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 회원 테스트 데이터 생성
     **/
    public void makeTestData() {
        System.out.println("==회원 테스트 데이터 생성==");
        members.add(new Member(1, Util.getNowStr(), Util.getNowStr(), "test1", "test1", "회원1"));
        members.add(new Member(2, Util.getNowStr(), Util.getNowStr(), "test2", "test2", "회원2"));
        members.add(new Member(3, Util.getNowStr(), Util.getNowStr(), "test3", "test3", "회원3"));
    }
}