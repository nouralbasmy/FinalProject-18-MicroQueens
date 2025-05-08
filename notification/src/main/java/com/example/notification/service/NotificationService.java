package com.example.notification.service;

import com.example.notification.model.Notification;
import com.example.notification.repository.NotificationRepository;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public NotificationService(NotificationRepository notificationRepository, MongoClient mongoClient) {
        this.notificationRepository = notificationRepository;
        this.mongoClient = mongoClient;
    }

    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
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
