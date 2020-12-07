# ohtu-miniprojekti2020
![Github Actions](https://github.com/lossitomatossi/ohtu-miniprojekti2020/workflows/Java%20CI%20with%20Gradle/badge.svg)
[![codecov](https://codecov.io/gh/lossitomatossi/ohtu-miniprojekti2020/branch/main/graph/badge.svg?token=IDJ0K0GQ94)](https://codecov.io/gh/lossitomatossi/ohtu-miniprojekti2020)

[Projektin backlog](https://github.com/lossitomatossi/ohtu-miniprojekti2020/projects/1)

[Definition of done](documentation/dod.md)

[Hyväksymiskriteerit](https://github.com/lossitomatossi/ohtu-miniprojekti2020/tree/main/src/test/resources/lukuvinkkikirjasto)

[Burndown chart](https://docs.google.com/spreadsheets/d/e/2PACX-1vTmBDwEEIEIljKgVgncTifNyLlCUqedPLnNmPdHPP6vrmpO1Sr8TnvkzvKwMOQGXnKHZeJUL-o2w2K_/pubchart?oid=1130428625&format=image)

[Google sheets](https://docs.google.com/spreadsheets/d/1sGVq349mKmRQ_noFouEtAV1N4oND8W4fjCAEKfueNR4/edit?usp=sharing)

### Testit
Komennolla `gradlew test`.

### Jacoco-raportin luominen
Komennolla `gradlew test jacocoTestReport`, jonka tulokset löytyvät avaamalla `build/reports/jacoco/test/html/index.html` selaimeen.

### Checkstyle-testien ajaminen
Komennolla `gradlew checkstyleMain`, jonka tulokset löytyvät avaamalla `build/reports/checkstyle/main.html` selaimeen.

### JAR-tiedoston luominen
Komennolla `gradlew shadowJar`. Luotu JAR löytyy kansiosta `build\libs\lukuvinkkikirjasto-all.jar`.


