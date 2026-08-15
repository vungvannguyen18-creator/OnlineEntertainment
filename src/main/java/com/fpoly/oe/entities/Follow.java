package com.fpoly.oe.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Follows", uniqueConstraints = {@UniqueConstraint(columnNames = {"FollowerId", "ChannelId"})})
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "FollowerId", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "ChannelId", nullable = false)
    private User channel;

    @Temporal(TemporalType.DATE)
    @Column(name = "FollowDate")
    private Date followDate = new Date();

    // Custom Getters and Setters just in case Lombok is not working in this specific env
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getFollower() { return follower; }
    public void setFollower(User follower) { this.follower = follower; }
    public User getChannel() { return channel; }
    public void setChannel(User channel) { this.channel = channel; }
    public Date getFollowDate() { return followDate; }
    public void setFollowDate(Date followDate) { this.followDate = followDate; }
}
