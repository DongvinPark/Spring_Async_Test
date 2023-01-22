package com.async_test;

import com.async_test.persist.NoteEntity;
import com.async_test.persist.NoteRepository;
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
    private final NoteRepository noteRepository;


    @Async
    @Transactional
    public void saveUser(String name, String email) {
        userRepository.save(
            UserEntity.builder()
                .name(name)
                .email(email)
                .build()
        );
        //일부러 예외 발생시키기. 여기서 예외 발생시키고, try,catch 등을 사용해서 치리하지 않아도,
        //config 패키지에 정의한 커스텀 어싱크 예외 핸들러가 잡아내서 처리해준다.
        int k = 1/0;
    }

    @Async
    @Transactional
    public void saveNote(String name, String count){
        noteRepository.save(
            NoteEntity.builder()
                .name(name)
                .pageCount(count)
                .build()
        );
    }

}



















