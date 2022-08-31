package com.example.springproject.service.openAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class PublicAPI {

    /* 8/29
    데이터 적을 때는 Gson이 좋다.

    URL?하고서 사용한 것은 clase로 반환을 해준다. 사용한 것은 반환을 해주어야 한다.
     */


    //메서드마다 기능은 꼭 하나씩만 하도록 규칙 SOLID
    public void resultAPI() {
        String result = readAPI();

        /* 8/31
        jacson : 빅데이터 등 큰 사이즈의 json 처리
        gson : 분산 아키텍쳐 설정 등 작은 용량의  json
        somplejson : 골고루 빠름

        json 방식은 http 프로토콜을 통해서 데이터 전송규약 (클라이언트와 백엔드 통신)
        백엔드와 백엔드 통신도 json 방식 > grpc 통신의 등장으로 다수의 백엔드 통신은 grpc로 변경. (앱통신도 grpc)
        http 프로토콜을 쓰는 경우가 많이 줄어듦음.
        신입이 grpc를 하는 것은 어려움. 다만 이런 것이 있구나 하는 정도로만 알면 좋음.
        5년 전쯤에는 회사에서 만들기도 했었다는데, 지금은 얼마나 정보가 퍼졌을지 알 수 없음.
        사람인에 grpc 검색해보는 것도 좋음.

        인증서는 서버마다 컴퓨터마다 무료로 되어 있다고 하나 거기까지 배우지 않고, 단순히 데이터를 받아와서 파싱하는 것까지만 함.
         */

        //Gson transfer (모양 보이기를 예쁘게 해줌.)
        Gson pretty = new GsonBuilder().setPrettyPrinting().create();
        String element = pretty.toJson(result);

        System.out.println(element);



        //String 문자열을 dot 객체로 변환
        //fromJson (문자열, DTO객체.class(런타임시점 객체))

    }


    public String readAPI() {
        //인증 키
        String key = "%2Fk49W4UhNTuGlvyhZ6NCaHVhV1%2BBp0wbhWy0YjmvKgHQSFbVPwQqzw4ppSYg8O9ubHyLPYi8N%2F0e4yGvEQKGug%3D%3D";

        //데이터를 파싱받을 변수
        String brResult ="";
        //데이터를 받아와서 String 객체로 만들기 전에 한줄씩 더해야 하므로, StringBuilder를 사용함.
        StringBuilder sb = new StringBuilder();

        //JSON API라는 것은 네트워크 통신을 통해 데이터를 다운받아 서비스 할 수 있도록 가공하기 위한 데이터
        //네트워크 통신이 끊기거나 예외적인 상황을 상정
        try {
            //https는 인증서가 필요하므로 http로 데이터 다운
            URL url = new URL("http://apis.data.go.kr/B551177/BusInformation/getBusInfo?serviceKey="
                    + key +"&numOfRows=2&pageNo=1&area=1&type=json");

            //인증서가 필요한 객체
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            while ((brResult = br.readLine()) !=null) {
                sb.append(brResult);
                System.out.println(brResult);
            }

            //자원을 끊어주어, 자원의 재활용을 막아줌. 사용한 것은 반환을 해주어야 한다. 닷 클래스(.class는 실행이 완료된 후, 그 파일의 데이터를 가져오는 것이다.)
            //이것 안해주어서 서버가 터지는 경우가 종종 있음.
            br.close();
            con.disconnect();

        }catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
