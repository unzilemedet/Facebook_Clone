package com.muhammet.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * !!!!DİKKAT!!!!
 * Bu model üzerinden RabbitMQ ya mesaj ileteceğiz ve bu mesajı kuyruğa işlemesini
 * isteyeceğiz. Burada gönderilecek olan sınıf bilgisi rabbitMQ ya base64 olarak
 * iletilecektir. Bu nedenle, sınıfın serileştirilmesi gerekmektedir. buna riaeyet
 * etmek için sınıfın Serilizable interface ini implemente etmesi gerekir.
 * ÇOOOOK DİKKAT!!!!
 * Yine burada oluşturulan sınıf için. Paket ismi  ve sınıf içinde kullanılan
 * tüm parametrelerin aynı olması gerekir. Aksi takdirde nesneniz iletilen
 * tarafta okunamayacaktır.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateUserModel implements Serializable {
    Long authid;
    String username;
    String email;
}
