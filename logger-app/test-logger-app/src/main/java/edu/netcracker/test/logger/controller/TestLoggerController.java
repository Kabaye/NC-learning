package edu.netcracker.test.logger.controller;

import edu.netcracker.test.logger.service.TestLoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author svku0919
 * @version 19.10.2020
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class TestLoggerController {

    private final TestLoggerService testLoggerService;

    @GetMapping("/random")
    public String getRandomString(@RequestParam(required = false) Integer intAmount) {
        return testLoggerService.getRandomString(intAmount);
    }
}
