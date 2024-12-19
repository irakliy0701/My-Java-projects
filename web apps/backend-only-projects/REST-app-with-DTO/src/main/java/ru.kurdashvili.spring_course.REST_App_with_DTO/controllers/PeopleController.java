package ru.kurdashvili.spring_course.REST_App_with_DTO.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kurdashvili.spring_course.REST_App_with_DTO.dto.PersonDTO;
import ru.kurdashvili.spring_course.REST_App_with_DTO.models.Person;
import ru.kurdashvili.spring_course.REST_App_with_DTO.services.PeopleService;
import ru.kurdashvili.spring_course.REST_App_with_DTO.util.PersonErrorResponse;
import ru.kurdashvili.spring_course.REST_App_with_DTO.util.PersonNotCreatedException;
import ru.kurdashvili.spring_course.REST_App_with_DTO.util.PersonNotFoundException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<PersonDTO> getPeople(){
        return peopleService.findAll().stream().map(this::convertToPersonDTO)
                .collect(Collectors.toList()); // Jackson атвоматич. конвертирует эти
                // объекты в JSON
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id){
        // Научимся обрабатывать сценарий когда передается id несущ человека
        return convertToPersonDTO(peopleService.findOne(id));// Jackson конвертирует в JSON

        // Если метод findOne() не найдет челоевка по id, то выбросит наше кастомное
        // искл (так прописали логику), мы можем поймать это искл и человку, который
        // отправил запрос на наш сервис, отправить тоже спец JSON с сообщением об ошибке
        // Чтобы это делать в Spring есть спец аннотация - @ExceptionHandler - этой
        // аннотацией помечаем тот метод, который в себя ловит искл и который возвр необход
        // JSON
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotFoundException e){ // в аргументах указываем класс искл,
                    // которое будет ловаить этот метод.
        PersonErrorResponse response = new PersonErrorResponse(
                "Person with this id was not found",
                System.currentTimeMillis()
        );

        // В HTTP ответе будет тело (response - наш объект ошибки, который будет
        // преобразован в JSON с помощью Jackson) и статус в заголовке (404)
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        // NOT_FOUND - 404 cтатус
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO, // теперь принимаем DTO от клиента
                                             BindingResult bindingResult) { // возвращаем объект, который просто
                // представляет из себя HTTP ответ (можем вернуть любой объект)
        if(bindingResult.hasErrors()){
            // Хотим ошибки валидации, который лежат в bindingResult объединить
            // в одну большую строку и отправить клиенту
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();

            for (FieldError error : errors){
                errorMsg.append(error.getField()).
                        append(" - ").append(error.getDefaultMessage()).
                        append("; ");
            }
            System.out.println(errorMsg);

            throw new PersonNotCreatedException(errorMsg.toString());
        }

        // Теперь перед тем как воспользоваться методом из сервиса и сохранить чел-ка в бд,
        // должны сконвертировать PersonDTO -> Person
        peopleService.save(convertToPerson(personDTO));

        // отправляем HTTP ответ с пустым телом и со статусом 200
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Person convertToPerson(PersonDTO personDTO) {
//        Person person = new Person();

        // Копируем данные из DTO в модель:
//        person.setName(personDTO.getName());
//        person.setAge(personDTO.getAge());
//        person.setEmail(personDTO.getEmail());
        // Это недостаток нашего кода - копировать данные из DTO в модель вручную плохо,
        // тк если будет много полей в DTO - будет много лишнего кода

        // В таком случае есть сущ решение - зависимость modelMapper (надо добавить в maven)
//        ModelMapper modelMapper = new ModelMapper(); // здесь вручную создаем объект класса
        // ModelMapper, можем это делигировать Spring, создав бин в корневом класce -> сможем
        // внедрять во все контроллеры и использовать один и тот же объект всегда (тк бин
        // будет Singleton), см. корневой класс и конструктор

        return modelMapper.map(personDTO, Person.class); // говорим, что хотим смапить из объекта
        // personDTO в объект класса Person, и ModelMapper он найдет все поля, которые совпадают
        // по названию и положит в объект класса Person все поля из DTO
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotCreatedException e){ // в аргументах указываем класс искл,
        // которое будет ловаить этот метод.
        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        // В HTTP ответе будет тело (response - наш объект ошибки, который будет
        // преобразован в JSON с помощью Jackson) и статус в заголовке (404)
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    public PersonDTO convertToPersonDTO(Person person){
        return modelMapper.map(person, PersonDTO.class);
    }
}
