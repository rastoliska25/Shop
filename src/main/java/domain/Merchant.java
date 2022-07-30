package domain;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Objects;

public class Merchant {
    @Nullable
    private Integer id;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String adress;

    public Merchant() {}

    public Merchant(@NonNull String name, @NonNull String email, @NonNull String adress) {
        this.name = name;
        this.email = email;
        this.adress = adress;
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getAdress() {
        return adress;
    }

    public void setAdress(@NonNull String adress) {
        this.adress = adress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Merchant merchant = (Merchant) o;
        return Objects.equals(id, merchant.id) && name.equals(merchant.name) && email.equals(merchant.email) && adress.equals(merchant.adress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, adress);
    }
}
