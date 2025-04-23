package com.async_test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final AsyncService asyncService;


    @GetMapping("/run")
    public void run(){
        //1초 이내의 짧은 순간에 1500개의 요청이 몰렸다고 가정하자.
        //총 3000번의 DB 입출력이 필요하지만 시리얼로 처리한다.

        //시간 측정 시작.
        long start = System.currentTimeMillis();
        for(int i=1; i<=1500; i++){
            asyncService.saveUserNonAsync("user", "e1@mail.com");
            asyncService.saveNoteNonAsync("John", "0x12AB4");
        }
        //결과를 측정된 시간을 표시하고 리턴한다.
        long end = System.currentTimeMillis();
        System.out.println("Without @Async 소요 시간(m/s) : " + (end - start));

        ResponseEntity.ok("All async tasks finished in: " + (end - start) + " ms");
    }

    @GetMapping("/async-true-wait")
    public void asyncTrueWait(){
        //똑같이 1초 이내의 짧은 순간에 1500개의 요청이 몰렸다고 가정하자.
        //총 3000번의 DB 입출력이 필요하지만 Thread pool과 Async로 처리한다.

        //시간 측정 시작.
        long start = System.currentTimeMillis();
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for(int i=1; i<=1500; i++){
            futures.add(asyncService.saveUserTrueAsync("user", "e1@mail.com"));
            futures.add(asyncService.saveNoteTrueAsync("John", "0x12AB4"));
        }
        // 모든 작업이 끝날 때까지 기다려본다.
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        long end = System.currentTimeMillis();

        //결과를 측정된 시간을 표시하고 리턴한다.
        System.out.println("With @Async 소요 시간(m/s) : " + (end - start));
        ResponseEntity.ok("All async tasks finished in: " + (end - start) + " ms");
    }

}
