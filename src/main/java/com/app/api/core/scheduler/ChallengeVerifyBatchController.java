package com.app.api.core.scheduler;

import com.app.api.challenge.service.UserChallengeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChallengeVerifyBatchController {

    private final UserChallengeService userChallengeService;

    @Scheduled(cron = "30 0 0 * * *")
    public void challengeVerifyFailBatch() {
        LocalDateTime start = LocalDateTime.now();
        log.info("------------ challenge verify batch start -----------------");
        log.info("------------ time : " + start +  " -----------------");

        userChallengeService.startUserChallengeVerifyBatch(start);

        LocalDateTime end = LocalDateTime.now();
        log.info("------------ challenge verify batch end -----------------");
        log.info("------------ time : " + end +  " -----------------");

    }
}
