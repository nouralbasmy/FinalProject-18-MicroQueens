package com.example.notification.service;

import com.example.notification.clients.CustomerClient;
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



    private final CustomerClient customerClient;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, MongoClient mongoClient, NotificationSender notificationSender, RabbitMQProducer rabbitMQProducer, CustomerClient customerClient) {
        this.notificationRepository = notificationRepository;
        this.mongoClient = mongoClient;
        this.notificationSender=notificationSender;
        this.rabbitMQProducer = rabbitMQProducer;
        this.customerClient = customerClient;

    }

//    public Notification addNotification(Notification notification) {
//
//        String email = customerClient.getEmailById(notification.getUserId());
//        String phoneNumber = customerClient.getPhoneNumberById(notification.getUserId());
//        notification.setEmail(email);
//        notification.setPhone(phoneNumber);
//        Notification savedNotification=notificationRepository.save(notification);
//
//        notificationSender.send(notification.getNotificationType(), savedNotification);
//        String toSendMessage = savedNotification.getOrderId() + ";" +
//        savedNotification.getRestaurantId() + ";" +
//        savedNotification.getMessage();
//
//        rabbitMQProducer.sendToCustomer(toSendMessage);
//        return savedNotification;
//    }

    public Notification addNotification(Long userId, Long restaurantId,Long orderId, String notificationType) {

        Notification notification = new Notification(userId,orderId, restaurantId,notificationType,false,"");
        String email = customerClient.getEmailById(notification.getUserId());
        String phoneNumber = customerClient.getPhoneNumberById(notification.getUserId());
        notification.setEmail(email);
        notification.setPhone(phoneNumber);
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


    ///////////////////////////////////////////////////////////////////////////

    public List<Notification> getByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }


    public List<Notification> getByRestaurantId(Long restaurantId) {
        return notificationRepository.findByRestaurantId(restaurantId);
    }

    public Notification markAsRead(String id) {
        return updateReadStatus(id, true);
    }

    public Notification markAsUnread(String id) {
        return updateReadStatus(id, false);
    }

    public Notification updateReadStatus(String id, boolean read) {
        Optional<Notification> optional = notificationRepository.findById(id);
        if (optional.isPresent()) {
            Notification notification = optional.get();
            notification.setRead(read);
            return notificationRepository.save(notification);
        }
        return null;
    }
}
