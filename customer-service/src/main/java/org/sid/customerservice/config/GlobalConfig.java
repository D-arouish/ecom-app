package org.sid.customerservice.config;


import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;



//create class (or record) that contains all variables then inject what ever you want
@ConfigurationProperties(prefix = "global.params")
@Getter @Setter @ToString @AllArgsConstructor @NoArgsConstructor
public class GlobalConfig{
        private int p1;
        private int p2;

}
