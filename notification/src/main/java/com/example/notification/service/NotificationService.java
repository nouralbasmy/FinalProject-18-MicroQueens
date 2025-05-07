package com.example.notification.service;

import com.example.notification.model.Notification;
import com.example.notification.rabbitmq.RabbitMQProducer;
import com.example.notification.repository.NotificationRepository;
import com.example.notification.strategy.NotificationSender;
import com.mongodb.client.MongoClient;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.BooleanOperators.Not;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private NotificationRepository notificationRepository;
    private MongoClient mongoClient;
    private final RabbitMQProducer rabbitMQProducer;

    private NotificationSender notificationSender;


    @Autowired
    public NotificationService(NotificationRepository notificationRepository, MongoClient mongoClient, NotificationSender notificationSender, RabbitMQProducer rabbitMQProducer) {
        this.notificationRepository = notificationRepository;
        this.mongoClient = mongoClient;
        this.notificationSender=notificationSender;
        this.rabbitMQProducer = rabbitMQProducer;

    }

    public Notification addNotification(Notification notification) {

        Notification savedNotification=notificationRepository.save(notification);
        notificationSender.send(notification.getNotificationType(), savedNotification);
        String toSendMessage = savedNotification.getOrderId() + ";" +
        savedNotification.getRestaurantId() + ";" +
        savedNotification.getMessage();

        rabbitMQProducer.sendToCustomer(toSendMessage);
        return savedNotification;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
    public Notification getNotificationById(String id) {
        return notificationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Notification not found"));
    }
    public Notification updateNotification(String id, Notification updatedNotification) {
        Optional<Notification> notification = notificationRepository.findById(id);
        if (notification.isPresent()) {
            notification.get().setMessage(updatedNotification.getMessage());
            notification.get().setNotificationTime(LocalDateTime.now());
            return notificationRepository.save(notification.get());

        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Notification not found");

    }
    public void deleteNotification(String id) {
        notificationRepository.deleteById(id);
    }


}