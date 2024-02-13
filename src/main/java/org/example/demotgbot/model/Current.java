package org.example.demotgbot.model;

import lombok.Data;

@Data
public class Current {
    private Condition condition;
    private String last_updated;
    private String temp_c;
    private String temp_f;
}
