package org.example.demotgbot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "tg.bot")
public class BotProperties {
    private String botName;
    private String token;
}
