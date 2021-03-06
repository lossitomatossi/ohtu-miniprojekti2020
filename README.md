# ohtu-miniprojekti2020
![Github Actions](https://github.com/lossitomatossi/ohtu-miniprojekti2020/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![codecov](https://codecov.io/gh/lossitomatossi/ohtu-miniprojekti2020/branch/main/graph/badge.svg?token=IDJ0K0GQ94)](https://codecov.io/gh/lossitomatossi/ohtu-miniprojekti2020)

[Projektin backlog](https://github.com/lossitomatossi/ohtu-miniprojekti2020/projects/1)

[Definition of done](documentation/dod.md)

[Hyväksymiskriteerit](https://github.com/lossitomatossi/ohtu-miniprojekti2020/tree/main/src/test/resources/lukuvinkkikirjasto)

[Burndown chart](https://docs.google.com/spreadsheets/d/e/2PACX-1vTmBDwEEIEIljKgVgncTifNyLlCUqedPLnNmPdHPP6vrmpO1Sr8TnvkzvKwMOQGXnKHZeJUL-o2w2K_/pubchart?oid=1130428625&format=image)

[Google sheets](https://docs.google.com/spreadsheets/d/1sGVq349mKmRQ_noFouEtAV1N4oND8W4fjCAEKfueNR4/edit?usp=sharing)

[Loppuraportti](https://docs.google.com/document/d/e/2PACX-1vTvUWiSQva0L9YKNV4G6JHscuy2s99OFMVhazgCQBUbb3_Zu_6mSNgdxHZpzsD228sc55bxiMhlvu26/pub)

## Kaikki gradlen komennot alkavat joko `gradlew` tai `./gradle`

### Testit
Komennolla `gradlew test`.

### Jacoco-raportin luominen
Komennolla `gradlew test jacocoTestReport`, jonka tulokset löytyvät avaamalla `build/reports/jacoco/test/html/index.html` selaimeen.

### Checkstyle-testien ajaminen
Komennolla `gradlew checkstyleMain`, jonka tulokset löytyvät avaamalla `build/reports/checkstyle/main.html` selaimeen.

### JAR-tiedoston luominen
Komennolla `gradlew shadowJar`. Luotu JAR löytyy kansiosta `build/libs/lukuvinkkikirjasto-all.jar`.

### Jar tiedoston ajaminen
Jar-tiedoston luomisen jälkeen ohjelman voi ajaa komennolla ``java -jar build/libs/lukuvinkkikirjasto-all.jar``.
Antamalla optio `demo` komentoriviargumenttina, alustetaan tietokanta hakemistosta `data/demo` löytyvillä lukuvinkeillä.

### Ohjelman käynnistäminen Gradlella
Käynnistys tapahtuu komennolla `gradlew run`
