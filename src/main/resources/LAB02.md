# LABORATORIUM 02

Zadania proszę realizować zgodnie z własnym tempem. Zadania mają pomóc w przygotowaniu do Egzaminu oraz zrozumieniu Frameworka Spring. Zadanie proszę przesłać na własne repozytorium.
Termin upływa po 14 dniach od rozpoczęciu laboratorium.

## ZADANIE 1. Sieciowe API do operacji typu CRUD na klientach

### Potrzeba biznesowa

Jako użytkownik systemu, chcę mieć możliwość zarządzania użytkownikami
serwisu FitnessTracker:

- móc ich wyszukiwać, pobierać
- móc wprowadzać nowych użytkowników do systemu
- móc usuwać użytkowników z systemu
- móc aktualizować użytkowników

### Wymagania funkcjonalne

Stworzone API powinno pozwalać na:

- [X] wylistowanie podstawowych informacji o wszystkich użytkownikach zapisanych w systemie (tylko ID oraz nazwa
  użytkownika)
  * ToDo :
    [x] Rout : "/simple"
    [x] stworzenie controllera, repo i service "getAllUserBasicDetails"
    [x] stworznie DtoBasic (tylko id, imie)
    [x] zmapoowanie na DtoBasic
    [x] testy integracyjne
  * 
- [X] pobranie szczegółów dotyczących wybranego użytkownika (dowolny parametr: ID/imię & nazwisko/datę urodzenia/ e-mail)
  * ToDo:
    [x] Rout : "/{id}"
    [x] stworzenie controllera z mapowaniem do userDto
    [x] stworzenie metody w serwisie do odzyskiwania usera po id usera 
    [x] testy integracyjne

- [X] utworzenie nowego użytkownika
  * ToDo :
    [x] Rout : ""
    [x] stworzenie serwisu do tworzenia nowego uzytkownika - parametr user
    [x] stworzeine endpoint typu create w controllerze /user
    [x] testy postman
    [x] testy integracyjne
- [X] usunięcie użytkownika (konkretny, np. konkretny ID danego uzytkownika)
    [x] Rout : "/{userId}"
    [x] stworzenie endpointa typu delete w kontrolerze /userdelete
    [x] stworzenie serwisu do kasacji usera w bazie
    [x] testy api postman
    [x] testy integracyjne
- [X] wyszukiwanie użytkowników po e-mailu, bez rozróżniania wielkości liter, wyszukujące po fragmencie nazwy (zwracane
  tylko ID oraz e-mail użytkowników)
    [x] Rout : "/email"
    [x] stworzyc controller i endpoint (/findUserByEmail/{email}) parametr string email
    [x] stworzenie userBasicEmailDto w celu zwrocenia id oraz email szukanego uzytkownika
    [x] stworzenie metody dla interface
    [x] stworzyc service w ktorym przetworzymhy string email to lower i nastapi wyszukiwanie i zwrocenie userBasicEmailDto
    [x] testy integracyjne
- [X] wyszukiwanie użytkowników po wieku starszym niż zdefiniowany
    [x] stworzyc controller (/userolder)
    [x] stworzenie serwisu do extrakcji danych z bazy
    [x] testy integracyjne
- [X] aktualizowanie użytkowników w systemie (dowolnie wybrany atrybut)
   [x] testy integracyjne

### Wymagania techniczne

- [x] API sieciowe powinno wykorzystywać protokół HTTP oraz format JSON do transferu danych
- [x] w repozytoriach rozwiązanie może wykorzystywać metody dostarczane przez interfejs JpaRepository oraz metody
  domyślne, pobierające dane za pomocą `findAll()` oraz przetwarzające je za pomocą strumieni (`Stream`). Przykład
  znaleźć można w `UserRepository`
- [x] rozwiązanie powinno spełniać zasady SOLID
- [x] testy integracyjne powinny poprawnie się wykonywać UserApiIntegrationTest
- [x] (OPCJONALNIE) rozwiązanie powinno być pokryte testami jednostkowymi (>80%)
- [x] rozwiązanie powinno być odpowiednio zhermetyzowane (nie udostępniać funkcjonalności pozostałym pakietom programu)
- [inprogress] kod powinien być odpowiednio udokumentowany za pomocą JavaDoc
- [ ] do kodu powinna zostać dołączona wyeksportowana kolekcja zapytań z programu Postman, pozwalająca przetestować
  stworzone API
- [x] rozwiązanie powinno wykorzystywać rekordy (Java 16+) do definicji obiektów transferu danych (DTO)


## ZADANIE 2: Zabezpieczenie API (Opcjonalnie)

### Potrzeba biznesowa:

Jako administrator systemu, chcę zabezpieczyć API, z którego mogą korzystać różne systemy

- API potrzebne do zbierania metryk powinno być dostępne dla narzędzi monitorujących
- API, które nie modyfikuje danych, powinno być dostępne dla znanych użytkowników
- API, które może modyfikować dane, powinno być zabezpieczone przed nieuprawnionym dostępem

### Wymagania funkcjonalne

Zabezpieczenia, powinny zagwarantować:

- [ ] API Spring Boot Actuator są dostępne bez zabezpieczenia, tj. nie wymagają uwierzytelnienia ani dodatkowych
  uprawnień
- [ ] API dla HTTP metody GET jest dostępne dla wszystkich uwierzytelnionych użytkowników
- [ ] API dla pozostałych metod jest dostępne dla użytkowników z rolą "ADMIN"
- [ ] lista użytkowników i ich ról jest statyczna (nie zmienia się)

### Wymagania techniczne

- [ ] zabezpieczenie powinno wykorzystywać bibliotekę Spring Security
- [ ] użytkownik może uwierzytelnić się jedynie za pomocą Basic Auth
- [ ] rozwiązanie powinno spełniać zasady SOLID
- [ ] rozwiązanie powinno być odpowiednio zhermetyzowane (nie udostępniać funkcjonalności pozostałym pakietom programu)
- [ ] kod powinien być odpowiednio udokumentowany za pomocą JavaDoc
- [ ] do kodu powinna zostać dołączona wyeksportowana kolekcja zapytań z programu Postman, pozwalająca przetestować
  rozwiązanie