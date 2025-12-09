package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static List<Article> articles = new ArrayList<>();
    static List<Member> members = new ArrayList<>();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("==프로그램 시작==");

        int lastArticleId = 3;
        int lastMemberId = 0;

        makeTestData();
        membersTest();




        System.out.println("로그인이 필요합니다.");
        System.out.println("*" + " 아이디가 없다면 \"회원가입\"이라고 작성해주세요" + "*");

        while (true) {
            System.out.print("명령어 ) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;
            } else if (cmd.length() == 0) {
                System.out.println("명령어 입력하세요");
                continue;
            }
            if (cmd.contains("Login")) {
                while (true) {
                    System.out.print("ID) ");
                    String id = sc.nextLine();
                    System.out.print("Password) ");
                    String password = sc.nextLine();
                    if (isJoinableLoginId(id) == false) {
                        if (password.equals((password))) {
                            for (Member member : members) {
                                System.out.println("로그인 되었습니다.");
                                break;
                            }
                        } else {
                            System.out.println("Password 또는 ID가 잘못 입력되었습니다.");
                        }
                    }
                }

            }
            if (cmd.equals("member join")) {
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
            } else if (cmd.equals("article write")) {
                System.out.println("==게시글 작성==");
                int id = lastArticleId + 1;
                System.out.print("제목 : ");
                String title = sc.nextLine().trim();
                System.out.print("내용 : ");
                String body = sc.nextLine().trim();
                String regDate = Util.getNowStr();
                String updateDate = Util.getNowStr();

                Article article = new Article(id, regDate, updateDate, title, body);
                articles.add(article);

                System.out.println(id + "번 글이 작성되었습니다.");
                lastArticleId++;
            } else if (cmd.startsWith("article list")) {
                System.out.println("==게시글 목록==");
                if (articles.size() == 0) {
                    System.out.println("아무것도 없음");
                    continue;
                }

                String searchKeyword = cmd.substring("article list".length()).trim();

                List<Article> forPrintArticles = articles;

                if (searchKeyword.length() > 0) {
                    System.out.println("검색어 : " + searchKeyword);
                    forPrintArticles = new ArrayList<>();

                    for (Article article : articles) {
                        if (article.getTitle().contains(searchKeyword)) {
                            forPrintArticles.add(article);
                        }
                    }
                    if (forPrintArticles.size() == 0) {
                        System.out.println("검색 결과 없음");
                        continue;
                    }
                }

                System.out.println("   번호  /       날짜       /       제목     /   내용  ");
                for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
                    Article article = forPrintArticles.get(i);
                    if (Util.getNowStr().split(" ")[0].equals(article.getRegDate().split(" ")[0])) {
                        System.out.printf("   %d     /    %s          /    %s     /     %s   \n", article.getId(), article.getRegDate().split(" ")[1], article.getTitle(), article.getBody());
                    } else {
                        System.out.printf("   %d     /    %s          /    %s     /     %s   \n", article.getId(), article.getRegDate().split(" ")[0], article.getTitle(), article.getBody());
                    }

                }

            } else if (cmd.startsWith("article detail")) {
                System.out.println("==게시글 상세보기==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("번호 : " + foundArticle.getId());
                System.out.println("작성날짜 : " + foundArticle.getRegDate());
                System.out.println("수정날짜 : " + foundArticle.getUpdateDate());
                System.out.println("제목 : " + foundArticle.getTitle());
                System.out.println("내용 : " + foundArticle.getBody());

            } else if (cmd.startsWith("article delete")) {
                System.out.println("==게시글 삭제==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                articles.remove(foundArticle);
                System.out.println(id + "번 게시글이 삭제되었습니다");
            } else if (cmd.startsWith("article modify")) {
                System.out.println("==게시글 수정==");

                int id = Integer.parseInt(cmd.split(" ")[2]);

                Article foundArticle = getArticleById(id);

                if (foundArticle == null) {
                    System.out.println("해당 게시글은 없습니다");
                    continue;
                }
                System.out.println("기존 title : " + foundArticle.getTitle());
                System.out.println("기존 body : " + foundArticle.getBody());
                System.out.print("새 제목 : ");
                String newTitle = sc.nextLine().trim();
                System.out.print("새 내용 : ");
                String newBody = sc.nextLine().trim();

                foundArticle.setTitle(newTitle);
                foundArticle.setBody(newBody);

                foundArticle.setUpdateDate(Util.getNowStr());

                System.out.println(id + "번 게시글이 수정되었습니다");
            } else {
                System.out.println("사용할 수 없는 명령어입니다");
            }
        }
        System.out.println("==프로그램 끝==");
        sc.close();
    }

    private static boolean isJoinableLoginId(String loginId) {
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)) {
                return false;
            }
        }
        return true;
    }

    private static Article getArticleById(int id) {
        for (Article article : articles) {
            if (article.getId() == id) {
                return article;
            }
        }
        return null;
    }

    /**
     * 게시글 테스트 데이터 생성
     **/
    private static void makeTestData() {
        System.out.println("==테스트 데이터 생성==");
        articles.add(new Article(1, "2025-12-07 12:12:12", "2025-12-07 12:12:12", "제목 123", "내용 1"));
        articles.add(new Article(2, Util.getNowStr(), Util.getNowStr(), "제목 23", "내용 2"));
        articles.add(new Article(3, Util.getNowStr(), Util.getNowStr(), "제목 1234", "내용 3"));
    }
    private static void membersTest() {
        System.out.println("==테스트 데이터 생성==");
        members.add(new Member(1, "2025-12-07 1:1:1", "2025-12-07 1:1:1", "규진", "내용 1","규진"));
        members.add(new Member(2, "2025-12-07 2:2:2", "2025-12-07 2:2:2", "큐진", "내용 2","규쥔"));
        members.add(new Member(3, "2025-12-07 3:3:3", "2025-12-07 3:3:3", "뀨진", "내용 3","규좡"));
    }
}

class Member {
    private int id;
    private String regDate;
    private String updateDate;
    private String loginId;
    private String password;
    private String name;

    public Member(int id, String updateDate, String regDate, String loginId, String password, String name) {
        this.id = id;
        this.updateDate = updateDate;
        this.regDate = regDate;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Article {
    private int id;
    private String regDate;
    private String updateDate;
    private String title;
    private String body;

    public Article(int id, String regDate, String updateDate, String title, String body) {
        this.id = id;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}