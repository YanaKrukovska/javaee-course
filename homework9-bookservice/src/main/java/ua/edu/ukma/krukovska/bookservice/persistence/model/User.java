package ua.edu.ukma.krukovska.bookservice.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    @Email(message = "Email can't be empty")
    private String email;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Username can't be empty")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "Login can contain only latin letters and digits")
    private String username;

    private String password;

    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String rawPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "favourite_books",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "book_id", referencedColumnName = "id"))
    private Set<Book> favouriteBooks;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public void addFavouriteBook(Book book) {
        this.getFavouriteBooks().add(book);
        book.getLikedByUsers().add(this);
    }

    public void removeFavouriteBook(Book book) {
        this.getFavouriteBooks().remove(book);
        book.getLikedByUsers().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}