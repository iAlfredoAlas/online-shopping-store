package com.kodigo.shopping.online.store.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET user_status = false WHERE id_user=?")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_user")
    @Getter
    @Setter
    private Long idUser;

    @Basic(optional = false)
    @Column(name = "user_name")
    @Getter
    @Setter
    private String userName;

    @Basic(optional = false)
    @Column(name = "password")
    @Getter
    @Setter
    private String password;

    @Basic(optional = false)
    @Column(name = "user_mail")
    @Getter
    @Setter
    private String userMail;

    @Basic(optional = false)
    @Column(name = "user_status")
    @Getter
    @Setter
    private Boolean isUserActive = Boolean.TRUE;

}
