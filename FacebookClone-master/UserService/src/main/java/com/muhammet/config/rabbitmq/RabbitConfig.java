package com.muhammet.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * Kuyruk sistemimizde kullanmak istediğimiz parametlere esas olmak üzere tanımlamalarını
     * yapıyoruz.
     */
    private String exchangeName = "auht-exchange";
    private String queueName = "auth-queue-create-user";
    private String routingOrBindingKey = "auth-routing-key-create-user";

    /**
     * Bir kuyruk ve bir BindingKey in bağlanması gerekli , bunu sağlayabilmek için
     * ortamda kuyruk,exchange nesneleri olmalı ve bu nesneleri bir birine bağlayan bir
     * binding nesnesi olmalıdır.
     */
    @Bean
    DirectExchange directAuthExchange(){
        return new DirectExchange(exchangeName);
    }
    /**
     * mesajların iletileceği ve dinleyenlerin okuyabileceği kuyruğu tanımlıyoruz.
     * @return
     */
    @Bean
    Queue authQueue(){
        return new Queue(queueName);
    }
    /**
     * Kuyruk ile Exchange arasında olan bağlantıyı kurmamaız gerekiyor. bunu yapmak için
     * binding nesnesine ihtiyaç var.
     */
    @Bean
    public Binding bindingAuthCreateUser(final Queue authQueue, final DirectExchange directAuthExchange){
        return BindingBuilder.bind(authQueue).to(directAuthExchange).with(routingOrBindingKey);
    }

}
