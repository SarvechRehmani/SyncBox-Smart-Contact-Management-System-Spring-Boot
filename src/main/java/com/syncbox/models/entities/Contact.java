package com.syncbox.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Contact {

    @Id
    private String contactId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    private String cloudinaryPublicId;
    @Column(length = 10000, columnDefinition = "TEXT")
    private String description;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;

    @ManyToOne
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SocialLink> socialLinks = new ArrayList<>();


}
