package com.async_test;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final AsyncService asyncService;


    @PostMapping("/run")
    public void run(){
        //시간 측정 시작.

        // 로컬 디비 호출하기. 응원이나 잔소리 1번당 DB 호출 3번을 필요로 한다.
        //1초 이내의 짧은 순간에 1000개의 요청이 몰렸다고 가정하자.
        //총 3000번의 DB 입출력이 필요하다.
        long start = System.currentTimeMillis();
        for(int i=1; i<=3000; i++){
            asyncService.saveAtDataBase("user", "e1@mail.com");
        }
        // 모든 작업이 끝날 때까지 기다리지 않는다.
        //CompletableFuture.allOf(page1, page2, page3).join();

        //결과를 측정된 시간과 함께 표시한다.
        System.out.println("소요 시간(m/s) : " + (System.currentTimeMillis() - start));
    }

}
