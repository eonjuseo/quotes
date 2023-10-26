package com.ll;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class quote {

    private int quoteNum;
    private String quote;
    private String writer;

    public quote(int quoteNum, String quote, String writer) {
        this.quoteNum = quoteNum;
        this.quote = quote;
        this.writer = writer;
    }

    public int getQuoteNum() {
        return quoteNum;
    }

    public String getQuote() {
        return quote;
    }

    public String getWriter() {
        return writer;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

}

public class Main {
    private static final String file = "C:\\Users\\82107\\quotedata.txt";

    public static void main(String[] args) throws IOException {
        System.out.println("== 명언 앱 ==");

        List<quote> quoteList = callData();
        int quoteNum = nextQuoteNum(quoteList);
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("명령) ");
            String command = sc.readLine();

            // 등록 목록 수정 삭제 종료
            switch (command) {
                case "등록":
                    System.out.print("명언 : ");
                    String quote = sc.readLine();
                    System.out.print("작가 : ");
                    String writer = sc.readLine();

                    quote quoteCreate = new quote(quoteNum, quote, writer);
                    quoteList.add(0, quoteCreate);
                    quoteNum++;
                    System.out.println(quoteNum - 1 + "번 명언이 등록되었습니다.");
                    break;

                case "삭제":
                    System.out.print("?id=");
                    int delNum = Integer.parseInt(sc.readLine());
                    boolean delete = false;
                    for (int i = 0; i < quoteList.size(); i++) {
                        if (quoteList.get(i).getQuoteNum() == delNum) {
                            quoteList.remove(i);
                            delete = true;
                            System.out.println(delNum + "번 명언이 삭제되었습니다.");
                            break;
                        }
                    }
                    if (!delete) {
                        System.out.println(delNum + "번 명언은 존재하지 않습니다.");
                    }
                    break;

                case "수정":
                    System.out.print("?id=");
                    int modNum = Integer.parseInt(sc.readLine());
                    boolean update = false;

                    for (int i = 0; i < quoteList.size(); i++) {

                        if (quoteList.get(i).getQuoteNum() == modNum) {
                            System.out.println("명언(기존) : " + quoteList.get(i).getQuote());
                            System.out.print("명언 : ");
                            String newQuote = sc.readLine();

                            if (!newQuote.isEmpty()) {
                                quoteList.get(i).setQuote(newQuote);
                            }

                            System.out.println("작가(기존) : " + quoteList.get(i).getWriter());
                            System.out.print("작가 : ");
                            String newWriter = sc.readLine();

                            if (!newWriter.isEmpty()) {
                                quoteList.get(i).setWriter(newWriter);
                            }

                            update = true;
                            System.out.println(modNum + "번 명언이 수정되었습니다.");
                            break;
                        }
                    }
                    if (!update) {
                        System.out.println(modNum + "번 명언은 존재하지 않습니다.");
                    }
                    break;

                case "목록":
                    System.out.println("번호 / 작가 / 명언");
                    System.out.println("----------------------");

                    for (quote quoteRead : quoteList) {
                        System.out.println(quoteRead.getQuoteNum() + " / "
                                + quoteRead.getWriter()
                                + " / " + quoteRead.getQuote());
                    }
                    break;

                case "빌드":
                    System.out.println("data.json파일의 내용이 갱신되었습니다.");


                case "종료":
                    saveData(quoteList);
                    return;

            }
        }
    }

    private static int nextQuoteNum(List<quote> quoteList) {
        int max = 0;
        for (quote quoteNext : quoteList) {
            if (quoteNext.getQuoteNum() > max) {
                max = quoteNext.getQuoteNum();
            }
        }
        return max + 1;
    }

    // 데이터 파일에서 명언 데이터를 읽어오는 함수
    private static List<quote> callData() {
        List<quote> quoteList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // 파일을 한 줄씩 읽어옵니다.
            while ((line = reader.readLine()) != null) {
                // 각 줄을 쉼표(,)로 분리하여 명언 번호, 명언 내용, 작가 정보를 추출합니다.
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    // 데이터를 추출하여 새 명언 객체를 생성하고 명언 목록에 추가합니다.
                    int quoteNum = Integer.parseInt(parts[0]);
                    String quote = parts[1];
                    String writer = parts[2];
                    quote quoteCall = new quote(quoteNum, quote, writer);
                    quoteList.add(quoteCall);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return quoteList; // 읽어온 명언 목록을 반환합니다.
    }

    // 명언 데이터를 파일에 저장하는 함수
    private static void saveData(List<quote> quoteList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (quote quoteSave : quoteList) {
                // 각 명언 객체의 정보를 CSV 형식으로 파일에 저장합니다.
                String line = String.format("%d,%s,%s%n", quoteSave.getQuoteNum(),
                        quoteSave.getQuote(), quoteSave.getWriter());
                writer.write(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}