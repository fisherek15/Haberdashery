## Pasmanteria POS - aplikacja webowa wspomagająca pracę sklepu pasmanteryjnego.

#### Opis ogólny
Aplikacja służy do gromadzenia i przetwarzania danych dotyczących obrotu towarem, generowania raportów sprzedażowych, a także umożliwia ręczne i automatyczne wprowadzanie artykułów do bazy danych, na podstawie skanów faktur.

#### Wykorzystane języki, technologie, biblioteki
Aplikacja została napisana z użyciem jezyka programowania Java, frameworków Spring i Vaadin 7 oraz biblioteki Tesseract (Tess4J) umożliwiającej rozpoznanie tekstu na fakturach. Wykorzystano bazę danych MySQL z zastosowaniem ORM i użyciem Hibernate i JPA.

#### Cel powstania aplikacji
Aplikacja powstała w większości w roku 2017, na potrzeby pracy inżynierskiej. Głownymi założeniami aplikacji było skupienie się na prostocie użytkowania, przy jednoczesnym zachowaniu pełnej funkcjonalności. Efekt został osiągnięty poprzez zastosowanie minimalnej, ale wystarczającej, ilości funkcji oraz łatwy w obsłudze i przejrzysty interfejs graficzny.

#### Kompletność aplikacji
Podstawowe założenia projektowe zostały osiągnięte, a funkcjonalności zaimplementowane, jednak projekt nigdy nie wyszedł z etapu developmentu. Mimo, że projekt działał na środowisku lokalnym, niektóre funkcje wymagały usprawnienia lub przebudowania w celu umożliwienia ich użycia na środowisku docelowym.

#### Zaimplementowane funkcjonalności:
* ręczne wprowadzanie towaru - pojedyncze wprowadzanie informacji o towarze (nazwa, cena, ilość sztuk, jednostka miary itd.)
* automatyczne wprowadzanie towaru znajdującego się na fakturze z wykorzystniem OCR - wskazanie obszarów na zdjęciu z informacjami, które mają zostać przetworzone i automatycznie wczytane (zrealizowane połowicznie, ponieważ wymaga tekst wczytany przez OCR wymaga czasami ręcznej korekty)
* rejestrowanie sprzedanych artykułów
* kategoryzowanie artykułów
* wyszukiwanie artykułów z możliwością filtrowania wg ustawionych parametrów
* przegląd i edycja danych o artykułach
* automatyczne tworzenie listy artykułów do zamówienia, na podstawie danych sprzedażowych
* generowanie raportów statystycznych

#### Wygląd wybranych widoków GUI
* Strona główna aplikacji 

![Main Page View](/images/app-strona_domowa.PNG)

* Automatyczne dodawanie towaru - wczytywanie pliku faktury

![Auto added article 1](/images/app-automatyczne_dodawanie_towaru1.png)

* Automatyczne dodawanie towaru - ustawianie obszarów do przetworzenia przez OCR

![Auto added article 3](/images/app-automatyczne_dodawanie_towaru2.png)

* Automatyczne dodawanie towaru - widok danych wczytanych z faktury za pomocą OCR (widoczne błędy)
 
![Auto added article 3](/images/app-automatyczne_dodawanie_towaru3.png)

* Automatyczne dodawanie towaru - widok poprawionych danych wczytanych z faktury (poprawione za pomocą przycisków funkcyjnych)

![Auto added article 4](/images/app-automatyczne_dodawanie_towaru4.png)

* Ręczne dodawanie towaru - dodanie istniejącego w bazie towaru za pomocą kodu kreskowego lub nr katalogowego

![Manual added article 1](/images/app-reczne_dodawanie_towaru2.PNG)

* Ręczne dodawanie towaru - wprowadzanie towaru nieistniejącego w bazie produktów

![Manual added article 2](/images/app-reczne_dodawanie_towaru4.PNG)

* Ręczne dodawanie towaru - określanie szczegółów artykułu (w tym przypadku dla kategorii 'zamki i akcesoria')

![Manual added article 3](/images/app-reczne_dodawanie_towaru6.PNG)

* Sprzedaż - widok z podsumowaniem artykułów wybranych przez klienta

![Sale view - ticket](/images/app-sprzedaz1.png)

* Asortyment - widok z asortymentem (możliwość filtrowania; pola filtrowania zmieniają się w zależności od wybranej kategorii)

![Warehouse view](/images/app-asortyment1.png)

* Raporty - widok umożliwiający generowanie raportów sprzedażowych

![Raports view](/images/app-raporty1.PNG)

