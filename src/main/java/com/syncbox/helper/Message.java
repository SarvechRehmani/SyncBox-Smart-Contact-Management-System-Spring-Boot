package com.syncbox.helper;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private String content;
    private MessageType type;
}
