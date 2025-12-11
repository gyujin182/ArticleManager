package org.example.ArticleMasnager;

import org.example.controller.ArticleController;
import org.example.controller.Controller;
import org.example.controller.MemberController;

import java.util.Scanner;

public class App {
    public void run() {
        Scanner sc = new Scanner(System.in);

        System.out.println("==프로그램 시작==");

        MemberController memberController = new MemberController(sc);
        ArticleController articleController = new ArticleController(sc);

        articleController.makeTestData();
        memberController.makeTestData();

        Controller controller = null;

        while (true) {
            System.out.print("명령어 ) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("exit")) {
                break;
            } else if (cmd.length() == 0) {
                System.out.println("명령어 입력하세요");
                continue;
            }

            String[] cmdBits = cmd.split(" ");

            String controllerName = cmdBits[0];

            if (cmdBits.length == 1) {
                System.out.println("명령어 확인 필요");
                continue;
            }

            String actionMethodName = cmdBits[1];

            String forLoginChecks = controllerName + "/" + actionMethodName;

            switch (forLoginChecks) {
                case "article/write":
                case "article/delete":
                case "article/modify":
                case "member/logout":
                    if (Controller.isLogined() == false) {
                        System.out.println("로그인이 필요해");
                        continue;
                    }
                    break;
            }

            switch (forLoginChecks) {
                case "member/login":
                case "member/join":
                    if (Controller.isLogined()) {
                        System.out.println("로그아웃이 필요해");
                        continue;
                    }
                    break;
            }


            if (controllerName.equals("article")) {
                controller = articleController;
            } else if (controllerName.equals("member")) {
                controller = memberController;
            } else {
                System.out.println("지원하지 않는 기능");
                continue;
            }

            controller.doAction(cmd, actionMethodName);

        }
        System.out.println("==프로그램 끝==");
        sc.close();
    }
}