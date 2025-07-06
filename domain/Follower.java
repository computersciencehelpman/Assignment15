package com.coderscampus.Assignment15.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(Follower.class)
@Table(name = "followers")
public class Follower {

    @Id
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @Id
    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false)
    private User followee;

    // Getters and Setters

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowee() {
        return followee;
    }

    public void setFollowee(User followee) {
        this.followee = followee;
    }

    @Override
    public String toString() {
        return "Follower [follower=" + follower.getUsername() + ", followee=" + followee.getUsername() + "]";
    }
}
