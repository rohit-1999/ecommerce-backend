package com.ecommerce.project.security.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {
    private String message;
    public MessageResponse(String s) {
        this.message=s;
    }
}
