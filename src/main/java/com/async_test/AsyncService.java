package com.async_test;

import com.async_test.persist.UserEntity;
import com.async_test.persist.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AsyncService {

    private static final Logger logger = LoggerFactory.getLogger(AsyncService.class);

    private final UserRepository userRepository;


    @Async
    @Transactional
    public void saveAtDataBase(String name, String email) {

         userRepository.save(
            UserEntity.builder()
                .name(name)
                .email(email)
                .build()
        );

        //return CompletableFuture.completedFuture(savedUserEntity);
    }

}



















