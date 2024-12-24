## Съдържание

- [System Under Test (SUT)](#-какво-е-system-under-test-sut-)
- [Структура на Unit тест](#-структура-на-unit-тест)
- [Unit vs Integration тестове](#-unit-vs-integration)
- [Структура на Maven проект](#-структурата-на-eдин-maven-проект)
- [Maven конфигурация и структура на `pom.xml`](#-maven-конфигурация)
- [JUnit 5](#-junit-5)
- [Изпълнение на JUnit тест](#-изпълнение-на-junit-тест)

## * Какво е System Under Test (SUT) ?
 - Или още „какво точно тестваме“ - всеки тест трябва да има ясен SUT. Това помага да категоризираме тестовете (клас, метод, компонент, система)
 - Например SUT-a на `UserTest.java` ще бъде класа `User.java`.


## * Структура на Unit тест 
###  **`The 3A`** структура:

#### 1. **`Arrange`**: Подготвяне на тестовите данни
#### 2. **`Act`**: Извършване на действието/процеса, който ще се тества
#### 3. **`Assert`**: Проверка на резултата от процеса


## * Unit vs Integration

| Unit     | Integration       |
|--------------------|----------------|
| Тест за един клас в изолация | Тестване на взаимодействието между много класове |
| Проверка на алгоритми, помощни функции или методи на класове | Проверка на алгоритми, помощни функции или методи на класове |
| Използва mock обекти, stub-ове или фалшиви данни | Уверяване, че комуникацията между услуги, API-та или слоеве е правилна |
| Бързи          | Бавни          |


## * Структурата на eдин Maven проект

#### 1. **`pom.xml`**
   - Основният конфигурационен файл за Maven. Съдържа метаинформация за проекта (име, версия и т.н.), зависимости, плъгини и настройки за изграждане.

#### 2. **`src/main/java`**
   - Директорията, която съдържа Java сорс кода на приложението.

#### 3. **`src/main/resources`**
   - Съдържа ресурси като конфигурационни файлове и други
   - Тези ресурси се копират в изходната директория (`target/classes`) по време на изграждането.
  
#### 4. **`src/test/java`**
   - Директория за класове, които съдържат тестовете за приложението (JUnit тестове).
   - Например `UserTest.java` може да съдържа тестове за класa `User.java`.

#### 5. **`src/test/resources`**
   - Съдържа ресурси, необходими при тестването, като тестови данни или конфигурационни файлове за тестове.

#### 6. **`target/`**
   - Създава се автоматично от Maven по време на т.нар build процес.
   - Съдържа компилирани `.class` файлове.
   - Например, `target/classes/` съдържа компилирани класове от `src/main/java`.

## * Maven Конфигурация

Конфигурационният файл `pom.xml` с коментари, обясняващи структурата му:

```xml
<!-- Root елемент, който определя този файл като Maven POM -->
<project xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    
    <!-- Версия на модела на POM -->
    <modelVersion>4.0.0</modelVersion>

    <!-- Group ID: Уникален идентификатор за проекта -->
    <groupId>pu.fmi.automation.test</groupId>

    <!-- Artifact ID: Името на артефакта/проекта (например името на JAR файла) -->
    <artifactId>IntroJUnit</artifactId>

    <!-- Version: Версия на артефакта/проекта -->
    <version>0.0.1-SNAPSHOT</version>

    <!-- Name: Име на проекта (По избор) -->
    <name>IntroJUnit</name>

    <properties>
        <!-- Версия на Java при компилация -->
        <maven.compiler.source>11</maven.compiler.source>
        <!-- Версия на Java при изпълнение -->
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <!-- Списък на зависимостите/библиотеките, които проектът изисква -->
    <dependencies>
        <!-- JUnit 5: Основната библиотека за тестване -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.11.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.assertj</groupId>
            <artifactId>assertj-core</artifactId>
            <version>3.26.3</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.hamcrest</groupId>
            <artifactId>hamcrest</artifactId>
            <version>3.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

## * JUnit 5

JUnit 5 е най-новата версия на библиотека за писане и изпълнение на тестове за Java проекти. 

### Основни Анотации в JUnit 5

#### 1. `@Test`
Анотацията `@Test` се използва за означаване на методите, които са тестови случаи. Тестовите методи трябва да бъдат публични, да връщат `void` и да не приемат аргументи.

```java
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import account.User;

public class UserTest {

	@Test
	void testCreateUser() {
		User user = new User("Ivan", "Petrov", "ivan.petrov@email.com");
		assertEquals("Ivan", user.getFirstName());
		assertEquals("Petrov", user.getLastName());
	}
}
```

#### 2. `@DisplayName` 
Анотацията @DisplayName позволява да зададете по-разбираемо име на тестовия метод, което да бъде показано в резултатите от тестовете.

...

## * Изпълнение на JUnit тест
### Eclipse IDE: 

![3  How to run test - 1](https://github.com/user-attachments/assets/3f1dcce7-4d8f-41e7-8d4b-074e3695015c)

![4  How to run test 2](https://github.com/user-attachments/assets/92ccbe7d-04d1-4226-8cea-7c57521b0e39)

### IntelliJ IDEA: 

![4  How to run test Intelij](https://github.com/user-attachments/assets/2d19bcb5-9a9a-4495-aa35-a2e210770409)




