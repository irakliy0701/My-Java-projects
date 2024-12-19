package ru.kurdashvili.spring_course.JWTAuthorizationApp.security;


// В пакете security будут лежать наши классы, которые нужыны для Spring
// Security

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kurdashvili.spring_course.JWTAuthorizationApp.models.Person;

import java.util.Collection;
import java.util.Collections;

// Мы могли бы использовать саму сущность Person и на объекта класса
// Person, который достали из бд, вызывать getPassword() и получать пароль
// для человека и с помошью него производить аутентификацию, но в Spring
// Security так не принято. Принято использовать специальный класс, который
// является классов оберткой над наешй сушеостью. То есть, когда работаем
// с Spring Security мы не напрямую работаем с сущностью Person, а работаем
// с этим классом оберткой PersonDetails, который будет прелоставлять нам детали
// или информацию о пользователе.
// PersonDetails должен реализовавыть интерфейс UserDetails, именно поэтому
// используется класс обертка. Потому что в UserDetails есть стандартные методы
// со стандартными названиями и сигнатурами, которые мы должны реализовать,
// чтобы Spring Security корректно работал. (А в нашей сущности можем иметь любое
// название для геттера пароля, например getPassword123()).
// Поэтому, чтобы данные о пользователе получались каким-то станадартным способом
// и существует интерфейс UserDetails
public class PersonDetails implements UserDetails {
    // Так как PersonDetails - обертка над Person, то надо внедрить
    // поле класса Person
    private final Person person;

    public PersonDetails(Person person) {
        this.person = person;
    }

    // Нужно, чтобы получать данные аутетифицированного пользователя
    public Person getPerson(){
        return person;
    }

    // Этот метод нужен для авторизации
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // в дальнейшем будем получать роли, которые есть
        // у пользователя (из бд) и здесь будем возвращать коллекцию тех прав
        // которые есть у этого конкретного пользователя

        // TODO: return roles (or authorities) of the user
        // (*) За счет этго метода даем понять Spring Security какие роли(аторити) есть
        // у человека
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));

        // (*) Если бы реализовывали авторизацию на основании списка, то здесь создавали бы
        // List<GrantedAuthority>, в этот список бы помещали new SimpleGrantedAuthority() с стороками
        // типа SHOW_ACCOUNT, WITHDRAW_MONEY  и тп
    }

    @Override
    public String getPassword() {
        return person.getPassword();
    }

    @Override
    public String getUsername() {
        return person.getUsername();
    }

    // Далее илдет ряд методов, которые говорят нам о том, что текущая сущность
    // она активна, то что этот акк не заблокан (пока везде ставим true)

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
