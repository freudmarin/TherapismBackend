package com.marindulja.therapismbackend.entities

import com.marindulja.therapismbackend.models.RoleType
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    private val password: String,

    @Enumerated(EnumType.STRING)
    val role: RoleType
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> =
        listOf(GrantedAuthority { "ROLE_$role" })

    override fun getPassword() = password
    override fun getUsername() = email
    override fun isAccountNonExpired() = true
    override fun isAccountNonLocked() = true
    override fun isCredentialsNonExpired() = true
    override fun isEnabled() = true
}
