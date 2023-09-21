package com.kodigo.shopping.online.store.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "user_email", unique = true)
    @Getter
    @Setter
    private String userEmail;

    @Basic(optional = false)
    @Column(name = "user_status")
    @Getter
    @Setter
    private Boolean isUserActive = Boolean.TRUE;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Rol.class)
    @JoinTable(name = "user_rol", joinColumns = {
            @JoinColumn(name = "idUser", referencedColumnName = "id_user") }, inverseJoinColumns = {
            @JoinColumn(name = "idRol", referencedColumnName = "id_rol") })
    private List<Rol> rolList = new ArrayList<>();

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}
