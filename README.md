# Задача Conditional приложение

## Описание
Как часто бывает в промышленной разработка, мы хотим в локальном или dev окружении использовать несколько упрощенные варианты наших сервисов. 
Поэтому сегодня я написал приложение на Spring boot, в котором в зависимости от параметров, будут вызываться разные сервисы. 

Для работы я подготовил несколько классов:

0. Создал spring boot приложение.

1. Сделал интерфейс, реализации которого я буду вызывать в зависимости от параметров моего приложения:

```$java
public interface SystemProfile {
     String getProfile();
}
``` 

И две реализации:

```$java
public class DevProfile implements SystemProfile {
     @Override
     public String getProfile() {
         return "Current profile is dev";
     }
}
``` 

и

```$java
public class ProductionProfile implements SystemProfile {
     @Override
     public String getProfile() {
         return "Current profile is production";
     }
}
``` 

2. Дальше написал JavaConfig, в котором я объявил бины классов `DevProfile` и `ProductionProfile`:
```$java
    @Bean
    public SystemProfile devProfile() {
        return new DevProfile();
    }

    @Bean
    public SystemProfile prodProfile() {
        return new ProductionProfile();
    }
```
    
3. Дальше сделал application.properties в папке resources, и добавил туда свой пользовательский параметр `netology.profile.dev = true`. Обратите внимание, что сейчас мой параметр имеет значение `true`. Как раз таки в зависимости от значения параметра, я и буду использовать ту или иную реализацию интерфейса `SystemProfile`.

4. Чтобы проверить работоспособность логики, сделал контроллер, который будет возвращать значения из сервиса `SystemProfile`:

```$java
@RestController
@RequestMapping("/")
public class ProfileController {
    private SystemProfile profile;

    public ProfileController(SystemProfile profile) {
        this.profile = profile;
    }

    @GetMapping("profile")
    public String getProfile() {
        return profile.getProfile();
    }
}
```
