# subject-declaration
Subject Declaration Project

**System deklaracji przedmiotów na studiach magisterskich**

Program umożliwia wybór przedmiotów na wybranym przez studenta semestrze studiów magisterskich, zgodnie z planem studiów.

**Główne funkcje systemu**

- Rejestracja konta studenta
- Autoryzacja logowania (konto powinno być aktywowane)
- Dodawanie przedmiotów do koszyka
- Usuwanie przedmiotów z koszyka
- Wyświetlanie zawartości koszyka
- Wyświetlanie tygodniowego harmonogramu zajęć
- Walidacja czy przedmioty dodane do koszyka spełniają wytyczne dla poszczególnego semestru

**Technologie**

- Spring (Web, Security), Hibernate

**Podstawowe byty:**

**Użytkownik (student)**

imię

nazwisko

płeć

kierunek

semestr

lista przedmiotów

pesel

email

hasło

lista przedmiotów

**Prowadzący zajęcia**

Sygnatura

Imię i nazwisko

**Przedmiot**

Sygnatura

Nazwa

Liczba pkt ECTS

**Zajęcia**

Forma (wykład/ćwiczenia)

Dzień tygodnia

Godzina rozpoczęcia zajęć

Godzina zakończenia zajęć

Miejsce

**Funkcjonalności**

_Rejestracja studenta_

- Pobranie danych studenta przy pomocy formularza
- Walidacja czy dane dla poszczególnych pól są poprawne (nr PESEL, format adresu email, liczba znaków poszczególnych pól, wystarczająco silne hasło, zaznaczenie płci)
- Obowiązek potwierdzenia zapoznania się z zasadami wyboru przedmiotów oraz opisem kierunków
- Wysłanie na podanego maila kodu weryfikacyjnego celem potwierdzenia rejestracji

_Dodawanie przedmiotów do koszyka_

- Wyszukiwanie przedmiotów w trzech kategoriach (przedmioty kierunkowe, związane z kierunkiem oraz wszystkie) wg dowolnego klucza (prowadzący, nazwa, sygnatura, termin)
- Uniemożliwienie dodania do koszyka przedmiotu, który koliduje terminem zajęć z innym przedmiotem wcześniej dodanym do koszyka oraz wyświetlenie informacji o kolizji
- Uniemożliwienie dodania do koszyka przedmiotu o tej samej sygnaturze i typie zajęć (wykład lub ćwiczenia), co inny przedmiot dodany wcześniej do koszyka
- Po zatwierdzeniu przez studenta deklaracji brak możliwości dodawania przedmiotów do koszyka

_Usuwanie przedmiotów z koszyka_

- Usuwanie z koszyka pojedynczych przedmiotów
- Zablokowanie możliwości usunięcia przedmiotów obowiązkowych, które automatycznie pojawiły się w koszyku w zależności od wybranego przez studenta semestru
- Po zatwierdzeniu przez studenta deklaracji brak możliwości usuwania przedmiotów z koszyka

_Wyświetlanie planu zajęć na podstawie przedmiotów znajdujących się w koszyku_

- Wyświetlanie w tabeli zawierającej dni tygodnia od poniedziałku do piątku planu zajęć na poszczególne dni, zawierającego nazwę przedmiotu, typ zajęć oraz miejsce

_Przeniesienie zawartości koszyka do deklaracji_

- Walidacja zawartości koszyka przedmiotów pod kątem:
  - dodania przedmiotów obowiązkowych dla danego semestru
  - liczby punktów ECTS wszystkich przedmiotów znajdujących się w koszyku (30-70 pkt ECTS)
  - liczby pkt ECTS reprezentowanych przez przedmioty kierunkowe oraz związane z kierunkiem (różne wymogu dla poszczególnych semestrów
  - dodania zarówno wykładu jak i ćwiczeń jeżeli dla danego przedmiotu jest dostępny wykład i ćwiczenia
- W przypadku, gdy walidacja nie przebiegła pomyślnie zwrócenie informacji, które przedmioty student powinien usunąć/dodać do koszyka
- W przypadku, gdy walidacja przebiegła pomyślnie zablokowanie możliwości dodawania bądź usuwania przedmiotów z koszyka

**Zasady wyboru przedmiotów niezależne od semestru, na którym znajduje się student:**

- student na każdym semestrze deklaruje zajęcia, na które będzie uczęszczał – dla większości przedmiotów ma on do wyboru różnych wykładowców prowadzących zajęcia w różnych terminach,
- student powinien zadeklarować przedmioty o łącznej sumie punktów ECTS nie mniejszej niż 30 i nie większej niż 70,
- dostępne przedmioty mogą składać się z samych ćwiczeń, samych wykładów bądź z ćwiczeń i wykładów – w przypadku tego ostatniego wariantu student powinien dodać do koszyka zarówno wykład jak i ćwiczenia,
- student nie może dodać do koszyka dwóch takich samych przedmiotów tego samego typu (wykład lub ćwiczenia) prowadzonych przez różnych prowadzących – student może jednak zapisać się na wykład z danego przedmiotu do jednego prowadzącego, natomiast na ćwiczenia z tego samego przedmiotu do innego prowadzącego,
- student nie może zapisać się na zajęcia, które kolidują terminem (ten sam dzień tygodnia oraz godzina rozpoczęcia) z innym zajęciami,
- na każdym semestrze student ma jedne zajęcia obowiązkowe (język obcy lub seminarium magisterskie), których nie może usunąć z koszyka.

Semestr I:

- student powinien zrealizować przedmioty obowiązkowe niezależnie od wybranego kierunku: Historia myśli ekonomicznej oraz Prawo gospodarcze,
- student powinien zrealizować zajęcia z języka obcego (4,5 pkt ECTS),
- student powinien wybrać przedmioty kierunkowe o wartości przynajmniej 16,5 pkt ECTS.

Semestr II:

- student powinien zrealizować zajęcia z języka obcego (6,0 pkt ECTS),
- student powinien wybrać przedmioty kierunkowe o wartości przynajmniej 12,0 pkt ECTS,
- student powinien wybrać przedmioty związane z kierunkiem o wartości przynajmniej 12,0 pkt ECTS.

Semestr III:

- student powinien zrealizować seminarium magisterskie (8,0 pkt ECTS),
- student powinien wybrać przedmioty kierunkowe o wartości przynajmniej 12,0 pkt ECTS,
- student powinien wybrać przedmioty związane z kierunkiem o wartości przynajmniej 9,0 pkt ECTS.

Semestr IV:

- student powinien zrealizować seminarium magisterskie (12,0 pkt ECTS),
- student powinien wybrać przedmioty związane z kierunkiem o wartości przynajmniej 9,0 pkt ECTS.
