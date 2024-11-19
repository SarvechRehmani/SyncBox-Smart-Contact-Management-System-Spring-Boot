package com.syncbox.models.entities;

import com.syncbox.models.entities.Contact;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "social-links")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SocialLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String link;
    private String title;

    @ManyToOne
    private Contact contact;

}
