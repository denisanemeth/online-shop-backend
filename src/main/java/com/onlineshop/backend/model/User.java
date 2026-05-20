package com.onlineshop.backend.model;

import com.onlineshop.backend.enums.Role;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
//adnotări (annotations) și înlocuiesc cod pe care altfel l-ai scrie manual.
@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Doar pentru SELLER:
    // true = aprobat de admin, false = în așteptare sau dezactivat
    private Boolean approved;

    // Seller poate fi dezactivat de admin (contul rămâne în DB dar nu se poate loga)
    private Boolean active;
}
