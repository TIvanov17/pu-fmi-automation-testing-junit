## Съдържание

- [System Under Test (SUT)](#-какво-е-system-under-test-sut-)
- [Структура на Unit тест](#-структура-на-unit-тест)
- [Unit vs Integration тестове](#-unit-vs-integration)
- [Структура на Maven проект](#-структурата-на-eдин-maven-проект)
- [Maven конфигурация и структура на `pom.xml`](#-maven-конфигурация)
- [JUnit 5](#-junit-5)
  - **[Анотации](#junit-5-annotations)**
  	- **[1. `@Test`](#junit-5-test)**
  	- **[2. `@DisplayName`](#junit-5-display-name)**
  	- **[3. `@TestInstance`](#junit-5-test-instance)**
  	- **[4. `@BeforeAll`](#junit-5-before-all)**
  	- **[5. `@BeforeEach`](#junit-5-before-each)**
  	- **[6. `@AfterEach`](#junit-5-after-each)**
  	- **[7. `@AfterAll`](#junit-5-after-all)**
  	- **[8. `@ParameterizedTest`](#junit-5-param-test)**
     - **[9. `@Nested`](#junit-5-nested)**
  - **[AssertJ & Hamcrest](#junit-5-asserts)**
- [Какво са Test Doubles](#test-doubles)
  - [1. Dummy](#test-doubles-dummy)
  - [2. Mock](#test-doubles-mock)
  - [3. Stub](#test-doubles-stub)
  - [4. Spy](#test-doubles-spy)
  - [5. Fake](#test-doubles-fake)
- [Създаване и използване на Test Doubles с Mockito](#mockito)
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
| Проверка на алгоритми, помощни функции или методи на класове | Уверяване, че комуникацията между услуги, API-та или слоеве е правилна  |
| Използва mock обекти, stub-ове или фалшиви данни | Може да използва реални или частично симулирани данни и процеси |
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

<a id="junit-5-annotations"></a>
### Основни Анотации

<a id="junit-5-test"></a>
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
<a id="junit-5-display-name"></a>
#### 2. `@DisplayName` 
Анотацията @DisplayName позволява да зададете по-разбираемо име на тестовия метод, което да бъде показано в резултатите от тестовете.

<a id="junit-5-test-instance"></a>
#### 3. `@TestInstance` и жизнения цикъл на тест класа

##### Сравнение между стойностите, които може да приема анотацията @TestInstance

| **Характеристика**                   | **`Lifecycle.PER_METHOD` (по подразбиране)**                             | **`Lifecycle.PER_CLASS`**                                        |
|--------------------------------------|---------------------------------------------------------------|-------------------------------------------------------|
| **Създаване на инстанции**           | Създаване на нова инстанция на тестовия клас за всеки тест метод        | Създаване на една инстанция за всички тест методи              |
| **Изолация между тестовете**         | Пълна изолация – всяка инстанция е независима                 | Полетата и състоянието се споделят между тестовете   |
| **`@BeforeAll` и `@AfterAll`**       | Трябва да бъдат `static`         | Могат да бъдат нестатични |
| **Подходящо за**                     | Тестове, които трябва да бъдат изолирани и независими един от друг.      | Тестове, в които искате да има споделяне на състояние между различните тестове |
| **Използване на `@TestInstance`**    | Не е необходимо (по подразбиране)                     |Изрично добавяне на  `@TestInstance(Lifecycle.PER_CLASS)`. |

<a id="junit-5-before-all"></a>
#### 4. `@BeforeAll`:
* изпълнява се преди всички тестове в класа 
* инициализация на глобални ресурси, които са споделени между тестовете

<a id="junit-5-before-each"></a>
#### 5. `@BeforeEach`:
* изпълнява се преди всеки тест

<a id="junit-5-after-each"></a>
#### 6. `@AfterEach`: 
* изпълнява се след всеки тест
* използва се за освобождаване на ресурси

<a id="junit-5-after-all"></a>
#### 7. `@AfterAll`:
* изпълнява след всички тестове в класа

<a id="junit-5-param-test"></a>
#### 8. `@ParameterizedTest`:
Тази анотацията се използва за изпълнение на тест методи с различни входни данни. 
Това позволява един и същ тест да бъде изпълнен многократно с различни стойности, като по този начин може да се провери
поведението на SUT-a при различни сценаии

Начини за предаване на параметри на теста:

##### 8.1 `@ValueSource`:
- тестът ще бъде изпълнен (3 пъти) за всяка стойност
- подаване на масив от различен тип (string, int, double)

```java
@ParameterizedTest
@ValueSource(strings = { "0.95", "1.22", "5.51" })
void testAddExchangeRateFromParams(String exchangeRate) {
	storage.addExchangeRate(
			"TEST_FROM", 
			"TEST_TO", 
			new BigDecimal(exchangeRate)
	);
		
	BigDecimal actualExchangeRate = storage.getExchangeRate("TEST_FROM", "TEST_TO");
	
	assertEquals(new BigDecimal(exchangeRate), actualExchangeRate);
}
```

##### 8.2 `@MethodSource`:
- подаване на данните от static метод, като връща Stream, колекция или масив
- тест метода трябва да приеме същите параметри като типовете, каквито метод-източника връща

```java
@ParameterizedTest
@MethodSource("getParam")
void testAddExchangeRateFromMethod(String param) {
	storage.addExchangeRate(
			"TEST_FROM", 
			"TEST_TO", 
			new BigDecimal(param)
	);
		
	BigDecimal actualExchangeRate = storage.getExchangeRate("TEST_FROM", "TEST_TO");
	
	assertEquals(new BigDecimal(param), actualExchangeRate);
}

static Stream<String> getParam(){
	return Stream.of("0.95", "1.22", "5.51");
}
```

##### 8.3 `@CsvSource`:
Анотацията позволява да подавате тестови данни директно в анотацията в CSV (Comma-Separated Values) формат. 
Използва се за тестове с множество аргументи или различни сценарии.

```java
@ParameterizedTest
@CsvSource({
    "TEST_FROM,        	TEST_TO,	   1.1",
    "CURRENCY_CODE_1,   CURRENCY_CODE_2,   0.2",
    "TEST_TO,         	TEST_FROM, 	   0.9",
    "CURRENCY_CODE_2,   CURRENCY_CODE_1,   2.2",
})
void testAddExchangeRateFromParams(String currencyCodeFrom, String currencyCodeTo, String exchangeRate) {
	storage.addExchangeRate(
			currencyCodeFrom, 
			currencyCodeTo, 
			new BigDecimal(exchangeRate)
	);
	
	BigDecimal actualExchangeRate = storage.getExchangeRate(currencyCodeFrom, currencyCodeTo);
	
	assertEquals(new BigDecimal(exchangeRate), actualExchangeRate);
}
```
<a id="junit-5-nested"></a>
#### 9. `@Nested`
Създаване на вложени тестови класове, организирайки тестовете в йерархия

```java
public class CurrencyFactoryTest {
	
	@Nested
	class CurrencyEUROTest {
	
		@Test 
		void testCreateValidEuro() {
			Currency usd = CurrencyFactory.createEUR();
			assertEquals(usd.getSymbol(), "€");
			assertEquals(usd.getCode(), "EUR");
		}
		
	}
}
```
<a id="junit-5-asserts"></a>
### Assertions

#### AssertJ
- използва method chaining за изграждане на проверките
``` java
// основна структура:
assertThat(actualValue)
  	.assertMethod(value)
  	.anotherAssertMethod(anotherValue)
  	.thirdAssertMethod(thirdValue);`
```

Пример:

```java
@Test
void testSettersOfUserAssertJ() {
	ivan.setFirstName("Ivan");
	ivan.setEmail("ivan.petrov@email.com");
	
	// AssertJ
	Assertions.assertThat(ivan.getFirstName())
				.isNotEmpty()
				.isNotNull()
				.isEqualTo("Ivan");
	
	Assertions.assertThat(ivan.getEmail())
				.isNotBlank()
				.isNotEqualTo("ivan")
				.containsIgnoringCase("@EMAIL.cOm")
				.startsWith("ivan.petrov")
				.endsWith(".com");
}
```

Повече инфо: https://assertj.github.io/doc/

#### Hamcrest
- предоставя matcher-и (обекти, които дефинират условия за съвпадение) за извършване на assert-и в тестовете
``` java 
// основна структура
assertThat(actualValue, matcher(value));
``` 

Пример:

```java
@Test
void testCreateUserHarmcrest() {
	assertThat(testUser.getFirstName(), is("Ivan"));
	assertThat(testUser.getLastName(), equalTo("Petrov"));
}

@Test
void testSetterOfUserHarmcrest() {
	User user = new User();
	user.setEmail("ivan.petrov@email.com");
	
	assertThat(user.getEmail(), startsWith("ivan."));
	assertThat(user.getEmail(), endsWith(".com"));
	assertThat(user.getEmail(), containsString("petrov"));
}
```
Повече инфо: https://hamcrest.org/JavaHamcrest/javadoc/1.3/org/hamcrest/Matchers.html

<a id="test-doubles"></a>
## * Какво са Test Doubles ?
Когато клас не зависи от други класове, неговото тестване е доста ясно, но когато зависи от други класове имаме 2 избора:
1. Да го тестваме заедно с другите класове
2. Може да се опитаме да го изолираме от другите класове

Test Doubles са обекти, използвани при тестване на софтуер, които заместват реални обекти. 
Целта им е да изолират кода, който се тества, от външни зависимости (като бази данни, комуникация с външни системи и др.)
и да осигурят предсказуемо и контролирано поведение по време на тестовете.

### Видове:
<a id="test-doubles-dummy"></a>
#### 1. Dummy
Обект, който не се използва реално, а съществува само, за да се подаде като аргументите на метод.
(Пример: Името на User, което не се използва в тестването на имейла му.)
<a id="test-doubles-mock"></a>
#### 2. Mock 
Обект, който дефинира очаквано поведение и проверява дали то е изпълнено. 
(Пример: Проверка дали определен метод е извикан с определени аргументи.)
<a id="test-doubles-stub"></a>
#### 3. Stub
Обект, който връща предварително зададени стойности, когато бъдат извикани неговите методи (без наистина да се изпълнят).
(Пример: Метод, който винаги връща определено value, вместо да извършва реално действие и да върне value-то.)
<a id="test-doubles-spy"></a>
#### 4. Spy 
Обект подобен на Stub, но освен че връща фиксирани стойности, също така записва какви методи са били извикани и с какви аргументи.
(Пример: Тест проверява дали определен метод е извикан точно веднъж.)
<a id="test-doubles-fake"></a>
#### 5. Fake 
Обект с реална имплементация, но опростена или оптимизирана. За разлика от Stub и Mock, той действително изпълнява някаква логика, но по по-лесен начин.

<a id="mockito"></a>
## * Създаване и използване на Test Doubles с Mockito ?
Mockito е Java библиотека, използвана за създаване на mock/stub/spy обекти при писането на unit тестове.

``` java
// Библиотека:
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
</dependency>
```

* Активиране в тест класа:
```java
@ExtendWith(MockitoExtension.class)
```
или 
``` java 
@BeforeEach
public void init() {
   MockitoAnnotations.openMocks(this);
}
```

#### Начини на създаване на mock обекти:
```java 
// 1.
UserDBStorage userDBStorage = Mockito.mock(UserDBStorage.class);
```
```java
// 2.
@Mock private UserDBStorage userDBStorage;
```

Mock обектите са заместители на реални обекти, които имат предварително зададено поведение. 

Когато Mockito създава mock не инициализира неговите променливи и връщат стойности по подразбиране. (null, 0, false и т.н.)
Това не е реална инстанция на обекта, а е proxy/dummy обект, който симулира поведението на клас по време на тестване.
Те се използват за изолиране на тестваната логика от реалните зависимости.

Ключови характеристики:
- Изцяло симулира обекта.
- Методите му не изпълняват реална логика, освен ако не е зададено поведение с Mockito.when().
- Използва се, когато искате пълно изолиране на тествания обект от неговите зависимости.

#### Начини на създаване на spy обекти:
```java
// 1.
UserDBStorage userDBStorage = Mockito.spy(new UserDBStorage());
```
```java
// 2.
@Spy private UserDBStorage userDBStorage;
```
Spy обектът е частично мокнат обект, който използва реалната имплементация на класа, освен ако не е зададено друго поведение чрез Mockito.when().
С други думи, ако не се зададе поведение на даден метод, той ще извика реалния метод на оригиналния обект.

Ключови характеристики:
- Комбинира реална логика със симулирана.
- Подходящ е, когато искате да тествате част от логиката на обекта, но да замените някои от зависимостите му с мокнати методи.
- За разлика от mock, методите по подразбиране извикват реалната си логика, освен ако не е указано друго.

#### Задаване на поведение:
##### When/Then Концепция - Заместване резултат (поведение) / поставяне на очакване / stub-ване на метод:

 - метод трябва да връща определен резултат:
``` java
when(userDBStorage.getUserById(1)).thenReturn(user);
```
``` java
doReturn(user).when(userDBStorage).getUserById(1);
```
 - хвърляне на грешка:
``` java
when(listMock.add("test").thenThrow(IllegalStateException.class);
```
``` java
doThrow(NullPointerException.class).when(listMock).clear();
```
 - множество извиквания:
``` java
when(userDBStorage.getUserByEmail("someEmail.com"))
	.thenReturn(null) // Първи 1-вото извикване на метода
	.thenReturn(mockedUser); // При 2-рото извикване на метода
```

#### Проверка на извиквания:
- проверка, че даден mock се е използвал (метод е бил извикан)
  ``` java verify(mockedList).size(); ```
- проверка за брой извиквания
  ``` java verify(mockedList, times(1)).size(); ```
- проверка, че не се е извикал
  ``` java verifyNoInteractions(mockedList); == verify(mockedList, times(0)).size(); ```
- проверка за ред на извикване на методите
``` java 
  InOrder inOrder = Mockito.inOrder(mockedList);
  inOrder.verify(mockedList).size();
  inOrder.verify(mockedList).add("a parameter");
  inOrder.verify(mockedList).clear();
```
 - метод никога не се извикал
   ``` java verify(mockedList, never()).clear(); ```
 - няколко пъти
   ``` java verify(mockedList, atMost(10)).clear(); ```
 - проверка на извикване с определен аргумент
   ``` java verify(mockedList).add("test"); ```
 - проверка с всеки аргумент
   ``` java verify(mockedList).add(anyString()); ```

#### `@InjectMocks`
@InjectMocks е анотация, която автоматично създава обект на класа, който искате да тествате и 
инжектира mock обектите (създадени с @Mock или @Spy) в неговите зависимости. 
Това улеснява настройката на unit тестове, при които класът, който се тества, има зависимости, които трябва да бъдат симулирани.

``` java
// класа, който искаме да тестваме и да изолираме от неговите зависимости
public class UserOperations {
  // зависимост 1
  private UserDBStorage userDBStorage;
 ....
}

// създаване на mock или spy на зависимост 1
@Mock private UserDBStorage userDBStorage;

// добавяне на този mock обекта в класа, който искаме да тестваме 
@InjectMocks private UserOperations userOperations;
```

## * Изпълнение на JUnit тест
### Eclipse IDE: 

![3  How to run test - 1](https://github.com/user-attachments/assets/3f1dcce7-4d8f-41e7-8d4b-074e3695015c)

![4  How to run test 2](https://github.com/user-attachments/assets/92ccbe7d-04d1-4226-8cea-7c57521b0e39)

### IntelliJ IDEA: 

![4  How to run test Intelij](https://github.com/user-attachments/assets/2d19bcb5-9a9a-4495-aa35-a2e210770409)

