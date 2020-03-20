package biz.middleware.log.controller;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class BizMiddlewareLogController {

    @RequestMapping(method = {RequestMethod.GET}, path = "/biz/middleware/change/log/level")
    public String changeLogLevel(@RequestParam("loggerName") String loggerName,
                                 @RequestParam("logLevel") String logLevel) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        ch.qos.logback.classic.Logger logger = loggerContext.getLogger(loggerName);

        Level level = Level.toLevel(logLevel);
        if (level == null) {
            return "invalid log level " + logLevel;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(loggerName).append(" logger level: ").append(logger.getLevel())
                .append(" change to ").append(logLevel);

        logger.setLevel(Level.toLevel(logLevel));

        return sb.toString();
    }

    @GetMapping("/biz/middleware/get/log/level")
    public String getLogLevel(@RequestParam("loggerName") String loggerName) {

        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

        ch.qos.logback.classic.Logger logger = loggerContext.getLogger(loggerName);

        if (logger == null) {
            return "not found logger " + loggerName;
        }

        return logger.getLevel() == null ? "null" : logger.getLevel().toString();
    }

    @GetMapping("/biz/middleware/log")
    public String log(@RequestParam("loggerName") String loggerName,
                      @RequestParam("logLevel") String logLevel,
                      @RequestParam("content") String content) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger = loggerContext.getLogger(loggerName);
        if (logger == null) {
            return "not found logger " + loggerName;
        }
        Level level = Level.toLevel(logLevel);
        if (level == null) {
            return "invalid log level " + logLevel;
        }
        int intLevel = Level.toLocationAwareLoggerInteger(level);
        logger.log(null, BizMiddlewareLogController.class.getName(), intLevel, content, null, null );

        return content;
    }
}
