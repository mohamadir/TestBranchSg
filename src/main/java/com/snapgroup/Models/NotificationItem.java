package com.snapgroup.Models;

/**
 * Created by snapgroup2 on 07/09/17.
 */

public class NotificationItem {
    public  String id,sender,group_id,subject,body,createdAt,notificationId,type,accepted;
    public boolean status;

    public NotificationItem(String sender, String group_id, String subject, String body, String createdAt, String notificationId, boolean status,String type,String accepted) {
        this.id = id;
        this.sender = sender;
        this.group_id = group_id;
        this.subject = subject;
        this.body = body;
        this.createdAt = createdAt;
        this.notificationId = notificationId;
        this.status = status;
        this.type=type;
        this.accepted=accepted;
    }
}
