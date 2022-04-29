package com.baglie.VirtualQueue.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private long id;
    private String username;
    private boolean connected;
    private int position;
}
